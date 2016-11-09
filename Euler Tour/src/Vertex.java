/**
 * Class to represent a vertex of a graph
 * @author rbk
 *
 */

import java.util.List;
import java.util.ArrayList;



public class Vertex{
    int name; // name of the vertex
    boolean seen; // flag to check if the vertex has already been visited
    int distance; // distance of vertex from a source
    List<Edge> adj, revAdj; // adjacency list; use LinkedList or ArrayList;
    int count; //No of edges traversed using iterator so far
    boolean indexed; //used for creating index for the newly discovered tour Nodes
    List<Integer> adjEdgeNames; //list of edges

    /**
     * Constructor for the vertex
     * 
     * @param n
     *            : int - name of the vertex
     */
    Vertex(int n) {
	name = n;
	seen = false;
	distance = Integer.MAX_VALUE;
	adj = new ArrayList<Edge>();
	revAdj = new ArrayList<Edge>();   /* only for directed graphs */
	adjEdgeNames=new ArrayList<Integer>(); 
	count = 0;
	indexed = false;

    }
  
    
    /**
     * Method to represent a vertex by its name
     */
    public String toString() {
	return Integer.toString(name);
    }
}
