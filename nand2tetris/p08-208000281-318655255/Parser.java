import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

    //creates command types
   enum commandType {
    C_ARITHMETIC,
    C_PUSH,
    C_POP,
    C_LABEL,
    C_GOTO,
    C_IF,
    C_FUNCTION,
    C_RETURN,
    C_CALL
  }

public class Parser{
    public Scanner file;
    private ArrayList<String> code;
    private int line;

    //this function reads the input file and removes all the white spaces and comments
    public Parser(String fileLocation) {
        this.code = new ArrayList<>();
        this.line = 0;
        try {
            this.file = new Scanner(new File(fileLocation));
            while (file.hasNextLine()) {
                String instruction = file.nextLine();
                if (!instruction.equals("") && !instruction.startsWith("//")) {
                    if (instruction.indexOf("//") != -1) {
                        instruction = instruction.substring(0, instruction.indexOf("//"));
                    }
                    if (!instruction.equals("")) {
                        code.add(instruction);
                    }
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    public boolean hasMoreLines() {
        return code.size() > line;
    }

    public void advance() {
        line++;
    }

    //a function that gets the instruction of the line    
    public String getInst() {
        while (hasMoreLines()) {
            return code.get(line);
        }
        return "";
    }

    //a function that checks if it is a pop or a push command
    public commandType commandType() {
        if (getInst().startsWith("pop")) return commandType.C_POP;
        if (getInst().startsWith("push")) return commandType.C_PUSH;
        if (getInst().startsWith("label")) return commandType.C_LABEL;
        if (getInst().startsWith("goto")) return commandType.C_GOTO;
        if (getInst().startsWith("call")) return commandType.C_CALL;
        if (getInst().startsWith("return")) return commandType.C_RETURN;
        if (getInst().startsWith("function")) return commandType.C_FUNCTION;
        if (getInst().startsWith("if")) return commandType.C_IF;
        return commandType.C_ARITHMETIC;
        }
        
    // a function that returns the argument after the first space    
    public String arg1() {
        commandType type =  this.commandType();
        if (type == commandType.C_RETURN) throw new Error ("Cannot get argument from return command");
        if (type == commandType.C_ARITHMETIC) {
            return getInst();
        }
        if (type == commandType.C_POP || type == commandType.C_PUSH || type == commandType.C_FUNCTION || type == commandType.C_CALL || type == commandType.C_LABEL || type == commandType.C_IF || type == commandType.C_GOTO){
            String[] str = getInst().split(" ");
            return str[1];
        }
        return "";
    }

    //a function that returns the argument after the second space
    public int arg2() {
        commandType type =  this.commandType();
        if (type == commandType.C_ARITHMETIC || type == commandType.C_LABEL || type == commandType.C_GOTO || type == commandType.C_IF || type == commandType.C_RETURN) throw new Error ("Cannot get argument from this command");
        String[] str = getInst().split(" ");
        str[2] = str[2].trim();
        int arg = Integer.parseInt(str[2]);
        return arg;
    }
}
