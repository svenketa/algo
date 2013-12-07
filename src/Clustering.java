import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;



public class Clustering {
	
	private static final int k = 4;
	private static int nodes = 0;
	private static List<Edge> givenList = new ArrayList<Edge>();
	private static List<List<Integer>> clusters = new ArrayList<List<Integer>>();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		readInput();
		int index = 0;

        while(clusters.size() > k){
            int[] clustersToMerge = getClustersToMerge(givenList, index++);
            clusters = mergeClusters(clustersToMerge);
        }
        printClusters();
        double maxDistance = getMaxDistance(index);
        System.out.println("maxDistance with "+k+" clusters = "+maxDistance);
	}
	

	private static double getMaxDistance(int index) {
		int[] clustersToMerge = getClustersToMerge(givenList, index);
		List<Integer> cluster1 = getClusterContaining(clustersToMerge[0]);
		List<Integer> cluster2 = getClusterContaining(clustersToMerge[1]);
		
		if (!same(cluster1, cluster2)){
			return givenList.get(index).dist;
		} else {
			return getMaxDistance(index+1);
		}
	}


	private static List<List<Integer>> mergeClusters(int[] clustersToMerge) {
		List<Integer> cluster1 = getClusterContaining(clustersToMerge[0]);
		List<Integer> cluster2 = getClusterContaining(clustersToMerge[1]);
		
		if (!same(cluster1, cluster2)){
			List<Integer> clusterMerged = new ArrayList<Integer>();
			clusterMerged.addAll(cluster1);
			clusterMerged.addAll(cluster2);
			clusters.remove(cluster1);
			clusters.remove(cluster2);
			clusters.add(clusterMerged);
		}
		return clusters;
		
	}


	private static boolean same(List<Integer> cluster1, List<Integer> cluster2) {
		if (cluster1 == null || cluster2 == null){
			return true;
		} else {
			if (cluster1.size() != cluster2.size()){
				return false;
			} else {
				Iterator<Integer> clusterIter = cluster1.iterator();
				while (clusterIter.hasNext()){
					if (!cluster2.contains(clusterIter.next())){
						return false;
					}
				}
			}
		}
		return true;
	}


	private static List<Integer> getClusterContaining(int i) {
		Iterator<List<Integer>> clusterIter = clusters.iterator();
		while (clusterIter.hasNext()){
			List<Integer> list = clusterIter.next();
			if (list.contains(i)){
				return list; 
			}
		}
		return null;
	}


	private static int[] getClustersToMerge(List<Edge> givenList, int index) {
		int[] clustersToMerge = new int[2];
		Edge edge = givenList.get(index);
		clustersToMerge[0] = edge.vertex1;
		clustersToMerge[1] = edge.vertex2;
		return clustersToMerge;
	}


	private static void printClusters() {
		Iterator<List<Integer>> clusterIter = clusters.iterator();
		int i=0;
		while (clusterIter.hasNext()){
			List<Integer> list = clusterIter.next();
			System.out.println("cluster["+i+"]="+list);
			i++;
		}
		
	}
	
	private static class Edge {
		int vertex1;
		int vertex2;
		double dist;
		
		Edge(int vertex1, int vertex2, double dist){
			this.vertex1 = vertex1;
			this.vertex2 = vertex2;
			this.dist = dist;
		}
	}


	private static void readInput(){
		try {
			
			Scanner in = new Scanner(new FileInputStream("/Users/sharadha/Downloads/clustering1.txt"));
			Set<Integer> nodeSet = new HashSet<Integer>();

			if (in.hasNextLine()){
				nodes = Integer.parseInt(in.nextLine().trim());
				System.out.println("nodes="+nodes);
			}
			while (in.hasNextLine()) {
				
				String[] str = in.nextLine().trim().split("\\s");
				
				int vertex1 = Integer.parseInt(str[0]);
				int vertex2 = Integer.parseInt(str[1]);
				nodeSet.add(vertex1);
				nodeSet.add(vertex2);
				double dist = Integer.parseInt(str[2]);
				Edge edge = new Edge(vertex1, vertex2, dist);
				givenList.add(edge);
			}
			Collections.sort(givenList, new Comparator<Edge>(){
				public int compare(Edge edge1, Edge edge2){
					return new Double(edge1.dist).compareTo(new Double(edge2.dist));
				}
			});
			Iterator<Integer> setIter = nodeSet.iterator();
			while (setIter.hasNext()){
				List<Integer> intList = new ArrayList<Integer>();
				intList.add(setIter.next());
				clusters.add(intList);	
			}
			
			in.close();
			
		} catch (FileNotFoundException e) {
			System.out.print(e.getMessage());
		}
	}

}
