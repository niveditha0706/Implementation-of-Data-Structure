/**
 *  Class for SkipList implementation
 *  @author Niveditha
 *  Ver 1.0
 *
 */
import java.util.Iterator;
import java.util.Random;


//skip list implementation.

public class SkipListImpl<T extends Comparable<? super T>> implements SkipList<T> {
	
	/**
	 *  Class for SkipList entries
	 *  element: contains the value
	 *  next[] : array of next pointers
	 *  level: height of the next pointers
	 *  width[]: No of entries between pointers
	 *
	 */
	@SuppressWarnings("hiding")
	class Entry<T>
	{
		T element;
		Entry<T>[] next;
		int level;
		int[] width;
		
		/**
		 * Constructor for Entry class
		 * @param x
		 * @param lvl
		 */
		@SuppressWarnings("unchecked")
		public Entry(T x, int lvl)
		{
			this.element = x;
			this.level = lvl;
			this.next = new Entry[lvl];
			this.width = new int[lvl];
		}
	}
	
	/**
	 * maxLevel: The max height of the next pointers
	 * header: Header of the SkipList
	 * tail: Tail of the SkipList
	 * last: Last element of the SkipList
	 * skipListSize: size of the SkipList
	 * countElements: array used for storing number of elements crossed
	 */
	int maxLevel = 10;
	Entry<T> header,tail;
	Entry<T> last;
	int countElements[];
	int skipListSize;

	
	/**
	 *  Class for storing SkipList entries and count of elements
	 *  element: contains the a SkipList entry
	 *  count: No of node crossed up to this element
	 *
	 */
	@SuppressWarnings("hiding")
	class Node<T>
	{
		Entry<T> entry;
		int count;
		
		/**
		 * Constructor for Node class
		 * @param x
		 */
		public Node(Entry<T> x)
		{
			this.entry = x;
			this.count = 0;
		}
	}
	
	/**
	 * Constructor of SkipListImpl class
	 */
	public SkipListImpl()
	{
		this.header = new Entry<T>(null,this.maxLevel);
		this.tail = new Entry<T>(null,this.maxLevel);
		this.last = null;
		this.skipListSize =0;

		for(int i = this.maxLevel-1;i>=0;i--)
		{
			this.header.next[i] = this.tail;
			this.header.width[i]=1;
			this.tail.next[i]= null;
			this.tail.width[i]=0;
		}	
		
	}
	
	/**
	 *class for implementing iterator for SkipList
	 */
	class SLIterator implements Iterator<T>
	{
		Entry<T> cursor;
		
		/**
		 * Constructor for SLIterator class
		 * @param header
		 */
		public SLIterator(Entry<T> header)
		{
			cursor = header;
		}
		
		/**
		 * method to Check if the SkipList has next element
		 */
		public boolean hasNext()
		{
			return cursor.next[0].element!=null;
		}
		
		/**
		 * method to get the next element of the SkipList
		 */
		public T next()
		{
			cursor = cursor.next[0];
			return cursor.element;
		}
	}
	
	
	/**
	 * method to randomly select the level of the SkipList node
	 * @return level
	 */
	private int choice()
	{
		int level = 0;
		while(level<maxLevel-1)
		{
			boolean b = new Random().nextBoolean();
			if(b)
			{
				break;
			}
			else
			{
				level++;
			}
		}
		return level;
	}
	
