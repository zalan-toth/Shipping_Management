package org.example;

import java.util.Iterator;

public class CustomList<F> implements Iterable {
	public CustomNode<F> first = null, last;
	public int size = 0;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void add(F element) {
		CustomNode<F> data = new CustomNode<>();
		data.setContents(element);
		//data.next = first;
		//first = data;
		if (first == null)
			first = last = data;
		else {
			last.next = data;
			last = data;
		}
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
			while((temporary!=null)&& !temporary.next){
				temporary=temporary.next;
			}
		return false;
	}*/
	/*public boolean remove(int position) {
		CustomNode<F> temporary = first;
		if (isValidIndex(position) && temporary != null && temporary.next != null) {
			for (int i = 0; i < position; i++) {
				temporary.next = temporary.next.next;
			}
			temporary.next = temporary.next.next;


		}
		return false;
	}*/

	public boolean remove(int position) {
		int index = size - 1 - position; //calculating the index because of reverse order
		if (!isValidIndex(index)) {
			return false;
		}

		if (index == 0) {
			first = first.next;
			size--;
			return true;
		}

		CustomNode<F> current = first;
		CustomNode<F> previous = null;
		int currentIndex = 0;

		while (current != null) {
			if (currentIndex == index) { //found the node to delete
				previous.next = current.next; // unlink the item
				size--;
				return true;
			}
			previous = current;
			current = current.next;
			currentIndex++;
		}

		return false;
	}

	public boolean isEmpty() {
		return (first == null);
	}

	public int indexOf(Object o) {
		int index = 0;
		for (Object current : this) {
			if (o == current) {
				return index;
			}
			index++;
		}
		return -1;
	}

	public int size() {
		return size;
	}

	public void clear() {
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
			int position = 0;
			for (Object o : this) {
				if (position == index) {
					return o;
				}
				position++;
			}

		}
		return null;
	}

	@Override
	public Iterator<F> iterator() {
		return new CustomIterator<F>(first);
	}

}