package models;

import java.util.ArrayList;
import java.util.Objects;

public class SearchNode {
    private final byte x;
    private final byte y;
    private final int depth;

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

    public byte getX() {
        return x;
    }

    public byte getY() {
        return y;
    }

    public int getDepth() {
        return depth;
    }

    public Iterable<SearchNode> expand() {
        boolean canMoveUp = y > 0;
        boolean canMoveDown = y < 14;
        boolean canMoveLeft = x > 0;
        boolean canMoveRight = x < 14;

        ArrayList<SearchNode> neighbours = new ArrayList<SearchNode>();

        if (canMoveLeft) {
            neighbours.add(new SearchNode((byte)(getX() - 1), getY(), getDepth() + 1));
        }

        if (canMoveUp) {
            neighbours.add(new SearchNode(getX(), (byte)(getY() - 1), getDepth() + 1));
        }

        if (canMoveRight) {
            neighbours.add(new SearchNode((byte)(getX() + 1), getY(), getDepth() + 1));
        }

        if (canMoveDown) {
            neighbours.add(new SearchNode(getX(), (byte)(getY() + 1), getDepth() + 1));
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