	/**
	 * method to find the element x in the SkipList
	 * @param x
	 * @return
	 */
	public Entry<T>[] find(T x)
	{
		Entry<T> p = this.header;
		@SuppressWarnings("unchecked")
		Entry<T>[] prev = new Entry[this.maxLevel];
		countElements = new int[this.maxLevel];
		
		for(int i = this.maxLevel-1;i>=0;i--)
		{
			int count = 0;
			while(checkNullAndCompare(p,x,i))
			{
				count = count + p.width[i];
				p = p.next[i];
				
				
			}
			countElements[i] = count; //Holds the value of no of nodes crossed
			prev[i]= p;
		}
			
		return prev;
	}
	
	
	/**
	 * Method to add an element to the SkipList
	 */
    @Override
    public boolean add(T x)
    {
    	if(this.skipListSize>(Math.pow(2,this.maxLevel))*1.50)
		{
			rebuild();
		}
    	Entry<T>[] prev = find(x);
    	if (checkNullAndCompare(prev[0],x))
    	{
    		return false;
    	}
    	else
    	{
    		int nodeLevel = choice();
    		Entry<T> newNode = new Entry<T>(x,nodeLevel+1);
    		//for loop for updating the next pointers of the nodes to reflect the add operation
    		for(int i =0; i<=nodeLevel;i++)
    		{
    			newNode.next[i] = prev[i].next[i];
    			prev[i].next[i] = newNode;
    		
    		}
    		
    		
    		if(newNode.next[0]== this.tail)
    		{
    			this.last = newNode;
    		}
    		this.skipListSize++;
    		
    		
    		// for loop for updating the width of the nodes to reflect the add operation
    		int count = 0;
    		for(int i =0; i<=this.maxLevel-1;i++)
    		{
    			if(i==0)
    			{
    				prev[i].width[i] = 1;
    				prev[i].next[i].width[i] = 1;
    			}
    			else
    			{
    				if(i>nodeLevel)
    				{
    					prev[i].width[i] = prev[i].width[i] +1;
    				}
    				else
    				{
    					int tempWidth = prev[i].width[i];
        				count = count + this.countElements[i-1];
        				prev[i].width[i] = count+1;
        				prev[i].next[i].width[i] = tempWidth - prev[i].width[i] +1;
    				}
    			}
    		}
    	
    		return true;
    	}
	
    }
    
    /**
     * Method to remove an element from the SkipList
     */
    @Override
    public T remove(T x) 
    {
    	Entry<T>[] prev = find(x);
    	if(checkNullAndCompare(prev[0],x))
    	{
    		Entry<T> node = prev[0].next[0];
    		// for loop for updating the width of the nodes  to reflect the remove operation
    		for(int i =0;i<this.maxLevel;i++)
    		{
    			if(i< node.level)
    				prev[i].width[i] = prev[i].width[i] + node.width[i]-1;
    			else
    				prev[i].width[i] = prev[i].width[i] - 1;
    		}
    		//for loop for updating the next pointers of the nodes to reflect the remove operation
    		for(int i =0;i<=this.maxLevel-1;i++)
    		{
    			if (prev[i].next[i]== node)
    			{
    				prev[i].next[i] = node.next[i];
    				
    			}
    			else
    				break;
    			
    		}
    		if(prev[0].next[0]== this.tail)
    		{
    			this.last = prev[0];
    		}
    		
    		this.skipListSize--;
    		
    		if(this.skipListSize>(Math.pow(2,10)) && this.skipListSize<(Math.pow(2,this.maxLevel))*0.50)
    		{
    			rebuild();
    		}
    		return node.element;
    	}
    	else
    	{
    		return null;
    	}
	
    }
    
    

    /**
     * Method to get the ceiling of the given x
     * Least element that is >= x, or null if no such element
     */
    @Override
    public T ceiling(T x) {
    	Entry<T>[] prev = find(x);
    	return prev[0].next[0].element;
    }

