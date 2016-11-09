/**
 * Class for Euler tour
 *  @author Niveditha
 *  Ver 1.0
 *  Methods:
 *  To find if the graph is eulerian or not
 *  Find the sub tours.
 *  Stitch the sub tours to get the euler tour.
 *  Verify the euler tour
 *
 */

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.HashSet;
public class EulerTour{

	Queue<CircularList<TourNode,Vertex>.Entry<TourNode,Vertex>> index = new LinkedList<>();
	/**
     * Method to check if the given graph is eulerian or not
     * @param g
     *            : Graph - graph to be checked
     * @return hasEulerTour
     * 			  : boolean - true if eulerian or false if not eulerian 
     */
	boolean hasEulerTour(Graph g) 
	{
		int degreeofvertex;
		boolean hasEulerTour = true;
		Iterator<Vertex> itrv = g.v.listIterator();
		itrv.next();
		while (itrv.hasNext())
		{
			Vertex v = itrv.next();
			degreeofvertex = v.adj.size();
			//check if the graph is connected and if every vertex has even no of edges
			if ((degreeofvertex == 0) || (degreeofvertex%2 != 0))
			{
				hasEulerTour = false;
			}
		}
		
		return hasEulerTour;
	}
	
	/**
     * Method to break the graph into sub tours
     * @param g
     *            : Graph - graph to be checked
     * @return list of sub tours
     *             
     */
	public List<CircularList<TourNode,Vertex>> breakIntoSubTour(Graph g)
	{
		Vertex v = g.getVertex(1);
		Queue<Vertex> vertexDiscovered = new LinkedList<Vertex>();
		vertexDiscovered.add(v); // add vertex to the queue when it is discovered
		List<CircularList<TourNode,Vertex>> subTours = new ArrayList<>();
		v.seen = true;
		//loop invariant: queue is not empty
		while(!vertexDiscovered.isEmpty())
		{
			CircularList<TourNode,Vertex> subTour = new CircularList<>();
			v = vertexDiscovered.remove();
			ListIterator<Edge> it = v.adj.listIterator(v.count);
			// get the next unvisited edge of the vertex and mark as seen
			//loop invariant: the vertex has unseen edges.
			while( it.hasNext())
			{
				Edge e = it.next();
				if(!e.seen)
				{
					e.seen = true;
					v.count++;
					Vertex u = e.otherEnd(v);
					
					if(!u.seen)
					{
						u.seen = true;
						vertexDiscovered.add(u);
					}

					subTour.add(new TourNode(v,e),v);	
					Vertex tourVertex = subTour.getLastNode().element;
					if(!tourVertex.indexed)
					{
						tourVertex.indexed = true;
						index.add(subTour.getLastNode());
					}
					
					v=u;
					it = v.adj.listIterator(v.count);
					continue;
				}
				else
				{
					v.count++;
				}
		
			}
			// Create a list of sub tours
			if(!subTour.isEmpty())
			{
				subTours.add(subTour);
			}

		}
		
		return subTours;
		
	}
	
	/**
     * Method to stitch the sub tours into a single euler tour
     * @param list of Circular list of sub tours
     * @return a single euler tour
     *             
     */	
	public CircularList<TourNode,Vertex> stitchTours(List<CircularList<TourNode,Vertex>> subTours)
	{
		Iterator<CircularList<TourNode,Vertex>> itr = subTours.iterator();
		CircularList<TourNode,Vertex> eulerTour = null;
		if(itr.hasNext())
		{
			eulerTour = itr.next();
			
		}
		// iterate through the list of sub tours and merge
		while(itr.hasNext())
		{
			CircularList<TourNode,Vertex> c = itr.next();
			eulerTour.merge(c,index);
		}
		return eulerTour;
		
	}
	
	/**
     * Method to verify the euler tour
     * @param g
     *            : Graph - graph 
     * @param tour
     *            : CircularList - Euler tour to be verified
     *       
     * @return true if valid or false if not valid 
     *             
     */	
	public boolean verifyEulerTour(Graph g, CircularList<TourNode,Vertex> tour)
	{
		HashSet<Integer> graphEdges = createIndex(g);		
		Iterator<TourNode> itrc = tour.iterator();
		TourNode c = null;
		TourNode firstNode = null;
		Vertex v = null;
		Vertex u = null;
		Edge e = null;
		int edgeCount = 0;
		if(itrc.hasNext())
		{
			c = itrc.next();
			firstNode = c;
			v= c.v;
			e = c.e;

		}
		// iterate through the tour, get the edges and mark it as visited. 
		//if the same edge is again visited then return false
		while (itrc.hasNext())
		{
			
			c = itrc.next();
			u= c.v;
			//check if the other end of edge is u and if the edge is in the graph and is not visited yet
			if(u.equals(e.otherEnd(v)) && !e.visited && graphEdges.contains(c.e.edgeName))
			{
				e.visited = true;
				edgeCount++;
			}
			else
			{
				return false;
			}
			if(c == firstNode)
			{
				break;
			}
			v=u;
			e = c.e;
		}
		// check the total number of edges visited in the given tour with number of edges in the graph
		if(edgeCount!=g.numOfEdges)
			return false;
		return true;
	}
	
	/**
     * Method to create an index of all edges of the graph
     * @param g
     *            : Graph - graph 
         
     * @return graphEdges
     * 			  : HashSet 
     *             
     */	
	public HashSet<Integer> createIndex(Graph g)
	{
		HashSet<Integer> graphEdges =  new HashSet<>();
		for(Vertex v: g)
		{
			graphEdges.addAll(v.adjEdgeNames);
		}
		return graphEdges;
	}
	 

}
