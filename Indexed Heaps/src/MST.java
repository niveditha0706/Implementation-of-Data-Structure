
/**
 * Class for implementing Prim's MST algorithm - priority queue of vertices, using indexed heaps
 */
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;

public class MST {
    static final int Infinity = Integer.MAX_VALUE;

    static int PrimMST(Graph g, Vertex s)
    {
        int wmst = 0;
        Vertex startVertex = g.getVertex(s.name); // Source vertex
        
        Vertex[] vertices = new Vertex[g.size+1];
        vertices[0] = null;
        int i = 1;
        for(Vertex v : g)
        {
        	v.seen = false;
        	v.d = Infinity;
        	v.p = null;
        	vertices[i++] = v;
        }
        startVertex.d = 0;
        
        for(int j = 1; j<vertices.length;j++)
        {
        	vertices[j].putIndex(j);
        }
        
        // build a indexed heap of vertices using Vertex.d as priority
        IndexedHeap<Vertex> pq = new IndexedHeap<Vertex>(vertices,new Vertex());
        while(!pq.isEmpty())
        {
        	Vertex u = pq.remove();
        	u.seen = true;
        	wmst = wmst + u.d;
        	for(Edge e: u.adj)
        	{
        		Vertex v = e.otherEnd(u);
        		if(!v.seen && e.weight<v.d)
        		{
        			v.d = e.weight;
        			v.p = u;
        			pq.decreaseKey(v);
        		}
        	}
        }
        return wmst;
    }

    /**
     * main method for executing Prim's MST algorithm - priority queue of vertices, using indexed heaps
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
        System.out.println("Time taken for Prim 2: Priority queue of vertices; using indexed heaps:\n"+timer);
    }
}
