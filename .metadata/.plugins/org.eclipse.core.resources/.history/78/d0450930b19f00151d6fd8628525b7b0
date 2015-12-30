import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * This is a Binary Search Tree class.
 *
 * @author spurrme.
 *         Created Nov 28, 2011.
 * @param <T> 
 */
public class BinarySearchTree<T extends Comparable<? super T>>{
	/**
	 * The root of the tree needed for the iterators.
	 */
	private BinaryNode root;
	private boolean empty;
	private int height;
	private int size;
	private int numberInserted;
	/**
	 * Constructs a new Binary Search Tree with the root equal to n1.
	 *
	 * @param n1
	 */
	public   BinarySearchTree(T n1) {
		this.root = new BinaryNode(n1);
		this.empty = false;
		this.height = 0;
		this.size = 1;
		this.numberInserted = 0;
	}

	/**
	 * Constructs a new Binary Search Tree.
	 *
	 */
	public BinarySearchTree() {
		this.root = null;
		this.empty = true;
		this.height = -1;
		this.size = 0;
		this.numberInserted = 0;
	}

	/**
	 * Calculates the size of the tree.
	 *
	 * @return the size of the tree
	 */
	public int size() {
		if(this.isEmpty()){
			return this.size;
		}
		return this.root.size();
	}

	/**
	 * Figures out if the tree is empty or not.
	 *
	 * @return if the tree is empty or not
	 */
	public boolean isEmpty() {
		return this.empty;
	}

	/**
	 * Calculates the height of the tree.
	 *
	 * @return the height of the tree
	 */
	public int height() {
		if(this.isEmpty()){
			return this.height;
		}
		return this.root.height();
	}
	/**
	 * Constructs a tree iterator that iterates over the tree in an in order
	 * fashion.
	 *
	 * @return a tree iterator
	 */
	public Iterator<T> iterator(){
		return new TreeIterator(this.numberInserted);

	}

	/**
	 * Constructs an array with the given tree .
	 *
	 * @return an array with the data of the tree
	 */
	public Object[] toArray() {
		if(this.isEmpty()){
			return new Object[0];
		}
		return this.root.toArray();
	}

	/**
	 * Makes a pre order iterator that will iterate over a tree.
	 *
	 * @return a tree iterator that iterates over the tree in the pre-order
	 * fashion
	 */
	public Iterator<T> preOrderIterator() {
		return new PreOrderIterator(this.numberInserted);
	}

	/**
	 * Makes an arraryList with the given tree.
	 *
	 * @return an arraryList with the values of the tree
	 */
	public Object toArrayList() {
		ArrayList<T> list  = new ArrayList<T>();
		if(this.isEmpty()){
			return list;
		}
		return this.root.toArrayList(list);
	}
	@Override
	public String toString(){
	Iterator<T> iterator = this.iterator();
	ArrayList<T> list = new ArrayList<T>();
	if(this.root == null){
		return list.toString();
	}
	while(iterator.hasNext()){
		list.add(iterator.next());
	}
	return list.toString();
	}

	/**
	 * Insert a new node into the tree with the given value i.
	 *
	 * @param i
	 * @return if the object was inserted in the tree
	 */
	public boolean insert(T i) {
		if(i == null){
			throw new IllegalArgumentException();
		}
		if(this.root == null){
			this.root = new BinaryNode();
			this.root.data = i;
			this.empty = false;
			this.numberInserted ++;
			return true;
		}
		if(this.root.data.compareTo(i) == 1){
			if(this.root.getLeftChild() == null){
				this.root.setLeftChild(new BinaryNode(i));
			}
			else{
				if(!this.root.getLeftChild().insert(i)){
					return false;
				}
			}
			this.empty = false;
			this.numberInserted ++;
			return true;
		}
		if(this.root.data.compareTo(i) == -1){
			if(this.root.getRightChild() == null){
				this.root.setRightChild(new BinaryNode(i));
			}
			else{
				if(!this.root.getRightChild().insert(i)){
					return false;
				}
			}
			this.empty = false;
			this.numberInserted ++;
			return true;
		}
		return false;
	}
	private class BinaryNode {
		private BinaryNode rightChild;
		private BinaryNode leftChild;
		private T data;

