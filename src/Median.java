
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Median {
	public static void main(String[] args) {
		final String path = "/Users/sharadha/Downloads/Median.txt";
		int result = 0;
		try {
			FileInputStream fstream = new FileInputStream(path);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			int x;
			PriorityQueue<Integer> l = new PriorityQueue<Integer>(1, new Comparator<Integer>() {
				public int compare(Integer a, Integer b) {
					return a < b ? 1 : -1;
				}
			});
			PriorityQueue<Integer> h = new PriorityQueue<Integer>(1, new Comparator<Integer>() {
				public int compare(Integer a, Integer b) {
					return a > b ? 1 : -1;
				}
			});
			while ((line = br.readLine()) != null) {
				x = Integer.parseInt(line);
				if (l.isEmpty() || x < l.peek()) {
					l.add(x);
				}
				else {
					h.add(x);
				}
				if (l.size() > h.size() + 1) {
					h.add(l.poll());
				}
				if (l.size() < h.size()) {
					l.add(h.poll());
				}
				result += l.peek();
			}
		}
		catch (Exception e) {
			System.err.println("can't read " + path + ": " + e.getMessage());
			System.exit(3);
		}
		System.out.println("answer: " + result % 10000);
	}
}