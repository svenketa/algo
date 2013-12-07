import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

/**
 * The class <code>Knapsack</code> is an implementation of a greedy algorithm to solve the knapsack problem.
 *
 */
public class Knapsack {
    
    /**
     * The main class
     */
    public static void main(String[] args) {
        try {
            solve("/Users/sharadha/Downloads/knapsack4.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Read the instance, solve it, and print the solution in the standard output
     */
    public static void solve(String fileName) throws IOException {
        
        if(fileName == null)
            return;
        
        // read the lines out of the file
        List<String> lines = new ArrayList<String>();

        BufferedReader input =  new BufferedReader(new FileReader(fileName));
        try {
            String line = null;
            while (( line = input.readLine()) != null){
                lines.add(line);
            }
        }
        finally {
            input.close();
        }
        
        
        // parse the data in the file
        String[] firstLine = lines.get(0).split("\\s+");
        int capacity = Integer.parseInt(firstLine[0]);
        int items = Integer.parseInt(firstLine[1]);


        int[] values = new int[items+1];
        int[] weights = new int[items+1];

        for(int i=1; i < items+1; i++){
          String line = lines.get(i);
          String[] parts = line.split("\\s+");

          values[i] = Integer.parseInt(parts[0]);
          weights[i] = Integer.parseInt(parts[1]);
        }
   
        Knapsack(values, weights, items, capacity);
        
        
    }

	private static void Knapsack(final int[] v, final int[] w, final int n,
			final int W) {
		
		HashSet h = new HashSet(); 
        
		int[][] A = new int[n+1][W+1];
		
		for (int i=0; i<W; i++){
			A[0][i] = 0; 
		}

		for (int i=1; i<=n; i++){
			for (int j=1; j<=W; j++){
				if (w[i] > j){
					A[i][j] = A[i-1][j];
				}else{
					A[i][j] = Math.max(v[i] + A[i-1] [j-w[i]], A[i-1][j]);
				}
				h.add(A[i][j]);
			}
		}
		
		System.out.println("Optimal Solution for Knapsack = "+A[n][W]);
	}
}