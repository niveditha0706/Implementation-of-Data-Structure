/** driver program using the graph class
 *  @author Niveditha
 *  Ver 1.0: Reads a graph and prints its Euler tour if the graph given is Eulerian.
 */

import java.util.List;
import java.util.Scanner;
import java.io.File;

public class Driver {
	
    public static void main(String[] args) {
    	try
    	{
    		Scanner in;
    		Timer t = new Timer();
    	        if (args.length > 0) {
    	            File inputFile = new File(args[0]);
    	            in = new Scanner(inputFile);
    	        } else {
    	            in = new Scanner(System.in);
    	        }
    	    
    	    t.start();
    		Graph g = Graph.readGraph(in);
    		t.end();
    		System.out.println("Time taken to read graph:" + t);
    		EulerTour et = new EulerTour();
    		t.start();
    		boolean hasEulerTour = et.hasEulerTour(g);
    		if(hasEulerTour)
    		{
    			t.end();
    			System.out.println("Time taken to test if the given graph has Euler tour or not:" + t);
    			t.start();
    			List<CircularList<TourNode,Vertex>> subTours =  et.breakIntoSubTour(g);
    			t.end();
    			System.out.println("Number of Sub tours: " + subTours.size());
    			System.out.println("Time taken to break the graph into sub tours:" + t);
    			t.start();
    			CircularList<TourNode,Vertex> eulerTour = et.stitchTours(subTours);
    			t.end();
    			System.out.println("Time taken to stitch the sub tours:" + t);
    			
    			t.start();
    			System.out.println("Euler Tour:");
    			eulerTour.printList();
    			t.end();
    			System.out.println("Time taken to print the Euler tour:" + t);
    			
    			t.start();
    			boolean verifyTour = et.verifyEulerTour(g, eulerTour);
    			if(verifyTour)
    			{
    				System.out.println("The tour is a valid Eulerian tour.");
    			}
    			else
    			{
    				System.out.println("The tour is not a valid Eulerian tour.");
    			}
    			t.end();
    			System.out.println("Time taken to verify the Euler tour:" + t);
    		}
    		else
    		{
    			System.out.println("The Graph is not Eulerian. So there exist no Euler tour for the given graph.");
    		}
    		
    	}
    	catch(Exception e)
    	{
    		System.out.println("Program Terminated due to exception:");
    		System.out.println(e);
    	}
	
	
    }
    
}

