
/**
 * Class for Binary Heap Implementation
 * @author Niveditha
 *
 */
import java.util.Comparator;

public class BinaryHeap<T> implements PQ<T> {
    T[] pq;
    Comparator<T> c;
    int heapSize;
    
    /** Build a priority queue with a given array q */
    BinaryHeap(T[] q, Comparator<T> comp) 
    {
		pq = q;
		c = comp;
		heapSize = q.length - 1;
		buildHeap();
    }

    /** 
     * Create an empty priority queue of given maximum size 
     * 
     */
    @SuppressWarnings("unchecked")
	BinaryHeap(int n, Comparator<T> comp) 
    { 
    	c = comp;
    	pq = (T[]) new Object[n+1];
    	heapSize = 0;
    }

    /** 
     * Method to insert an element 
     * 
     */
    public void insert(T x) 
    {
    	add(x);
    }

    /** 
     * Method to delete the minimum element 
     * 
     */
    public T deleteMin() 
    {
    	if(heapSize == 0)
    	{
    		return null;
    	}
    	return remove();
    }

    /** 
     * Method to get the minimum element 
     * 
     */
    public T min() 
    { 
    	return peek();
    }

    /** 
     * Method to add an element 
     * 
     */
    @SuppressWarnings("unchecked")
	public void add(T x) 
    { 
    	if( heapSize == pq.length -1)
    	{
    		T[] tempHeap = pq;
    		pq = (T[]) new Object[tempHeap.length * 2 + 1];
    		for(int i = 0; i< tempHeap.length ; i++)
    		{
    			pq[i] = tempHeap[i];
    		}
    		
    		
    	}
    	pq[++heapSize] = x;
		percolateUp(heapSize);
    }

    /** 
     * Method to remove an element 
     * 
     */
    public T remove() 
    { 
    	T value = peek();
    	pq[1] = pq[heapSize--];
    	percolateDown(1);
    	return value;
    }

    /** 
     * Method to get the root element 
     * 
     */
    public T peek() 
    { 
    	if(heapSize == 0)
    	{
    		return null;
    	}
    	
    	return pq[1];
    }

    /** pq[i] may violate heap order with parent */
    void percolateUp(int i)
    {
    	int hole = i;
    	pq[0] = pq[i];
    	while (c.compare(pq[0], pq[hole/2]) < 0)
    	{
    		transferIndex(hole,hole/2);
    		hole = hole/2;
    	}
    	
    	transferIndex(hole,0);
    }

    /** pq[i] may violate heap order with children */
    void percolateDown(int i) 
    { 
    	pq[0] = pq[i];
    	
    	while(2*i <= heapSize)
    	{
    		if (2*i == heapSize)
    		{
    			if (c.compare(pq[0], pq[heapSize]) > 0)
    			{
    				transferIndex(i,heapSize);
    				i = heapSize;
    			}
    			else
    			{
    				break;
    			}
    		}
    		else
    		{
    			int sChild = 2*i;
        		if (c.compare(pq[sChild], pq[sChild+1]) > 0)
        		{
        			sChild++;
        		}
        		if (c.compare(pq[0], pq[sChild]) >0)
        		{
        			transferIndex(i,sChild);
        			i = sChild;
        		}
        		else
        		{
        			break;
        		}
    			
    		}
    		
    	}
    	transferIndex(i,0);
    	
    }
    
    /** 
     * Method to check if the heap is empty or not 
     * 
     */
    public boolean isEmpty()
    {
    	return heapSize == 0;
    }
    
    /** 
     * Method to transfer elements
     * 
     */
    public void transferIndex(int i, int j)
    {
    	pq[i] = pq[j];
    }

    /** Create a heap.  Precondition: none. */
    void buildHeap() 
    {
    	for(int i = heapSize/2;i>0;i--)
    	{
    		percolateDown(i);
    	}
    }

    /* sort array A[1..n].  A[0] is not used. 
       Sorted order depends on comparator used to build heap.
       min heap ==> descending order
       max heap ==> ascending order
     */
    public static<T> void heapSort(T[] A, Comparator<T> comp) 
    { 
    	BinaryHeap<T> binaryHeap = new BinaryHeap<>(A,comp);
    	for(int i = binaryHeap.heapSize; i>=2;i--)
    	{
    		binaryHeap.pq[i] = binaryHeap.remove();
    		
    	}
    }
}
