import java.io.*;
import java.util.*;

import javax.imageio.IIOException;

import java.io.File;


public class CompilationEngine {
    private VMWriter vmw;
    private JackTokenizer tokenizer;
    private SymbolTable sm;
    private int index;
    private String className , subRoutineName;

    //constructor
    public CompilationEngine(File inFile, File outFile) {
        index = 0;
        vmw = new VMWriter(outFile);
        tokenizer = new JackTokenizer(inFile);
        sm = new SymbolTable();
    }

    public void compileClass(){
        tokenizer.advance();
        tokenizer.advance();
        className = tokenizer.identifier();
        tokenizer.advance();
        compileClassVarDec();
        compileSubRoutine();
        tokenizer.advance(); //remove if not relevant
        vmw.close();
    }

    public void compileClassVarDec(){
        String kind, type;
        tokenizer.advance();
        // while we have field or static keyword
        while (tokenizer.keyWord().equals("static") || tokenizer.keyWord().equals("field")) {
            if (tokenizer.keyWord().equals("static"))
                kind = "static";
            else
                kind = "field";
            tokenizer.advance();
            if (tokenizer.tokenType() != JackTokenizer.IDENTIFIER)
                type = tokenizer.keyWord();
                //throw new IllegalArgumentException("no identifier");
            else
                type = tokenizer.identifier();
            tokenizer.advance();
            //defines a new idintifier of type, kind in the symbol table
            sm.define(tokenizer.identifier(), type, kind);
            tokenizer.advance();
            while(tokenizer.symbol()==','){
                tokenizer.advance();
                sm.define(tokenizer.identifier(), type, kind);
                tokenizer.advance();
            }
            tokenizer.advance();
        }
        if (tokenizer.keyWord().equals("function") || tokenizer.keyWord().equals("method") || tokenizer.keyWord().equals("constructor")) {
            tokenizer.decrementIndex();
            return;
        }
    }

    public void compileSubRoutine() {
        String keyword = "";
        String type = "";
        tokenizer.advance();
        //base case for the recursive call
        if (tokenizer.tokenType()==JackTokenizer.SYMBOL && tokenizer.symbol() == '}')
            return;
        if (tokenizer.keyWord().equals("function") || tokenizer.keyWord().equals("method") || tokenizer.keyWord().equals("constructor")) {
            keyword = tokenizer.keyWord();
            sm.reset();
            if(keyword.equals("method"))
                sm.define("this", className, "argument");
            tokenizer.advance();
        }
        //if the return type is void int char boolean or identifier
        if (tokenizer.keyWord().equals("void") && tokenizer.tokenType()==JackTokenizer.KEYWORD) {
            type = "void";
            tokenizer.advance();
        }
        else if (tokenizer.tokenType()==JackTokenizer.KEYWORD && (tokenizer.keyWord().equals("int")|| tokenizer.keyWord().equals("boolean") || tokenizer.keyWord().equals("char"))) {
            type = tokenizer.keyWord();
            tokenizer.advance();
        }
        else {
            type = tokenizer.identifier();
            tokenizer.advance();
        }
        //the name of the subroutine
        if (tokenizer.tokenType()==JackTokenizer.IDENTIFIER) {
            subRoutineName = tokenizer.identifier();
            tokenizer.advance();
        }
        //parameters
        if (tokenizer.symbol() == '(')
            compileParameterList();
        tokenizer.advance();
        compileSubRoutineBody(keyword);
        //the recursive call
        compileSubRoutine();
    }
    public void compileParameterList() {
        boolean hasp = false;
        String type = "",name = "";
        tokenizer.advance();
        //while we didn't reach to the end - )
        while (!(tokenizer.tokenType()==JackTokenizer.SYMBOL && tokenizer.symbol() == ')')) {
            if (tokenizer.tokenType()==JackTokenizer.IDENTIFIER)
                type = tokenizer.identifier();
            else if (tokenizer.tokenType()==JackTokenizer.KEYWORD) {
                hasp = true;
                type = tokenizer.keyWord();
            }
            tokenizer.advance();
            if (tokenizer.tokenType() == JackTokenizer.IDENTIFIER)
                name = tokenizer.identifier();
            tokenizer.advance();
            //if we have a comma
            if (tokenizer.tokenType() == JackTokenizer.SYMBOL && tokenizer.symbol() == ','){
                sm.define(name, type, "argument");
                tokenizer.advance();
            }
        }
        if(hasp)
            sm.define(name, type, "argument");
    }

