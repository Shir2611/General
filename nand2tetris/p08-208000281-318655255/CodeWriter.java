import java.io.*;

public class CodeWriter {
    public BufferedWriter output;
    int count , labelcount;

    public CodeWriter(String filename){
        this.count = 0;
        this.labelcount = 0;
        File out = new File(filename);
        FileWriter outwriter = null;
        try {
            outwriter = new FileWriter(out.getAbsoluteFile()); //in order to write output
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        //creates a new buffer reader
        this.output = new BufferedWriter(outwriter);
        
    }
    // a function that returns the asm name of an argument
    public String RAMLocation(String arg1){
        if (arg1.equals("local")) return "LCL";
        if (arg1.equals("argument")) return "ARG";
        if (arg1.equals("this")) return "THIS";
        return "THAT";
    }
    //bootstrap code
    public void writeInit(){
        try{
            output.write("@256" + '\n');
            output.write("D=A" + '\n');
            output.write("@SP" + '\n');
            output.write("M=D" + '\n');
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.writeCall("Sys.init", 0);
    }
    //translating the function command
    public void writeFunction(String arg1 , int arg2){
        try{
            output.write("(" + arg1 + ")" + '\n');
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < arg2; i++){
            WritePushPop(commandType.C_PUSH, "constant", 0 , "");
        }
    }
    //translating the call command
    public void writeCall(String arg1 , int arg2){
        String label = "RETURN_LABEL" + labelcount;
        labelcount++;
        try{
            output.write("@" + label + '\n');
            output.write("D=A" + '\n');
            output.write("@SP" + '\n');
            output.write("A=M" + '\n');
            output.write("M=D" + '\n');
            output.write("@SP" + '\n');
            output.write("M=M+1" + '\n');
            output.write("@LCL" + '\n');
            output.write("D=M" + '\n');
            output.write("@SP" + '\n');
            output.write("A=M" + '\n');
            output.write("M=D" + '\n');
            output.write("@SP" + '\n');
            output.write("M=M+1" + '\n');
            output.write("@ARG" + '\n');
            output.write("D=M" + '\n');
            output.write("@SP" + '\n');
            output.write("A=M" + '\n');
            output.write("M=D" + '\n');
            output.write("@SP" + '\n');
            output.write("M=M+1" + '\n');
            output.write("@THIS" + '\n');
            output.write("D=M" + '\n');
            output.write("@SP" + '\n');
            output.write("A=M" + '\n');
            output.write("M=D" + '\n');
            output.write("@SP" + '\n');
            output.write("M=M+1" + '\n');
            output.write("@THAT" + '\n');
            output.write("D=M" + '\n');
            output.write("@SP" + '\n');
            output.write("A=M" + '\n');
            output.write("M=D" + '\n');
            output.write("@SP" + '\n');
            output.write("M=M+1" + '\n');
            output.write("@SP" + '\n');
            output.write("D=M" + '\n');
            output.write("@5" + '\n');
            output.write("D=D-A" + '\n');
            output.write("@" + arg2 + '\n');
            output.write("D=D-A" + '\n');
            output.write("@ARG" + '\n');
            output.write("M=D" + '\n');
            output.write("@SP" + '\n');
            output.write("D=M" + '\n');
            output.write("@LCL" + '\n');
            output.write("M=D" + '\n');
            output.write("@" + arg1 + '\n');
            output.write("0;JMP" + '\n');
            output.write("(" + label + ")" + '\n');
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    //translating the label command
    public void writeLabel(String arg1){
        try{
            output.write("(" + arg1 + ")" + '\n');
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    //translating the if command
    public void writeIf(String arg1){
        try{
            output.write("@SP" + '\n');
            output.write("AM=M-1" + '\n');
            output.write("D=M" + '\n');
            output.write("A=A-1" + '\n');
            output.write("@" + arg1 + '\n');
            output.write("D;JNE" + '\n');
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    //translating the goto command
    public void writeGoto(String arg1){
        try{
            output.write("@" + arg1 + '\n');
            output.write("0;JMP" + '\n');
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }
    //translating the return command
    public void writeReturn(){
        try{
            output.write("@LCL" + '\n');
            output.write("D=M" + '\n');
            output.write("@R11" + '\n');
            output.write("M=D" + '\n');
            output.write("@5" + '\n');
            output.write("A=D-A" + '\n');
            output.write("D=M" + '\n');
            output.write("@R12" + '\n');
            output.write("M=D" + '\n');
            output.write("@ARG" + '\n');
            output.write("D=M" + '\n');
            output.write("@0" + '\n');
            output.write("D=D+A" + '\n');
            output.write("@R13" + '\n');
            output.write("M=D" + '\n');
            output.write("@SP" + '\n');
            output.write("AM=M-1" + '\n');
            output.write("D=M" + '\n');
            output.write("@R13" + '\n');
            output.write("A=M" + '\n');
            output.write("M=D" + '\n');
            output.write("@ARG" + '\n');
            output.write("D=M" + '\n');
            output.write("@SP" + '\n');
            output.write("M=D+1" + '\n');
            output.write("@R11" + '\n');
            output.write("D=M-1" + '\n');
            output.write("AM=D" + '\n');
            output.write("D=M" + '\n');
            output.write("@THAT" + '\n');
            output.write("M=D" + '\n');
            output.write("@R11" + '\n');
            output.write("D=M-1" + '\n');
            output.write("AM=D" + '\n');
            output.write("D=M" + '\n');
            output.write("@THIS" + '\n');
            output.write("M=D" + '\n');
            output.write("@R11" + '\n');
            output.write("D=M-1" + '\n');
            output.write("AM=D" + '\n');
            output.write("D=M" + '\n');
            output.write("@ARG" + '\n');
            output.write("M=D" + '\n');
            output.write("@R11" + '\n');
            output.write("D=M-1" + '\n');
            output.write("AM=D" + '\n');
            output.write("D=M" + '\n');
            output.write("@LCL" + '\n');
            output.write("M=D" + '\n');
            output.write("@R12"+ '\n');
            output.write("A=M"+ '\n');
            output.write("0;JMP"+ '\n');
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public void writeArithmetic(String command){
        command = command.trim();
        if (command.equals("add")){
            //subtract 1 input to another input
            try{
                output.write("@SP" + '\n');
                output.write("A=M" + '\n');
                output.write("A=A-1" + '\n');
                output.write("A=A-1" + '\n');
                output.write("D=M" + '\n');
                output.write("A=A+1" + '\n');
                output.write("D=D+M" + '\n');
                output.write("@SP" + '\n');
                output.write("M=M-1" + '\n');
                output.write("M=M-1" + '\n');
                output.write("A=M" + '\n');
                output.write("M=D" + '\n');
                output.write("@SP" + '\n');
                output.write("M=M+1" + '\n');
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        if (command.equals("sub")){
            //subtract 1 input from another input
            try{
                output.write("@SP" + '\n');
                output.write("A=M" + '\n');
                output.write("A=A-1" + '\n');
                output.write("A=A-1" + '\n');
                output.write("D=M" + '\n');
                output.write("A=A+1" + '\n');
                output.write("D=D-M" + '\n');
                output.write("@SP" + '\n');
                output.write("M=M-1" + '\n');
                output.write("M=M-1" + '\n');
                output.write("A=M" + '\n');
                output.write("M=D" + '\n');
                output.write("@SP" + '\n');
                output.write("M=M+1" + '\n');
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        if (command.equals("neg")){
            //negates the input
            try{
                output.write("@SP" + '\n');
                output.write("M=M-1" + '\n');
                output.write("A=M" + '\n');
                output.write("M=-M" + '\n');
                output.write("@SP" + '\n');
                output.write("M=M+1" + '\n');
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        if (command.equals("gt")){
            //checks whether 1 input is greater than another
            try{
                output.write("@SP" + '\n');
                output.write("A=M" + '\n');
                output.write("A=A-1" + '\n');
                output.write("A=A-1" + '\n');
                output.write("D=M" + '\n');
                output.write("A=A+1" + '\n');
                output.write("D=D-M" + '\n');
                output.write("@SP" + '\n');
                output.write("M=M-1" + '\n');
                output.write("M=M-1" + '\n');
                output.write("@JUMPSTART" + count +'\n');
                output.write("D;JGT" + '\n');
                output.write("@SP" + '\n');
                output.write("A=M" + '\n');
                output.write("M=0" + '\n');
                output.write("@JUMPEND" + count + '\n');
                output.write("0;JMP" + '\n');
                output.write('\n');
                output.write("(JUMPSTART" + count + ")" +'\n');
                output.write("@SP" +'\n');
                output.write("A=M" + '\n');
                output.write("M=-1" + '\n');
                output.write('\n');
                output.write("(JUMPEND" + count + ")" +'\n');
                output.write("@SP" + '\n');
                output.write("M=M+1" +'\n');
            }
            catch(IOException e){
                e.printStackTrace();
            }
            count++;
        }
        if (command.equals("eq")){
            //checks if 2 inputs are equal to each other
            try{
                output.write("@SP" + '\n');
                output.write("A=M" + '\n');
                output.write("A=A-1" + '\n');
                output.write("A=A-1" + '\n');
                output.write("D=M" + '\n');
                output.write("A=A+1" + '\n');
                output.write("D=D-M" + '\n');
                output.write("@SP" + '\n');
                output.write("M=M-1" + '\n');
                output.write("M=M-1" + '\n');
                output.write("@JUMPSTART" + count + '\n');
                output.write("D;JEQ" + '\n');
                output.write("@SP" + '\n');
                output.write("A=M" + '\n');
                output.write("M=0" + '\n');
                output.write("@JUMPEND" + count + '\n');
                output.write("0;JMP" + '\n');
                output.write('\n');
                output.write("(JUMPSTART" + count + ")" +'\n');
                output.write("@SP" +'\n');
                output.write("A=M" + '\n');
                output.write("M=-1" + '\n');
                output.write('\n');
                output.write("(JUMPEND" + count + ")" +'\n');
                output.write("@SP" + '\n');
                output.write("M=M+1" +'\n');
            }
            catch(IOException e){
                e.printStackTrace();
            }
            count++;
        }
        if (command.equals("lt")){
            //checks if 1 input is less than another input
            try{
                output.write("@SP" + '\n');
                output.write("AM=M-1" + '\n');
                output.write("D=M" + '\n');
                output.write("A=A-1" + '\n');
                output.write("D=M-D" + '\n');
                output.write("@FALSE" + count +'\n');
                output.write("D;JGE" + '\n');
                output.write("@SP" + '\n');
                output.write("A=M-1" + '\n');
                output.write("M=-1" + '\n');
                output.write("@CONTINUE" + count + '\n');
                output.write("0;JMP" + '\n');
                output.write("(FALSE" + count + ")"+'\n');
                output.write("@SP" + '\n');
                output.write("A=M-1" + '\n');
                output.write("M=0" + '\n');
                output.write("(CONTINUE" + count + ")" + '\n');
            }
            catch(IOException e){
                e.printStackTrace();
            }
            count++;
        }
        if (command.equals("and")){
            //logical and
            try{
                output.write("@SP" + '\n');
                output.write("A=M" + '\n');
                output.write("A=A-1" + '\n');
                output.write("A=A-1" + '\n');
                output.write("D=M" + '\n');
                output.write("A=A+1" + '\n');
                output.write("D=D&M" + '\n');
                output.write("@SP" + '\n');
                output.write("M=M-1" + '\n');
                output.write("M=M-1" + '\n');
                output.write("A=M" + '\n');
                output.write("M=D" + '\n');
                output.write("@SP" + '\n');
                output.write("M=M+1" + '\n');
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        if (command.equals("or")){
            //logical or
            try{
                output.write("@SP" + '\n');
                output.write("A=M" + '\n');
                output.write("A=A-1" + '\n');
                output.write("A=A-1" + '\n');
                output.write("D=M" + '\n');
                output.write("A=A+1" + '\n');
                output.write("D=D|M" + '\n');
                output.write("@SP" + '\n');
                output.write("M=M-1" + '\n');
                output.write("M=M-1" + '\n');
                output.write("A=M" + '\n');
                output.write("M=D" + '\n');
                output.write("@SP" + '\n');
                output.write("M=M+1" + '\n');
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        if (command.equals("not")){
            //logical not
            try{
                output.write("@SP" + '\n');
                output.write("M=M-1" + '\n');
                output.write("A=M" + '\n');
                output.write("M=!M" + '\n');
                output.write("@SP" + '\n');
                output.write("M=M+1" + '\n');
            }
            catch(IOException e){
                e.printStackTrace();
            }
         }
    }
    // push and pop commands :
    public void WritePushPop(commandType type , String arg1 , int arg2 , String filenameforstatic){
        String arg = String.valueOf(arg2);
        //if it is a pop command
        if (type == commandType.C_POP){
            if (arg1.equals("pointer")){
                String pointer;
                if (arg.equals("0")) {
                    pointer = "THIS";
                }
                else {
                    pointer = "THAT";
                }
                try{
                    output.write("@SP" + '\n');
                    output.write("M=M-1" + '\n');
                    output.write("A=M" + '\n');
                    output.write("D=M" + '\n');
                    output.write("@" + pointer + '\n');
                    output.write("M=D" + '\n');
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
            //if we have a this, that, local or argument command
            if (arg1.equals("this") || arg1.equals("that") || arg1.equals("local") || arg1.equals("argument")){
                try{
                    output.write("@" + arg + '\n');
                    output.write("D=A" + '\n');
                    output.write("@" + this.RAMLocation(arg1) + '\n');
                    output.write("A=M" +'\n');
                    output.write("D=D+A" + '\n');
                    output.write("@" + this.RAMLocation(arg1) + '\n');
                    output.write("M=D" + '\n');
                    output.write("@SP" + '\n');
                    output.write("M=M-1" + '\n');
                    output.write("A=M" + '\n');
                    output.write("D=M" + '\n');
                    output.write("@" + this.RAMLocation(arg1) + '\n');
                    output.write("A=M" + '\n');
                    output.write("M=D" + '\n');
                    output.write("@" + arg + '\n');
                    output.write("D=A" + '\n');
                    output.write("@" + this.RAMLocation(arg1) + '\n');
                    output.write("A=M" +'\n');
                    output.write("D=A-D" + '\n');
                    output.write("@" + this.RAMLocation(arg1) + '\n');
                    output.write("M=D" + '\n');
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
            //if we have a static command
            if (arg1.equals("static")){
                try{
                    output.write("@SP" + '\n');
                    output.write("M=M-1" + '\n');
                    output.write("A=M" + '\n');
                    output.write("D=M" + '\n');
                    output.write("@" + filenameforstatic + "." + arg + '\n');
                    output.write("M=D" + '\n');
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
            //if we have a temp command
            if (arg1.equals("temp")){
                String index = String.valueOf(arg2 + 5);
                try{
                    output.write("@" + index + '\n');
                    output.write("D=A" + '\n');
                    output.write("@R14" + '\n');
                    output.write("M=D" + '\n');
                    output.write("@SP" + '\n');
                    output.write("M=M-1" + '\n');
                    output.write("A=M" + '\n');
                    output.write("D=M" + '\n');
                    output.write("@R14" + '\n');
                    output.write("A=M" + '\n');
                    output.write("M=D" + '\n');
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        else {
            if (arg1.equals("constant")){
                try{
                    output.write("@" + arg + '\n');
                    output.write("D=A" + '\n');
                    output.write("@SP" + '\n');
                    output.write("A=M" + '\n');
                    output.write("M=D" + '\n');
                    output.write("@SP" + '\n');
                    output.write("M=M+1" + '\n');
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
            if (arg1.equals("static")){
                try{
                    output.write("@" + filenameforstatic + "." + arg + '\n');
                    output.write("D=M" + '\n');
                    output.write("@SP" + '\n');
                    output.write("A=M" + '\n');
                    output.write("M=D" + '\n');
                    output.write("@SP" + '\n');
                    output.write("M=M+1" + '\n');
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
            if (arg1.equals("pointer")){
                String pointer;
                if (arg.equals("0")) {
                    pointer = "THIS";
                }
                else {
                    pointer = "THAT";
                }
                try{
                    output.write("@" + pointer + '\n');
                    output.write("D=M" + '\n');
                    output.write("@SP" + '\n');
                    output.write("A=M" + '\n');
                    output.write("M=D" + '\n');
                    output.write("@SP" + '\n');
                    output.write("M=M+1" + '\n');
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
            //if we have an this, that, local or argument command
            if (arg1.equals("this") || arg1.equals("that") || arg1.equals("local") || arg1.equals("argument")){
                try{
                    output.write("@" + arg + '\n');
                    output.write("D=A" + '\n');
                    output.write("@" + this.RAMLocation(arg1) + '\n');
                    output.write("A=M" + '\n');
                    output.write("D=D+A" + '\n');
                    output.write("A=D" + '\n');
                    output.write("D=M" + '\n');
                    output.write("@SP" + '\n');
                    output.write("A=M" + '\n');
                    output.write("M=D" + '\n');
                    output.write("@SP" + '\n');
                    output.write("M=M+1" + '\n');
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
            //if we have a temp command
            if (arg1.equals("temp")){
                try{
                    output.write("@11" + '\n');
                    output.write("D=M" + '\n');
                    output.write("@SP" + '\n');
                    output.write("A=M" + '\n');
                    output.write("M=D" + '\n');
                    output.write("@SP" + '\n');
                    output.write("M=M+1" + '\n');
                }
                catch(IOException e){
                    e.printStackTrace();
               }
            }
        }
    }
    //close the output file
    public void close(){  
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

