/** Draws the Koch curve and the the Koch snowflake fractal. */
public class Koch {

	public static void main(String[] args) {

		//// Uncomment the first code block to test the curve function.
		//// Uncomment the second code block to test the snowflake function.
		//// Uncomment only one block in each test, and remember to compile
		//// the class whenever you change the test.

        
		// Tests the curve function:
		// Gets n, x1, y1, x2, y2,
		// and draws a Koch curve of depth n from (x1,y1) to (x2,y2).
		/*curve(Integer.parseInt(args[0]),
			  Double.parseDouble(args[1]), Double.parseDouble(args[2]), 
		      Double.parseDouble(args[3]), Double.parseDouble(args[4]));
		*/

		
		// Tests the snowflake function:
		// Gets n, and draws a Koch snowflake of n edges in the standard canvass.
		snowFlake(Integer.parseInt(args[0]));
		
	}

	/** Gets n, x1, y1, x2, y2,
     *  and draws a Koch curve of depth n from (x1,y1) to (x2,y2). */
	public static void curve(int n, double x1, double y1, double x2, double y2)
	{
		//// Write the rest of your code below.
		double x1new=x1+((x2-x1)/3);
		double y1new=y1+((y2-y1)/3);
		double x2new=x1+(((x2-x1)/3)*2);
		double y2new=y1+(((y2-y1)/3)*2);
		double xp3= ((Math.sqrt(3)/6)*(y1-y2)+((0.5)*(x1+x2)));
		double yp3= ((Math.sqrt(3)/6)*(x2-x1))+((0.5)*(y1+y2));

		if(n==0)
			return;
		if(n==1)
			StdDraw.line(x1, y1, x2, y2);

		if(n>1)
		{
			curve(n-1,x1,y1, x1new, y1new);
			curve(n-1,x1new, y1new,xp3,yp3);
			curve(n-1,xp3,yp3, x2new, y2new);
			curve(n-1,x2new,y2new, x2, y2);
		}


	}

    /** Gets n, and draws a Koch snowflake of n edges in the standard canvass. */
	public static void snowFlake(int n)
	{
		// A little tweak that makes the drawing look better
		StdDraw.setYscale(0, 1.1);
		StdDraw.setXscale(0, 1.1);
		// Draws a Koch snowflake of depth n
		//// Write the rest of your code below.
		curve(n, 0.2, 0.9, 0.9 , 0.9);
		curve(n, 0.55, (18-(7*Math.sqrt(3)))/20, 0.2, 0.9);
		curve(n,0.9, 0.9, 0.55, (18-(7*Math.sqrt(3)))/20);

	}
}
