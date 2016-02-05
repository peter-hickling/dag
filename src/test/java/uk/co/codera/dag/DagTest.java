package uk.co.codera.dag;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

public class DagTest {

	@Test
	public void testNodeHasName() {
		Dag<String, List<String>> dag = new Dag<String, List<String>>("name", "parent");

		assertEquals("name", dag.getName());
	}

	@Test
	public void testToString() {
		Dag<String, List<String>> dag = new Dag<String, List<String>>("name", "parent");

		assertEquals("name", dag.toString());
	}

	@Test
	public void testAddOneParentToRuleThemAll() {
		Dag<String, List<String>> dag = new Dag<String, List<String>>("dag", "king");
		assertEquals("king", dag.getKing());
	}

	@Test
	public void testAddSingleChildOfKing() {

		Dag<String, List<String>> dag = new Dag<String, List<String>>("dag", "parent");
		HashMap<String, List<String>> child = new HashMap<String, List<String>>();
		
		ArrayList<String> parents = new ArrayList<String>();
		parents.add("parent");
		child.put("child", parents);

		dag.addNodes(child);

		assertEquals(Arrays.asList(dag.getKing()), dag.getParentsOf("child"));
	}

	@Test
	public void testAddChildrenOfKing() {

		Dag<String, List<String>> dag = new Dag<String, List<String>>("dag", "parent");
		HashMap<String, List<String>> child1 = new HashMap<String, List<String>>();
		HashMap<String, List<String>> child2 = new HashMap<String, List<String>>();
		HashMap<String, List<String>> child3 = new HashMap<String, List<String>>();

		child1.put("child1", Arrays.asList("parent"));
		child2.put("child2", Arrays.asList("parent"));
		child3.put("child3", Arrays.asList("parent"));

		dag.addNodes(child1);
		dag.addNodes(child2);
		dag.addNodes(child3);

		assertEquals(Arrays.asList(dag.getKing()), dag.getParentsOf("child2"));
		assertEquals(Arrays.asList(dag.getKing()), dag.getParentsOf("child1"));
		assertEquals(Arrays.asList(dag.getKing()), dag.getParentsOf("child3"));
	}

	@Test
	public void testAddGrandchildren() {

		Dag<String, List<String>> dag = new Dag<String, List<String>>("dag", "parent");
		HashMap<String, List<String>> child1 = new HashMap<String, List<String>>();
		HashMap<String, List<String>> child2 = new HashMap<String, List<String>>();
		HashMap<String, List<String>> grandchild1 = new HashMap<String, List<String>>();
		HashMap<String, List<String>> grandchild2 = new HashMap<String, List<String>>();

		child1.put("child1", Arrays.asList("parent"));
		child2.put("child2", Arrays.asList("parent"));
		grandchild1.put("grandchild1", Arrays.asList("child1"));
		grandchild2.put("grandchild2", Arrays.asList("child1"));

		dag.addNodes(child1);
		dag.addNodes(child2);
		dag.addNodes(grandchild1);
		dag.addNodes(grandchild2);

		assertEquals(Arrays.asList(dag.getKing()), dag.getParentsOf("child2"));
		assertEquals(Arrays.asList("child1"), dag.getParentsOf("grandchild1"));
	}

	@Test
	public void testAddGreatGreatGrandchildren() {

		Dag<String, List<String>> dag = new Dag<String, List<String>>("dag", "parent");
		HashMap<String, List<String>> child1 = new HashMap<String, List<String>>();
		HashMap<String, List<String>> child2 = new HashMap<String, List<String>>();
		HashMap<String, List<String>> grandchild1 = new HashMap<String, List<String>>();
		HashMap<String, List<String>> grandchild2 = new HashMap<String, List<String>>();
		HashMap<String, List<String>> greatGrandchild1 = new HashMap<String, List<String>>();
		HashMap<String, List<String>> greatGreatGrandchild1 = new HashMap<String, List<String>>();

		child1.put("child1", Arrays.asList("parent"));
		child2.put("child2", Arrays.asList("parent"));
		grandchild1.put("grandchild1", Arrays.asList("child1"));
		grandchild2.put("grandchild2", Arrays.asList("child1"));
		greatGrandchild1.put("greatGrandchild1", Arrays.asList("grandchild1"));
		greatGreatGrandchild1.put("greatGreatGrandchild1", Arrays.asList("greatGrandchild1"));

		dag.addNodes(child1);
		dag.addNodes(child2);
		dag.addNodes(grandchild1);
		dag.addNodes(grandchild2);
		dag.addNodes(greatGrandchild1);
		dag.addNodes(greatGreatGrandchild1);

		assertEquals(Arrays.asList(dag.getKing()), dag.getParentsOf("child2"));
		assertEquals(Arrays.asList("greatGrandchild1"), dag.getParentsOf("greatGreatGrandchild1"));
		assertEquals(Arrays.asList("greatGrandchild1"), dag.getParentsOf("greatGreatGrandchild1"));
	}

