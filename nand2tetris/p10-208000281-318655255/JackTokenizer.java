import java.io.*;
import java.util.*;

public class JackTokenizer {
    private Scanner scan;
    private String line;
    private String curr;
    private ArrayList<String> tokens;
    private int index;
    static ArrayList<String>KEYWORDS;
    static String symbols;
    static String operations;
    private boolean first;

    //possible token types
    public static final int KEYWORD = 1;
    public static final int SYMBOL = 2;
    public static final int IDENTIFIER = 3;
    public static final int INT_CONST = 4;
    public static final int STRING_CONST = 5;

    //builds new array list that contains all the words
    static{
        KEYWORDS = new ArrayList<String>();
        KEYWORDS.add("class");
        KEYWORDS.add("constructor");
        KEYWORDS.add("function");
        KEYWORDS.add("method");
        KEYWORDS.add("field");
        KEYWORDS.add("static");
        KEYWORDS.add("var");
        KEYWORDS.add("int");
        KEYWORDS.add("char");
        KEYWORDS.add("boolean");
        KEYWORDS.add("void");
        KEYWORDS.add("true");
        KEYWORDS.add("false");
        KEYWORDS.add("null");
        KEYWORDS.add("this");
        KEYWORDS.add("do");
        KEYWORDS.add("if");
        KEYWORDS.add("else");
        KEYWORDS.add("while");
        KEYWORDS.add("return");
        KEYWORDS.add("let");

       symbols = "{}()[].,;+-*/&|<>=-~"; // we can chack if a specific symbol exsits in the symbols string with *contains method*
       operations = "+-*/&|<>=";
    }

    public JackTokenizer(File file){
        try {
            scan = new Scanner(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        line = "";
        while(scan.hasNextLine()) {
            String str = scan.nextLine();
            while(Comments(str) || str.equals("")) {
                if (Comments(str))
                    str = removeComments(str);
                if (str.trim().equals("")) {
                    if (scan.hasNextLine())
                        str = scan.nextLine();
                    else
                        break;
                }
            }
            line += str.trim();
        }
        tokens = new ArrayList<String>();
        //ignors all the spaces
        while (line.length() > 0) {
            while (line.charAt(0) == ' ') {
                line = line.substring(1);
            }
            for (int i = 0; i < KEYWORDS.size(); i++) {
                if (line.startsWith(KEYWORDS.get(i).toString())) {
                    String key = KEYWORDS.get(i).toString();
                    tokens.add(key);
                    line = line.substring(key.length());
                }
            }
            //if we have a symbol
            if (symbols.contains(line.substring(0, 1))) {
                char s = line.charAt(0);
                tokens.add(Character.toString(s));
                line = line.substring(1);
            }
            //if we have \
            else if (line.subSequence(0, 1).equals("\"")) {
                line = line.substring(1);
                String str1 = "\"";
                while ((line.charAt(0) != '\"')) {
                    str1 += line.charAt(0);
                    line = line.substring(1);
                }
                str1 = str1 + "\"";
                tokens.add(str1);
                line = line.substring(1);
            }
            else if (Character.isLetter(line.charAt(0)) || (line.substring(0, 1).equals("_"))) {
                String identify = line.substring(0, 1);
                line = line.substring(1);
                while ((Character.isLetter(line.charAt(0))) || (line.substring(0, 1).equals("_"))) {
                    identify += line.substring(0, 1);
                    line = line.substring(1);
                }
                tokens.add(identify);
            }
            //if we have number
            else if (Character.isDigit(line.charAt(0))) {
                String val = line.substring(0, 1);
                line = line.substring(1);
                while (Character.isDigit(line.charAt(0))) {
                    val += line.subSequence(0, 1);
                    line = line.substring(1);
                }
                tokens.add(val);
            }
            index = 0;
            first = true;
        }
    }

    //boolean function that checks whether we have more lines
    public boolean hasMoreTokens() {
        if (index < tokens.size()-1)
            return true;
        return false;
    }

    //advance
    public void advance() {
        if (hasMoreTokens()) {
           if (!first) index++;
           else first = false;
        }
        curr = tokens.get(index);
        return;
    }

    //checks the token type of the spesific token
    public int tokenType() {
        if (KEYWORDS.contains(curr)) {
            return KEYWORD;
        }else if (symbols.contains(curr)) {
            return SYMBOL;
        } else if ((Character.isLetter(curr.charAt(0))) || (curr.charAt(0) == '_')) {
            return IDENTIFIER;
        } else if (curr.substring(0, 1).equals("\"")) {
            return STRING_CONST;
        } else {
            return INT_CONST;
        }
    }

    //checks whether the spesific char is keyword
    public String keyWord() {
        if (tokenType() == KEYWORD)
            return curr;
        return "";
    }

    //checks whether the spesific char is symbol
    public char symbol() {
        if (tokenType() == SYMBOL)
            return curr.charAt(0);
        return '\0';
    }

    //checks whether the spesific char is identifier
    public String identifier() {
        if (tokenType() == IDENTIFIER)
            return curr;
        return "";
    }

    //checks whether the spesific char is int
    public int intVal() {
        if (tokenType() == INT_CONST)
            return Integer.parseInt(curr);
        return 0;
    }

    //checks whether the spesific char is string
    public String stringVal() {
        if (tokenType() == STRING_CONST)
            return curr.substring(1, curr.length() - 1);
        return "";
    }

    //a function that checks whether we have comments in a spesific line
    private boolean Comments(String str) {
        boolean hasComments = false;
        if (str.contains("//") || str.contains("/*") || str.trim().startsWith("*"))
            hasComments = true;
        return hasComments;
    }

    //a new function that removes comments
    private String removeComments(String str) {
        String nocomments = str;
        if (Comments(str)) {
            int offset;
            if (str.trim().startsWith("*")) {
                offset = str.indexOf("*");
            }
            else if (str.contains("/*")) {
                offset = str.indexOf("/*");
            }
            else
                offset = str.indexOf("//");
            nocomments = str.substring(0, offset).trim();
        }
        return nocomments;
    }

    public void decrementIndex() {
        if (index > 0) index--;
    }

    //new function - checks whether the spesific symbol is an operation
    public boolean isOperation() {
        for (int i = 0; i < operations.length(); i++) {
            if (operations.charAt(i) == symbol()) return true;
        }
        return false;
    }
}
