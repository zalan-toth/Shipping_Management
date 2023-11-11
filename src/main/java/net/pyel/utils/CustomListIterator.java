package net.pyel.utils;

import java.util.ListIterator;
import java.util.NoSuchElementException;

//TODO DEMO CODE
public class CustomListIterator<F> implements ListIterator<F> {
	private CustomNode<F> position;
	private CustomNode<F> lastReturned = null;
	private int index = 0;

	public CustomListIterator(CustomNode<F> node, int startIndex) {
		position = node;
		for (int i = 0; i < startIndex; i++) {
			if (position != null) {
				position = position.next;
				index++;
			}
		}
	}

	@Override
	public boolean hasNext() {
		return position != null;
	}

	@Override
	public F next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		lastReturned = position;
		position = position.next;
		index++;
		return lastReturned.getContents();
	}

	@Override
	public boolean hasPrevious() {
		return position != null && position.previous != null;
	}

	@Override
	public F previous() {
		if (!hasPrevious()) {
			throw new NoSuchElementException();
		}
		position = position.previous;
		index--;
		lastReturned = position;
		return position.getContents();
	}

	@Override
	public int nextIndex() {
		return index;
	}

	@Override
	public int previousIndex() {
		return index - 1;
	}

	@Override
	public void remove() {

	}

	@Override
	public void set(F f) {
		if (lastReturned != null) {
			lastReturned.setContents(f);
		}
	}

	@Override
	public void add(F f) {
	}
}
