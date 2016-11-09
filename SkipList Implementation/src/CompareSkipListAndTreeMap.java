
import java.util.TreeMap;
import java.util.Random;
/**
 * 
 * @author Niveditha
 * Class for comparing SkipList and TreeMap
 *
 */
public class CompareSkipListAndTreeMap 
{
	public static void main(String args[])
	{
		Timer timer = new Timer();
		TreeMap<Long,Long> objTreeMap = new TreeMap<>();
		SkipListImpl<Long> objSkipList = new SkipListImpl<>();
		for(long i=1; i<=500000;i++)
		{
			long element =  new Random().nextLong();
			objTreeMap.put(element, element);
			objSkipList.add(element);
		}
		/*
		timer.start();
		for(long i=1; i<=50000;i++)
		{
			objTreeMap.put(i, i);
		
		}
		timer.end();
		System.out.println("Time taken for add() in TreeMap:"+timer);
		timer.start();
		for(long i=1; i<=50000;i++)
		{
			objSkipList.add(i);
		
		}
		timer.end();
		System.out.println("Time taken for add() in SkipList:"+timer);
		*/
		long data = 15243;
		timer.start();
		objTreeMap.put(data, data);
		timer.end();
		System.out.println("Time taken for add() in TreeMap:"+timer);
		timer.start();
		objSkipList.add(data);
		timer.end();
		System.out.println("Time taken for add() in SkipList:"+timer);
		data = 15456;
		timer.start();
		objTreeMap.containsKey(data);
		System.out.println("Time taken for contains() in TreeMap:"+timer);
		timer.end();
		
		timer.start();
		objSkipList.contains(data);
		System.out.println("Time taken for contains() in SkipList:"+timer);
		timer.end();
		
		data = 25123;
		timer.start();
		objTreeMap.remove(data);
		System.out.println("Time taken for remove() in TreeMap:"+timer);
		timer.end();
		
		timer.start();
		objSkipList.remove(data);
		System.out.println("Time taken for remove() in SkipList:"+timer);
		timer.end();
	}

}
