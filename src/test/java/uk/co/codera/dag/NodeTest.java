package uk.co.codera.dag;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;

import org.junit.Test;

public class NodeTest {

	@Test
	public void testNodeHasName() {
		Node<String> node = new Node<String>("Test");

		assertEquals("Test", node.getData());
	}

	@Test
	public void testToString() {
		Node<String> node = new Node<String>("Test");

		assertEquals("Test", node.toString());
	}

	@Test
	public void testAddChild() {
		Node<String> parent = new Node<String>("Parent");
		Node<String> child = new Node<String>("Child");

		assertFalse(parent.hasChild(child));

		parent.addChild(child);

		assertTrue(parent.hasChild(child));
	}

	@Test
	public void testAddSameChildTwice() {
		Node<String> parent = new Node<String>("Parent");
		Node<String> child = new Node<String>("Child");

		parent.addChild(child);
		parent.addChild(child);

		assertEquals(1, parent.getChildren().size());
	}

	@Test
	public void testAddSameChildNameTwice() {
		Node<String> parent = new Node<String>("Parent");
		Node<String> child1 = new Node<String>("Child1");
		Node<String> child2 = new Node<String>("Child1");

		parent.addChild(child1);
		parent.addChild(child2);

		assertEquals(1, parent.getChildren().size());
	}

	@Test
	public void testReturnChildren() {
		Node<String> parent = new Node<String>("Parent");
		Node<String> child1 = new Node<String>("Child1");
		Node<String> child2 = new Node<String>("Child2");
		Node<String> child3 = new Node<String>("Child3");
		parent.addChild(child2);
		parent.addChild(child1);
		parent.addChild(child3);
		assertEquals(3, parent.getChildren().size());
	}

	@Test
	public void testReturnImmutableCollection() {
		Node<String> drink = new Node<String>("Drink");
		Node<String> alcoholic = new Node<String>("Alcoholic");

		drink.addChild(alcoholic);
		Collection<Node<String>> children = drink.getChildren();

		assertEquals(1, drink.getChildren().size());

		try {

			children.add(new Node<String>("jobber"));
			fail();
		} catch (Exception e) {
		}
	}

	@Test
	public void testCountDescendents() {
		Node<String> parent = new Node<String>("Parent");
		Node<String> child1 = new Node<String>("Child1");
		Node<String> grandchild1 = new Node<String>("Grandchild1");
		Node<String> greatgrandchild1 = new Node<String>("GreatGrandchild1");

		assertEquals(0, parent.getDescendentCount());

		parent.addChild(child1);
		child1.addChild(grandchild1);

		assertEquals(2, parent.getDescendentCount());

		Node<String> child2 = new Node<String>("Child2");
		Node<String> grandchild2 = new Node<String>("Grandchild2");

		parent.addChild(child2);
		child1.addChild(grandchild2);
		grandchild1.addChild(greatgrandchild1);

		assertEquals(5, parent.getDescendentCount());

	}

	@Test
	public void testCountComplexDescendents() {
		Node<String> parent = new Node<String>("Parent");
		Node<String> child1 = new Node<String>("Child1");
		Node<String> child2 = new Node<String>("Child2");
		Node<String> grandchild1 = new Node<String>("Grandchild1");
		Node<String> grandchild2 = new Node<String>("Grandchild2");
		Node<String> grandchild3 = new Node<String>("Grandchild3");
		Node<String> greatgrandchild1 = new Node<String>("GreatGrandchild1");

		parent.addChild(child1);
		parent.addChild(child2);
		child1.addChild(grandchild1);
		child1.addChild(grandchild2);
		child2.addChild(grandchild3);
		grandchild1.addChild(greatgrandchild1);
		grandchild2.addChild(greatgrandchild1);

		assertEquals(6, parent.getDescendentCount());

		assertTrue(parent.getDescendents().contains(greatgrandchild1));
		assertTrue(parent.getDescendents().contains(grandchild1));
		assertTrue(parent.getDescendents().contains(child1));
		System.out.println(parent.getDescendents());
	}

	@Test
	public void testRandomData() {
		Node<Integer> node = new Node<Integer>(7);

		assertEquals(Integer.valueOf(7), node.getData());
	}

	@Test
	public void testAcyclic() {
		Node<String> parent = new Node<String>("Parent");
		Node<String> child = new Node<String>("Child");
		Node<String> grandChild = new Node<String>("Grandchild");

		parent.addChild(child);
		child.addChild(parent);
		child.addChild(grandChild);
		grandChild.addChild(parent);

		assertFalse(child.getDescendents().contains(parent));
		assertFalse(grandChild.getDescendents().contains(parent));

	}

}
