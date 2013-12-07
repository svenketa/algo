import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;


public class TwoSum {
	
	private static double[] loadArray() throws NumberFormatException, IOException{
		double[] doubleArray = new double[1000000];
		int i=0;
		
		File file = new File("/Users/sharadha/Downloads/algo1-programming_prob-2sum.txt");
		//File file = new File("/Users/sharadha/Downloads/algo1.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		while ((line = br.readLine()) != null) {
		   // process the line.
			doubleArray[i++] = new Double(line).doubleValue();
		}
		br.close();
		return doubleArray;

	}
	
	public static void main(String[] args) throws NumberFormatException, IOException{
//		double[] doubleArray = loadArray();
//		Arrays.sort(doubleArray);
		
		Hashtable<Double, Boolean> doubleHashTable = loadHashtable();
		
		int count = 0;

//		for (double i=-10000; i <= 10000; i++){
//			if (twoSumHashtable(doubleHashTable, i)){
//				count ++;
//			}
//		}

		System.out.println("twoSumHashtable of 6 ="+twoSumHashtable(doubleHashTable, 6));
		System.out.println("twoSumHashtable of 100 ="+twoSumHashtable(doubleHashTable, 100));
		//System.out.println("Number of 2Sum = "+count);
	}
	
	private static Hashtable<Double, Boolean> loadHashtable() throws IOException {
		Hashtable<Double, Boolean> doubleHashTable = new Hashtable<Double, Boolean>();
		
		//File file = new File("/Users/sharadha/Downloads/algo1-programming_prob-2sum.txt");
		File file = new File("/Users/sharadha/Downloads/algo1.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		while ((line = br.readLine()) != null) {
		   // process the line.
			doubleHashTable.put(new Double(line).doubleValue(), true);
		}
		br.close();
		return doubleHashTable;
	}

	public static boolean twoSum(double[] doubleArray, double sum){
		
		for (int i = 0; i < doubleArray.length - 1; i++) {
	        double tofind = sum - doubleArray[i];
	        double returned = Arrays.binarySearch(doubleArray, i + 1, doubleArray.length - 1, tofind);
	        if (returned > 0) {
	            return true;
	        }
	    }
		
		return false;
	}
	
	public static boolean twoSumHashtable(Hashtable<Double, Boolean> doubleHashTable, double sum){
		
		for (Double value : doubleHashTable.keySet()) {
	        double tofind = sum - value;
	        Object returned = doubleHashTable.get(tofind);
	        if (returned != null) {
	            return true;
	        }
	    }
		
		return false;
	}

}