		public BinaryNode(){
			this.data = null;
			this.rightChild = null;
			this.leftChild = null;
		}
		/**
		 * Constructor for a Binary Node.
		 *
		 * @param i
		 */
		public BinaryNode(T i) {
			this.data = i;
		}
		/**
		 * Recursive call to the insert method
		 *
		 * @param i
		 * @return true if the node inserted
		 */
		public boolean insert(T i) {
			if(this.data.compareTo(i) == 1){
				if(this.getLeftChild() == null){
					this.setLeftChild(new BinaryNode(i));
				}
				else{
					if(!this.getLeftChild().insert(i)){
						return false;
					}
				}
				return true;
			}
			if(this.data.compareTo(i) == -1){
				if(this.getRightChild() == null){
					this.setRightChild(new BinaryNode(i));
				}
				else{
					if(!this.getRightChild().insert(i)){
						return false;
					}
				}
				return true;
			}
			return false;
			
		}
		/**
		 * Set the right child.
		 *
		 * @param n2
		 */
		public void setRightChild(BinaryNode n2) {
			this.rightChild = n2;
		}
		/**
		 * Set the left child.
		 *
		 * @param n1
		 */
		public void setLeftChild(BinaryNode n1){
			this.leftChild = n1;
		}
		/**
		 * Get the right child.
		 *
		 * @return the right Child
		 */
		public BinaryNode getRightChild(){
			return this.rightChild;
		}
		/**
		 * Get the left child.
		 *
		 * @return the left child
		 */
		public BinaryNode getLeftChild(){
			return this.leftChild;
		}
		/**
		 * Get the data from the node.
		 *
		 * @return data
		 */
		public T getData(){
			return this.data;
		}
		/**
		 * Recursively calculates the height of the tree.
		 *
		 * @return recursive call of the height of a tree
		 */
		public int height(){
			int rightHeight = -1;
			int leftHeight = -1;
			if(this.leftChild != null){
				 leftHeight = this.leftChild.height();
			}
			if(this.rightChild != null){
				rightHeight = this.rightChild.height();
			}
			if(rightHeight > leftHeight){
				return rightHeight + 1;
			}
			return leftHeight + 1;
		}
		/**
		 * Recursively calculates the size of the tree.
		 *
		 * @return the size of the tree
		 */
		public int size(){
			int size = 1;
			if(this.leftChild != null){
				size = this.leftChild.size();
				size +=1;
			}
			if(this.rightChild != null){
				size = this.rightChild.size();
				size +=1;
			}
			return size;
		}

		/**
		 * Recursively makes an Array List out of the given tree.
		 * @param list 
		 *
		 * @return an arrayList of the tree
		 */
		public ArrayList<T> toArrayList(ArrayList<T> list) {
			list.add(this.data);
			if(this.leftChild != null){
				list = this.leftChild.toArrayList(list);
			}
			if(this.rightChild != null){
				list = this.rightChild.toArrayList(list);
				
			}
			return list;
		}

		/**
		 * Turns the arrayList into an array.
		 *
		 * @return an array of the tree
		 */
		public Object[] toArray() {
			ArrayList<T> list = new ArrayList<T>();
			return this.toArrayList(list).toArray();
		}
		@Override
		public String toString(){
			return this.data.toString();
		}
		/**
		 * Recursive method to remove a node from a tree
		 *
		 * @param i
		 * @param parent
		 * @return if the node has been removed
		 */
		public boolean remove(T i, BinaryNode parent) {
			if(i.compareTo(this.data) == -1){
				if(this.leftChild != null){
					return this.leftChild.remove(i, this);
				}
				else{
					return false;
				}
			}
			else if (i.compareTo(this.data) == 1){
				if(this.rightChild != null){
					return this.rightChild.remove(i, this);
				}
				else{
					return false;
				}
			}
			else{
				if(this.leftChild != null && this.rightChild != null){
					this.data = this.leftChild.maxValue();
					this.leftChild.remove(this.data, this);
				}
				else if (parent.leftChild == this){
					parent.leftChild = (this.leftChild != null) ? this.leftChild : this.rightChild;
				}
				else if (parent.rightChild == this){
					parent.rightChild = (this.leftChild != null) ? this.leftChild : this.rightChild;

				}
				return true;
			}
		}

		/**
		 * Finds the max value of the left subtree.
		 
		 * @return the max value
		 */
		private T maxValue() {
			if(this.rightChild == null){
				return this.data;
			}
			else{
				return this.rightChild.maxValue();
			}
		}
	}
	private class TreeIterator implements Iterator<T> {
		private Stack<BinaryNode> stack = new Stack<BinaryNode>();
		private int savedCount;
		private boolean nextHasBeenCalled;
		private BinaryNode lastHit;
		
