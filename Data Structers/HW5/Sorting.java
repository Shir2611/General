import java.util.Random;

import Plotter.Plotter;


public class Sorting{

	final static int SELECT_VS_QUICK_LENGTH = 12;
	final static int MERGE_VS_QUICK_LENGTH = 15;
	final static int COUNTING_VS_QUICK_LENGTH = 16;
	final static int BUBBLE_VS_MERGE_LENGTH = 12;
	final static int MERGE_VS_QUICK_SORTED_LENGTH =11;
	final static double T = 600.0;
	
	/**
	 * Sorts a given array using the quick sort algorithm.
	 * At each stage the pivot is chosen to be the rightmost element of the subarray.
	 * 
	 * Should run in average complexity of O(nlog(n)), and worst case complexity of O(n^2)
	 * 
	 * @param arr - the array to be sorted
	 */
	public static void quickSort(double[] arr){
		quickSort(arr, 0, arr.length - 1);
	}
	
	public static void quickSort(double[] arr, int pivot, int r){
		if (pivot < r){
			int index = partition(arr, pivot, r);
			quickSort(arr, pivot, index - 1);
			quickSort(arr, index + 1, r);
		}
	}	

	private static int partition (double[] arr, int pivot, int r){
		double x = arr[r];
		int start = pivot - 1;
		for(int j = pivot; j < r; j++){
			if (arr[j] <= x){
				swap(arr, start + 1, j);
				start++;
			}
		}
		swap(arr, start + 1 , r);
		return start + 1;
	}
	
	private static void swap (double[] arr, int left, int right) {
		double temp = arr[left];
		arr[left] = arr[right];
		arr[right] = temp;
	}

	/**
	 * Given an array arr and an index i returns the the i'th order statstics in arr. 
	 * In other words, it returns the element with rank i in the array arr.
	 * 
	 * At each stage the pivot is chosen to be the rightmost element of the subarray.
	 * 
	 * Should run in average complexity of O(n), and worst case complexity of O(n^2)
	 * 
	 **/
	public static double QuickSelect(double[] arr, int i){
		return QuickSelect(arr, 0, arr.length - 1, i);
	}
	
	public static double QuickSelect(double[] arr, int p, int r, int i){
		if (p == r){
			return arr[p];
		}
		int q = partition(arr, p, r);
		int m = q - p + 1;
		if(i == m){
			return arr[q];
		}
		if (i < m){
			return QuickSelect(arr, p, q - 1, i);
		}
		return QuickSelect(arr, q++, r, i - m + 1);
	}
	
	
	/**
	 * Sorts a given array using the merge sort algorithm.
	 * 
	 * Should run in complexity O(nlog(n)) in the worst case.
	 * 
	 * @param arr - the array to be sorted
	 */
	public static void mergeSort(double[] arr){
		mergeSort(arr, 0, arr.length - 1);
	}

	public static void mergeSort(double[] arr, int pivot, int r){
		if (pivot < r){
			int q = (int)((pivot + r) / 2);
			mergeSort(arr, pivot, q);
			mergeSort(arr, q + 1, r);
			merge(arr, pivot, q, r);
		}
	}
	
	public static void merge (double[] arr, int pivot, int q, int r){
		int index = 0;
		double[] L = new double[q - pivot + 2];
		double[] R = new double[r - q + 1];
		for (int i = pivot; i < q + 1; i++){
			L[index] = arr[i];
			index++;
		}
		L[L.length - 1] = Double.POSITIVE_INFINITY;
		R[R.length - 1] = Double.POSITIVE_INFINITY;
		index = 0;
		for (int j = q + 1; j < r + 1; j++){
			R[index] = arr[j];
			index++;
		}
		int i = 0, j = 0;
		for (int k = pivot; k < r + 1; k++){
			if (L[i] <= R[j]){
				arr[k] = L[i];
				i++;
			}
			else {
				arr[k] = R[j];
				j++;
			}
		}
	}

