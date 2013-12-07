import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;


public class Inversion {
	
	static double merge(double[] arr, double[] left, double[] right) {
	    int i = 0, j = 0;
	    double count = 0;
	    while (i < left.length || j < right.length) {
	        if (i == left.length) {
	            arr[i+j] = right[j];
	            j++;
	        } else if (j == right.length) {
	            arr[i+j] = left[i];
	            i++;
	        } else if (left[i] <= right[j]) {
	            arr[i+j] = left[i];
	            i++;                
	        } else {
	            arr[i+j] = right[j];
	            count += left.length-i;
	            j++;
	        }
	    }
	    return count;
	}

	static double invCount(double[] arr) {
	    if (arr.length < 2)
	        return 0;

	    int m = (arr.length + 1) / 2;
	    double left[] = Arrays.copyOfRange(arr, 0, m);
	    double right[] = Arrays.copyOfRange(arr, m, arr.length);

	    return invCount(left) + invCount(right) + merge(arr, left, right);
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException{
		double[] intArray = new double[100000];
		int i=0;
		
		File file = new File("/Users/sharadha/Downloads/IntegerArray.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		while ((line = br.readLine()) != null) {
		   // process the line.
			intArray[i++] = new Double(line).doubleValue();
		}
		br.close();

		System.out.println(invCount(intArray));
	}

}
