import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class BigClustering {
	
	private static int nodes = 0;
	private static int bits = 0;
	private static Map<String, Integer> lookup = new HashMap<String, Integer>();
	private static String[] givenList;
	private static List<List<String>> clusters = new ArrayList<List<String>>();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		readInput();
		int distance = 1;
		int index = 0;
		double maxDistance = 100000;

        while(maxDistance >= 3 && index < nodes && distance < maxDistance){
        	List<String> clustersToMerge = getClustersToMerge(lookup, distance, index);
        	if (clustersToMerge != null && clustersToMerge.size() > 0){
        		clusters = mergeClusters(clustersToMerge);
                maxDistance = getMaxDistance(index, distance);	
                index = index + 1;
        	} else {
        		distance = distance + 1;
        	}
            
        }
        //printClusters();
        System.out.println("Number of clusters required to get maxDistance of "+maxDistance + " ="+clusters.size());
	}
	

	private static int getMaxDistance(int index, int distance) {
		if (distance >= nodes){
			return nodes;
		}
		List<String> clustersToMerge = getClustersToMerge(lookup, distance, index);
		if (clustersToMerge != null && clustersToMerge.size() > 0){
			Iterator<String> clustersToBeMergedIter = clustersToMerge.iterator();
			String str1 = null;
			String str2 = null;
			
			if (clustersToBeMergedIter.hasNext()){
				str1 = clustersToBeMergedIter.next();
			}
			List<String> cluster1 = getClusterContaining(str1);
			
			while (clustersToBeMergedIter.hasNext()){
				str2 = clustersToBeMergedIter.next();
				List<String> cluster2 = getClusterContaining(str2);
				if (!same(cluster1, cluster2)){
					return getDistance(str1, str2);
				} else {
					return  getMaxDistance(index+1, distance);
				}
			}
		}
		return getMaxDistance(index, distance+1);
		
	}


	private static int getDistance(String str1, String str2) {
		int dist = 0;
		int i =0; 
		int j = 0;
		while (i < str1.length() && j < str2.length()){
			if (str1.charAt(i) != str2.charAt(j)){
				dist ++;
			}
			i++;
			j++;
		}
		return dist;
	}


	private static List<List<String>> mergeClusters(List<String> clustersToMerge) {
		
		Iterator<String> clustersToBeMergedIter = clustersToMerge.iterator();
		String str1 = null;
		String str2 = null;
		
		if (clustersToBeMergedIter.hasNext()){
			str1 = clustersToBeMergedIter.next();
		}
		List<String> cluster1 = getClusterContaining(str1);
		
		while (clustersToBeMergedIter.hasNext()){
			str2 = clustersToBeMergedIter.next();
			List<String> cluster2 = getClusterContaining(str2);
			if (!same(cluster1, cluster2)){
				List<String> clusterMerged = new ArrayList<String>();
				
				clusterMerged.addAll(cluster1);
				clusterMerged.addAll(cluster2);
				clusters.remove(cluster1);
				clusters.remove(cluster2);
				clusters.add(clusterMerged);
			}
		}
		
		return clusters;
		
	}


	private static boolean same(List<String> cluster1, List<String> cluster2) {
		if (cluster1 == null || cluster2 == null){
			return true;
		} else {
			if (cluster1.size() != cluster2.size()){
				return false;
			} else {
				Iterator<String> clusterIter = cluster1.iterator();
				while (clusterIter.hasNext()){
					if (!cluster2.contains(clusterIter.next())){
						return false;
					}
				}
			}
		}
		return true;
	}


	private static List<String> getClusterContaining(String i) {
		Iterator<List<String>> clusterIter = clusters.iterator();
		while (clusterIter.hasNext()){
			List<String> list = clusterIter.next();
			if (list.contains(i)){
				return list; 
			}
		}
		return null;
	}


	private static List<String> getClustersToMerge(Map<String, Integer> lookup, int distance, int index) {
		 List<String> clustersToBeMerged = new ArrayList<String>();
		// Get Strings at that index that differ by distance 
		 String givenStr = givenList[index]; 
		 List<String> strListThatDifferByDist = getStringListsThatDifferByDistance(givenStr, distance);
		 for (String strToLookup: strListThatDifferByDist){
			 if (lookup.get(strToLookup) != null){
				 clustersToBeMerged.add(strToLookup);	 
			 }
			 	 
		 }
		 clustersToBeMerged.add(givenStr);
		 return clustersToBeMerged;
	}


	private static List<String> getStringListsThatDifferByDistance(
			String givenStr, int distance) {
		List<String> retStringArr = new ArrayList<String>();
		if (distance == 1){
			for (int i=0; i< givenStr.length(); i++){
				String newStr = new String(givenStr);
				char iChar = newStr.charAt(i);
				if(iChar == '0' || iChar == '1'){
					if (i > 0 && i +1 < givenStr.length()){
						newStr = newStr.substring(0, i) + (iChar == '0' ? "1": "0") + newStr.substring(i+1);	
					}else if (i == 0){
						newStr = (newStr.charAt(i) == '0' ? "1":"0") + newStr.substring(i+1);
					} else{
						newStr = newStr.substring(0, i) + (newStr.charAt(i) == '0' ? "1":"0");
					}
					
					retStringArr.add(newStr);	
				}
				
			}
		} else if (distance == 2){
			for (int i=0; i< givenStr.length() - 1; i++){
				String newStr = new String(givenStr);
				char iChar = newStr.charAt(i);
				if(iChar == '0' || iChar == '1'){
					if (i > 0 && i +1 < givenStr.length()){
						newStr = newStr.substring(0, i) + (newStr.charAt(i) == '0' ? "1":"0") + newStr.substring(i+1);	
					}else if (i == 0){
						newStr = (newStr.charAt(i) == '0' ? "1":"0") + newStr.substring(i+1);
					} else{
						newStr = newStr.substring(0, i) + (newStr.charAt(i) == '0' ? "1":"0");
					}
					
					for (int j=i+1; j< newStr.length(); j++){
						char jChar = newStr.charAt(j);
						if(jChar == '0' || jChar == '1'){
							if (j + 1 < givenStr.length()){
								newStr = newStr.substring(0, j) + (newStr.charAt(j) == '0' ? "1":"0") + newStr.substring(j+1);	
							} else {
								newStr = newStr.substring(0, j) + (newStr.charAt(j) == '0' ? "1":"0");
							}
							
							retStringArr.add(newStr);	
						}
						
					}	
				}
				
			}
		}
		return retStringArr;
	}


	private static void printClusters() {
		Iterator<List<String>> clusterIter = clusters.iterator();
		int i=0;
		while (clusterIter.hasNext()){
			List<String> list = clusterIter.next();
			System.out.println("cluster["+i+"]="+list);
			i++;
		}
		
	}
	
	private static void readInput(){
		try {
			
			Scanner in = new Scanner(new FileInputStream("/Users/sharadha/Downloads/clustering_big3.txt"));

			if (in.hasNextLine()){
				String[] str = in.nextLine().trim().split("\\s");
				
				nodes = Integer.parseInt(str[0]);
				bits = Integer.parseInt(str[1]);
				
				System.out.println("nodes="+nodes);
				System.out.println("bits="+bits);
			}
			int i=0;
			givenList = new String[nodes];
			while (in.hasNextLine()) {
				
				String str = in.nextLine().trim();
				givenList[i] = str;
				lookup.put(str, i++);
				
				List<String> strList = new ArrayList<String>();
				strList.add(str);
				clusters.add(strList);	
			}
		
			in.close();
			
		} catch (FileNotFoundException e) {
			System.out.print(e.getMessage());
		}
	}

}
