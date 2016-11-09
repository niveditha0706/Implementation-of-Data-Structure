/**
 * Class to represent a graph
 *  @author rbk
 *
 */

import java.util.ArrayList;
import java.util.Iterator;

import java.util.List;

import java.util.Scanner;

public class Graph implements Iterable<Vertex> {
    List<Vertex> v; // vertices of graph
    int size; // number of vertices in the graph
    boolean directed;  // true if graph is directed, false otherwise
    int numOfEdges; // total number of edges in the graph


    /**
     * Constructor for Graph
     * 
     * @param size
     *            : int - number of vertices
     */
    Graph(int size,int numOfEdges) {
	this.size = size;
	this.v = new ArrayList<>(size + 1);
	this.v.add(0, null);  // Vertex at index 0 is not used
	this.directed = false;  // default is undirected graph
	this.numOfEdges=numOfEdges;
	// create an array of Vertex objects
	for (int i = 1; i <= size; i++)
	    this.v.add(i, new Vertex(i));

    }

    /**
     * Find vertex no. n
     * @param n
     *           : int
     */
    Vertex getVertex(int n) {
	return this.v.get(n);
    }
    
    /**
     * Method to add an edge to the graph
     * 
     * @param a
     *            : int - one end of edge
     * @param b
     *            : int - other end of edge
     * @param weight
     *            : int - the weight of the edge
     */
    void addEdge(Vertex from, Vertex to, int weight,Integer name) {
	Edge e = new Edge(name,from, to, weight);
	if(this.directed) {
	    from.adj.add(e);
            to.revAdj.add(e);
	} else {
	    from.adj.add(e);
	    from.adjEdgeNames.add(e.edgeName);
	    to.adj.add(e);
	    to.adjEdgeNames.add(e.edgeName);
	}
    }

    /**
     * Method to create iterator for vertices of graph
     */
    public Iterator<Vertex> iterator() {
	Iterator<Vertex> it = this.v.iterator();
	it.next();  // Index 0 is not used.  Skip it.
	return it;
    }

  

    // read a directed graph using the Scanner interface
    public static Graph readDirectedGraph(Scanner in) {
	return readGraph(in, true);
    }
    
    // read an undirected graph using the Scanner interface
    public static Graph readGraph(Scanner in) {
	return readGraph(in, false);
    }
    
    public static Graph readGraph(Scanner in, boolean directed) {
	// read the graph related parameters
	int n = in.nextInt(); // number of vertices in the graph
	int m = in.nextInt(); // number of edges in the graph

	// create a graph instance
	Graph g = new Graph(n,m);
	g.directed = directed;
	for (int i = 0; i < m; i++) {
	    int u = in.nextInt();
	    int v = in.nextInt();
	    int w = in.nextInt();
	    g.addEdge(g.getVertex(u), g.getVertex(v), w, (Integer)i+1);
	}
	in.close();
	return g;
    }
    
   

}
