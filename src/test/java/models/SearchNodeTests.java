package models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SearchNodeTests {
    @Test
    public void constructor_correctly_sets_values() {
        // Arrange
        final byte x = 14;
        final byte y = 32;
        final int depth = 123;

        // Act
        final SearchNode searchNode = new SearchNode(x, y, depth);

        // Assert
        assertEquals(searchNode.getX(), x);
        assertEquals(searchNode.getY(), y);
        assertEquals(searchNode.getDepth(), depth);
        assertNull(searchNode.getParent());
    }

    @Test
    public void constructor_including_parent_correctly_sets_values() {
        // Arrange
        final byte x = 14;
        final byte y = 32;
        final int depth = 123;
        SearchNode parent = new SearchNode((byte)(x - 1), y, depth - 1);

        // Act
        final SearchNode searchNode = new SearchNode(x, y, depth, parent);

        // Assert
        assertEquals(searchNode.getX(), x);
        assertEquals(searchNode.getY(), y);
        assertEquals(searchNode.getDepth(), depth);
        assertSame(parent, searchNode.getParent());
    }

    @Test
    public void x_must_not_be_negative() {
        // Arrange
        final byte x = -1;
        final byte y = 0;
        final int depth = 0;

        // Act
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new SearchNode(x, y, depth));

        // Assert
        assertEquals("Value supplied as x must not be negative. -1 was supplied.", exception.getMessage());
    }

    @Test
    public void y_must_not_be_negative() {
        // Arrange
        final byte x = 0;
        final byte y = -1;
        final int depth = 0;

        // Act
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new SearchNode(x, y, depth));

        // Assert
        assertEquals("Value supplied as y must not be negative. -1 was supplied.", exception.getMessage());
    }

    @Test
    public void depth_must_not_be_negative() {
        // Arrange
        final byte x = 0;
        final byte y = 0;
        final int depth = -1;

        // Act
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new SearchNode(x, y, depth));

        // Assert
        assertEquals("Value supplied as depth must not be negative. -1 was supplied.", exception.getMessage());
    }

    @Test
    public void expanding_a_non_edge_searchnode_contains_four_neighbours() {
        // Arrange
        final SearchNode searchNode = new SearchNode((byte)1, (byte)1, 0);

        // Act
        ArrayList<SearchNode> neighbours = new ArrayList<SearchNode>();
        searchNode.expand(new int[3][3]).forEach(neighbours::add);

        // Assert
        assertEquals(4, neighbours.size());
        assertEquals(new SearchNode((byte)0,(byte)1,1), neighbours.get(0));
        assertEquals(new SearchNode((byte)1,(byte)0,1), neighbours.get(1));
        assertEquals(new SearchNode((byte)2,(byte)1,1), neighbours.get(2));
        assertEquals(new SearchNode((byte)1,(byte)2,1), neighbours.get(3));
        assertTrue(neighbours.stream().allMatch(n -> n.getDepth() == 1 && n.getParent() == searchNode));
    }

    @Test
    public void expanding_top_left_searchnode_contains_two_neighbours() {
        // Arrange
        final SearchNode searchNode = new SearchNode((byte)0, (byte)0, 0);

        // Act
        ArrayList<SearchNode> neighbours = new ArrayList<SearchNode>();
        searchNode.expand(new int[5][5]).forEach(neighbours::add);

        // Assert
        assertEquals(2, neighbours.size());
        assertEquals(new SearchNode((byte)1,(byte)0, 1), neighbours.get(0));
        assertEquals(new SearchNode((byte)0,(byte)1, 1), neighbours.get(1));
        assertTrue(neighbours.stream().allMatch(n -> n.getDepth() == 1 && n.getParent() == searchNode));
    }

    @Test
    public void expanding_bottom_right_searchnode_contains_two_neighbours() {
        // Arrange
        final SearchNode searchNode = new SearchNode((byte)4, (byte)4, 0);

        // Act
        ArrayList<SearchNode> neighbours = new ArrayList<SearchNode>();
        searchNode.expand(new int[5][5]).forEach(neighbours::add);

        // Assert
        assertEquals(2, neighbours.size());
        assertEquals(new SearchNode((byte)3,(byte)4, 1), neighbours.get(0));
        assertEquals(new SearchNode((byte)4,(byte)3, 1), neighbours.get(1));
        assertTrue(neighbours.stream().allMatch(n -> n.getDepth() == 1 && n.getParent() == searchNode));
    }

    @Test
    public void expanding_searchnode_surrounded_by_roadblocks_contains_zero_neighbours() {
        // Arrange
        final SearchNode searchNode = new SearchNode((byte)1, (byte)1, 0);
        int[][] grid = new int[3][3];
        grid[0][1] = 1;
        grid[1][0] = 1;
        grid[2][1] = 1;
        grid[1][2] = 1;

        // Act
        ArrayList<SearchNode> neighbours = new ArrayList<SearchNode>();
        searchNode.expand(grid).forEach(neighbours::add);

        // Assert
        assertEquals(0, neighbours.size());
    }

    @Test
    public void toString_outputs_expected_representation() {
        // Arrange
        final SearchNode searchNode = new SearchNode((byte)2, (byte)54, 97);

        // Act
        final String representation = searchNode.toString();

        // Assert
        final String expectedRepresentation = "(2,54,97)";
        assertEquals(representation, expectedRepresentation);
    }

    @Test
    public void a_searchnode_is_equal_to_itself() {
        // Arrange
        final SearchNode searchNode = new SearchNode((byte)67, (byte)0, 104);

        // Act
        final Boolean areEqual = searchNode.equals(searchNode);

        // Assert
        assertTrue(areEqual);
    }

    @Test
    public void two_identical_searchnodes_are_equal() {
        // Arrange
        final byte x = 5;
        final byte y = 23;
        final int depth = 100;
        final SearchNode searchNode = new SearchNode(x, y, depth);
        final SearchNode identicalSearchNode = new SearchNode(x, y, depth);

        // Act
        final Boolean areEqual = searchNode.equals(identicalSearchNode);

        // Assert
        assertTrue(areEqual);
        assertEquals(searchNode.hashCode(), identicalSearchNode.hashCode());
    }

    @Test
    public void two_searchnodes_that_differ_by_x_only_are_not_equal() {
        // Arrange
        final byte y = 54;
        final int depth = 127;
        final SearchNode searchNode = new SearchNode((byte)87, y, depth);
        final SearchNode differentXSearchNode = new SearchNode((byte)41, y, depth);

        // Act
        final Boolean areEqual = searchNode.equals(differentXSearchNode);

        // Assert
        assertFalse(areEqual);
        assertNotEquals(searchNode.hashCode(), differentXSearchNode.hashCode());
    }

    @Test
    public void two_searchnodes_that_differ_by_y_only_are_not_equal() {
        // Arrange
        final byte x = 32;
        final int depth = 127;
        final SearchNode searchNode = new SearchNode(x, (byte)12, depth);
        final SearchNode differentYSearchNode = new SearchNode(x, (byte)6, depth);

        // Act
        final Boolean areEqual = searchNode.equals(differentYSearchNode);

        // Assert
        assertFalse(areEqual);
        assertNotEquals(searchNode.hashCode(), differentYSearchNode.hashCode());
    }

    @Test
    public void two_searchnodes_that_differ_by_depth_only_are_equal() {
        // Arrange
        final byte x = 87;
        final byte y = 98;
        final SearchNode searchNode = new SearchNode(x, y, 12);
        final SearchNode differentDepthSearchNode = new SearchNode(x, y, 73);

        // Act
        final Boolean areEqual = searchNode.equals(differentDepthSearchNode);

        // Assert
        assertTrue(areEqual);
        assertEquals(searchNode.hashCode(), differentDepthSearchNode.hashCode());
    }

    @Test
    public void searchnode_and_null_are_not_equal() {
        // Arrange
        final SearchNode searchNode = new SearchNode((byte)12, (byte)0, 2);

        // Act
        final Boolean areEqual = searchNode.equals(null);

        // Assert
        assertFalse(areEqual);
    }

    @Test
    public void searchnode_and_another_type_are_not_equal() {
        // Arrange
        final SearchNode searchNode = new SearchNode((byte)12, (byte)0, 2);

        // Act
        final Boolean areEqual = searchNode.equals(Integer.valueOf(42));

        // Assert
        assertFalse(areEqual);
    }
}
