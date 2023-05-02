import java.io.*;
import java.util.*;
import java.util.ArrayList;

public class JackAnalyzer{

    //a function that gets all of the jack file in a given directory
    public static ArrayList<File> getJackFiles(File dir){
        File[] files = dir.listFiles();
        ArrayList<File> result = new ArrayList<>();
        if (files == null)
            return result;
        for (File f:files){
            if (f.getName().endsWith(".jack"))
                result.add(f);
        }
        return result;
    }

    public static void main(String[] args) {
        String filename = "";
        if (args.length != 1){
            System.out.println("Usage:java JackAnalyzer [filename|directory]");
            return;
        }
        //creates a new file with the file name we got as an input
        File fileIn = new File(args[0]);
        //create a array list of all jack files
        ArrayList<File> jackFiles = new ArrayList<>();

        //we checks whether the file we got is a file and also ends with jack extention
        if (fileIn.isFile()) {
            //if it is a single file, see whether it is a vm file
            if (!args[0].endsWith(".jack")) {
                throw new IllegalArgumentException(".jack file is required!");
            }
            jackFiles.add(fileIn);
            filename = args[0].substring(0, args[0].length() - 5);
        }

        //we check whether the input we got is a directiory and if so we insert to an array all the files that has a jack extention 
        else if (fileIn.isDirectory()) {
            jackFiles = getJackFiles(fileIn);
            filename = args[0];
            //if there is no jack file in this directory
            if (jackFiles.size() == 0) {
                throw new IllegalArgumentException("No jack file in this directory");
            }
        }
        File fileOut;
        filename = filename + ".xml";
        fileOut = new File(filename);
        FileWriter fw = null;
        try {
            fw = new FileWriter(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //creates the new files according to every jack file we have in the jack files array list 
        for (File f: jackFiles) {
            String myPath = f.toString().substring(0 , f.toString().length() - 5) + ".xml";
            fileOut = new File(myPath);
            CompilationEngine compilationEngine = new CompilationEngine(f,fileOut);
            compilationEngine.compileClass();
        }
        try {
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }   
}