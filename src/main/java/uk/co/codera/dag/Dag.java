package uk.co.codera.dag;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

public class Dag<Data, Parents extends Collection<Data>> {

	private Map<Data, Parents> nodes = new HashMap<Data, Parents>();
	private String type;
	private Data king;

	public Dag(String type, Data king) {
		this.type = type;
		this.king = king;
		nodes.put(king, null);
	}

	public String getName() {
		return type;
	}

	@Override
	public String toString() {
		return type;
	}

	public void addNodes(Map<Data, Parents> node) {

		for (Data data : node.keySet()) {
			if (this.getDescendentsOf(data).isEmpty()) {
				nodes.putAll(node);
			}
		}
	}

	public Map<Data, Parents> getDag() {
		return nodes;
//		return (new HashMap<Data, Parents>()).putAll(nodes);
	}

	public Data getKing() {
		return king;
	}

	public Parents getParentsOf(Data child) {
		return nodes.get(child);
	}

	public int getNumberOfNodes() {
		return nodes.size();
	}

	public Collection<Data> getChildrenOf(Data data) {

		Collection<Data> children = new HashSet<Data>();

		for (Parents parentsOfNode : nodes.values()) {

			if (parentsOfNode != null) {

				for (Data parent : parentsOfNode) {

					if (parent.equals(data)) {

						for (Entry<Data, Parents> node : nodes.entrySet()) {
							if (node.getValue() == parentsOfNode) {
								children.add(node.getKey());
							}
						}
					}

				}
			}
		}
		return children;
	}

	public Collection<Data> getDescendentsOf(Data parent) {
		Collection<Data> descendents = new HashSet<Data>();
		for (Data child : this.getChildrenOf(parent)) {
			descendents.add(child);
			descendents.addAll(this.getDescendentsOf(child));
		}
		return descendents;
	}

}
