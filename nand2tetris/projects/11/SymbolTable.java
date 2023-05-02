import java.io.*;
import java.util.HashMap;

public class SymbolTable {
    //for static and field
    private HashMap <String,Symbol> classTable; 
    //for arg and var
    private HashMap <String,Symbol> methodTable;
    private HashMap <String,Integer> indicies;

    //creates a new empty symbol table init all the indicies
    public SymbolTable(){
        classTable = new HashMap<>();
        methodTable = new HashMap<>();
        indicies = new HashMap<>();
        indicies.put("static", 0);
        indicies.put("field", 0);
        indicies.put("argument", 0);
        indicies.put("var", 0);

    }
    //reset the 4 indicies to 0 and clears the symbol table
    public void reset(){
        methodTable.clear();
        indicies.put("argument", 0);
        indicies.put("var", 0);
        indicies.put("field", 0);
        indicies.put("static", 0);

    }

    //defines a new identifier of a given name,type and kind
    public void define(String name, String type, String kind){
        int i = indicies.get(kind);
        Symbol s = new Symbol(type, kind, i);
        if(kind.equals("static")|| kind.equals("field")) {
            indicies.put(kind,i+1);
            classTable.put(name, s);
        }
        else if(kind.equals("argument")|| kind.equals("var")) {
            indicies.put(kind,i+1);
            methodTable.put(name, s);
        }
    }

    //returns the number of variables of the given kind already defined in the current scope
    public int varCount(String kind){
        int count = indicies.get(kind);
        return count;
    }

    //returns the kind of the named identifier in the current scope, 
    //if the identifier is unknown in the current scope returns NONE
    public String kindOf(String name){
        String kind = "";
        if (methodTable.get(name) != null) {
            kind = methodTable.get(name).getKind();
        }
        else if (classTable.get(name)!= null){
            kind = classTable.get(name).getKind();
        }
        else kind = "none";
        return kind;
    }

    //returns the type of the named identifier in the current scope
    public String typeOf (String name){
        String type = "";
        if (methodTable.get(name) != null) {
            type = methodTable.get(name).getType();
        }
        else if (classTable.get(name)!= null){
            type = classTable.get(name).getType();
        }
        else type = "";
        return type;
    }

    //returns the index assigned to the named identifier
    public int indexOf (String name){
        int index;
        if (methodTable.get(name) != null) {
            index = methodTable.get(name).getNum();
        }
        else if (classTable.get(name)!= null){
            index = classTable.get(name).getNum();
        }
        else index = -1;
        return index;
    }
}