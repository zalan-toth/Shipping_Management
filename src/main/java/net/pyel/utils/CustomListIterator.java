package net.pyel.utils;

import java.util.ListIterator;

public class CustomListIterator<F> implements ListIterator<F> {
	private CustomNode<F> position;

	public CustomListIterator(CustomNode<F> node) {
		position = node;
	}

	@Override
	public boolean hasNext() {
		return position != null;
	}

	@Override
	public F next() {
		return position.next.getContents();
	}

	@Override
	public boolean hasPrevious() {
		return false;
	}

	@Override
	public F previous() {
		return null;
	}

	@Override
	public int nextIndex() {
		return 0;
	}

	@Override
	public int previousIndex() {
		return 0;
	}

	@Override
	public void remove() {

	}

	@Override
	public void set(F f) {

	}

	@Override
	public void add(F f) {

	}
}