    public void compileSubRoutineBody (String keyword) {
        String fun = "";
        if (tokenizer.symbol() == '{')
            tokenizer.advance();
        //we get all the var declarations in the spesific subroutine
        while (tokenizer.keyWord().equals("var") && (tokenizer.tokenType()==JackTokenizer.KEYWORD)) {
            tokenizer.decrementIndex();
            compileVarDec();
        }
        if(className.length()!=0 && subRoutineName.length()!=0)
            fun += className + "." + subRoutineName;
        vmw.writeFunction(fun, sm.varCount("var"));
        if(keyword.equals("method")) {
            vmw.writePush("argument", 0);
            vmw.writePop("pointer", 0);
        }
        else if (keyword.equals("constructor")) {
            vmw.writePush("constant", sm.varCount("field"));
            vmw.writeCall("Memory.alloc", 1);
            vmw.writePop("pointer", 0);
        }
        compileStatements();
    }

    public void compileVarDec() {
        String type = "",name = "";
        tokenizer.advance();
        if (tokenizer.keyWord().equals("var") && (tokenizer.tokenType()==JackTokenizer.KEYWORD))
            tokenizer.advance();
        //if we have identifier then what type of var
        if (tokenizer.tokenType()==JackTokenizer.IDENTIFIER) {
            type = tokenizer.identifier();
            tokenizer.advance();
        }
        //if we have keyword, then what type of var
        else if (tokenizer.tokenType()==JackTokenizer.KEYWORD) {
            type = tokenizer.keyWord();
            tokenizer.advance();
        }
        //the name of the var
        if (tokenizer.tokenType()==JackTokenizer.IDENTIFIER) {
            name = tokenizer.identifier();
            tokenizer.advance();
        }
        sm.define(name, type, "var");
        while(tokenizer.tokenType()==JackTokenizer.SYMBOL && tokenizer.symbol() == ','){
            tokenizer.advance();
            name = tokenizer.identifier();
            sm.define(name, type, "var");
            tokenizer.advance();
        }
        //the end of the var line
        if ((tokenizer.tokenType()==JackTokenizer.SYMBOL) && (tokenizer.symbol() == ';'))
            tokenizer.advance();
    }

    public void compileStatements() {
        if (tokenizer.symbol() == '}' && (tokenizer.tokenType()==JackTokenizer.SYMBOL))
            return;
        //let
        else if (tokenizer.keyWord().equals("let") && (tokenizer.tokenType()==JackTokenizer.KEYWORD))
            compileLet();
        //if
        else if (tokenizer.keyWord().equals("if") && (tokenizer.tokenType()==JackTokenizer.KEYWORD))
            compileIf();
        //while
        else if (tokenizer.keyWord().equals("while") && (tokenizer.tokenType()==JackTokenizer.KEYWORD))
            compileWhile();
        //do
        else if (tokenizer.keyWord().equals("do") && (tokenizer.tokenType()==JackTokenizer.KEYWORD))
            compileDo();
        //return 
        else if (tokenizer.keyWord().equals("return") && (tokenizer.tokenType()==JackTokenizer.KEYWORD))
            compileReturn();
        tokenizer.advance();
        compileStatements();
    }

    public void compileLet() {
        boolean arr = false;
        tokenizer.advance();
        String var = tokenizer.identifier();
        tokenizer.advance();
        if ((tokenizer.tokenType()==JackTokenizer.SYMBOL) && (tokenizer.symbol() == '[')) {
            //if we have an expression
            arr=true;
            vmw.writePush(sm.kindOf(var), sm.indexOf(var));
            compileExpression();
            tokenizer.advance();
            if ((tokenizer.tokenType()==JackTokenizer.SYMBOL) && ((tokenizer.symbol() == ']'))){
            }
            vmw.writeArithmetic("add");
            //advance only if there is an expression
            tokenizer.advance();
        }
        compileExpression();
        tokenizer.advance();
        if(arr){
            vmw.writePop("temp", 0);
            vmw.writePop("pointer", 1);
            vmw.writePush("temp", 0);
            vmw.writePop("that", 0);
        }
        else
            vmw.writePop(sm.kindOf(var), sm.indexOf(var));
    }

