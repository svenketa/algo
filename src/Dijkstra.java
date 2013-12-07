import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class Dijkstra {
	//private static final int NUM_NODES = 200;
	private static final int NUM_NODES = 5;
	private static final Node[] GRAPH = new Node[NUM_NODES];
	private static final int[] DISTANCES = new int[NUM_NODES];
	private static final Set<Integer> VISITED = new HashSet<Integer>();
	//private static final int[] PICKED = {7,37,59,82,99,115,133,165,188,197};
	private static final int[] PICKED = {1,2,3,4,5};

	
	public static void main(String[] args) {
		readInput("/Users/sharadha/Downloads/dijkstraData1.txt");
		System.out.println("Finished reading input graph");
		printGraph();
		PriorityQueue<Node> frontier = new PriorityQueue<Node>(NUM_NODES, new Comparator<Node>(){
			@Override
			public int compare(Node o1, Node o2) {
				return o1.distance - o2.distance;
			}
		});

		frontier.add(GRAPH[0]);
		while (!frontier.isEmpty()) {
			Node w = frontier.poll();
			VISITED.add(w.id);
			for (Entry<Integer, Integer> neighborEntry : w.neighbors.entrySet()) {
				int edgeLength = neighborEntry.getValue();
				int currentDistance = DISTANCES[w.id] + edgeLength;
				Node neighbor = GRAPH[neighborEntry.getKey()];
				if (VISITED.contains(neighbor.id)) {
					continue;
				}
				if (frontier.contains(neighbor)) {
					if (neighbor.distance > currentDistance) {
						frontier.remove(neighbor);
						neighbor.distance = currentDistance;
						frontier.add(neighbor);
						DISTANCES[neighbor.id] = currentDistance;
					}
				} else {
					neighbor.distance = currentDistance;
					DISTANCES[neighbor.id] = currentDistance;
					frontier.add(neighbor);
				}
			}
		}
		for (int node : PICKED) {
			System.out.print(DISTANCES[node-1] + ",");
		}
	}

	private static class Node {
		Map<Integer, Integer> neighbors = new HashMap<Integer, Integer>();
		int id;
		int distance;
		public Node(int id) {
			this.id = id;
		}
	}

	private static void readInput(String fileName) {
		try {
			Scanner in = new Scanner(new FileInputStream(fileName));
			int cnt = 0;
			while (in.hasNextLine()) {
				int src = in.nextInt() - 1;
				Node srcNode;
				if (GRAPH[src] == null) {
					srcNode = new Node(src);
					GRAPH[src] = srcNode;
				} else {
					srcNode = GRAPH[src];
				}
				String[] restOfLine = in.nextLine().trim().split("\\s");
				for (String str : restOfLine) {
					String[] pair = str.split(",");
					int dst = Integer.parseInt(pair[0]) - 1;
					int len = Integer.parseInt(pair[1]);
					Node dstNode;
					if (GRAPH[dst] == null) {
						dstNode = new Node(dst);
						GRAPH[dst] = dstNode;
					} else {
						dstNode = GRAPH[dst];
					}
					srcNode.neighbors.put(dst, len);
					cnt++;
				}

			}
			System.out.println(String.format("total number of edges: %d", cnt));
			in.close();
		} catch (FileNotFoundException e) {
			System.out.print(e.getMessage());
		}
	}
	
	private static void printGraph(){
		for (int i=0; i< GRAPH.length; i++){
			System.out.println("printing graph["+i+"] contents");
			System.out.println("id="+GRAPH[i].id + " distance="+GRAPH[i].distance + " neighbors="+GRAPH[i].neighbors);
		}
	}
}