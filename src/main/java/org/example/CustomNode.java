package org.example;

public class CustomNode<N> {
	public CustomNode<N> next = null;
	private N contents;

	public N getContents() {
		return contents;
	}

	public void setContents(N c) {
		contents = c;
	}
}