	/**
	 * Sorts a given array using bubble sort.
	 * 
	 * The algorithm should run in complexity O(n^2).
	 * 
	 * @param arr - the array to be sorted
	 */
	public static void bubbleSort(double[] arr){
		for (int i = 0; i < arr.length - 2; i++){
			for (int j = 1; j < arr.length - i; j++){
				if (arr[j - 1] > arr[j]){
					swap(arr, j--, j);
				}
			}
		}
	}

	/**
	 * Sorts a given array, using the counting sort algorithm.
	 * You may assume that all elements in the array are between 0 and k (not including k).
	 * 
	 * Should run in complexity O(n + k) in the worst case.
	 * 
	 * @param arr - an array with possitive integers
	 * @param k - an upper bound for the values of all elements in the array.
	 */
	public static void countingSort(int[] arr, int k){
		int[] C = new int[k+1];
		int[] B = new int[arr.length];
		for (int i = 0; i < arr.length; i++){
			C[arr[i]]++;
		}
		for (int j = 1; j < C.length; j++){
			C[j] = C[j] + C[j - 1];
		}
		for (int l = arr.length - 1; l >= 0; l--){
			B[C[arr[l]]-1] = arr[l];
			C[arr[l]]--;
		}
		for (int i = 0 ; i < arr.length; i++){
			arr[i] = B[i];
		}
	}

	 
    
	public static void main(String[] args) {
	
		countingVsQuick();
		mergeVsQuick();
		mergeVsQuickOnSortedArray();
		mergeVsBubble();
		QuickSelectVsQuickSort();
		
	}
	


	private static void countingVsQuick() {
		double[] quickTimes = new double[COUNTING_VS_QUICK_LENGTH];
		double[] countingTimes = new double[COUNTING_VS_QUICK_LENGTH];
		long startTime, endTime;
		Random r = new Random();
		for (int i = 0; i < COUNTING_VS_QUICK_LENGTH; i++) {
			long sumQuick = 0;
			long sumCounting = 0;
			for(int k = 0; k < T; k++){
				int size = (int)Math.pow(2, i);
				double[] a = new double[size];
				int[] b = new int[size];
				for (int j = 0; j < a.length; j++) {
					b[j] = r.nextInt(size);
					a[j] = b[j];
				}
				startTime = System.currentTimeMillis();
				quickSort(a);
				endTime = System.currentTimeMillis();
				sumQuick += endTime - startTime;
				startTime = System.currentTimeMillis();
				countingSort(b, size);
				endTime = System.currentTimeMillis();
				sumCounting += endTime - startTime;
			}
			quickTimes[i] = sumQuick/T;
			countingTimes[i] = sumCounting/T;
		}
		Plotter.plot("Counting sort on arrays with elements < n", countingTimes, "Quick sort on arrays with elements < n", quickTimes);
		
	}


	
	/**
	 * Compares the merge sort algorithm against quick sort on random arrays
	 */
	public static void mergeVsQuick(){
		double[] quickTimes = new double[MERGE_VS_QUICK_LENGTH];
		double[] mergeTimes = new double[MERGE_VS_QUICK_LENGTH];
		long startTime, endTime;
		Random r = new Random();
		for (int i = 0; i < MERGE_VS_QUICK_LENGTH; i++) {
			long sumQuick = 0;
			long sumMerge = 0;
			for (int k = 0; k < T; k++) {
				int size = (int)Math.pow(2, i);
				double[] a = new double[size];
				double[] b = new double[size];
				for (int j = 0; j < a.length; j++) {
					a[j] = r.nextGaussian() * 5000;
					b[j] = a[j];
				}
				startTime = System.currentTimeMillis();
				quickSort(a);
				endTime = System.currentTimeMillis();
				sumQuick += endTime - startTime;
				startTime = System.currentTimeMillis();
				mergeSort(b);
				endTime = System.currentTimeMillis();
				sumMerge += endTime - startTime;
			}
			quickTimes[i] = sumQuick/T;
			mergeTimes[i] = sumMerge/T;
		}
		Plotter.plot("quick sort on random array", quickTimes, "merge sort on random array", mergeTimes);
	}
	
