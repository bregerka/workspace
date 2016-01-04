import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * @author bregerka
 *
 * @param <T>
 */
public class AVLTree<T extends Comparable<? super T>>{
	private BinaryNode root;
	private int numberInserted;
	private int numberOfRotations;
	/**
	 * Construct BinarySearchTree with null root
	 */
	public AVLTree(){
		this.root = null;
		this.numberInserted = 0;
		this.numberOfRotations = 0;
	}
	/**
	 * Construct BinarySearchTree with n root
	 * @param n1
	 */
	public AVLTree(T n){
		root = new BinaryNode(n);
		this.numberInserted = 0;
		this.numberOfRotations = 0;
	}

	/**
	 * Caculates Height of tree
	 * @return Height of the tree
	 */
	public Object height() {
		if(root == null) return -1;
		return root.height();
	}
	/**
	 * Calculates the size of the tree
	 * @return the size of the tree
	 */
	public Object size() {
		if(root == null) return 0;
		return root.size();
	}
	/**
	 * Determines if the tree is empty
	 * @return boolean for whether or not tree is empty
	 */
	public boolean isEmpty() {
		return root == null;
	}
	/**
	 * Iterates over the tree using in order traversal
	 * @return in order tree iterator
	 */
	public Iterator<T> iterator() {
		return new PreOrderIterator(this.numberInserted);
	}
	/**
	 * Iterates over the tree using preorder traversal
	 * @return preorder tree iterator
	 */
	public Iterator<T> preOrderIterator() {
		return new PreOrderIterator(this.numberInserted);
	}
	/**
	 * Creates an Arraylist of the tree
	 * @return Arraylist containing the elements of the tree
	 */
	public Object toArrayList() {
		ArrayList<T> list  = new ArrayList<T>();
		if(this.isEmpty()){
			return list;
		}
		return this.root.toArrayList(list);
	}
	/**
	 * Creates an Array of the tree
	 * @return Array containing the elements of the tree
	 */
	public Object[] toArray() {
		if(this.isEmpty()) return new Object[0];
		return this.root.toArray();
	}
	
	@Override
	/**
	 * Creates a string of the tree elements
	 * @return string of tree elements
	 */
	public String toString(){
	Iterator<T> iterator = this.iterator();
	ArrayList<T> list = new ArrayList<T>();
	if(this.root == null) return list.toString();
	while(iterator.hasNext()) list.add(iterator.next());
	return list.toString();
	}
	/**
	 * Inserts a new node into the tree with element i
	 * @param i 
	 * @return whether or not object is inserted
	 */
	public boolean insert(T i) {
		if(i == null){
			throw new IllegalArgumentException();
		}
		if(this.root == null){
			this.root = new BinaryNode();
			this.root.element = i;
			this.numberInserted ++;
			return true;
		}
		BooleanWrapper boolValue = new BooleanWrapper(false);
		this.root = this.root.insert(i,boolValue);
		return boolValue.getBool();	
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
		BooleanWrapper boolValue = new BooleanWrapper(false);
		if(this.root == null){
			boolValue.setFalse();
			return boolValue.getBool();
		}		
		this.root = this.root.remove(i,boolValue);
		return boolValue.getBool();	
	}
	public int getRotationCount() {
		return this.numberOfRotations;
	}
	private class BinaryNode {
		private T element;
		private BinaryNode leftChild;
		private BinaryNode rightChild;
		private int height;
		
		/**
		 * Constructs a BinaryNode with a null element
		 */
		public BinaryNode(){
			this.element = null;
			this.rightChild = null;
			this.leftChild = null;
			this.height = -1;
		}
		/**
		 * Constructs a BinaryNode with an element
		 * @param element
		 */
		public BinaryNode(T element){
			this.element = element;
			this.leftChild = null;
			this.rightChild = null;	
			this.height = 0;
		}
		/**
		 * Changes an ArrayList to an Array
		 * @return an Array
		 */
		public Object[] toArray() {
			ArrayList<T> list = new ArrayList<T>();
			return this.toArrayList(list).toArray();
		}
		/**
		 * Makes an ArrayList of the tree 
		 * @param list - ArrayList to add into
		 * @return ArrayList of the tree
		 */
		public ArrayList<T> toArrayList(ArrayList<T> list) {
			list.add(this.element);
			if(this.leftChild != null){
				list = this.leftChild.toArrayList(list);
			}
			if(this.rightChild != null){
				list = this.rightChild.toArrayList(list);	
			}
			return list;
		}
		
