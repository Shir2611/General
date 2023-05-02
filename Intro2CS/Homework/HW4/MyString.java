public class MyString {
	public static void main(String []args) {
		// Calls parseInt, and adds 1 to the returned value,
		// to verify that the returned value is indeed the correct int.
		System.out.println(parseInt("5613") + 1);
		System.out.println(parseInt("9a7"));
	}
	
	/**
	 * Returns the integer value of the given string of digit characters,
	 * or -1 if the string contains one or more non-digit characters.
	 */
	public static int parseInt(String str)
	{
		int nnum=0;
		for( int i=0; i<str.length(); i++)
		{
			if((int)str.charAt(i)<=47||(int)str.charAt(i)>=58)
				return -1;
		}
		
		for( int j=0; j<str.length(); j++)
		{
			int realN= ((int)str.charAt(j)-48);
			nnum= nnum + (realN*(int)(Math.pow(10,str.length()-(j+1))));
		}		
   		return nnum;
	}
}