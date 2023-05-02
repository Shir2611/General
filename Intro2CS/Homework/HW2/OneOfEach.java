public class OneOfEach
{
	public static void main (String[] args)
	{
		int count=0;
		boolean t=false;
		boolean s=false;
		
		while(t==false||s==false)
		{
			if (Math.random()>=.5)
			{
				System.out.print("b ");
				count++;
				t=true;
			}
			else
			{
				System.out.print("g ");
				count++;
				s=true;
			}
		}
		
		System.out.println("");
		System.out.println("You made it... and you now have " + count + " children.");
		
	}
}
