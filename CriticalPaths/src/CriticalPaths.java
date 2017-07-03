import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
/**
 * Class for finding Critical paths in PERT charts
 * @author Niveditha
 *
 */
public class CriticalPaths {
	Graph g;
	Vertex s;
	Vertex t;
	HashSet<Vertex> criticalNodes;
	List<LinkedList<Vertex>> criticalPaths;
	
	/**
	 * Constructor for the class CriticalPaths
	 * @param g
	 */
    CriticalPaths(Graph g) {
    	this.g = g;
    	this.s = g.getVertex(g.size-1); // Initialize start vertex s
    	this.t = g.getVertex(g.size); // Initialize end vertex t
    	
    	//add edges for s and t.
    	for(Vertex v: this.g)
    	{
    		if(!(v.equals(s)) && !(v.equals(t)))
    		{
    			if(v.revAdj.size() ==0)
    			{
    				this.g.addEdge(s, v, 1);
    			}
    			if(v.adj.size()==0)
    			{
    				this.g.addEdge(v, t, 1);
    			}
    		}
    	}
    	
    	this.criticalNodes = new HashSet<>(); //maintains set of critical nodes
    	this.criticalPaths = new ArrayList<>(); // stores all critical paths	
    }
    
    
    /**
     * Method to find Critical Paths
     */
    public void findCriticalPaths() {
    	
    	computeEC(); // Computes early completion time for the tasks
    	computeLCAndSlack(); // computes latest completion time and slack for the tasks
    	findCriticalNodes(); // finds all critical nodes
    	LinkedList<Vertex> path=new LinkedList<>();
    	path.add(s);
    	findCriticalPaths(s,path); // finds all critical paths
    	printOutput(); //prints the output
    	
    	
    }
    
    
    /**
     * Method to find the topological ordering of the vertices using indegree
     * @return
     */
    public List<Vertex> findTopologicalOrder()
    {
    	LinkedList<Vertex> topList = new LinkedList<Vertex>();
    	
    	for(Vertex u: g)
    	{
    		u.inDegree = u.revAdj.size();
    	}
    	
    	Queue<Vertex> q = new LinkedList<Vertex>();
    	int count = 0;
    	
    	for(Vertex u: g)
    	{
    		if(u.inDegree ==0)
    		{
    			q.add(u);
    		}
    	}
    	while(!q.isEmpty())
    	{
    		Vertex u = q.remove();
    		topList.add(u);
    		u.topOrder = ++count;
    		for(Edge e: u.adj)
    		{
    			Vertex v = e.otherEnd(u);
    			v.inDegree--;
    			if(v.inDegree ==0)
        		{
        			q.add(v);
        		}
    		}
    	}
    	
    	if(count != g.size)
    	{
    		System.out.println("The Given Graph G is not a DAG");
    		System.exit(0);
    	}
    	
    	return topList;
    }
    
    
    /**
     * Method to find reverse topological order using DFS finish time
     * @return
     */
    public List<Vertex> findRevTopologicalOrder()
    {
    	LinkedList<Vertex> topList = new LinkedList<Vertex>();
    	for(Vertex v: g)
    	{
    		v.seen = false;
    	}
    	for(Vertex v: g)
    	{
    		if(!v.seen)
    		{
    			DFSVisit(v,topList);
    		}
    	}
    	
    	return topList;
    }
    
    /**
     * Method for DFS visit of a node
     * @param u
     * @param topList
     */
    public void DFSVisit(Vertex u, LinkedList<Vertex> topList)
    {
    	u.seen = true;
    	for(Edge e: u.adj)
    	{
    		Vertex v = e.otherEnd(u);
    		if (!v.seen)
    		{
    			DFSVisit(v,topList);
    		}
    	}
    	topList.add(u);
    }
    
