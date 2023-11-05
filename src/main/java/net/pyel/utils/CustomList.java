package net.pyel.utils;

import java.util.*;
import java.util.function.UnaryOperator;

public class CustomList<F> implements Iterable<F>, List<F> {
	public CustomNode<F> first = null;
	public CustomNode<F> last = null;
	public int size = 0;

	public int getSize() {
		return size;
	}


	@Override
	public boolean add(Object element) {
		CustomNode<F> data = new CustomNode<>();
		data.setContents((F) element);
		//data.next = first;
		//first = data;
		if (first == null)
			first = last = data;
		else {
			last.next = data;
			last = data;
		}
		size++;
		return false;
	}


	@Override
	public F remove(int position) {
		int index = position; //It is here because previously the list was in reverse order!
		if (!isValidIndex(index)) {
			return null;
		}

		if (index == 0) {
			first = first.next;
			size--;
			return null;
		}

		CustomNode<F> current = first;
		CustomNode<F> previous = null;
		int currentIndex = 0;

		while (current != null) {
			if (currentIndex == index) { //found the node to delete
				previous.next = current.next; // unlink the item
				size--;
				return current.getContents();
			}
			previous = current;
			current = current.next;
			currentIndex++;
		}

		return null;
	}

	@Override
	public boolean isEmpty() {
		return (first == null);
	}

	@Override
	public boolean contains(Object o) {
		return false;
	}

	@Override
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

	@Override
	public int lastIndexOf(Object o) {
		return 0;
	}

	@Override
	public ListIterator listIterator() {
		return null;
	}

	@Override
	public ListIterator listIterator(int index) {
		return null;
	}

	@Override
	public List subList(int fromIndex, int toIndex) {
		return null;
	}

	//ModifiableObservableListBase methods begin
	@Override
	public int size() {
		return size;
	}

	protected void doAdd(int index, F element) {
		this.add(index, element);
	}

	protected F doSet(int index, F element) {
		F oldVal = this.get(index);
		// Implement set in CustomList if required.
		// For now, this will just remove and add.
		this.remove(index);
		this.add(index, element);
		return oldVal;
	}

	protected F doRemove(int index) {
		return this.remove(index);
	}

	//ModifiableObservableListBase methods end
	@Override
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

	@Override
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
	public Object set(int index, Object element) {
		return null;
	}

	@Override
	public void add(int index, Object element) {

	}

	@Override
	public Iterator<F> iterator() {
		return new CustomIterator<F>(first);
	}

	@Override
	public Object[] toArray() {
		return new Object[0];
	}

	/*@Override
	public boolean add(Object o) {
		return false;
	}*/

	@Override
	public boolean remove(Object o) {
		return false;
	}

	@Override
	public boolean addAll(Collection c) {
		boolean modified = false;
		for (Object item : c) {
			if (add(item)) { // Using the add method
				modified = true;
			}
		}
		return modified;
	}

	@Override
	public boolean addAll(int index, Collection c) {
		return false;
	}

	@Override
	public void replaceAll(UnaryOperator operator) {

	}

	@Override
	public void sort(Comparator c) {

	}

	@Override
	public boolean retainAll(Collection c) {
		return false;
	}

	@Override
	public boolean removeAll(Collection c) {
		return false;
	}

	@Override
	public boolean containsAll(Collection c) {
		return false;
	}

	@Override
	public Object[] toArray(Object[] a) {
		return new Object[0];
	}
/*
	@Override
	public void addListener(ListChangeListener<? super F> listener) {

	}

	@Override
	public void removeListener(ListChangeListener<? super F> listener) {

	}

	@Override
	public boolean addAll(F... elements) {
		return false;
	}

	@Override
	public boolean setAll(F... elements) {
		return false;
	}

	@Override
	public boolean setAll(Collection<? extends F> col) {
		return false;
	}

	@Override
	public boolean removeAll(F... elements) {
		return false;
	}

	@Override
	public boolean retainAll(F... elements) {
		return false;
	}

	@Override
	public void remove(int from, int to) {

	}

	@Override
	public void addListener(InvalidationListener listener) {

	}

	@Override
	public void removeListener(InvalidationListener listener) {

	}

	/*@Override
	public void forEach(Consumer action) {
		Iterable.super.forEach(action);
	}

	@Override
	public Spliterator spliterator() {
		return Iterable.super.spliterator();
	}

	@Override
	public void addListener(InvalidationListener listener) {

	}

	@Override
	public void removeListener(InvalidationListener listener) {

	}*/


}