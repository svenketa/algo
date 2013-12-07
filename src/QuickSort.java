import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;



public class QuickSort {
	
	static long count = 0;
	
	static String PIVOT_TYPE_FIRST = "PIVOT_FIRST";
	static String PIVOT_TYPE_LAST = "PIVOT_LAST";
	static String PIVOT_TYPE_MEDIAN = "PIVOT_MEDIAN";
	

	private static int getPivotalAndPartition(long[] arr, int left, int right, String pivotType){
		
		long pivot;
		if (pivotType.equals(PIVOT_TYPE_FIRST)){
				
		} else if (pivotType.equals(PIVOT_TYPE_LAST)){
			swap(arr, left, right);
		} else if (pivotType.equals(PIVOT_TYPE_MEDIAN)){
			int middle = (int)Math.floor((right + left)/2);
			int median;
			if ((arr[middle] > arr[left] && arr[middle] < arr[right]) || (arr[middle] < arr[left] && arr[middle] > arr[right])){
				median = middle;
			} else if ((arr[left] > arr[middle] && arr[left] < arr[right]) || (arr[left] < arr[middle] && arr[left] > arr[right])){
				median = left;
			} else {
				median = right;
			}
			
			swap(arr, left, median);
		}
		
		pivot = arr[left];
		
		int i = left + 1;
		for (int j=left+1; j <= right; j++){
			if (arr[j] < pivot){
				swap(arr, i, j);
				i++;
			}
		}
		swap(arr, left, i-1);
		
		count = count + right - left;
		
		return i;
	}
	
	
	private static void swap(long[] a, int i, int j) {
        long temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
	
	public static void sort(long[] arr, int left, int right, String pivotType){
		
		if (left < right){
			int partition = getPivotalAndPartition(arr, left, right, pivotType);
			sort(arr, left, partition - 2, pivotType);
			sort(arr, partition, right, pivotType);	
		}
		
	}
	
	
	
	public static void main(String[] args) throws NumberFormatException, IOException{
		long[] intArray = new long[10000];
		count = 0;
		 
		intArray = loadArray(intArray);

		sort(intArray, 0, intArray.length - 1, PIVOT_TYPE_FIRST);
		System.out.println(count);
		
		intArray = new long[10000];
		intArray = loadArray(intArray);
		 count = 0;
		sort(intArray, 0, intArray.length - 1, PIVOT_TYPE_LAST);
		System.out.println(count);
		
		intArray = new long[10000];
		intArray = loadArray(intArray);
		 count = 0;
		sort(intArray, 0, intArray.length - 1, PIVOT_TYPE_MEDIAN);
		System.out.println(count);
		
		
	}
	
	private static long[] loadArray(long[] intArray) throws NumberFormatException, IOException{
		int i=0;
		File file = new File("/Users/sharadha/Downloads/QuickSort.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		while ((line = br.readLine()) != null) {
		   // process the line.
			intArray[i++] = new Long(line).longValue();
		}
		br.close();
		return intArray;
	}
}
