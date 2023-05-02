public class MyArrays {

    // Two arrays, for testing purposes. Used by the testing methods in this class.
    private static final int[] a = { 2, 4, 2, 5};
    private static final int[] b = { 3, 6, 9};
	
	/** 
     * If every element in the array is greater than or equal to the previous element, returns true.
	 * Otherwise, returns false.
     */
	public static boolean isInIncreasingOrder(int[] arr)
	{
    	for(int i=1; i<arr.length; i++)
		{
		if(arr[i]<arr[i-1])
			return false;
		}
		return true;
    }

    /**
     * Returns an array whose elements consist of all the elements of arr1,
     * followed by all the elements of arr2.
     */
    public static int[] concat(int[] arr1, int[] arr2) 
	{
        int[] bigarr = new int [arr1.length+arr2.length];
		
		for (int k=0; k<arr1.length; k++)
			bigarr[k]=arr1[k];
		
		for (int j=0; j<arr2.length; j++)
			bigarr[arr1.length+j]=arr2[j];
		
        return bigarr;
    }
	
	/** If the given array contains an element that appears more than once, returns true.
	 *  Otherwise, returns false. */
	public static boolean hasDuplicates(int[] arr) 
	{
    	for( int i=0; i<arr.length; i++)
		{
			for ( int j=i+1; j<arr.length; j++)
			{
				if(arr[i]==arr[j])
					return true;
			}
		}
		return false;
    }

	// Prints the given int array​, and then prints an empty line.
	public static void println(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

    public static void main(String[] args)
	{
        System.out.print("Array a: "); println(a);
        System.out.print("Array b: "); println(b);
        //// Uncomment the test that you wish to execute
		testIsInIncreasingOrder();
		testConcat();
		testHasDuplicates();
        ////testIsInIncreasingOrder();
		////testConcat();
		////testHasDuplicates();
	}

    private static void testIsInIncreasingOrder() {
        System.out.println();
        System.out.println("Array a is " + ((isInIncreasingOrder(a)) ? "" : "not ") + "in order");
		System.out.println("Array b is " + ((isInIncreasingOrder(b)) ? "" : "not ") + "in order");
	}

    private static void testConcat() {
        System.out.println();
        System.out.print("Concatenantion of a and b: "); println(concat(a, b));
	}
    	
    private static void testHasDuplicates() {
        System.out.println();
        System.out.println("Array a has " + ((hasDuplicates(a)) ? "" : "no ") + "duplicates");
		System.out.println("Array b has " + ((hasDuplicates(b)) ? "" : "no ") + "duplicates");
	}
}