	/**
	 * Compares the merge sort algorithm against quick sort on pre-sorted arrays
	 */
	public static void mergeVsQuickOnSortedArray(){
		double[] quickTimes = new double[MERGE_VS_QUICK_SORTED_LENGTH];
		double[] mergeTimes = new double[MERGE_VS_QUICK_SORTED_LENGTH];
		long startTime, endTime;
		for (int i = 0; i < MERGE_VS_QUICK_SORTED_LENGTH; i++) {
			long sumQuick = 0;
			long sumMerge = 0;
			for (int k = 0; k < T; k++) {
				int size = (int)Math.pow(2, i);
				double[] a = new double[size];
				double[] b = new double[size];
				for (int j = 0; j < a.length; j++) {
					a[j] = j;
					b[j] = j;
				}
				startTime = System.currentTimeMillis();
				quickSort(a);
				endTime = System.currentTimeMillis();
				sumQuick += endTime - startTime;
				startTime = System.currentTimeMillis();
				mergeSort(b);
				endTime = System.currentTimeMillis();
				sumMerge  += endTime - startTime;
			}
			quickTimes[i] = sumQuick/T;
			mergeTimes[i] = sumMerge/T;
		}
		Plotter.plot("quick sort on sorted array", quickTimes, "merge sort on sorted array", mergeTimes);
	}
	/**
	 * Compares merge sort and bubble sort on random arrays
	 */
	public static void mergeVsBubble(){
		double[] mergeTimes = new double[BUBBLE_VS_MERGE_LENGTH];
		double[] bubbleTimes = new double[BUBBLE_VS_MERGE_LENGTH];
		long startTime, endTime;
		Random r = new Random();
		for (int i = 0; i < BUBBLE_VS_MERGE_LENGTH; i++) {
			long sumMerge = 0;
			long sumBubble = 0;
			for(int k = 0; k < T; k++){
				int size = (int)Math.pow(2, i);
				double[] a = new double[size];
				double[] b = new double[size];
				for (int j = 0; j < a.length; j++) {
					a[j] = r.nextGaussian() * 5000;
					b[j] = a[j];
				}
				startTime = System.currentTimeMillis();
				mergeSort(a);
				endTime = System.currentTimeMillis();
				sumMerge += endTime - startTime;
				startTime = System.currentTimeMillis();
				bubbleSort(b);
				endTime = System.currentTimeMillis();
				sumBubble += endTime - startTime;
			}
			mergeTimes[i] = sumMerge/T;
			bubbleTimes[i] = sumBubble/T;
		}
		Plotter.plot("merge sort on random array", mergeTimes, "bubble sort on random array", bubbleTimes);
	}
	
	



	/**
	 * Compares the quick select algorithm with a random rank, and the quick sort algorithm.
	 */
	public static void QuickSelectVsQuickSort(){
		double[] QsortTimes = new double[SELECT_VS_QUICK_LENGTH];
		double[] QselectTimes = new double[SELECT_VS_QUICK_LENGTH];
		Random r = new Random();
		long startTime, endTime;
		for (int i = 0; i < SELECT_VS_QUICK_LENGTH; i++) {
			long sumQsort = 0;
			long sumQselect = 0;
			for (int k = 0; k < T; k++) {
				int size = (int)Math.pow(2, i);
				double[] a = new double[size];
				double[] b = new double[size];
				for (int j = 0; j < a.length; j++) {
					a[j] = r.nextGaussian() * 5000;
					b[j] = a[j];
				}
				startTime = System.currentTimeMillis();
				quickSort(a);
				endTime = System.currentTimeMillis();
				sumQsort += endTime - startTime;
				startTime = System.currentTimeMillis();
				QuickSelect(b, r.nextInt(size) + 1);
				endTime = System.currentTimeMillis();
				sumQselect += endTime - startTime;
			}
			QsortTimes[i] = sumQsort/T;
			QselectTimes[i] = sumQselect/T;
		}
		Plotter.plot("quick sort with an arbitrary pivot", QsortTimes, "quick select with an arbitrary pivot, and a random rank", QselectTimes);
	}
	

}
