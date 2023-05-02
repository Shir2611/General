import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class VMWriter {
    private FileWriter fw;

    //creates a new file and prepares it for writing
    public VMWriter(File file) {
        try {
            fw = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //writes a VM push command
    public void writePush(String segment , int index) {
        if (segment.equals("field")) {
            segment = "this";
        }
        if (segment.equals("var")) {
            segment = "local";
        }
        try {
            fw.write("push " + segment + " " + index + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //writes a VM pop command
    public void writePop(String segment , int index) {
        if (segment.equals("field")) {
            segment = "this";
        }
        if (segment.equals("var")) {
            segment = "local";
        }
        try {
            fw.write("pop " + segment + " " + index + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //writes a VM arithmetic command
    public void writeArithmetic(String command) {
        try {
            fw.write(command + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //writes a VM label command
    public void writeLabel(String label) {
        try {
            fw.write("label " + label + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //writes a VM goto command
    public void writeGoto(String label) {
        try {
            fw.write("goto " + label + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //writes a VM if-goto command
    public void writeIf(String label) {
        try {
            fw.write("if-goto " + label + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //writes a VM call command
    public void writeCall(String name , int nArgs) {
        try {
            fw.write("call " + name + " " + nArgs + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //writes a VM fuction command
    public void writeFunction(String name , int nVars) {
        try {
            fw.write("function " + name + " " + nVars + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //writes a VM return command
    public void writeRetun() {
        try {
            fw.write("return\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //close the output file
    public void close() {
        try {
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
