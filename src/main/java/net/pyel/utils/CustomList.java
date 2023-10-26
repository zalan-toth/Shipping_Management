package net.pyel.utils;

import java.util.Iterator;

public class CustomList<F> implements Iterable {
	public CustomNode<F> first = null;
	public CustomNode<F> last = null;
	public int size = 0;

	public int getSize() {
		return size;
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


	public boolean remove(int position) {
		int index = position; //It is here because previously the list was in reverse order!
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
			if (o.equals(current)) {
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

	public void removeAll() {
		first = null;
	}

	public F getContents() {
		return first.getContents();
	}

	public boolean isValidIndex(int index) {
		return ((index < size) && (index >= 0));
	}

	public F get(int index) {
		if (isValidIndex(index)) {
			int position = 0;
			for (int i = 0; i < size; i++) { //Object o : this
				if (position == index) {
					return (F) this.get(i);
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