package org.example;

import java.util.Iterator;

public class CustomList<F> implements Iterable {
	public CustomNode<F> first = null;
	public int size = 0;

	public void add(F element) {
		CustomNode<F> data = new CustomNode<>();
		data.setContents(element);
		data.next = first;
		first = data;
		size++;
	}

	/*public boolean remove(int index) { //NOT FINISHED
		if (isValidIndex(index)) {
			CustomNode<F> position = first;
			for (int i = 0; i < index; i++) {
				position = position.next; //NOT FINISHED
			}
			CustomNode<F> skipToThisPosition = position.next;
			position.setNext(skipToThisPosition);
		}
		return false;
	}*/
	public boolean remove(int index) { //NOT FINISHED
		if (isValidIndex(index)) {
			CustomNode<F> position = first;
			for (int i = 0; i < index; i++) {
				position = position.next; //NOT FINISHED
			}
			position.setNext(position.next.next);
			

		}
		return false;
	}

	public boolean isEmpty() {
		return (first == null);
	}

	public int indexOf(Object o) {
		return -1;
	}

	public int size() {
		return size;
	}

	public void clear() { //Empty list
		first = null;
	}

	public F getContents() {
		return first.getContents();
	}

	public boolean isValidIndex(int index) {
		return ((index < size) && (index >= 0));
	}

	public Object get(int index) {
		if (isValidIndex(index)) {
			int reverseIndex = size - 1;
			for (Object o : this) {
				if (reverseIndex == index) {
					return o;
				}
				reverseIndex--;
			}

		}
		return null;
	}

	@Override
	public Iterator<F> iterator() {
		return new CustomIterator<F>(first);
	}

}