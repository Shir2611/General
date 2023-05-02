import java.io.*;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;


public class CompilationEngine {
    private FileWriter fw;
    private JackTokenizer tokenizer;
    private boolean first;

    public CompilationEngine(File inFile, File outFile) {
        try {
            fw = new FileWriter(outFile);
            tokenizer = new JackTokenizer(inFile);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        first = true;
    }

    public void compileClass(){
        try{
            tokenizer.advance();
            fw.write("<class>\n");
            fw.write("<keyword> class </keyword>\n");
            tokenizer.advance();
            fw.write("<identifier> " + tokenizer.identifier() + " </identifier>\n");
            tokenizer.advance();
            fw.write("<symbol> { </symbol>\n");
            compileClassVarDec();
            compileSubRoutine();
            fw.write("<symbol> } </symbol>\n");
            fw.write("</class>\n");
            fw.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void compileClassVarDec(){
        tokenizer.advance();
        try{
            // while we have field or static keyword
            while (tokenizer.keyWord().equals("static") || tokenizer.keyWord().equals("field")) {
                fw.write("<classVarDec>\n");
                fw.write("<keyword> " + tokenizer.keyWord() + " </keyword>\n");
                tokenizer.advance();
                if (tokenizer.tokenType() != JackTokenizer.IDENTIFIER) {
                    fw.write("<keyword> " + tokenizer.keyWord() + " </keyword>\n");
                }
                else {
                    fw.write("<identifier> " + tokenizer.identifier() + " </identifier>\n");

                }
                tokenizer.advance();
                fw.write("<identifier> " + tokenizer.identifier() + " </identifier>\n");
                tokenizer.advance();
                //if there are more than one identifier in the same line
                if (tokenizer.symbol() == ',') {
                    fw.write("<symbol> , </symbol>\n");
                    tokenizer.advance();
                    fw.write(("<identifier> " + tokenizer.identifier() + " </identifier>\n"));
                    tokenizer.advance();
                }
                // semicolon
                fw.write("<symbol> ; </symbol>\n");
                tokenizer.advance();
                fw.write("</classVarDec>\n");
            }
            if (tokenizer.keyWord().equals("function") || tokenizer.keyWord().equals("method") || tokenizer.keyWord().equals("constructor")) {
                tokenizer.decrementIndex();
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        
        }
    }

    public void compileSubRoutine() {
        //boolean variable holds if we have subroutines initialize to false
        boolean isSubRoutines = false;
        tokenizer.advance();
        try {
            //base case for the recursive call
            if (tokenizer.tokenType()==JackTokenizer.SYMBOL && tokenizer.symbol() == '}') {
                return;
            }
            if ((tokenizer.keyWord().equals("function") || tokenizer.keyWord().equals("method") || tokenizer.keyWord().equals("constructor")) && (first)) {
                fw.write("<subroutineDec>\n");
                isSubRoutines = true;
                first = false;
            }
            // if we have a function, method or a constractor keyword
            if (tokenizer.keyWord().equals("function") || tokenizer.keyWord().equals("method") || tokenizer.keyWord().equals("constructor")) {
                fw.write("<keyword> " + tokenizer.keyWord() + " </keyword>\n");
                isSubRoutines = true;
                tokenizer.advance();
            }
            if (tokenizer.tokenType()==JackTokenizer.IDENTIFIER) {
                fw.write("<identifier> " + tokenizer.identifier() + " </identifier>\n");
                tokenizer.advance();
            }
            //if instead of a subroutine statment we have keyword
            else if (tokenizer.tokenType()==JackTokenizer.KEYWORD) {
                fw.write("<keyword> " + tokenizer.keyWord() + " </keyword>\n");
                tokenizer.advance();
            }
            //the name of the subroutine
            if (tokenizer.tokenType()==JackTokenizer.IDENTIFIER) {
                fw.write("<identifier> " + tokenizer.identifier() + " </identifier>\n");
                tokenizer.advance();
            }
            //parameters
            if (tokenizer.symbol() == '(') {
                fw.write("<symbol> ( </symbol>\n");
                fw.write("<parameterList> ");
                compileParameterList();
                fw.write("</parameterList>\n");
                fw.write("<symbol> ) </symbol>\n");
            }
            tokenizer.advance();
            compileSubRoutineBody();
            //if we did obtain a subroutine
            if (isSubRoutines) {
                first = true;
                fw.write("</subroutineBody>\n");
                fw.write("</subroutineDec>\n");
            }
            //the recursive call
            compileSubRoutine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void compileParameterList() {
        tokenizer.advance();
        try {
            //while we didn't reach to the end - )
            while (!(tokenizer.tokenType()==JackTokenizer.SYMBOL && tokenizer.symbol() == ')')) {
                if (tokenizer.tokenType()==JackTokenizer.IDENTIFIER) {
                    fw.write("<identifier> " + tokenizer.identifier() + " </identifier>\n");
                    tokenizer.advance();
                }
                //if we have comma
                else if ((tokenizer.tokenType()==JackTokenizer.SYMBOL) && (tokenizer.symbol() == ',')) {
                    fw.write("<symbol> , </symbol>\n");
                    tokenizer.advance();

                }
                else if (tokenizer.tokenType()==JackTokenizer.KEYWORD) {
                    fw.write("<keyword> " + tokenizer.keyWord() + " </keyword>\n");
                    tokenizer.advance();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void compileSubRoutineBody () {
        try{
            if (tokenizer.symbol() == '{') {
                fw.write("<subroutineBody>\n");
                fw.write("<symbol> { </symbol>\n");
                tokenizer.advance();
            }
            //we get all the var declarations in the spesific subroutine
            while (tokenizer.keyWord().equals("var") && (tokenizer.tokenType()==JackTokenizer.KEYWORD)) {
                fw.write("<varDec>\n ");
                tokenizer.decrementIndex();
                compileVarDec();
                fw.write("</varDec>\n");
            }
            fw.write("<statements>\n");
            compileStatements();
            fw.write("</statements>\n");
            fw.write("<symbol> " + tokenizer.symbol() + " </symbol>\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void compileVarDec() {
        tokenizer.advance();
        try {
            if (tokenizer.keyWord().equals("var") && (tokenizer.tokenType()==JackTokenizer.KEYWORD)) {
                fw.write("<keyword> var </keyword>\n");
                tokenizer.advance();
            }
            //if we have identifier then what type of var
            if (tokenizer.tokenType()==JackTokenizer.IDENTIFIER) {
                fw.write("<identifier> " + tokenizer.identifier() + " </identifier>\n");
                tokenizer.advance();
            }
            //if we have keyword, then what type of var
            else if (tokenizer.tokenType()==JackTokenizer.KEYWORD) {
                fw.write("<keyword> " + tokenizer.keyWord() + " </keyword>\n");
                tokenizer.advance();
            }
            //the name of the var
            if (tokenizer.tokenType()==JackTokenizer.IDENTIFIER) {
                fw.write("<identifier> " + tokenizer.identifier() + " </identifier>\n");
                tokenizer.advance();
            }
            //if there are mutliple symbols in one line
            if ((tokenizer.tokenType()==JackTokenizer.SYMBOL) && (tokenizer.symbol() == ',')) {
                fw.write("<symbol> , </symbol>\n");
                tokenizer.advance();
                fw.write(("<identifier> " + tokenizer.identifier() + " </identifier>\n"));
                tokenizer.advance();
            }
            //the end of the var line
            if ((tokenizer.tokenType()==JackTokenizer.SYMBOL) && (tokenizer.symbol() == ';')) {
                fw.write("<symbol> ; </symbol>\n");
                tokenizer.advance();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void compileStatements() {
        try {
            if (tokenizer.symbol() == '}' && (tokenizer.tokenType()==JackTokenizer.SYMBOL)) {
                return;
            } 
            //let
            else if (tokenizer.keyWord().equals("let") && (tokenizer.tokenType()==JackTokenizer.KEYWORD)) {
                fw.write("<letStatement>\n ");
                compileLet();
                fw.write(("</letStatement>\n"));
            }
            //if
            else if (tokenizer.keyWord().equals("if") && (tokenizer.tokenType()==JackTokenizer.KEYWORD)) {
                fw.write("<ifStatement>\n ");
                compileIf();
                fw.write(("</ifStatement>\n"));
            }
            //while
            else if (tokenizer.keyWord().equals("while") && (tokenizer.tokenType()==JackTokenizer.KEYWORD)) {
                fw.write("<whileStatement>\n ");
                compileWhile();
                fw.write(("</whileStatement>\n"));
            } 
            //do
            else if (tokenizer.keyWord().equals("do") && (tokenizer.tokenType()==JackTokenizer.KEYWORD)) {
                fw.write("<doStatement>\n ");
                compileDo();
                fw.write(("</doStatement>\n"));
            } 
            //return 
            else if (tokenizer.keyWord().equals("return") && (tokenizer.tokenType()==JackTokenizer.KEYWORD)) {
                fw.write("<returnStatement>\n ");
                compileReturn();
                fw.write(("</returnStatement>\n"));
            }
            tokenizer.advance();
            compileStatements();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void compileLet() {
        try {
            fw.write("<keyword> " + tokenizer.keyWord() + " </keyword>\n");
            tokenizer.advance();
            fw.write("<identifier> " + tokenizer.identifier() + " </identifier>\n");
            tokenizer.advance();
            if ((tokenizer.tokenType()==JackTokenizer.SYMBOL) && (tokenizer.symbol() == '[')) {
                //if we have an expression
                fw.write("<symbol> " + tokenizer.symbol() + " </symbol>\n");
                compileExpression();
                tokenizer.advance();
                if ((tokenizer.tokenType()==JackTokenizer.SYMBOL) && ((tokenizer.symbol() == ']'))) {
                    fw.write("<symbol> " + tokenizer.symbol() + " </symbol>\n");
                }
                //advance only if there is an expression
                tokenizer.advance();
            }
            fw.write("<symbol> " + tokenizer.symbol() + " </symbol>\n");
            compileExpression();
            fw.write("<symbol> " + tokenizer.symbol() + " </symbol>\n");
            tokenizer.advance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void compileIf() {
        try {
            fw.write("<keyword> if </keyword>\n");
            tokenizer.advance();
            fw.write("<symbol> ( </symbol>\n");
            //the expression in the if
            compileExpression();
            fw.write("<symbol> ) </symbol>\n");
            tokenizer.advance();
            fw.write("<symbol> { </symbol>\n");
            tokenizer.advance();
            fw.write("<statements>\n");
            //the statements in the if brackets
            compileStatements();
            fw.write("</statements>\n");
            fw.write("<symbol> } </symbol>\n");
            tokenizer.advance();
            //if we have an else statment
            if (tokenizer.tokenType()==JackTokenizer.KEYWORD && tokenizer.keyWord().equals("else")) {
                fw.write("<keyword> else </keyword>\n");
                tokenizer.advance();
                fw.write("<symbol> { </symbol>\n");
                tokenizer.advance();
                fw.write("<statements>\n");
                //the statements in the else brackets
                compileStatements();
                fw.write("</statements>\n");
                fw.write("<symbol> } </symbol>\n");
            } else {
                tokenizer.decrementIndex();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void compileWhile() {
        try {
            fw.write("<keyword> " + tokenizer.keyWord() + " </keyword>\n");
            tokenizer.advance();
            fw.write("<symbol> " + tokenizer.symbol() + " </symbol>\n");
            //the inside of brackets
            compileExpression();
            tokenizer.advance();
            fw.write("<symbol> " + tokenizer.symbol() + " </symbol>\n");
            tokenizer.advance();
            fw.write("<symbol> " + tokenizer.symbol() + " </symbol>\n");
            //the inside of the curly brackets
            fw.write("<statements>\n");
            compileStatements();
            fw.write("</statements>\n");
            fw.write("<symbol> " + tokenizer.symbol() + " </symbol>\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void compileDo() {
        try {
            if (tokenizer.keyWord().equals("do")) {
                fw.write("<keyword> do </keyword>\n");
            }
            //function call
            compileCall();
            tokenizer.advance();
            fw.write("<symbol> " + tokenizer.symbol() + " </symbol>\n");

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void compileReturn() {
        try {
            fw.write("<keyword> return </keyword>\n");
            tokenizer.advance();
            if (!((tokenizer.tokenType()==JackTokenizer.SYMBOL && tokenizer.symbol() == ';'))) {
                tokenizer.decrementIndex();
                compileExpression();
            }
            if (tokenizer.tokenType()==JackTokenizer.SYMBOL && tokenizer.symbol() == ';') {
                fw.write("<symbol> ; </symbol>\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void compileCall() {
        tokenizer.advance();
        try {
            fw.write("<identifier> " + tokenizer.identifier() + " </identifier>\n");
            tokenizer.advance();
            if ((tokenizer.tokenType()==JackTokenizer.SYMBOL) && (tokenizer.symbol() == '.')) {
                fw.write("<symbol> " + tokenizer.symbol() + " </symbol>\n");
                tokenizer.advance();
                fw.write("<identifier> " + tokenizer.identifier() + " </identifier>\n");
                tokenizer.advance();
                fw.write("<symbol> " + tokenizer.symbol() + " </symbol>\n");
                //the parameters in the parentheses
                fw.write("<expressionList>\n");
                compileExpressionList();
                fw.write("</expressionList>\n");
                tokenizer.advance();
                fw.write("<symbol> " + tokenizer.symbol() + " </symbol>\n");
            }
            else if ((tokenizer.tokenType()==JackTokenizer.SYMBOL) && (tokenizer.symbol() == '(')) {
                fw.write("<symbol> " + tokenizer.symbol() + " </symbol>\n");
                fw.write("<expressionList> ");
                compileExpressionList();
                fw.write("</expressionList>\n");
                tokenizer.advance();
                fw.write("<symbol> " + tokenizer.symbol() + " </symbol>\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
    private void compileExpression() {
        try {
            fw.write("<expression>\n");
            compileTerm();
            while (true) {
                tokenizer.advance();
                if (tokenizer.tokenType()==JackTokenizer.SYMBOL && tokenizer.isOperation()) {
                    //if we have < > &
                    if (tokenizer.symbol() == '<') {
                        fw.write("<symbol> &lt; </symbol>\n");
                    } else if (tokenizer.symbol() == '&') {
                        fw.write("<symbol> &amp; </symbol>\n");
                    }else if (tokenizer.symbol() == '>') {
                        fw.write("<symbol> &gt; </symbol>\n");
                    } else {
                        fw.write("<symbol> " + tokenizer.symbol() + " </symbol>\n");
                    }
                    compileTerm();
                } else {
                    tokenizer.decrementIndex();
                    break;
                }
            }
            fw.write("</expression>\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void compileTerm() {
        try {
            fw.write("<term>\n");
            tokenizer.advance();
            if (tokenizer.tokenType()==JackTokenizer.IDENTIFIER) {
                String prevIdentifier = tokenizer.identifier();
                tokenizer.advance();
                if (tokenizer.tokenType()==JackTokenizer.SYMBOL && tokenizer.symbol() == '[') {
                    fw.write("<identifier> " + prevIdentifier + " </identifier>\n");
                    fw.write("<symbol> [ </symbol>\n");
                    compileExpression();
                    tokenizer.advance();
                    fw.write("<symbol> ] </symbol>\n");
                }
                else if (tokenizer.tokenType()==JackTokenizer.SYMBOL && (tokenizer.symbol() == '(' || tokenizer.symbol() == '.')) {
                    tokenizer.decrementIndex();
                    tokenizer.decrementIndex();
                    compileCall();

                } 
                else {
                    fw.write("<identifier> " + prevIdentifier + " </identifier>\n");
                    tokenizer.decrementIndex();
                }
            } else {
                //int
                if (tokenizer.tokenType()==JackTokenizer.INT_CONST) {
                    fw.write("<integerConstant> " + tokenizer.intVal() + " </integerConstant>\n");
                }
                //operators
                else if (tokenizer.tokenType()==JackTokenizer.SYMBOL && (tokenizer.symbol() == '-' || tokenizer.symbol() == '~')) {
                    fw.write("<symbol> " + tokenizer.symbol() + " </symbol>\n");
                    //recursive call
                    compileTerm();
                }
                //T,F or null
                else if (tokenizer.tokenType()==JackTokenizer.KEYWORD && (tokenizer.keyWord().equals("this") || tokenizer.keyWord().equals("null")
                        || tokenizer.keyWord().equals("false") || tokenizer.keyWord().equals("true"))) {
                    fw.write("<keyword> " + tokenizer.keyWord() + " </keyword>\n");
                }
                //string
                else if (tokenizer.tokenType()==JackTokenizer.STRING_CONST) {
                    fw.write("<stringConstant> " + tokenizer.stringVal() + "</stringConstant>\n");
                }
                //brackets
                else if (tokenizer.tokenType()==JackTokenizer.SYMBOL && tokenizer.symbol() == '(') {
                    fw.write("<symbol> " + tokenizer.symbol() + " </symbol>\n");
                    compileExpression();
                    tokenizer.advance();
                    fw.write("<symbol> " + tokenizer.symbol() + " </symbol>\n");
                }
            }
            fw.write("</term>\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void compileExpressionList() {
        tokenizer.advance();
        if (tokenizer.symbol() == ')' && tokenizer.tokenType()==JackTokenizer.SYMBOL) {
            tokenizer.decrementIndex();
        } 
        else {
            tokenizer.decrementIndex();
            compileExpression();
        }
        while (true) {
            tokenizer.advance();
            if (tokenizer.tokenType()==JackTokenizer.SYMBOL && tokenizer.symbol() == ',') {
                try {
                    fw.write("<symbol> , </symbol>\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                compileExpression();
            } 
            else {
                tokenizer.decrementIndex();
                break;
            }
        }
    }
}

