
/** 
 * Indexed heap Implementation 
 * 
 */
import java.util.Comparator;

public class IndexedHeap<T extends Index> extends BinaryHeap<T> {
    /** Build a priority queue with a given array q */
    IndexedHeap(T[] q, Comparator<T> comp) 
    {
    	super(q, comp);
    }

    /** Create an empty priority queue of given maximum size */
    IndexedHeap(int n, Comparator<T> comp) 
    {
    	super(n, comp);
    }

    /** restore heap order property after the priority of x has decreased */
    void decreaseKey(T x) 
    {
    	percolateUp(x.getIndex());
    }
    
    /** 
     * Method to transfer elements and Assign index
     * 
     */
    public void transferIndex(int i, int j)
    {
    	super.transferIndex(i, j);
    	pq[i].putIndex(i);
    }
}
