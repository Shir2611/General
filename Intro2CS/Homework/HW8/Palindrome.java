/** Reads a command line string and checks if it's a palindrome. */
public class Palindrome {

	public static void main(String[]args) {
    	System.out.println(isPalindrome(args[0]));
    }
	
	public static boolean isPalindrome(String s)
	{
		if (s.length()==1||s.length()==0)
			return true;

		if(s.charAt(0)==s.charAt(s.length()-1))
			return isPalindrome(s.substring(1, s.length()-1));

		return false;

		
    }
}