		public int getLeftHeight(){
			if(this.leftChild == null) return -1;
			return this.leftChild.height();
		}
		
		public int getRightHeight(){
			if(this.rightChild == null) return -1;
			return this.rightChild.height();
		}
		
		public BinaryNode balance(){
			if((this.getRightHeight() - this.getLeftHeight()) == 2){
				if(this.rightChild.leftChild != null) return DoubleRotateWithRight();
				else return rotateWithRight();
			}
			if((this.getRightHeight() - this.getLeftHeight()) == -2){
				if(this.leftChild.rightChild != null) return DoubleRotateWithLeft();
				else return rotateWithLeft();
			}
			return this;
		}			
		private BinaryNode DoubleRotateWithRight(){
			if(this.rightChild.rightChild != null){
				BinaryNode originalRL = this.rightChild.leftChild;
				BinaryNode originalR = this.rightChild;
				originalR.leftChild = this;
				this.rightChild = originalRL;
				AVLTree.this.numberOfRotations++;
				return originalR;
			}
			else{
				BinaryNode originalRL = this.rightChild.leftChild;
				BinaryNode originalR = this.rightChild;
				originalRL.leftChild = this;
				originalRL.rightChild = originalR;
				this.rightChild = null;
				originalR.leftChild = null;
				AVLTree.this.numberOfRotations += 2;
				return originalRL;
			}
		}
		private BinaryNode DoubleRotateWithLeft(){
			if(this.leftChild.leftChild != null){
				BinaryNode originalLR = this.leftChild.rightChild;
				BinaryNode originalL = this.leftChild;
				originalL.rightChild = this;
				this.leftChild = originalLR;
				AVLTree.this.numberOfRotations++;
				return originalL;
			}
			else{
				BinaryNode originalLR = this.leftChild.rightChild;
				BinaryNode originalL = this.leftChild;
				originalLR.rightChild = this;
				originalLR.leftChild = originalL;
				this.leftChild = null;
				originalL.rightChild = null;
				AVLTree.this.numberOfRotations += 2;
				return originalLR;
			}		
		}
		public BinaryNode rotateWithRight() {
			BinaryNode orignalRNode = this.rightChild;			
			orignalRNode.leftChild = this;
			this.rightChild = null;
			AVLTree.this.numberOfRotations++;
			return orignalRNode;
		}
		
		public BinaryNode rotateWithLeft() {
			BinaryNode orignalLNode = this.leftChild;			
			orignalLNode.rightChild = this;
			this.leftChild = null;
			AVLTree.this.numberOfRotations++;
			return orignalLNode;
		}
		/**
		 * Recursively calculates the height of the tree
		 * @return the height of the tree
		 */
		public int height(){
			// initialize to -1
			int rHeight = -1;
			int lHeight = -1;
			if(this.leftChild != null) lHeight = this.leftChild.height();
			if(this.rightChild != null) rHeight = this.rightChild.height();
			return ((lHeight>rHeight)?lHeight:rHeight)+1;
		}
		/**
		 * Recursively calculates the size of the tree
		 * @return the size of the tree
		 */
		public int size(){
			int size = 1;
			if(this.leftChild != null) size += this.leftChild.size();
		    if(this.rightChild != null) size += this.rightChild.size();
			return size;
		}
		/**
		 * Recursively inserts the node
		 * @param i
		 * @return A BinaryNode class
		 */
		public BinaryNode insert(T i,BooleanWrapper bool) {
			if(this.element.compareTo(i) == 1){
				if(this.leftChild == null){
					this.leftChild = new BinaryNode(i);
					bool.setTrue();
					return this;
				}
				else this.leftChild = this.leftChild.insert(i,bool);
			}
			if(this.element.compareTo(i) == -1){
				if(this.rightChild == null){
					this.rightChild = new BinaryNode(i);
					bool.setTrue();
					return this;
				}
				else this.rightChild = this.rightChild.insert(i,bool);
			}
			return balance();
			
		}
		/**
		 * Recursive method to remove a node from a tree
		 *
		 * @param i
		 * @param boolValue
		 * @return if the node has been removed
		 */
		public BinaryNode remove(T i, BooleanWrapper boolValue) {
			if(i.compareTo(this.element) == -1){
					if(this.leftChild!=null){
					this.leftChild = this.leftChild.remove(i, boolValue);
					
				}
			}
			if(i.compareTo(this.element) == 1){
					if(this.rightChild!=null){
					this.rightChild = this.rightChild.remove(i, boolValue);
					}
			}
			if(boolValue.bool)
				this.height =Math.max(this.rightChild.height, this.leftChild.height) + 1;
			if(i.compareTo(this.element) == 0){
				if(this.leftChild == null && this.rightChild == null){
					boolValue.setTrue();
					return null;
				}
				if (this.leftChild != null && this.rightChild == null){
					boolValue.setTrue();
					return this.leftChild;
				}
				if (this.leftChild == null && this.rightChild != null){
					boolValue.setTrue();
					return this.rightChild;
				}
				else{
					BinaryNode cycleNode = this.rightChild;
					while(cycleNode.leftChild != null){
						cycleNode = cycleNode.leftChild;
					}
					this.element = cycleNode.element;
					this.rightChild = this.rightChild.remove(cycleNode.element, boolValue);
					return balance();
				}
			}
			
			
			return balance();
		}

