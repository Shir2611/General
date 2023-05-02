public class Reverse
{
	public static void main (String[] args)
	{
		String s=args[0];
		int num=s.length();
		String snew="";
		
		if(num==1)
		{
			System.out.println(s);
			System.out.println("The middle character is " + s);
		}
		
		else
		{
			if(num==0)
				System.out.println("Please insert a word");
			
			else
			{
				for (int i=num; i>0; i--)
					snew=snew+(s.charAt(i-1));
				System.out.println(snew);
			
			int middle=num/2;
			System.out.println("The middle character is "+s.charAt(middle-1));
			}
		}


	}
}
