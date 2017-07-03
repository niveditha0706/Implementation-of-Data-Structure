
/**
 * Class for implementing Dijkstra's Shortest path algorithm
 */
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;

public class ShortestPath {
    static final int Infinity = Integer.MAX_VALUE;

    static void DijkstraShortestPaths(Graph g, Vertex s)
    {
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
        	for(Edge e: u.adj)
        	{
        		Vertex v = e.otherEnd(u);
        		if(!v.seen && u.d+e.weight<v.d)
        		{
        			v.d = u.d+e.weight;
        			v.p = u;
        			pq.decreaseKey(v);
        		}
        	}
        }
    }
    
    /**
     * main method to execute Dijkstra's Shortest path algorithm
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
	Scanner in;

        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } else {
            in = new Scanner(System.in);
        }

	Graph g = Graph.readGraph(in);
	int src = in.nextInt();
	int target = in.nextInt();
        Vertex s = g.getVertex(src);
	Vertex t = g.getVertex(target);
        DijkstraShortestPaths(g, s);

	System.out.println(t.d);
    }
}
