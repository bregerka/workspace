import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.Test;

public class Testing {
		
	private static int points = 0;
	
	@Test
	public void testBalanceFactor(){
		BinarySearchTree<Integer> b = new BinarySearchTree<Integer>();
		assertEquals("0/0", b.balanceFactor());
		points += 1;
		b.insert(5);
		assertEquals("1/1", b.balanceFactor());
		points += 2;
		b.insert(2);
		assertEquals("2/1", b.balanceFactor());
		b.insert(1);
		assertEquals("3/1", b.balanceFactor());
		b.insert(3);
		assertEquals("3/1", b.balanceFactor());
		b.insert(4);
		assertEquals("4/1", b.balanceFactor());
		b.insert(15);
		assertEquals("4/2", b.balanceFactor());
		b.insert(20);	
		assertEquals("4/2", b.balanceFactor());
		b.insert(12);
		assertEquals("4/3", b.balanceFactor());
		b.insert(11);
		b.insert(13);
		b.insert(19);
		b.insert(21);		
		assertEquals("4/3", b.balanceFactor());
		points += 17;
	}
	
	@Test
	public void testIsSet(){
		BinarySearchTree<Integer> b = new BinarySearchTree<Integer>();
		assertTrue(b.isSet());
		points += 1;
		b.insert(5);
		b.insert(2);
		b.insert(10);
		b.insert(15);
		b.insert(20);
		assertTrue(b.isSet());
		points += 8;
		b.insert(2);
		assertFalse(b.isSet());
		points += 8;
		b.insert(15);
		assertFalse(b.isSet());
		points += 3;
	}
	
	@Test
	public void testStore(){
		BinarySearchTree<Integer> b = new BinarySearchTree<Integer>();
		ArrayList<Integer> a = b.store();
		assertEquals(0, a.size());
		points += 2;
		
		b.insert(10);
		a = b.store();
		assertEquals(1, a.size());	
		assertEquals(new Integer(10), a.get(0));
		points += 3;
		
		b.insert(5);
		b.insert(15);
		a = b.store();
		assertEquals(3, a.size());	
		assertEquals(new Integer(10), a.get(0));	
		assertEquals(new Integer(5), a.get(1));
		assertEquals(new Integer(15), a.get(2));
		points += 3;
		
		b = new BinarySearchTree<Integer>();
		b.insert(10);
		b.insert(15);
		a = b.store();
		assertEquals(3, a.size());	
		assertEquals(new Integer(10), a.get(0));
		assertEquals(null, a.get(1));
		assertEquals(new Integer(15), a.get(2));
		points += 5;
		
		b.insert(5);
		b.insert(12);
		b.insert(18);
		a = b.store();
		assertEquals(7, a.size());	
		assertEquals(new Integer(10), a.get(0));
		assertEquals(new Integer(5), a.get(1));
		assertEquals(new Integer(15), a.get(2));
		assertEquals(null, a.get(3));
		assertEquals(null, a.get(4));
		assertEquals(new Integer(12), a.get(5));
		assertEquals(new Integer(18), a.get(6));		
		points += 7;
		b = new BinarySearchTree<Integer>();
		b.insert(10);
		b.insert(15);
		b.insert(20);
		a = b.store();
		assertEquals(7, a.size());	
		assertEquals(new Integer(10), a.get(0));
		assertEquals(null, a.get(1));
		assertEquals(new Integer(15), a.get(2));
		assertEquals(null, a.get(3));
		assertEquals(null, a.get(4));
		assertEquals(null, a.get(5));
		assertEquals(new Integer(20), a.get(6));		
		points += 5;
	}
	
	@AfterClass
	public static void testNothing(){
		System.out.println(points);
	}
	
	
}

