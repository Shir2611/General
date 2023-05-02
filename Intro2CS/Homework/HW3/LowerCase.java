
/** String processing exercise 1. */
public class LowerCase {
    public static void main(String[] args) {  
        String str = args[0];
        System.out.println(lowerCase(str));
    }

   /**
    * Returns a string which is identical to the original string, 
    * except that all the upper-case letters are converted to lower-case letters.
    * Non-letter characters are left as is.
    */
    public static String lowerCase(String s)
	{
       String s1="";
	   int lenght=s.length();
	   
	   for(int i=0; i<lenght; i++)
	   {
			int asciivalue=(int)(s.charAt(i));
			if(asciivalue>=65 && asciivalue<=90)
				s1=s1+(char)(asciivalue+32);
			else
				s1=s1+s.charAt(i);
			
		}
				
		return s1;

    }
}