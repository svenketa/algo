

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KnapsackBig {
	private static final double EPS = .00001;
	private final int n, k; // n: number of items; k: weight capacity
	private final int[] v, w, x; // v: values; w: weights; x: decisions
	private boolean optimal; // whether solution proved optimal

	/**
	 * Initialize a new knapsack and fill it optimally. Note that parameters
	 * <code>v</code> and <code>w</code> must be the same length.
	 *
	 * @param k integer capacity
	 * @param v integer array of values for each item, length at least 1
	 * @param w integer array of weights for each item, length at least 1
	 */
	public KnapsackBig(int k, int[] v, int[] w) {
		if (v.length != w.length)
			throw new IllegalArgumentException("ambiguous problem size");
		n = v.length;
		if (n < 1)
			throw new IllegalArgumentException("no items to put in knapsack");
		this.k = k;
		this.v = new int[n];
		this.w = new int[n];
		System.arraycopy(v, 0, this.v, 0, n);
		System.arraycopy(w, 0, this.w, 0, n);
		x = new int[n];
		int max = optimize(); // sets x
		assert feasible();
		optimal = objective() == max;
	}

	/**
	 * Return true if item <code>i</code> is in the solution.
	 *
	 * @param i the index of the decision variable being queried
	 */
	public boolean item(int i) {
		return x[i] == 1;
	}

	/**
	 * Return problem size
	 */
	public int size() { return n; }

	/**
	 * Return the value of the objective function given the solution.
	 */
	public int objective() {
		int value = 0;
		for (int i = 0; i < n; i++)
			value += v[i] * x[i];
		return value;
	}

	/**
	 * Return true if the algorithm proved that the solution was optimal.
	 */
	public boolean optimal() {
		return optimal;
	}

	// Assertions that the soluiton is feasible. Only called by assert. Return
	// value is useless.
	private boolean feasible() {
		int weight = 0;
		for (int i = 0; i < n; i++) {
			assert x[i] == 0 || x[i] == 1;
			weight += w[i] * x[i];
		}
		return weight <= k;
	}

	/**
	 * Return a string containing the answer as the class grading tool expects
	 * it.
	 * <p>
	 * The first line contains an integer objective-function value followed by a
	 * <code>1</code> if the value is optimal or a <code>0</code> otherwise. The
	 * next line is a space separated list of zeros and ones for each decision
	 * variable in order.
	 */
	public String classOut() {
		StringBuilder s = new StringBuilder();
		s.append(objective());
		s.append(' ');
		s.append(optimal() ? 1 : 0);
		s.append('\n');
		for (int i = 0; i < n; i++) {
			s.append(item(i) ? 1 : 0);
			s.append(i < n - 1 ? ' ' : '\n');
		}
		return s.toString();
	}

	private class Item implements Comparable {
		private double ratio;
		private int i;
		public Item(int i) {
			this.i = i;
			ratio = (double) v[i] / w[i];
		}
		public int i() { return i; }
		public int compareTo(Object o) {
			if (o == null)
				throw new NullPointerException("cannot compare to null");
			Item other = (Item) o;
			if      (ratio <  other.ratio) return -1;
			else if (ratio == other.ratio) return  0;
			else                           return  1;
		}
	}

	// Return an upper bound for the value at the best leaf of current search
	// node.  See where bound() is called to understand its paramaters.
	private double bound(int i, double weight, double value, int[] items) {
		int item, wi;
		assert weight - k < EPS; // Because of when bound is called in fill
		for (int j = n - 1; j >= 0; j--) {
			item = items[j];
			if (item <= i)
				continue;
			wi = w[item];
			if (wi < k - weight) {
				weight += wi;
				value += v[item];
			}
			else return value + ((double) (k - weight) / wi) * v[item];
		}
		return value;
	}

	/**
	 * Fill the knapsack as optimally as possible. Results stored in instance
	 * variables <code>x</code>. Return best objective value found.
	 */
	private int optimize() {
		// items: Items sorted by value-to-weight ratio for linear relaxation
		// t: the decision vector being tested at each node
		// ws, vs, is, bs: stacks of weight, value, item id, whether bring item
		// p: stack pointer; i, b, weight, value: loop caches; best: max search
		// ss: stack size: Always <=2 children on stack for <=n-1 parents
		Item[] items = new Item[n];
		int ss = 2 * n;
		int[] itemsSorted = new int[n], t = new int[n], ws = new int[ss],
			vs = new int[ss], is = new int[ss], bs = new int[ss];
		int i, b, weight, value, best = 0, p = 0;

		for (int j = 0; j < n; j++)
			items[j] = new Item(j);
		Arrays.sort(items);
		for (int j = 0; j < n; j++)
			itemsSorted[j] = items[j].i();
		items = null; // For garbage collection.

		// Push item 0 onto the stack with and without bringing it.
		ws[p] = 0; vs[p] = 0; is[p] = 0; bs[p] = 1; p++;
		ws[p] = 0; vs[p] = 0; is[p] = 0; bs[p] = 0; p++;

		while (p > 0) {
			p--; // Pop the latest item off the stack
			i = is[p]; b = bs[p];
			weight = ws[p] + w[i] * b;
			if (weight > k)
				continue;
			value = vs[p] + v[i] * b;
			if (bound(i, weight, value, itemsSorted) < best)
				continue;
			best = Math.max(value, best);
			t[i] = b;
			if (i < n - 1) { // Push children onto stack w/ & w/o bringing item
				ws[p] = weight; vs[p] = value; is[p] = i + 1; bs[p] = 1; p++;
				ws[p] = weight; vs[p] = value; is[p] = i + 1; bs[p] = 0; p++;
			}
			else if (value >= best)
				System.arraycopy(t, 0, x, 0, n);
		}
		return best;
	}
	
	/**
     * The main class
     */
    public static void main(String[] args) {
        try {
            solve("/Users/sharadha/Downloads/knapsack2.txt");
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
   
        KnapsackBig knapsack = new KnapsackBig(capacity, values, weights);
        System.out.print(knapsack.classOut());
        
    }

	
}