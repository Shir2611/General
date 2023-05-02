public class Perfect
{
	public static void main (String[] args)
	{
		int x=Integer.parseInt(args[0]);
		int sum=0;
		String div="1";
		
		if(x<=1)
			System.out.println("Please insert a number that is larger than 1");
		
		for (int i=1; i<=(int)(x/2); i++)
		{
			if (x%i==0)
			{
				sum=sum+i;
				if (i!=1)
				div=div+ " + " + i;
			}
		}
		if (sum==x)
			System.out.println(x + " is a perfect number since " + x + " = " + div);
		else
			System.out.println(x + " is not a perfect number");
		
	}
}
