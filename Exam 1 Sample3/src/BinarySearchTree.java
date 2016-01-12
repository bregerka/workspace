import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class BinarySearchTree<T extends Comparable<? super T>> implements
		Iterable<T> {

	private BinaryNode root;
	private int size;

	public BinarySearchTree() {
		root = null;
		size = 0;
	}

	/**
	 * This method determines whether the current BST forms a set or not. Notice
	 * that the given code was modified to allow for sets.
	 * 
	 * This method runs in linear time.
	 * 
	 * @return This method returns true if the BST forms a set and false
	 *         otherwise.
	 */
	public boolean isSet() {
		if (root == null)
			return true;
			

		return true;
	}

	/**
	 * This method determines the ratio of the longest depth and the shortest
	 * depth from the root to a leaf. For this problem, we calculate the depth
	 * as the number of nodes, from the root to a null child. For example, the
	 * balance factor of the following tree is: 3/2 10 / \ 5 15 / \ / \ null
	 * null 12 18 / \ / \ null null null null
	 * 
	 * The balance factor of the following tree is: 2/1 10 / \ null 15 / \ null
	 * null
	 * 
	 * This method runs in linear time.
	 *
	 * @return This method returns the ratio of longest to shortest depth.
	 */
	public String balanceFactor() {
		if (root == null)
			return "0/0";
		int small = 1;
		int large = this.height() + 1;
		BinaryNode n= root;
		
		while(n.leftChild!=null){
			if (n.leftChild.height()+1 < small) {
				small = n.leftChild.height() ;
			}
			if (n.rightChild != null)
				if (n.rightChild.height() < small)
					small = n.rightChild.height() ;
			
			n=n.leftChild;
		}
		BinaryNode r=root;
		while(r.rightChild!=null){
			if (r.rightChild.height() < small) {
				small = r.rightChild.height() ;
			}
			if (r.leftChild != null)
				if (r.leftChild.height() < small)
					small = r.leftChild.height() ;
			
			r=r.rightChild;
		}
		
		return large + "/" + small;

	}
	/**
	 * This method places the elements of the BST into an ArrayList in a manner
	 * that enables us to recreate the exact same BST from the ArrayList. For
	 * example the following tree would be stored as follows: 10, 5, 15, null,
	 * null, 12, 18.
	 * 
	 * 10 / \ 5 15 / \ 12 18
	 * 
	 * Notice that if we draw the contents of the ArrayList, we get 10 / \ 5 15
	 * / \ / \ null null 12 18
	 * 
	 * The following tree would be stored as follows: 10, null, 15, null, null,
	 * null, 20 10 \ 15 \ 20
	 * 
	 * Again, drawing the contents of the ArrayList, we get: 10 / \ null 15 / \
	 * / \ null null null 20
	 * 
	 * This method runs in linear time.
	 * 
	 * @return This method returns an ArrayList as described.
	 */
	public ArrayList<T> store() {
		ArrayList<T> answer = new ArrayList<T>();
		if (root != null) {
			answer.add(root.element);
			int h = height();
			while(h > -1){
			if (root.leftChild != null || root.rightChild != null) {
				if (root.leftChild != null)
					answer.add(root.leftChild.element);
				else
					answer.add(null);
				if (root.rightChild != null)
					answer.add(root.rightChild.element);
				else
					answer.add(null);
			}
			else
			{
				if (root.leftChild != null)
					answer.add(root.leftChild.element);
				if (root.rightChild != null)
					answer.add(root.rightChild.element);
			}
			h--;
			}
		}
		return answer;
	}

	
	public boolean insert(T element) {
		if (element == null) {
			throw new NullPointerException(
					"Cannot insert null element into binary search tree");
		} else if (root == null) {
			root = new BinaryNode(element);
			size++;
			return true;
		} else {
			return root.insert(element);
		}
	}

	public String toString() {
		if (root == null)
			return "";
		return root.toString();
	}

	public String simpleToString() {
		if (root == null)
			return "";
		return root.simpleToString();
	}

	public Iterator<T> iterator() {
		return new TreeIterator();
	}

	public int height() {
		if (root == null)
			return -1;
		return root.height();
	}

	private class BinaryNode {

		private T element;
		private BinaryNode leftChild;
		private BinaryNode rightChild;

		public BinaryNode(T e) {
			element = e;
			leftChild = rightChild = null;
		}

		public boolean insert(T element2) {
			if (element.compareTo(element2) >= 0) {
				if (leftChild == null) {
					leftChild = new BinaryNode(element2);
					size++;
					return true;
				} else {
					return leftChild.insert(element2);
				}
			} else {
				if (rightChild == null) {
					rightChild = new BinaryNode(element2);
					size++;
					return true;
				} else {
					return rightChild.insert(element2);
				}
			}
		}

		public String toString() {
			String s = "[" + element + " "
					+ ((leftChild == null) ? null : leftChild.element) + " "
					+ ((rightChild == null) ? null : rightChild.element)
					+ "]\n";
			if (leftChild != null) {
				s += leftChild.toString();
			}
			if (rightChild != null) {
				s += rightChild.toString();
			}
			return s;
		}

		public String simpleToString() {
			String s = element.toString();
			if (leftChild != null) {
				s += leftChild.simpleToString();
			}
			if (rightChild != null) {
				s += rightChild.simpleToString();
			}
			return s;
		}

		public ArrayList<BinaryNode> getLeftAncestors() {
			ArrayList<BinaryNode> temp = new ArrayList<BinaryNode>();
			BinaryNode ptr = this;
			while (ptr != null) {
				temp.add(0, ptr);
				ptr = ptr.leftChild;
			}
			return temp;
		}

		public int height() {
			int lheight = -1;
			int rheight = -1;
			if (leftChild != null) {
				lheight = leftChild.height();
			}
			if (rightChild != null) {
				rheight = rightChild.height();
			}
			if (lheight > rheight)
				return lheight + 1;
			return rheight + 1;
		}
	}

	// This is an in-order iterator
	private class TreeIterator implements Iterator<T> {
		private ArrayList<BinaryNode> nodes;

		public TreeIterator() {
			nodes = new ArrayList<BinaryNode>();
			if (root != null) {
				nodes.addAll(root.getLeftAncestors());
			}
		}

		public boolean hasNext() {
			return !nodes.isEmpty();
		}

		public T next() {
			if (!hasNext())
				throw new NoSuchElementException();
			BinaryNode temp = nodes.remove(0);
			if (temp.rightChild != null) {
				nodes.addAll(0, temp.rightChild.getLeftAncestors());
			}
			return temp.element;
		}

		public void remove() {
		}
	}

}
