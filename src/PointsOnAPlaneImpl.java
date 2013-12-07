import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;



public class PointsOnAPlaneImpl{
	
	private class Point{
		private int x;
		private int y;
		public Point(int x, int y){
			this.x = x;
			this.y = y;
		}
	}

     private class PointDistance{
        Point myPoint;
        int dist;
        
        public PointDistance(Point point, int distance){
        	myPoint = point;
        	dist = distance;
        }
  
        Point getMyPoint(){
          return myPoint;
        }

        int getDist(){
          return dist;
        }
     }
   
     List<Point> allPoints = new ArrayList<Point>();

     void addPoint(Point point) {
       allPoints.add(point);
     }
     
     private int distance(Point a, Point b){
    	 return 100;
     }

     Collection<Point> findNearest(Point center, int m) {
         List<Point> nearestPoints = new ArrayList<Point>();

         
         PriorityQueue<PointDistance> myQueue = new PriorityQueue<PointDistance>(1, new Comparator<PointDistance>(){
            public int compare(PointDistance one, PointDistance two){
                return one.getDist() - two.getDist();
            }
         });

         for(Point point : allPoints) { 
            int dist = distance(point, center);
            if (myQueue.isEmpty() || myQueue.peek().getDist() > dist){
                myQueue.add(new PointDistance(point, dist));
            }
            
         }
        for (int j=0; j < m; j++){
           nearestPoints.add(myQueue.poll().getMyPoint());
        }

        return nearestPoints;
     }

}