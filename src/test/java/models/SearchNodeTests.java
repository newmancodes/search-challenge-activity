package models;

import org.junit.Assert;
import org.junit.Test;

public class SearchNodeTests {
    @Test
    public void constructor_correctly_sets_values() {
        // Arrange
        final byte x = 14;
        final byte y = 32;
        final byte depth = 123;

        // Act
        final SearchNode searchNode = new SearchNode(x, y, depth);

        // Assert
        Assert.assertEquals(searchNode.getX(), x);
        Assert.assertEquals(searchNode.getY(), y);
        Assert.assertEquals(searchNode.getDepth(), depth);
    }

    @Test(expected = IllegalArgumentException.class)
    public void x_must_not_be_negative() {
        // Arrange
        final byte x = -1;
        final byte y = 0;
        final byte depth = 0;

        // Act
        final SearchNode searchNode = new SearchNode(x, y, depth);
    }

    @Test(expected = IllegalArgumentException.class)
    public void y_must_not_be_negative() {
        // Arrange
        final byte x = 0;
        final byte y = -1;
        final byte depth = 0;

        // Act
        final SearchNode searchNode = new SearchNode(x, y, depth);
    }

    @Test(expected = IllegalArgumentException.class)
    public void depth_must_not_be_negative() {
        // Arrange
        final byte x = 0;
        final byte y = 0;
        final byte depth = -1;

        // Act
        final SearchNode searchNode = new SearchNode(x, y, depth);
    }

    @Test
    public void toString_outputs_expected_representation() {
        // Arrange
        final SearchNode searchNode = new SearchNode((byte)2, (byte)54, (byte)97);

        // Act
        final String representation = searchNode.toString();

        // Assert
        final String expectedRepresentation = "SearchNode{x=2, y=54, depth=97}";
        Assert.assertEquals(representation, expectedRepresentation);
    }

    @Test
    public void a_searchnode_is_equal_to_itself() {
        // Arrange
        final SearchNode searchNode = new SearchNode((byte)67, (byte)0, (byte)104);

        // Act
        final Boolean areEqual = searchNode.equals(searchNode);

        // Assert
        Assert.assertTrue(areEqual);
    }

    @Test
    public void two_identical_searchnodes_are_equal() {
        // Arrange
        final byte x = 5;
        final byte y = 23;
        final byte depth = 100;
        final SearchNode searchNode = new SearchNode(x, y, depth);
        final SearchNode identicalSearchNode = new SearchNode(x, y, depth);

        // Act
        final Boolean areEqual = searchNode.equals(identicalSearchNode);

        // Assert
        Assert.assertTrue(areEqual);
        Assert.assertEquals(searchNode.hashCode(), identicalSearchNode.hashCode());
    }

    @Test
    public void two_searchnodes_that_differ_by_x_only_are_not_equal() {
        // Arrange
        final byte y = 54;
        final byte depth = 127;
        final SearchNode searchNode = new SearchNode((byte)87, y, depth);
        final SearchNode differentXSearchNode = new SearchNode((byte)41, y, depth);

        // Act
        final Boolean areEqual = searchNode.equals(differentXSearchNode);

        // Assert
        Assert.assertFalse(areEqual);
        Assert.assertNotEquals(searchNode.hashCode(), differentXSearchNode.hashCode());
    }

    @Test
    public void two_searchnodes_that_differ_by_y_only_are_not_equal() {
        // Arrange
        final byte x = 32;
        final byte depth = 127;
        final SearchNode searchNode = new SearchNode(x, (byte)12, depth);
        final SearchNode differentYSearchNode = new SearchNode(x, (byte)6, depth);

        // Act
        final Boolean areEqual = searchNode.equals(differentYSearchNode);

        // Assert
        Assert.assertFalse(areEqual);
        Assert.assertNotEquals(searchNode.hashCode(), differentYSearchNode.hashCode());
    }

    @Test
    public void two_searchnodes_that_differ_by_depth_only_are_equal() {
        // Arrange
        final byte x = 87;
        final byte y = 98;
        final SearchNode searchNode = new SearchNode(x, y, (byte)12);
        final SearchNode differentDepthSearchNode = new SearchNode(x, y, (byte)73);

        // Act
        final Boolean areEqual = searchNode.equals(differentDepthSearchNode);

        // Assert
        Assert.assertTrue(areEqual);
        Assert.assertEquals(searchNode.hashCode(), differentDepthSearchNode.hashCode());
    }

    @Test
    public void searchnode_and_null_are_not_equal() {
        // Arrange
        final SearchNode searchNode = new SearchNode((byte)12, (byte)0, (byte)2);

        // Act
        final Boolean areEqual = searchNode.equals(null);

        // Assert
        Assert.assertFalse(areEqual);
    }

    @Test
    public void searchnode_and_another_type_are_not_equal() {
        // Arrange
        final SearchNode searchNode = new SearchNode((byte)12, (byte)0, (byte)2);

        // Act
        final Boolean areEqual = searchNode.equals(Integer.valueOf(42));

        // Assert
        Assert.assertFalse(areEqual);
    }
}
