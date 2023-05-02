public class Symbol {
    private String type;
    private String kind;
    private int num;

    public Symbol(String type,String kind,int num){
        this.type = type;
        this.kind = kind;
        this.num = num;
    }

    public String getType(){
        return this.type;
    }

    public String getKind(){
        return this.kind;
    }

    public int getNum(){
        return this.num;
    }

    @Override
    public String toString() {
        return "Symbol{" + "type='" + type + '\'' + ", kind=" + kind + ", num=" + num + '}';
    }
}
