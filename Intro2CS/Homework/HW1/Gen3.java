public class Gen3
{
	public static void main(String[] args)
	{
		int num1=Integer.parseInt(args[0]);
		int num2=Integer.parseInt(args[1]);
		
		int x1=(int)(Math.random()*(num2-num1))+num1;
		int x2=(int)(Math.random()*(num2-num1))+num1;
		int x3=(int)(Math.random()*(num2-num1))+num1;
	
		
		
		System.out.println(x1);
		System.out.println(x2);
		System.out.println(x3);
		
		int mini=Math.min(x1,x2);
		if (x3<mini)
		System.out.println("The minimal generated number was " + x3);
		else
		System.out.println("The minimal generated number was " + mini);
		
	}
}