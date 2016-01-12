
import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class BinarySearchTree<T extends Comparable<? super T>> implements Iterable<T> {
	public BinaryNode root;
	private int size;
	
	public BinarySearchTree(){
		root = null;
		size = 0;
	}
	
	public int size(){
		return size;
	}
	
	public boolean insert(T element){
		if (element == null) throw new IllegalArgumentException("Attempting to insert null.");
		if (root == null) {
			root = new BinaryNode(element);
			size++;
			return true;
		}
		return root.insert(element);
	}
	
	public boolean remove(T element){
		if (element == null) throw new IllegalArgumentException("Attempting to insert null.");
		if (root == null) {
			return false;
		}
		MyBoolean b = new MyBoolean();
		root = root.remove(element, b);
		return b.value;
	}
	
	/**
	 * Removes from this list all of its elements that are contained in the specified collection.
	 * This method runs in linear time. 
	 * @param c - collection containing elements to be removed from this list
	 * @return true if this list changed as a result of the call
	 */
	public boolean removeAll(Collection<T> c){
		Iterator<T> i = c.iterator();
		MyBoolean b = new MyBoolean();
		while(i.hasNext()){
			root = root.remove(i.next(), b);
		}
		return b.value;
	}
	
	/**
	 * Retains only the elements in this list that are contained in the specified collection. 
	 * In other words, removes from this list all of its elements that are not contained in 
	 * the specified collection. This method has to be efficient. Have a look at the performance
	 * test case. It should run in less than 3 seconds. 
	 * @param c - collection containing elements to be retained in this list
	 * @return true if this list changed as a result of the call
	 */
	public boolean retainAll(Collection<T> c){
		Iterator<T> i = c.iterator();
		MyBoolean b = new MyBoolean();
		int initialsize = this.size;
		boolean check;
		this.root = null;
		this.size = 0;
		while(i.hasNext()){
			check = BinarySearchTree.this.insert(i.next());
			if(!check ){
				b.setTrue();
			}
		}
		if(initialsize != this.size) return false;
		else return true;
	}
	
	/**
	 * Returns true if this BinarySearch contains the specified element. 
	 * This method calls a recursive method in the BinaryNode class. That 
	 * method runs in average case log time. 
	 * @param e - element whose presence in this list is to be tested
	 * @return true if this list contains the specified element
	 */
	public boolean contains(T e){
		if(e == null){
			throw new IllegalArgumentException();
		}
		if(this.root == null) return false;
		MyBoolean b = new MyBoolean();
		this.root.contains(e,b);
		return b.value;
	}
	
	public Iterator<T> iterator(){
		return new InOrderIterator();
	}

	public ArrayList<T> toArrayList(){
		ArrayList<T> a = new ArrayList<T>();
		Iterator<T> i = iterator();
		while (i.hasNext())	a.add(i.next());
		return a;
	}

	

	public class BinaryNode {
		private T element;
		private BinaryNode leftChild;
		private BinaryNode rightChild;
		
		public BinaryNode(T element){
			this.element = element;
			this.leftChild = null;
			this.rightChild = null;		
		}
		
		public void contains(T e, MyBoolean b) {
		if(e.compareTo(this.element) == 0){
			return;
		}
		if(e.compareTo(this.element) == -1){
			if(this.leftChild!=null){
			this.leftChild.contains(e, b);	
			}
			else if(this.leftChild == null){
				b.setFalse();
			}
		}
		if(e.compareTo(this.element) == 1){
				if(this.rightChild!=null){
				this.rightChild.contains(e, b);
				}
				else if(this.rightChild == null){
					b.setFalse();
				}
		}			
		}
		public boolean insert(T element){
			int v = element.compareTo(this.element);
			if (v == -1) {
				if (leftChild != null) return leftChild.insert(element);
				leftChild = new BinaryNode(element);
				size++;
				return true;
			}
			if (v == 1) {
				if (rightChild != null) return rightChild.insert(element);
				rightChild = new BinaryNode(element);
				size++;
				return true;
			}
			return false;
		}
		
		public BinaryNode remove(T element, MyBoolean b){
			int v = element.compareTo(this.element);
			if (v == -1) {
				if (leftChild != null) {
					leftChild = leftChild.remove(element, b);
				} else {
					b.setFalse();
				}
				return this;
			}
			if (v == 1) {
				if (rightChild != null) {
					rightChild = rightChild.remove(element, b);
				} else {
					b.setFalse();
				}
				return this;
			}
			if (leftChild == null && rightChild == null) {
				size--;
				b.setTrue();
				return null;
			}
			if (leftChild == null) {
				size--;
				b.setTrue();
				return rightChild;
			}
			if (rightChild == null) {
				size--;
				b.setTrue();
				return leftChild;
			}
			this.element = leftChild.getLargest();
			leftChild = leftChild.remove(this.element, b);	
			return this;		
		}
		
		private T getLargest(){
			if (rightChild != null) return rightChild.getLargest();
			return element;
		}

	}
	
	private class InOrderIterator implements Iterator<T> {
		private ArrayList<T> a;
		private int position;
		private T lastOneReturned;
		
		public InOrderIterator(){
			a = new ArrayList<T>();
			position = 0;
			lastOneReturned = null;
			if (root != null) fillArrayList(root);
		}
		
		private void fillArrayList(BinaryNode n){
			if (n.leftChild != null) fillArrayList(n.leftChild);
			a.add(n.element);
			if (n.rightChild != null) fillArrayList(n.rightChild);			
		}

		public boolean hasNext() {
			return position < a.size();
		}
			
		public T next() {
			if (!hasNext()) throw new NoSuchElementException("Go to Purdue!");
			lastOneReturned = a.get(position++);
			return lastOneReturned;
		}
		
		public void remove(){
			if (lastOneReturned == null) throw new IllegalStateException();
			BinarySearchTree.this.remove(lastOneReturned);
			lastOneReturned = null;
		}

	}
	
	private class MyBoolean {
		private boolean value = true;
		
		public void setFalse(){
			value = false;
		}
		public void setTrue(){
			value = true;
		}
	}

}
