/** @author Niveditha
 *  Circularly linked list: for instructional purposes only
 *  Ver 1.0: initial description.
 */

import java.util.*;
public class CircularList<T,E> implements Iterable<T> {

	/** Class Entry holds a single node of the circular list */
	class Entry<T,E> {
		T node;
	    E element;
	    Entry<T,E> next;
	    Entry<T,E> prev;
	    
	    /**
	     * Constructor for node of the circular list
	     */
	    Entry(T x, E y,Entry<T,E> nxt,Entry<T,E> previous) {
	        node = x;
	        element = y;
	        next = nxt;
	        prev = previous;
	    }
	}
    // Dummy header is used.  tail stores reference of tail element of list
    Entry<T,E> header, tail, firstNode;
    int size;
    
    /**
     * Constructor for CircularList
     */
    CircularList() {
        header = new Entry<>(null, null, null, null);
        tail = header;
        size = 0;
        firstNode = null;
    }

    public Iterator<T> iterator() { return new SLLIterator<>(header); }

    /** Class SLLIterator is used for iterating through the circular list */
    private class SLLIterator<T> implements Iterator<T> {
	Entry<T,E> cursor, prev;
	SLLIterator(Entry<T,E> head) {
	    cursor = head;
	    prev = null;
	}

	public boolean hasNext() {
	    return cursor.next != null;
	}
	
	public T next() {
	    prev = cursor;
	    cursor = cursor.next;
	    return cursor.node;
	}

	public void remove() {
	    prev.next = cursor.next;
	    prev = null;
	}
    }

    // Add new Entry to the end of the list and make the tail point to the first element of the list
    void add(T x,E y) {
    	if (header == null)
    	{
    		header.next = new Entry<>(x,y,null,tail);
    		tail = header.next;
    		tail.next = header.next;
    		firstNode = header.next;
    		   	 	
    	}
    	else
    	{
    		tail.next= new Entry<>(x,y,null,tail);
            tail = tail.next;
            tail.next=header.next;
    	}
    	    	 	
    	size++;
	
    }

   
    /**
     * Method to print the Circular List
     */
    void printList() {
    	
    	//Code without using implicit iterator in for each loop:
    	Entry<T,E> x = header.next;
    	do
        {
        	if (x == null)
        	{
        		System.out.println("List is empty");
        		
        	}
        	else
        	{
        		System.out.println(x.node);
        		x = x.next;
        	}
        }while(x != header.next);
	
   		
    }
    
    
    /**
     * Method to check if the Circular List is empty
     */
	public boolean isEmpty()
	{
		return this.header.next == null;
	}
	
	/**
     * Method to get the last node of the Circular List
     */
	public Entry<T,E> getLastNode()
	{
		return this.tail;
	}

	/**
     * Method to merge two circular lists
     * Replace "this" list by the merging it with the circular list c.
     * If the first node of c stores x, find the node of "this" list that stores x and merge.
     */
	
	public void merge(CircularList<T,E> c, Queue<CircularList<T,E>.Entry<T,E>> index)
	{
		
    	Entry<T,E> firstNode_c = c.header.next;
    	while(!index.isEmpty())
    	{
    		Entry<T,E> currentNode_list = index.remove();
	    	if (currentNode_list.element== firstNode_c.element)
	    	{
	    		if (currentNode_list == this.header.next)
	    		{
	    			c.tail.next = currentNode_list;
	    			this.header.next = c.header.next;
	    			this.tail.next = this.header.next;
	    		}
	    		else
	    		{
	    			Entry<T,E> temp = currentNode_list.prev;
	    			temp.next = c.header.next;
		    		c.tail.next = currentNode_list;
	    		}
	    		
	    		break;
	    	}
    	}
    	
    	
	}
}