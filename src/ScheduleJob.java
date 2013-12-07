import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ScheduleJob {
	
	private static int jobs;
	private static List<Job> jobList = new ArrayList<Job>();

	/**
	 * @param args
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		loadJobs();
        Collections.sort(jobList, new Comparator<Job>(){
        	public int compare(Job job1, Job job2) {
    			return (new Double((job2.w - job2.l) * 1000 + job2.w)).compareTo((new Double((job1.w - job1.l) * 1000 + job1.w)));
    				
    		}
        });
        System.out.println("By difference");
        
      
        printCompletionTime(jobList);
        
        jobList.clear();
        loadJobs();
        Collections.sort(jobList, new Comparator<Job>(){
        	public int compare(Job job1, Job job2) {
    			return new Double((job2.w / job2.l)).compareTo(new Double(job1.w / job1.l));
    		}
        });
        
        System.out.println("By ratio");
        
        printCompletionTime(jobList);
        
	}
	
	private static void printCompletionTime(List<Job> jobList) {
		double curTime = 0;
        double	tot = 0;
        for (Job job : jobList){
        	curTime = curTime + job.l;
        	tot = tot + curTime * job.w;
        }
        System.out.println("total = "+tot);
		
	}

	private static class Job{
		private double w;
		private double l;
		
		public Job(double w, double l){
			this.w = w;
			this.l = l;
		}
		
	}
	
	private static void loadJobs() throws NumberFormatException, IOException{
		File file = new File("/Users/sharadha/Downloads/jobs.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		jobs = new Integer(br.readLine()).intValue();
		System.out.println(" jobs= "+jobs);
		String line;
		
		while ((line = br.readLine()) != null) {
		   // process the line.
			String[] pair = line.split(" ");
			double w = Double.parseDouble(pair[0]);
			double l = Double.parseDouble(pair[1]);
			Job job = new Job(w, l);
			jobList.add(job);
		}
		
		
		br.close();
	}

}