    /**
     * Method to find if the given element is present in the list or not
     */
    @Override
    public boolean contains(T x) {
    	Entry<T>[] prev = find(x);
    	if (checkNullAndCompare(prev[0],x))
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}	
    }

    /**
     * Method to find the element at the given index in O(log n) time
     * Returns the element at index n in the list (index starts at 0)
     */
    @Override
    public T findIndex(int n) 
    {
    	n=n+1;
    	if(n>this.skipListSize)
    	{
    		return null;
    	}
    	else
    	{
    		
    		Entry<T> node = this.header;
    		int ind = 0;
    		int i = this.maxLevel - 1;
    		while(i>=0)
    		{
    			while((ind+node.width[i]<=n))
    			{
    				ind = ind + node.width[i];
    				node = node.next[i];
    			}
    			
    			if(ind == n)
    			{
    				break;
    			}
    			i--;
    			
    		}
    		return node.element;
    	}
    	
    	
    }

    /**
     * Method to retrieve the first element of the SkipList
     */
    @Override
    public T first() {
	return this.header.next[0].element;
    }

    /**
     * Method to get the floor value of the given x
     * Greatest element that is <= x, or null if no such element
     */
    @Override
    public T floor(T x)
    {
    	Entry<T>[] prev = find(x);
    	if(checkNullAndCompare(prev[0],x))
    	{
    		return prev[0].next[0].element;
    	}
    	else
    	{
    		return prev[0].element;
    	}
	
    }

    /**
     * Method to find if the SkipList is empty or not
     */
    @Override
    public boolean isEmpty() {
    	if(this.skipListSize ==0)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
	
    }

    
    /**
     * Method to get new iterator for the SkipList
     */
    @Override
    public Iterator<T> iterator() {
	return new SLIterator(this.header);
    }

    /**
     * Method to retrieve the last element of the SkipList (element before the tail)
     */
    @Override
    public T last() 
    {
		return this.last.element;
    	
    }

    
    /**
     * Method to Rebuild a perfect SkipList of the given Size
     */
    @Override
    public void rebuild() 
    {
    	@SuppressWarnings("unchecked")
		Entry<T>[] rebuildSL = new Entry[this.skipListSize];
    	this.maxLevel = (int)Math.ceil((Math.log(this.skipListSize)/Math.log(2))); //new maxLevel
    	//builds an array that represents perfect SkipList without header and tail
    	rebuildHelper(rebuildSL,0,this.skipListSize-1,this.maxLevel); 
    	@SuppressWarnings("unchecked")
		Node<T>[] pointer = new Node[this.maxLevel];
    	
     	Entry<T> cursor = this.header.next[0];
    	this.header = new Entry<T>(null,this.maxLevel); //new header
    	for(int i=0;i<this.header.level;i++)
		{
			pointer[i]= new Node<T>(this.header);
		}
    	
    	//Populate values into the perfect SkipList from the old list
    	for(int k =0; k < rebuildSL.length;k++)
    	{
    		
    		rebuildSL[k].element = cursor.element;
    		rebuildElementsAndWidth(rebuildSL[k],pointer);
    		cursor = cursor.next[0];    		
    	}
    	this.tail = new Entry<T>(null,this.maxLevel); //new tail
    	for(int i=0;i<this.tail.level;i++)
		{
			this.tail.width[i] = 0;
			this.tail.next[i]= null;
		}
    	
    	rebuildElementsAndWidth(this.tail,pointer);
    	this.last = rebuildSL[rebuildSL.length-1]; //set the last element of the list

    }
    
    /**
     * Method to build an array with Entries of a perfect SkipList
     * @param rbSL
     * @param p
     * @param r
     * @param k
     */
    private void rebuildHelper(Entry<T>[] rbSL, int p,int r,int k)
    {
    	if(p<=r)
    	{
    		if (k==0)
    		{
    			for(int i = p;i<=r;i++)
    			{
    				rbSL[i] = new Entry<T>(null,0);
    			}
    		}
    		else
    		{
    			int q = (int) Math.ceil(((double)p+r)/2);
    			rbSL[q] = new Entry<T>(null,k);
    			rebuildHelper(rbSL,p,q-1,k-1);
    			rebuildHelper(rbSL,q+1,r,k-1);
    		}
    	}
    }

    /**
     * Helper function used to populate the elements into the empty perfect SkipList
     * @param node
     * @param pointer
     */
    private void rebuildElementsAndWidth(Entry<T> node,Node<T>[] pointer)
    {
    	for(int i =0;i<this.maxLevel;i++)
    	{
    		if(i<node.level)
    		{
    			pointer[i].entry.next[i] = node;
    			pointer[i].entry.width[i] = pointer[i].count +1;
    			pointer[i].entry = node;
    			pointer[i].count = 0;
    		}
    		else
    		{
    			pointer[i].count = pointer[i].count +1;
    		}
    	}
    }

    
    /**
     * Method to get the size of the SkipList 
     * returns no of elements in the list
     */
    @Override
    public int size() 
    {
    	return this.skipListSize;
    }
    
    
    /**
     * Helper function to check null and compare the element
     * @param node
     * @param x
     * @return
     */
    public boolean checkNullAndCompare(Entry<T> node,T x)
    {
    	if(node!=null)
    	{
    		if(node.next[0]!=null)
    		{
    			if(node.next[0].element!=null)
    			{
    				return node.next[0].element.compareTo(x)==0;
    			}
    		}
    	}
    	return false;
    }
    
    /**
     * Helper function to check null and compare the element
     * @param node
     * @param x
     * @return
     */
    public boolean checkNullAndCompare(Entry<T> node,T x,int i)
    {
    	if(node!=null)
    	{
    		if(node.next[i]!=null)
    		{
    			if(node.next[i].element!=null)
    			{
    				return node.next[i].element.compareTo(x)<0;
    			}
    		}
    	}
    	return false;
    }
}