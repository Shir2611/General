import java.io.*;
import java.util.ArrayList;

public class VMTranslator {

    public static ArrayList<File> VMFiles(File name){
        File[] files = name.listFiles();
        ArrayList<File> VMfiles = new ArrayList<File>();
        for (File f:files){
            if (f.getName().endsWith(".vm")){
                VMfiles.add(f);
            }
        }
        return VMfiles;
    }

    public static void main(String[] args) {
        String outfilename = "";
        File in = new File(args[0]);
        ArrayList<File> VMFiles = new ArrayList<File>();

        if (in.isFile()){
            outfilename = in.getPath().substring(0 , in.getPath().length() - 3)  + ".asm";
            VMFiles.add(in);
        }
        else if (in.isDirectory()){
            VMFiles = VMFiles(in);
            outfilename = in.getAbsolutePath() + "/" + in.getName() + ".asm";
        }
        
        CodeWriter cd = new CodeWriter(outfilename); //new code object
        File fileone = VMFiles.get(0);
        //check whether we need to write the init code or not
        if (VMFiles.size() != 1 || fileone.getName().equals("Sys.vm")) {
            cd.writeInit();
        }
        

        for (File f:VMFiles){
            Parser parser = new Parser(f.getPath()); //new parser object
             //check wether we have more lines to read
            while(parser.hasMoreLines()){
                //if it is an arithmetic command
                if (parser.commandType() == commandType.C_ARITHMETIC) {
                    cd.writeArithmetic(parser.arg1());
                }
                //if it is a push or a pop command
                if (parser.commandType() == commandType.C_PUSH || parser.commandType() == commandType.C_POP) {
                    String filenameforstatic = f.getName().substring(0, f.getName().indexOf("."));
                    cd.WritePushPop(parser.commandType(), parser.arg1(), parser.arg2() , filenameforstatic);
                }
                //if it is a call command
                if (parser.commandType() == commandType.C_CALL){
                    cd.writeCall(parser.arg1(), parser.arg2());
                }
                //if it is a function command
                if (parser.commandType() == commandType.C_FUNCTION){
                    cd.writeFunction(parser.arg1(), parser.arg2());
                }
                //if it is a label command
                if (parser.commandType() == commandType.C_LABEL){
                    cd.writeLabel(parser.arg1());
                }
                //if it is a goto command
                if (parser.commandType() == commandType.C_GOTO){
                    cd.writeGoto(parser.arg1());
                }
                //if it is a if command
                if (parser.commandType() == commandType.C_IF){
                    cd.writeIf(parser.arg1());
                }
                //if it is a return command
                if (parser.commandType() == commandType.C_RETURN){
                    cd.writeReturn();
                }
                parser.advance();
            }
        }
        //close the output file once we are done
        cd.close();
    }
}
