package org.example;

import java.util.Iterator;

public class CustomIterator<K> implements Iterator {
	private CustomNode<K> position;

	public CustomIterator(CustomNode<K> node) {
		position = node;
	}

	@Override
	public boolean hasNext() {
		return position != null;
	}

	@Override
	public K next() {
		CustomNode<K> temporary = position;
		position = position.next;
		return temporary.getContents();
	}

}