	@Test
	public void testAddSameActualNodeTwice() {
		Dag<String, List<String>> dag = new Dag<String, List<String>>("dag", "parent");
		HashMap<String, List<String>> child1 = new HashMap<String, List<String>>();

		child1.put("child1", Arrays.asList("parent"));

		dag.addNodes(child1);
		dag.addNodes(child1);

		assertEquals(2, dag.getNumberOfNodes());
	}

	@Test
	public void testAddDifferentNodeWithSameDataTwice() {
		Dag<String, List<String>> dag = new Dag<String, List<String>>("dag", "parent");
		HashMap<String, List<String>> child1 = new HashMap<String, List<String>>();
		HashMap<String, List<String>> child2 = new HashMap<String, List<String>>();

		child1.put("child1", Arrays.asList("parent"));
		child2.put("child1", Arrays.asList("parent"));

		dag.addNodes(child1);
		dag.addNodes(child2);

		assertEquals(2, dag.getNumberOfNodes());
	}

	@Test
	public void getChildrenOf() {
		Dag<String, List<String>> dag = new Dag<String, List<String>>("dag", "parent");
		HashMap<String, List<String>> child1 = new HashMap<String, List<String>>();
		HashMap<String, List<String>> child2 = new HashMap<String, List<String>>();

		child1.put("child1", Arrays.asList("parent"));
		child2.put("child2", Arrays.asList("parent"));

		dag.addNodes(child1);
		dag.addNodes(child2);

		assertEquals(2, dag.getChildrenOf("parent").size());
	}

	@Test
	public void testGetDescendents() {

		Dag<String, List<String>> dag = new Dag<String, List<String>>("dag", "parent");
		HashMap<String, List<String>> child1 = new HashMap<String, List<String>>();
		HashMap<String, List<String>> child2 = new HashMap<String, List<String>>();
		HashMap<String, List<String>> grandchild1 = new HashMap<String, List<String>>();
		HashMap<String, List<String>> grandchild2 = new HashMap<String, List<String>>();
		HashMap<String, List<String>> greatGrandchild1 = new HashMap<String, List<String>>();
		HashMap<String, List<String>> greatGreatGrandchild1 = new HashMap<String, List<String>>();

		child1.put("child1", Arrays.asList("parent"));
		child2.put("child2", Arrays.asList("parent"));
		grandchild1.put("grandchild1", Arrays.asList("child1"));
		grandchild2.put("grandchild2", Arrays.asList("child1"));
		greatGrandchild1.put("greatGrandchild1", Arrays.asList("grandchild1"));
		greatGreatGrandchild1.put("greatGreatGrandchild1", Arrays.asList("greatGrandchild1"));

		dag.addNodes(child1);
		dag.addNodes(child2);
		dag.addNodes(grandchild1);
		dag.addNodes(grandchild2);
		dag.addNodes(greatGrandchild1);
		dag.addNodes(greatGreatGrandchild1);
		
		
		assertEquals(Arrays.asList(dag.getKing()), dag.getParentsOf("child2"));
		assertEquals(4, dag.getDescendentsOf("child1").size());
		assertTrue(dag.getDescendentsOf("child1").contains("greatGreatGrandchild1"));
	}
	
	@Test 
	public void testMultipleParents() {
		Dag<String, List<String>> dag = new Dag<String, List<String>>("dag", "parent");
		HashMap<String, List<String>> child1 = new HashMap<String, List<String>>();
		HashMap<String, List<String>> child2 = new HashMap<String, List<String>>();
		HashMap<String, List<String>> grandchild1 = new HashMap<String, List<String>>();
		
		child1.put("child1", Arrays.asList("parent"));
		child2.put("child2", Arrays.asList("parent"));
		grandchild1.put("grandchild1", Arrays.asList("child2", "child1"));

		dag.addNodes(child1);
		dag.addNodes(child2);
		dag.addNodes(grandchild1);
		
		assertTrue(dag.getDescendentsOf("child1").contains("grandchild1"));
		assertTrue(dag.getDescendentsOf("child2").contains("grandchild1"));
	}

	@Test
	public void testCyclicBehaviourNotAllowed() {
		
		Dag<String, List<String>> dag = new Dag<String, List<String>>("dag", "parent");
		HashMap<String, List<String>> child1 = new HashMap<String, List<String>>();
		HashMap<String, List<String>> child2 = new HashMap<String, List<String>>();
		HashMap<String, List<String>> grandchild1 = new HashMap<String, List<String>>();
		HashMap<String, List<String>> greatGrandchild1 = new HashMap<String, List<String>>();

		child1.put("child1", Arrays.asList("parent", "greatGrandchild1"));
		child2.put("child2", Arrays.asList("parent"));
		grandchild1.put("grandchild1", Arrays.asList("child1"));
		greatGrandchild1.put("greatGrandchild1", Arrays.asList("grandchild1"));

		dag.addNodes(child1);
		dag.addNodes(child2);
		dag.addNodes(grandchild1);
		dag.addNodes(greatGrandchild1);

		assertEquals(3, dag.getDescendentsOf("parent").size());
		assertFalse(dag.getDescendentsOf("grandchild1").contains("child1"));
	}
	
}
