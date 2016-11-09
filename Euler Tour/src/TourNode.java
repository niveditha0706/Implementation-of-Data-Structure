/**
 * Class for Tour node
 *  @author Niveditha
 *  Ver 1.0
 */
public class TourNode {
	public Vertex v;
	public Edge e;
	
	TourNode(Vertex v, Edge e)
	{
		this.v = v;
		this.e= e ;
	}
	public String toString() {
		return Integer.toString(this.v.name);
	    }

}
