public class Tester{
	
	public static void main(String[] args){
		
		// create variables
		double[] arr1 = { 3.5, 1.0, 2.3, 0.0, 10.8, 7.3, 1.1, 5.7 };
		double[] arr2 = { 3.5, 1.0, 2.3, 0.0, 10.8, 7.3, 1.1, 5.7, 20.5, 13.4, 0.5, 2.0 };
		double[] arr3 = { 1.0, 3.0, 8.0, 2.0, 7.0, 6.0, 5.0, 4.0 };
		double[] arr4 = { 1.0, 3.0, 8.0, 2.0, 7.0, 6.0, 5.0, 4.0 };  // copy purpose
		double[] arr5 = { 3.5, 1.0, 2.3, 0.0, 10.8, 7.3, 1.1, 5.7, 20.5, 13.4, 0.5, 2.0 };
		int[] arr6 = { 0, 8, 20, 15, 9, 7, 13, 5, 1, 4, 20, 8, 3 };
		
		// test Bubble-Sort
		System.out.println("Testing Bubble-Sort:");
		Sorting.bubbleSort(arr1);
		printArr(arr1);
		System.out.println();
		// expected result: [ 0.0, 1.0, 1.1, 2.3, 3.5, 5.7, 7.3, 10.8 ]
		
		// test Quick-Sort
		System.out.println("Testing Quick-Sort:");
		Sorting.quickSort(arr2);
		printArr(arr2);
		System.out.println();
		// expected result: [ 0.0, 0.5, 1.0, 1.1, 2.0, 2.3, 3.5, 5.7, 7.3, 10.8, 13.4, 20.5 ]
		
		// test Quick-Select
		System.out.println("Testing Quick-Select:");
		double[] arr8 = new double[arr4.length];
		for(int i=0; i<arr4.length; i++){
			arr8[i] = Sorting.QuickSelect(arr4, i+1);
			copyArr(arr3, arr4);
		}
		printArr(arr8);
		System.out.println();
		// expected result: [ 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0 ]
		
		// test Merge-Sort
		System.out.println("Testing Merge-Sort:");
		Sorting.mergeSort(arr5);
		printArr(arr5);
		System.out.println();
		// expected result: [ 0.0, 0.5, 1.0, 1.1, 2.0, 2.3, 3.5, 5.7, 7.3, 10.8, 13.4, 20.5 ]
		
		// test Counting-Sort
		System.out.println("Testing Counting-Sort:");
		Sorting.countingSort(arr6, 21);
		printArr(arr6);
		System.out.println();
		// expected result: [0, 1, 3, 4, 5, 7, 8, 8, 9, 13, 15, 20, 20]
	}
	
	
	/**
	 * Prints an array
	 * @param arr - given array to print  
	 **/
	public static void printArr(double[] arr){
		int n = arr.length;
		String str = "[";
		for(int i=0;i<n;i++){
			str += arr[i];
			if(i<n-1) str += ", "; 
		}
		str += "]";
		System.out.println(str);
	}
	
	public static void printArr(int[] arr){
		int n = arr.length;
		String str = "[";
		for(int i=0;i<n;i++){
			str += arr[i];
			if(i<n-1) str += ", "; 
		}
		str += "]";
		System.out.println(str);
	}
	
	public static void copyArr(double[] source, double[] target) {
		if (source.length != target.length) throw new RuntimeException("Invalid call");
		for(int i=0; i<source.length; i++){
			target[i] = source[i];
		}
	}
}