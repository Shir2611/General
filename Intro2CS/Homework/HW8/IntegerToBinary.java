/* Features a function that prints the decimal value of a given integer value. */
public class IntegerToBinary {

    public static void main(String[] args) {
        integerToBinary(Integer.parseInt(args[0]));
        System.out.println();
    }

    public static void integerToBinary(int n)
    {
        
        if(n==0)
            System.out.print("0");

        if(n==1)
            System.out.print("1");

        if(n>1)
        {
            integerToBinary(n/2);
            System.out.print(Integer.toString(n%2));
        }
    }
 }