    public void compileIf() {
        String one,two;
        one = "LABEL_" + index++;
        two = "LABEL_" + index++;
        tokenizer.advance();
        //the expression in the if
        compileExpression();
        tokenizer.advance();
        vmw.writeArithmetic("not");
        vmw.writeIf(one);
        tokenizer.advance();
        //the statements in the if brackets
        compileStatements();
        vmw.writeGoto(two);
        vmw.writeLabel(one);
        tokenizer.advance();
        //if we have an else statment
        if (tokenizer.tokenType()==JackTokenizer.KEYWORD && tokenizer.keyWord().equals("else")) {
            tokenizer.advance();
            tokenizer.advance();
            //the statements in the else brackets
            compileStatements();
        } 
        else
            tokenizer.decrementIndex();
        vmw.writeLabel(two);
    }

    public void compileWhile() {
        String one,two;
        two = "LABEL_" + index++;
        one = "LABEL_" + index++;
        vmw.writeLabel(one);
        tokenizer.advance();
        //the inside of brackets
        compileExpression();
        tokenizer.advance();
        vmw.writeArithmetic("not");
        vmw.writeIf(two);
        tokenizer.advance();
        compileStatements();
        vmw.writeGoto(one);
        vmw.writeLabel(two);
    }

    public void compileDo() {
        if (tokenizer.keyWord().equals("do"))
        //function call
        compileCall();
        tokenizer.advance();
        vmw.writePop("temp", 0);
    }

    public void compileReturn() {
        tokenizer.advance();
        if (!((tokenizer.tokenType()==JackTokenizer.SYMBOL && tokenizer.symbol() == ';'))) {
            tokenizer.decrementIndex();
            compileExpression();
        }
        else if (tokenizer.tokenType()==JackTokenizer.SYMBOL && tokenizer.symbol() == ';')
            vmw.writePush("constant", 0);
        vmw.writeRetun();
    }

    private void compileCall() {
        String f,obj,type;
        int arg = 0;
        tokenizer.advance();
        f = tokenizer.identifier();
        tokenizer.advance();
        if ((tokenizer.tokenType()==JackTokenizer.SYMBOL) && (tokenizer.symbol() == '.')) {
            obj = f;
            tokenizer.advance();
            tokenizer.advance(); //only if "Main"
            f = tokenizer.identifier();
            if (f.equals("uble")) {
                f = "double";
            }
            else {
                tokenizer.decrementIndex();
                f = tokenizer.identifier();
            }
            type = sm.typeOf(obj);
            if (type.equals(""))
                f = obj + "." + f;
            else{
                String mykind = sm.kindOf(obj);
                int myindex = sm.indexOf(obj);
                arg = 1;
                vmw.writePush(mykind, myindex);
                f = sm.typeOf(obj) + "." + f;
            }
            tokenizer.advance();
            arg +=compileExpressionList();
            tokenizer.advance();
            vmw.writeCall(f, arg);
        }
        else if ((tokenizer.tokenType()==JackTokenizer.SYMBOL) && (tokenizer.symbol() == '(')) {
            vmw.writePush("pointer", 0);
            arg = compileExpressionList() + 1;
            tokenizer.advance();
            vmw.writeCall(className + "." + f, arg);
        }
    }
   
