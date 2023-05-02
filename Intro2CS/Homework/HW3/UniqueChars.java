/** String processing exercise 2. */
public class UniqueChars {
    public static void main(String[] args) {  
        String str = args[0];
        System.out.println(uniqueChars(str));
    }

    /**
     * Returns a string which is identical to the original string, 
     * except that all the duplicate characters are removed,
     * unless they are space characters.
     */
    public static String uniqueChars(String s)
	{
		String s1="";
		int length=s.length();
		
		for(int i=0; i<length; i++)
		{
			if((int)s.charAt(i)==32)
				s1=s1+" ";
			if(s1.indexOf(s.charAt(i))<0)
				s1=s1+s.charAt(i);
			
		}
		
        return s1;
    }
}