		public TreeIterator(int number) {
			this.savedCount = number;
			pushLeftChild(BinarySearchTree.this.root);
			this.nextHasBeenCalled = false;
			this.lastHit = new BinaryNode();
		}

		public boolean hasNext() {
			return !(this.stack.isEmpty());
		}
	
		public T next() {
			this.nextHasBeenCalled = true;
			if(this.savedCount != BinarySearchTree.this.numberInserted){
				throw new ConcurrentModificationException();
			}
			if(! this.hasNext()){
				throw new NoSuchElementException("no more elemetns");
			}
			BinaryNode node = this.stack.pop();
			T result  = node.getData();
			if(node.getRightChild() != null){
				BinaryNode right  = node.getRightChild();
				pushLeftChild(right);
			}
			this.lastHit = node;
			return result;
			
		}
		/**
		 * Recursive call to put all the left children on the stack. This is a
		 * helper method.
		 *
		 * @param node
		 */
		private void pushLeftChild(BinaryNode node) {
			if(node != null){
				this.stack.push(node);
				pushLeftChild(node.getLeftChild());
			}
		}
	
		public void remove() {
			if(!this.nextHasBeenCalled){
				throw new IllegalStateException();
			}
			else{
				BinarySearchTree.this.remove(this.lastHit.data);
				this.nextHasBeenCalled = false;
			}
	
		}

}
	private class PreOrderIterator implements Iterator<T> {
		private Stack<BinaryNode> stack = new Stack<BinaryNode>();
		private int savedNumber;
		private boolean nextHasBeenCalled;
		private BinaryNode lastHit;

		/**
		 * Constructs a new Pre-order Iterator.
		 * @param number 
		 *
		 */
		public PreOrderIterator(int number) {
			if(BinarySearchTree.this.root != null){
				this.stack.push(BinarySearchTree.this.root);
			}
			this.savedNumber = number;
			this.nextHasBeenCalled = false;
			this.lastHit = new BinaryNode();
		}

		/**
		 * Constructs the iterator to iterate over the given tree.
		 *
		 * @param binarySearchTree
		 */

		@Override
		public boolean hasNext() {
			return !(this.stack.isEmpty());
		}

		@Override
		public T next() {
			this.nextHasBeenCalled = true;
			if(this.savedNumber != BinarySearchTree.this.numberInserted){
				throw new ConcurrentModificationException();
			}
			if(! this.hasNext()){
				throw new NoSuchElementException("no more elements");
			}
			BinaryNode node = this.stack.pop();
			if(node.getRightChild() != null){
				this.stack.push(node.getRightChild());
			}
			if(node.getLeftChild() != null){
				this.stack.push(node.getLeftChild());
			}
			this.lastHit = node;
			return node.getData();
		}

		@Override
		public void remove() {
			if(!this.nextHasBeenCalled){
				throw new IllegalStateException();
			}
			else{
				BinarySearchTree.this.remove(this.lastHit.data);
				this.nextHasBeenCalled = false;
			}
		}
	}
	/**
	 *Remove the data i from the binary search tree.
	 *
	 * @param i
	 * @return if the node was removed or not
	 */
	public boolean remove(T i) {
		if(i == null){
			throw new IllegalArgumentException();
		}
		if(this.root == null){
			return false;
		}
		else{
			if(this.root.getData().compareTo(i) == 0){
				BinaryNode dummyRoot = new BinaryNode();
				dummyRoot.setLeftChild(this.root);
				boolean result  = this.root.remove(i, dummyRoot);
				this.root = dummyRoot.getLeftChild();
				if(result){
					this.numberInserted ++;
				}
				return result;
			}
			else{
				boolean result = this.root.remove(i, null);
				if(result){
					this.numberInserted++;
				}
				return result;
			}
		}
	}

	/**
	 * Gets the desired object from the tree.
	 *
	 * @param e
	 * @return the data needed from the tree
	 */
	public T get(T e) {
		Iterator<T> i = this.preOrderIterator();
		T node;
		while (i.hasNext()){
			node = i.next();
			if(e.compareTo(node)==0){
				return node;
			}
		}
		return null;
	}
}