    private void compileExpression() {
        compileTerm();
        while (true) {
            tokenizer.advance();
            if (tokenizer.tokenType()==JackTokenizer.SYMBOL && tokenizer.isOperation()) {
                //if we have < > &
                if (tokenizer.symbol() == '<') {
                    compileTerm();
                    vmw.writeArithmetic("lt");
                } else if (tokenizer.symbol() == '&') {
                    compileTerm();
                    vmw.writeArithmetic("and");
                } else if (tokenizer.symbol() == '>') {
                    compileTerm();
                    vmw.writeArithmetic("gt");
                } else if (tokenizer.symbol() == '+') {
                        compileTerm();
                        vmw.writeArithmetic("add");
                } else if (tokenizer.symbol() == '-') {
                        compileTerm();
                        vmw.writeArithmetic("sub");
                } else if (tokenizer.symbol() == '*') {
                        compileTerm();
                        vmw.writeCall("Math.multiply", 2);
                } else if (tokenizer.symbol() == '/') {
                    compileTerm();
                    vmw.writeCall("Math.divide", 2);
                } else if (tokenizer.symbol() == '=') {
                    compileTerm();
                    vmw.writeArithmetic("eq");
                } else if (tokenizer.symbol() == '|') {
                    compileTerm();
                    vmw.writeArithmetic("or");
                }
            } 
            else {
                tokenizer.decrementIndex();
                break;
            }
        }
    }

    private void compileTerm() {
        tokenizer.advance();
        if (tokenizer.tokenType()==JackTokenizer.IDENTIFIER) {
            String prev = tokenizer.identifier();
            tokenizer.advance();
            if (tokenizer.tokenType()==JackTokenizer.SYMBOL && tokenizer.symbol() == '[') {
                vmw.writePush(sm.kindOf(prev), sm.indexOf(prev));
                compileExpression();
                tokenizer.advance();
                vmw.writeArithmetic("add");
                vmw.writePop("pointer", 1);
                vmw.writePush("that", 0);
            }
            else if (tokenizer.tokenType()==JackTokenizer.SYMBOL && (tokenizer.symbol() == '(' || tokenizer.symbol() == '.')) {
                tokenizer.decrementIndex();
                tokenizer.decrementIndex();
                compileCall();
            } 
            else {
                tokenizer.decrementIndex();
                vmw.writePush(sm.kindOf(prev), sm.indexOf(prev));
            }
        } 
        else {
            //int
            if (tokenizer.tokenType()==JackTokenizer.INT_CONST)
                vmw.writePush("constant", tokenizer.intVal());
            else if (tokenizer.tokenType() == JackTokenizer.KEYWORD && tokenizer.keyWord().equals("this"))
                vmw.writePush("pointer", 0);
            //operators
            else if (tokenizer.tokenType()==JackTokenizer.SYMBOL && (tokenizer.symbol() == '-' || tokenizer.symbol() == '~')) {
                //recursive call
                compileTerm();
                if (tokenizer.symbol()=='-')
                    vmw.writeArithmetic("neg");
                else if (tokenizer.symbol()=='~')
                    vmw.writeArithmetic("not");
            }
            //T,F or null
            else if (tokenizer.tokenType()==JackTokenizer.KEYWORD && (tokenizer.keyWord().equals("null") || tokenizer.keyWord().equals("false")))
                vmw.writePush("constant", 0);
            //string
            else if (tokenizer.tokenType()==JackTokenizer.STRING_CONST) {
                vmw.writePush("constant", tokenizer.stringVal().length());
                vmw.writeCall("String.new", 1);
                for (int i = 0 ; i < tokenizer.stringVal().length() ; i++){
                    vmw.writePush("constant", (int) tokenizer.stringVal().charAt(i));
                    vmw.writeCall("String.appendChar", 2);
                }
            }
            else if (tokenizer.tokenType()==JackTokenizer.KEYWORD && tokenizer.keyWord().equals("true")){
                vmw.writePush("constant", 0);
                vmw.writeArithmetic("not");
            }
            //brackets
            else if (tokenizer.tokenType()==JackTokenizer.SYMBOL && tokenizer.symbol() == '(') {
                compileExpression();
                tokenizer.advance();
            }
        }
    }

    private int compileExpressionList() {
        int arg = 0;
        tokenizer.advance();
        if (tokenizer.symbol() == ')' && tokenizer.tokenType()==JackTokenizer.SYMBOL)
            tokenizer.decrementIndex();
        else {
            arg = 1;
            tokenizer.decrementIndex();
            compileExpression();
        }
        while (true) {
            tokenizer.advance();
            if (tokenizer.tokenType()==JackTokenizer.SYMBOL && tokenizer.symbol() == ',') {
                compileExpression();
                arg++;
            }
            else {
                tokenizer.decrementIndex();
                break;
            }
        }
        return arg;
    }
}

