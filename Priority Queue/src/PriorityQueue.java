
import java.util.ArrayList;

public class PriorityQueue <T extends Comparable<? super T>> extends ArrayList<T>{
	int size;
	
	public PriorityQueue(){
		this.size = 0 ;
	}
	
	public boolean add(T value) {
		if(value == null) throw new NullPointerException();
		boolean b;
		b = super.add(value);
		if(super.size()>1){
			percolateUp(super.size()-1);
		}
		return b;	
	}
	
	/**
	 * after removing elements this resorts the queue
	 * @param spot
	 */
	public void percolateDown(int spot) {
		if (spot != super.size() - 1) {
			
				int lspot = ((spot + 1) * 2) - 1;
				int rspot = (spot + 1) * 2;
				if (rspot <= super.size() - 1) {
				T lelement = super.get(lspot);
				T relement = super.get(rspot);
				T element = super.get(spot);

				if (element.compareTo(lelement) == 1
						|| element.compareTo(relement) == 1) {
					int check = relement.compareTo(lelement);
					if (check == -1) {
						super.set(spot, relement);
						super.set(rspot, element);
						percolateDown(rspot);
					} else if (check == 1) {
						super.set(spot, lelement);
						super.set(lspot, element);
						percolateDown(lspot);
					} else {
						return;
					}

				}
			}
		}

	}

	/**
	 * after adding an element to the end of the queue this resorts its all
	 * @param index
	 */
	public void percolateUp(int index) {

		int parentIndex = ((index / 2) + (index % 2) - 1);
		T parent = super.get(parentIndex);
		if (parent.compareTo(super.get(index)) == 1) {
			super.set(parentIndex, super.get(index));
			super.set(index, parent);
			if (parentIndex > 0)
				percolateUp(parentIndex);
		}
	}
	
	public Object[] toArray() {
		return super.toArray();
	}

	public boolean offer(T value) {
		return this.add(value);
	}

	public T peek() {
		if(super.size() == 0) return null;
		return super.get(0);
	}

	public void clear() {
		super.clear();
	}

	public boolean contains(T value) {
		int contains = super.indexOf(value);
		if(contains == -1) return false;
		return true;
	}

	public int size() {
		return super.size();
	}

	public Integer[] toArray(T[] numbers) {
		return super.toArray();
	}

	public T poll() {
		if(super.size() == 0) return null;
		if(super.size()>2)
		{
			T temp1= super.get(1);
			super.set(1,super.get(2));
			super.set(2,temp1);
		}
		T temp2 = super.get(0);
		this.remove(temp2);
		return temp2;
	}

	public boolean remove(T value) {
		if(value == null) throw new NullPointerException();
		int savedSpot = super.indexOf(value);
		if(savedSpot == -1) return false;
		super.set(savedSpot, super.get(super.size() - 1));
		super.remove(super.size() - 1);
		//super.remove(savedSpot);
		if(super.size()>1){
			percolateDown(savedSpot);
		}
		return true;	
	}
	
}
