package models;

import java.util.Objects;

public class SearchNode {
    private final byte x;
    private final byte y;
    private final byte depth;

    public SearchNode(byte x, byte y, byte depth) {
        if (x < 0) {
            throw new IllegalArgumentException();
        }

        if (y < 0) {
            throw new IllegalArgumentException();
        }

        if (depth < 0) {
            throw new IllegalArgumentException();
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

    public byte getDepth() {
        return depth;
    }

    @Override
    public String toString() {
        return "SearchNode{" +
                "x=" + x +
                ", y=" + y +
                ", depth=" + depth +
                '}';
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
