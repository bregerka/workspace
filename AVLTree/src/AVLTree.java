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
		//return root.height();
		return root.height;
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
			this.root.height = 0;
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
	/**
	 * 
	 * @return number of rotations used
	 */
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
		/**
		 * 	Binary Node that has been balanced on both sides using rotations or was already balanced
		 * @return Binary Node 
		 */
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
		/**
		 * Perform a double left rotation
		 * @return Binary Node that was rotated
		 */
		private BinaryNode DoubleRotateWithRight(){
			if(this.rightChild.rightChild != null){
				BinaryNode originalRLNode = this.rightChild.leftChild;
				BinaryNode originalR = this.rightChild;
				originalR.leftChild = this;
				this.rightChild = originalRLNode;
				this.height = Math.max(this.getLeftHeight(), this.getRightHeight()) + 1; //height update
				originalRLNode.height = Math.max(originalRLNode.getLeftHeight(), originalRLNode.getRightHeight()) + 1; //height update
				AVLTree.this.numberOfRotations++;
				return originalR;
			}
			else{
				BinaryNode originalRLNode = this.rightChild.leftChild;
				BinaryNode originalR = this.rightChild;
				originalRLNode.leftChild = this;
				originalRLNode.rightChild = originalR;
				this.rightChild = null;
				originalR.leftChild = null;
				originalR.height = Math.max(originalR.getLeftHeight(), originalR.getRightHeight()) + 1;
				this.height = Math.max(this.getLeftHeight(), this.getRightHeight()) + 1; //height update
				originalRLNode.height = Math.max(originalRLNode.getLeftHeight(), originalRLNode.getRightHeight()) + 1; //height update
				AVLTree.this.numberOfRotations += 2;
				return originalRLNode;
			}
		}
		/**
		 *  Perform a double right rotation
		 * @return Binary node that was rotated
		 */
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
				originalL.height = Math.max(originalL.getLeftHeight(), originalL.getRightHeight()) + 1;
				this.height = Math.max(this.getLeftHeight(), this.getRightHeight()) + 1; //height update
				originalLR.height = Math.max(originalLR.getLeftHeight(), originalLR.getRightHeight()) + 1; //height update
				AVLTree.this.numberOfRotations += 2;
				return originalLR;
			}		
		}
		/**
		 * 
		 * @return the height of the current nodes left child (or -1 if its left child is null)
		 */
		private int getLeftHeight() {
			if(this.leftChild == null) return -1;
			return leftChild.height;
		}
		/**
		 * 
		 * @return the height of the current nodes right child (or -1 if its right child is null)
		 */
		private int getRightHeight() {
			if(this.rightChild == null) return -1;
			return rightChild.height;
		}
		/**
		 * 
		 * @return Left rotated BinaryNode
		 */
		public BinaryNode rotateWithRight() {
			BinaryNode orignalRNode = this.rightChild;
			BinaryNode temp = orignalRNode.leftChild;
			orignalRNode.leftChild = this;
			this.rightChild = temp;
			this.height = Math.max(this.getLeftHeight(), this.getRightHeight()) + 1; //height update
			orignalRNode.height = Math.max(orignalRNode.getLeftHeight(), orignalRNode.getRightHeight()) + 1; //height update		
			AVLTree.this.numberOfRotations++;
			return orignalRNode;
		}
		/**
		 * 
		 * @return Right rotated BinaryNode
		 */
		public BinaryNode rotateWithLeft() {
			BinaryNode orignalLNode = this.leftChild;
			BinaryNode temp = orignalLNode.rightChild;
			orignalLNode.rightChild = this;
			this.leftChild = temp;
			this.height = Math.max(this.getLeftHeight(), this.getRightHeight()) + 1; //height update
			orignalLNode.height = Math.max(orignalLNode.getLeftHeight(), orignalLNode.getRightHeight()) + 1; //height update
			AVLTree.this.numberOfRotations++;
			return orignalLNode;
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
		public BinaryNode insert(T i,BooleanWrapper boolValue) {
			if(this.element.compareTo(i) == 1){
				if(this.leftChild == null){
					this.leftChild = new BinaryNode(i);
					boolValue.setTrue();
				}
				else this.leftChild = this.leftChild.insert(i,boolValue);
			}
			else if(this.element.compareTo(i) == -1){
				if(this.rightChild == null){
					this.rightChild = new BinaryNode(i);
					boolValue.setTrue();
				}
				else this.rightChild = this.rightChild.insert(i,boolValue);
			}
			if(boolValue.bool){
				this.height = Math.max(this.getLeftHeight(), this.getRightHeight()) + 1;
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
			if(boolValue.bool){
				if((this.rightChild != null) && (this.leftChild != null))
				this.height = Math.max(this.rightChild.height, this.leftChild.height) + 1;
				if((this.rightChild != null) && (this.leftChild == null))
					this.height = this.rightChild.height + 1;
				if((this.rightChild == null) && (this.leftChild != null))
					this.height = this.leftChild.height + 1;
				if((this.rightChild == null) && (this.leftChild == null)) this.height = 0;
			}	
			return balance();
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