package org.example;

import java.util.Iterator;

public class CustomList<F> implements Iterable {
	public CustomNode<F> first = null;
	public int size = 0;

	public void add(F element) { //Add element to head of list
		CustomNode<F> data = new CustomNode<>();
		data.setContents(element);
		data.next = first;
		first = data;
		size++;
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

	public Object get(int index) {
		if ((index < size) && (index >= 0)) {
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