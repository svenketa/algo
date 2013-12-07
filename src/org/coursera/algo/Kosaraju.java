package org.coursera.algo;

import java.io.*;
import java.util.*;
import java.util.zip.*;

import org.coursera.algo.DirectedGraph.EdgeTraversalPolicy;

 
/**
 * https://class.coursera.org/algo/quiz/attempt?quiz_id=57
 * 
 * Reads the graph directly from the zip file
 */
public class Kosaraju {
    
    private static int t;
    private static ArrayList<Integer> scc = new ArrayList<Integer>();
    private static int pass = 0;
    
    private static void dfsLoop( DirectedGraph gr, EdgeTraversalPolicy tp ) {
        t = 0;
        
        Collection<DirectedVertex> vs; 
        if( pass == 0 ) 
            vs = gr.getVerticesInReversedOrder().values();
        else {
            vs = new TreeSet<DirectedVertex>(new Comparator<DirectedVertex>() {
                @Override
                public int compare( DirectedVertex v1, DirectedVertex v2 ) {
                    return new Integer( v2.getF() ).compareTo( v1.getF() );
                }
            });
            vs.addAll( gr.getVertices().values() );
        }
        
        for ( DirectedVertex v : vs ) {
            if( !v.isVisited() ) {
                
                dfs( tp, v );
                
                if( pass == 1 ) {
                    //scc.add( v.getF() );
                	scc.add(t);
                    t = 0;
                }
            }
        }
        
        pass++;
    }
    
    private static void dfs( EdgeTraversalPolicy tp, DirectedVertex v ) {
        
        v.setVisited( true );
        
        for ( DirectedEdge edge : tp.edges( v ) ) {
            DirectedVertex next = tp.vertex( edge );
            if( !next.isVisited() )
                dfs( tp, next );
        }
        t++;
        if( pass == 0 ) {
            v.setF( t );
        }
    }
    
    private static DirectedGraph readGraph( InputStream is ) throws FileNotFoundException {
        Scanner sc = new Scanner( is );
        DirectedGraph gr = new DirectedGraph();
        while( sc.hasNext() ) {
            addEdge( gr, sc.nextInt(), sc.nextInt() );
        }
        sc.close();
        
        return gr;
    }
    
    private static void addEdge( DirectedGraph gr, int tailId, int headId ) {
        DirectedVertex tail = gr.getVertex( tailId );
        DirectedVertex head = gr.getVertex( headId );
        DirectedEdge edge = new DirectedEdge( tail, head );
        gr.addEdge( edge );
        tail.addOutgoingEdge( edge );
        head.addIncomingEdge( edge );
    }
 
    private static void test( DirectedGraph gr ) {
        System.out.println("First pass:");
        dfsLoop( gr, DirectedGraph.BACKWARD_TRAVERSAL );
        
        gr.reset();
        System.out.println("Second pass:");
        dfsLoop( gr, DirectedGraph.FORWARD_TRAVERSAL );
        
        int count = 0;
        Collections.sort( scc );
        
        for( int i = scc.size()-1; i >= 0; i-- ) {
            if( count >= 5 ) break;
            System.out.println("scc["+ i + "] = " + scc.get( i ));
            count++;
        }
        
        cleanup();
    }
 
    private static void cleanup() {
        t = 0;
        pass = 0;
        scc.clear();
    }
    
    private static DirectedGraph example1() {
        DirectedGraph gr = new DirectedGraph();
        addEdge( gr, 1, 2 );
        addEdge( gr, 1, 3 );
        addEdge( gr, 3, 4 );
        addEdge( gr, 2, 4 );
        addEdge( gr, 4, 1 );
        printGraph(gr);
        return gr;
    }
 
    private static DirectedGraph example2() {
        DirectedGraph gr = new DirectedGraph();
        addEdge( gr, 1, 4 );
        addEdge( gr, 2, 8 );
        addEdge( gr, 3, 6 );
        addEdge( gr, 4, 7 );
        addEdge( gr, 5, 2 );
        addEdge( gr, 6, 9 );
        addEdge( gr, 7, 1 );
        addEdge( gr, 8, 5 );
        addEdge( gr, 8, 6 );
        addEdge( gr, 9, 3 );
        addEdge( gr, 9, 7 );
        printGraph(gr);
        return gr;
    }
    
    private static void printGraph( Graph gr ) {
        System.out.println("Printing graph");
        System.out.println( "Graph: " + gr.getVertices().size() + " vertices, "
                + gr.getEdges().size() + " edges." );
        System.out.println( "Vertices:");
        for (Object v: gr.getVertices().values()){
        	System.out.println(((DirectedVertex)v).getLbl());
        }
        System.out.println( "Edges:");
        for (Object e: gr.getEdges()){
        	System.out.println(((DirectedEdge)e).getFirst() + "," + ((DirectedEdge)e).getSecond());
        }
    }
 
    private static DirectedGraph example3()
            throws Exception {
        
        long start = System.currentTimeMillis();
        ZipFile zf = new ZipFile( "/Users/sharadha/Downloads/SCC.zip" );
        DirectedGraph g = readGraph( zf.getInputStream( zf.getEntry( "SCC.txt" ) ) );
        
        System.out.println( "Read from ZIP: " + ( System.currentTimeMillis() - start ) );
        System.out.println( "Graph: " + g.getVertices().size() + " vertices, "
                + g.getEdges().size() + " edges." );
        return g;
    }
    
    /**
     * @param args
     * @throws IOException 
     */
    public static void main( String[] args ) throws Exception {
    	 System.out.println( "Example 1" );
         test(example1());
         System.out.println( "Example 2" );
         test(example2());
         System.out.println( "Example 3" );
         test(example3());
    }
 
}