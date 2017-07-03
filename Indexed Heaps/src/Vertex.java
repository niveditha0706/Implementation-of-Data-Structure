/**
 * Class to represent a vertex of a graph
 * @author Niveditha
 *
 */

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class Vertex implements Index, Comparator<Vertex>, Iterable<Edge> {
    int name; // name of the vertex
    boolean seen; // flag to check if the vertex has already been visited
    int d;  Vertex p;  // fields used in algorithms of Prim and Dijkstra
    List<Edge> adj, revAdj; // adjacency list; use LinkedList or ArrayList
    int index;
    
    /** 
     * Method to get index of a vertex 
     * 
     */
    public int getIndex() 
    {  
    	return index;
    }
    
    /** 
     * Method to assign index for a vertex 
     * 
     */
    public void putIndex(int i) 
    {
    	index = i;
    }
    /** 
     * Method to compare the distance of two vertices from the source
     * 
     */
    public int compare(Vertex u, Vertex v) 
    { 
    	return u.d - v.d ;
    }

    /**
     * Constructor for the vertex
     * 
     * @param n
     *            : int - name of the vertex
     */
    Vertex(int n) {
	name = n;
	seen = false;
	d = Integer.MAX_VALUE;
	p = null;
	adj = new ArrayList<Edge>();
	revAdj = new ArrayList<Edge>();   /* only for directed graphs */
    }
    
    Vertex()
    {
    	// default constructor
    }
    public Iterator<Edge> iterator() { return adj.iterator(); }

    /**
     * Method to represent a vertex by its name
     */
    public String toString() {
	return Integer.toString(name);
    }
}
