import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Stack;

//import AVLTree.BinaryNode;

/**
 * This class implements a Binary Search Tree. 
 *
 * @author wollowsk.
 *         Created January 5, 2011.
 */

public class BinarySearchTree <T extends Comparable<? super T>> implements Iterable<T>{

	private BinaryNode root;
	private int size;
	
	public BinarySearchTree() {
		root = null;	
		size = 0;
	}
	
	
	/** 
	 * This method places the elements of the BinarySearchTree into a Java PriorityQueue.
	 * It runs in linear time.
	 * 
	 * @return This method returns a Java PriorityQueue
	 */
	public PriorityQueue<T> toPriorityQueue(){
		PriorityQueue<T> priority = new PriorityQueue<T>();
		Iterator<T> i = this.iterator();
		while(i.hasNext()){
			priority.add(i.next());
		}
		return priority;
	}
	
	/** 
	 * This method reverses the order of the elements in the BinarySearchTree so that
	 * they now appear in reverse order.
	 * This method runs in linear time.
	 */
	public void reverse(){
		Stack<T> stack = new Stack<T>();
		Iterator<T> i = this.iterator();
		while(i.hasNext()){
			stack.push(i.next());
		}
		if(stack.empty()){
			return;
		}
		this.root = new BinaryNode(stack.pop());
		while(!stack.empty()){
			this.root.insert(stack.pop());
		}
	}

	
	/** 
	 * We consider a BST to form a V if each left child only has a left child 
	 * or is a leaf and if each right child has only a right child or is a leaf.
	 * Additionally, there must be as many left children as there are right children.
	 * A single node tree trivially forms a V and so does an empty tree.
	 * This method calls another method that is recursive and runs in linear time.
	 * Here is an example of a V:
	 * 								10
	 * 							5		  15
	 * 						3					18
	 * 					1							   20
	 *  
	 * @return This method returns true, if the BST forms a V and false otherwise.
	 */
	public boolean formsAV(){
		if(this.root == null) return true;
		if((this.root.leftChild == null) && (this.root.rightChild == null)) return true;
		if((this.root.leftChild == null) || (this.root.rightChild == null)) return false;
		BooleanWrapper value = new BooleanWrapper(true);
		root.recursiveSearch(value,root.leftChild,root.rightChild);
		return value.getBool();
	}
	
	public boolean insert(T element) {
		if (element == null) {
			throw new NullPointerException("Cannot insert null element into binary search tree");
		} else if (root == null) {
			root = new BinaryNode(element);
			size++;
			return true;
		} else {
			return root.insert(element);
		}
	}

	public String toString() {
		if (root == null) return "";
		return root.toString();
	}
	
	public String simpleToString() {
		if (root == null) return "";
		return root.simpleToString();
	}
	
	public Iterator<T> iterator(){
		return new TreeIterator();
	}

private class BinaryNode {
		
	    private T element;  
	    private BinaryNode leftChild;
	    private BinaryNode rightChild;
	    
	    public BinaryNode(T e) {  
			element = e;
			leftChild = rightChild = null;
	    }
	

	    public void recursiveSearch(BooleanWrapper value, BinaryNode lc, BinaryNode rc) {
	    	int rInt, lInt;
			lInt = leftSearch(lc, value, 0);
			if(value.bool == false) return;
			rInt = rightSearch(rc, value, 0);
			if(value.bool == false) return;
			if(lInt != rInt) value.setFalse();
		}
	    
	    public int leftSearch(BinaryNode lc, BooleanWrapper value, int count){
	    	if(lc.rightChild != null){
				value.setFalse();
				return count;
			}
	    	if(lc.leftChild == null) return count;
	    	if(lc.leftChild != null){
	    		count++;
	    		count = leftSearch(lc.leftChild,value,count);
	    	}
	    	return count;
	    }
	    public int rightSearch(BinaryNode rc, BooleanWrapper value,int count){
	    	if(rc.leftChild != null){
				value.setFalse();
				return count;
			}
	    	if(rc.rightChild == null) return count;
	    	if(rc.rightChild != null){
	    		count++;
	    		count = rightSearch(rc.rightChild,value,count);
	    	}
	    	return count;
	    }

		public boolean insert(T element2) {
	 		if (element.compareTo(element2) > 0) {
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
	    	String s = "[" + element + " " +
	    	 			((leftChild == null)? null : leftChild.element)+ " " +
	    	 			((rightChild == null)? null : rightChild.element)+	
	    	 			"]\n";
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
	    
		public ArrayList<BinaryNode> getLeftAncestors(){
			ArrayList<BinaryNode> temp = new ArrayList<BinaryNode>();
			BinaryNode ptr = this;
			while (ptr != null) {
				temp.add(0,ptr);
				ptr = ptr.leftChild;
			}
			return temp;
		}
	}

// This is an in-order iterator
private class TreeIterator implements Iterator<T> {
	private ArrayList<BinaryNode> nodes;

	public TreeIterator(){
		nodes = new ArrayList<BinaryNode>();
		if (root != null){
			nodes.addAll(root.getLeftAncestors());
		}
	}
	public boolean hasNext() {
		return !nodes.isEmpty();
	}

	public T next() {
		if (!hasNext()) throw new NoSuchElementException();
		BinaryNode temp = nodes.remove(0);
		if (temp.rightChild != null){
			nodes.addAll(0, temp.rightChild.getLeftAncestors());
		}
		return temp.element;
	}

	public void remove() {
	}
}

/**
 * 
 * Creates a Boolean Wrapper
 *
 */
private class BooleanWrapper{
	private boolean bool;
	
	public BooleanWrapper(boolean value){
		this.bool = value;
	}
	public void setTrue(){
		this.bool = true;
	}
	public void setFalse(){
		this.bool = false;
	}
	public boolean getBool(){
		return this.bool;
	}
}
}
