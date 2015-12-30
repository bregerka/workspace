import static org.junit.Assert.*;
import org.junit.Test;

public class Testing {

	@Test
	public void testHeight(){
		BinarySearchTree b = new BinarySearchTree();
		assertEquals(-1, b.height());
		
		BinaryNode n1 = new BinaryNode(3);
		b = new BinarySearchTree(n1);
		assertEquals(0, b.height());
		BinaryNode n2 = new BinaryNode(4);
		BinaryNode n3 = new BinaryNode(5);
		n1.setRightChild(n2);
		n2.setRightChild(n3);
		assertEquals(2, b.height());
		BinaryNode n4 = new BinaryNode(2);
		n1.setLeftChild(n4);
		assertEquals(2, b.height());
	}
	
	@Test
	public void testSize(){
		BinarySearchTree b = new BinarySearchTree();
		assertEquals(0, b.size());
		
		BinaryNode n1 = new BinaryNode(3);
		b = new BinarySearchTree(n1);
		assertEquals(1, b.size());
		
		BinaryNode n2 = new BinaryNode(4);
		BinaryNode n3 = new BinaryNode(5);
		n1.setRightChild(n2);
		n2.setRightChild(n3);
		assertEquals(3, b.size());
		BinaryNode n4 = new BinaryNode(2);
		n1.setLeftChild(n4);
		assertEquals(4, b.size());
	}
	
	@Test
	public void testIsEmpty(){
		BinarySearchTree b = new BinarySearchTree();
		assertTrue(b.isEmpty());
		
		BinaryNode n1 = new BinaryNode(3);
		b = new BinarySearchTree(n1);
		assertFalse(b.isEmpty());
	}
	
		
	@Test
	public void testToString(){
		BinarySearchTree b = new BinarySearchTree();
		assertEquals("[]", b.toString());
		
		BinaryNode n1 = new BinaryNode(3);
		b = new BinarySearchTree(n1);
		assertEquals("[3]", b.toString());	
		BinaryNode n2 = new BinaryNode(4);
		BinaryNode n3 = new BinaryNode(5);
		n1.setRightChild(n2);
		n2.setRightChild(n3);
		assertEquals("[3, 4, 5]", b.toString());
		BinaryNode n4 = new BinaryNode(2);
		n1.setLeftChild(n4);
		assertEquals( "[2, 3, 4, 5]", b.toString());
	}

}