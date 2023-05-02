/**
 * A library of basic matrix operations.
 */
public class MatrixOps {
	/**
	 * Returns the matrix resulting from adding the two given matrices,
	 * or null if the matrices don't have the same dimensions.
	 */
	public static int[][] add(int[][] m1, int[][] m2)
	{
		int N= m1.length;
		int M= m1[0].length;
		int[][] sum = new int[N][M];
		 if((m1.length!=m2.length)||(m1[0].length!=m2[0].length))
			 return null;
		
		for (int i =0; i<N; i++)
		{
			for (int j=0; j<M; j++)
				sum[i][j] = m1[i][j] + m2[i][j];
		}
		return sum;
	}

	/**
	 * Returns a unit matrix of the given size.
	 * A unit matrix of size N is a square N x N matrix that contains 0's
	 * in all its cells, except that the cells in the diagonal contain 1.
	 */
	public static int[][] unit(int n)
	{
		int [][] unitMat = new int [n][n];
		for (int i =0; i<n; i++)
		{
			for (int j=0; j<n; j++)
			{
				if ( i==j)
					unitMat[i][j] = 1;
				else
					unitMat[i][j] = 0;
			}
		}
		
		return unitMat;
	}

	/**
	 * Returns the matrix resulting from multuplying the two matrices,
	 * or null if they have incompatible dimensions.
	 */
	public static int[][] mult(int[][] m1, int[][] m2)
	{
		int sum=0;
		int col=m1[0].length;
		int row=m2.length;
		int newrow = m1.length;
		int newcol= m2[0].length;
		int [][] multMat= new int[newrow][newcol];
		if (row!=col)
			return null;
		else
		{
			for(int i=0; i<newrow; i++)
			{
				for (int j=0; j<newcol; j++)
				{
					for (int k=0; k<col; k++)
					{
						sum=sum+(m1[i][k]*m2[k][j]);
					}
					multMat[i][j]=sum;
					sum=0;
				}
			}
			
		}
		
		return multMat;
	}

	/**
	 * Returns a matrix which is the transpose of the given matrix.
	 */
	public static int[][] transpose(int[][] m) 
	{
		int rows = m.length;
		int columns = m[0].length;
		int [][] transMat = new int [columns][rows];
		
		for(int i=0; i<rows; i++)
		{
			for (int j=0; j<columns; j++)
			{
				transMat[j][i]=m[i][j];
			}
		}
		
		return transMat;
	}

	/**
	 * Prints the given matrix, and then prints an empty line.
	l */
	public static void println(int[][] m) {
		for (int row = 0; row < m.length; row++) {
			for (int col = 0; col < m[1].length; col++) {
				System.out.print(m[row][col] + "  ");
			}
			System.out.println();
		}
		System.out.println();
	}
		
	/** 
	 * Tests all the matrix operations featured by this class.
	 */ 
	public static void main(String args[]) {
		// Creates two matrices, for testing
		int[][] a = { { 1, 2, 1 },
			          { 0, 1, 1 },
					  { 2, 0, 1 } };
						  
		int[][] b = { { 1, 0, 2 },
			          { 1, 2, 0 },
					  { 2, 0, 1 } };	
		System.out.println("Matrix A:");  println(a); 
		System.out.println("Matrix B:");  println(b); 

        //// Uncomment the statements that you wish to execute.
	    System.out.println("A + B:");  println(add(a,b)); 
		System.out.println("A * B:");  println(mult(a,b)); 
		System.out.println("I (a unit matrix of size 3):"); println(unit(3));
		System.out.println("A * I: "); println(mult(a,unit(3)));
		
		int[][] c = { { 1, 2, 3 },
		              { 4, 5, 6 }, };
		System.out.println("Matrix C:"); println(c);
		System.out.println("C, transposed:"); println(transpose(c));

		////System.out.println("Random sum test, using matrix C:");
		////sumRandom(c);
	}
}