		/**
		 * Finds the max value of the left subtree.
		 
		 * @return the max value
		 */
		private T maxValue() {
			if(this.rightChild == null){
				return this.element;
			}
			else{
				return this.rightChild.maxValue();
			}
		}
	}
	private class TreeIterator implements Iterator<T>{
		private Stack<BinaryNode> stack = new Stack<BinaryNode>();
		private int savedCount;
		private boolean nextHasBeenCalled;
		private BinaryNode lastHit;
		/**
		 * Constructs an in order iterator
		 */
		public TreeIterator(int number){
		this.savedCount = number;
		pushLeftChild(AVLTree.this.root);
		this.nextHasBeenCalled = false;
		this.lastHit = new BinaryNode();
		}
		/**
		 * Helper recursive method to push all LeftChildren onto the stack
		 * @param node
		 */
		private void pushLeftChild(BinaryNode node){
			if(node != null){
				this.stack.push(node);
				pushLeftChild(node.leftChild);
			}
		}

		@Override
		public boolean hasNext() {
			return !this.stack.empty();
		}

		@Override
		public T next() {
			this.nextHasBeenCalled = true;
			
			if(!this.hasNext()) throw new NoSuchElementException("No Elements Remaining");
			BinaryNode node = this.stack.pop();
			if(node.rightChild != null){
				BinaryNode right  = node.rightChild;
				pushLeftChild(right);
			}
			this.lastHit = node;
			return node.element;
		}
		public void remove() {
			if(!this.nextHasBeenCalled){
				throw new IllegalStateException();
			}
			else{
				AVLTree.this.remove(this.lastHit.element);
				this.nextHasBeenCalled = false;
			}
	
		}
	}
	private class PreOrderIterator implements Iterator<T>{
		private Stack<BinaryNode> stack = new Stack<BinaryNode>();
		private int savedNumber;
		private boolean nextHasBeenCalled;
		private BinaryNode lastHit;
		/**
		 * Creates a constructor for the preorder iterator
		 */
		public PreOrderIterator(int number){
		if(AVLTree.this.root != null) stack.push(AVLTree.this.root);
		this.savedNumber = number;
		this.nextHasBeenCalled = false;
		this.lastHit = new BinaryNode();
		}

		@Override
		public boolean hasNext() {
			return !this.stack.empty();
		}
		
		@Override
		public T next() {
			this.nextHasBeenCalled = true;
			
			if(!this.hasNext()) throw new NoSuchElementException("No Elements Remaining");
			BinaryNode node = this.stack.pop();			
			if(node.rightChild != null) this.stack.push(node.rightChild);		
			if(node.leftChild != null) this.stack.push(node.leftChild);
			this.lastHit = node;
			return node.element;
		}
		@Override
		public void remove() {
			if(!this.nextHasBeenCalled){
				throw new IllegalStateException();
			}
			else{
				AVLTree.this.remove(this.lastHit.element);
				this.nextHasBeenCalled = false;
			}
		}
	}
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