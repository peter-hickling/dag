package uk.co.codera.dag;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Node<T> {
	private T data;
	private Collection<Node<T>> children = new HashSet<Node<T>>();

	public Node(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return data.toString();
	}

	public T getData() {
		return data;
	}

	public void addChild(Node<T> node) {
		if (!node.getDescendents().contains(this)) {
			children.add(node);
		};
	}

	public Boolean hasChild(Node<T> node) {
		return children.contains(node);
	}

	public Collection<Node<T>> getChildren() {
		return Collections.unmodifiableCollection(children);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.data).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	public int getDescendentCount() {
		return getDescendents().size();
	}

	public Collection<Node<T>> getDescendents() {
		Collection<Node<T>> descendents = new HashSet<Node<T>>();
		for (Node<T> child : children) {
			descendents.add(child);
			descendents.addAll(child.getDescendents());
		}
		return descendents;
	}
	
}