package models;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class SearchNode {
    private final byte x;
    private final byte y;
    private final int depth;
    private SearchNode parent;

    public SearchNode(byte x, byte y, int depth) {
        if (x < 0) {
            throw new IllegalArgumentException(String.format("Value supplied as x must not be negative. %d was supplied.", x));
        }

        if (y < 0) {
            throw new IllegalArgumentException(String.format("Value supplied as y must not be negative. %d was supplied.", y));
        }

        if (depth < 0) {
            throw new IllegalArgumentException(String.format("Value supplied as depth must not be negative. %d was supplied.", depth));
        }

        this.x = x;
        this.y = y;
        this.depth = depth;
    }

    public SearchNode(byte x, byte y, int depth, SearchNode parent) {
        this(x, y, depth);
        this.parent = parent;
    }

    public byte getX() {
        return x;
    }

    public byte getY() {
        return y;
    }

    public int getDepth() {
        return depth;
    }

    public SearchNode getParent() {
        return this.parent;
    }

    public Iterable<SearchNode> expand(int[][] grid) {
        boolean canMoveUp = y > 0;
        boolean canMoveDown = y < grid[this.getX()].length - 1;
        boolean canMoveLeft = x > 0;
        boolean canMoveRight = x < grid.length - 1;

        ArrayList<SearchNode> neighbours = new ArrayList<SearchNode>();

        if (canMoveLeft) {
            SearchNode neighbour = new SearchNode((byte) (getX() - 1), getY(), getDepth() + 1, this);
            if (grid[neighbour.getX()][neighbour.getY()] != 1) {
                neighbours.add(neighbour);
            }
        }

        if (canMoveUp) {
            SearchNode neighbour = new SearchNode(getX(), (byte) (getY() - 1), getDepth() + 1, this);
            if (grid[neighbour.getX()][neighbour.getY()] != 1) {
                neighbours.add(neighbour);
            }
        }

        if (canMoveRight) {
            SearchNode neighbour = new SearchNode((byte) (getX() + 1), getY(), getDepth() + 1, this);
            if (grid[neighbour.getX()][neighbour.getY()] != 1) {
                neighbours.add(neighbour);
            }
        }

        if (canMoveDown) {
            SearchNode neighbour = new SearchNode(getX(), (byte) (getY() + 1), getDepth() + 1, this);
            if (grid[neighbour.getX()][neighbour.getY()] != 1) {
                neighbours.add(neighbour);
            }
        }

        return neighbours;
    }

    @Override
    public String toString() {
        return "(" + x + ',' + y + ',' + depth + ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchNode that = (SearchNode) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
