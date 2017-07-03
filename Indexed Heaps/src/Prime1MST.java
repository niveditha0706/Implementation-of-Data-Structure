
/**
 * Class for implementing Prim's MST algorithm - priority queue of edges; using Java's priority queues
 */
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Prime1MST {
    static final int Infinity = Integer.MAX_VALUE;

    static int PrimMST(Graph g, Vertex s)
    {
        int wmst = 0;

        Vertex startVertex = g.getVertex(s.name); //source vertex
        startVertex.seen = true;
        startVertex.d = wmst;
        Comparator<Edge> comp = (Comparator<Edge>) new Edge();
        PriorityQueue<Edge> pq = new PriorityQueue<Edge>(g.size * g.size, comp); //PQ of Edges
        for(Edge e: startVertex.adj)
        {
        	pq.add(e);
        }
        
        //Loop through every edge in the graph
        while(!pq.isEmpty())
        {
        	Edge e = pq.remove();
        	if (e.from.seen && e.to.seen)
        	{
        		continue;
        	}

        	if (e.from.seen)
        	{
        		Vertex v = e.to;
        		v.seen = true;
        		v.p = e.from;
        		v.d =  e.weight;
        		wmst =  wmst + e.weight;
        		
            	
            	for(Edge edg: v.adj)
            	{
            		Vertex u = edg.otherEnd(v);
            		if(!u.seen)
            		{
            			pq.add(edg);
            		}
            	}
        	}
        	else
        	{
        		Vertex v = e.from;
        		v.seen = true;
        		v.p = e.to;
        		v.d =  e.weight;
        		wmst =  wmst + e.weight;
        		
            	for(Edge edg: v.adj)
            	{
            		Vertex u = edg.otherEnd(v);
            		if(!u.seen)
            		{
            			pq.add(edg);
            		}
            	}
        	} 
        	
        }
        return wmst;
    }

    /**
     * main method to execute Prim's MST algorithm - priority queue of edges; using Java's priority queues
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException 
    {
    	Scanner in;
    	Timer timer = new Timer();
        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } else {
            in = new Scanner(System.in);
        }

        Graph g = Graph.readGraph(in);
        Vertex s = g.getVertex(1);
        timer.start();
        System.out.println(PrimMST(g, s));
        timer.end();
        System.out.println("Time taken for Prim 1: Priority queue of edges; using Java's priority queues:\n"+timer);
    }
}