    /**
     * Method to find early completion time
     */
    public void computeEC()
    {
    	s.ec = 0;
    	for(Vertex v :g)
    	{
    		v.ec = v.d;
    	}
    	List<Vertex> topOrder = findTopologicalOrder();
    	for(Vertex u: topOrder)
    	{
    		for(Edge e: u.adj)
    		{
    			Vertex v = e.otherEnd(u);
    			if (v.ec< u.ec+v.d)
    			{
    				v.ec = u.ec+v.d;
    			}
    		}
    	}
    }
    
    /**
     * Method to find Latest completion time and slack
     */
    public void computeLCAndSlack()
    {
    	t.lc = t.ec;
    	for(Vertex v: g)
    	{
    		v.lc = t.lc;
    	}
    	List<Vertex> revTopOrder = findRevTopologicalOrder();
    	for(Vertex u: revTopOrder)
    	{
    		u.slack = u.lc - u.ec;
    		for(Edge e: u.revAdj)
    		{
    			Vertex v = e.otherEnd(u);
    			if (v.lc> u.lc-u.d)
    			{
    				v.lc = u.lc - u.d;
    			}
    		}
    	}
    	
    }
    
    /**
     * Method to find critical nodes using EC and LC
     */
    public void findCriticalNodes()
    {
		for(Vertex v:g)
		{
			if(v.ec == v.lc)
			{
				criticalNodes.add(v);
			}
		}
    }
    
    /**
     * method to find all critical paths using critical nodes and tight edges
     * @param currentNode
     * @param path
     */
    public void findCriticalPaths(Vertex currentNode,LinkedList<Vertex> path)
    {
    	if(currentNode.name==t.name)
		{
    		LinkedList<Vertex> cp = new LinkedList<>(path);
			criticalPaths.add(cp);
		}
		else
		{
				for(Edge e: currentNode.adj)
				{

					if((currentNode.name==s.name)||(isTightEdge(e)) )
					{
						Vertex v=e.otherEnd(currentNode);
						path.add(v);
						findCriticalPaths(v,path);
						path.remove(v);
					}
				}
		}
    	
    }
    
    /**
     * Method to check if an edge is tight edge or not
     * @param e
     * @return
     */
    public boolean isTightEdge(Edge e)
    {
    	Vertex u=e.from;
		Vertex v=e.to;
		if(criticalNodes.contains(v) && criticalNodes.contains(u))
		{
			if((u.lc+v.d) == v.lc)
			{
				return true;
			}
		}
		
		return false;
    }
    
    /**
     * Method to Print the Output
     */
    public void printOutput()
    {
    	//Length of critical path
    	System.out.println(t.lc);
    	
    	//A Critical path
    	if(criticalPaths.size()>0)
		{
			List<Vertex> path=criticalPaths.get(0);
			for(Vertex v: path)
			{
				if(v.name != s.name && v.name !=t.name)
				    System.out.print(v+" ");
			}
			System.out.println();
		}
    	
    	//for printing Task number, its earliest completion time, latest completion time, and slack.
    	printEcLcSlack(); 
    	System.out.println();
    	
    	//No of Critical nodes
    	System.out.println(criticalNodes.size()-2);
    	
    	//No of critical paths
    	System.out.println(criticalPaths.size());
    	
    	//for printing critical paths
    	printCriticalPaths();
    	System.out.println();
    }
    
    /**
     * Method to print the table of EC, LC and slack of all the tasks
     */
    public void printEcLcSlack()
	{
		System.out.println("Task\tEC\tLC\tSlack");
		for(Vertex v: g)
		{
			if(v.name != s.name && v.name !=t.name)
			     System.out.println(v+"\t"+v.ec+"\t"+v.lc+"\t"+v.slack);
		}
	}
    
    /**
     * Method to print all critical paths
     */
    public void printCriticalPaths()
    {
    	for(List<Vertex> path: criticalPaths)
		{
			for(Vertex v: path)
			{
				if(v.name != s.name && v.name !=t.name)
				    System.out.print(v+" ");
			}
			System.out.println();
		}
    	
    }
}
