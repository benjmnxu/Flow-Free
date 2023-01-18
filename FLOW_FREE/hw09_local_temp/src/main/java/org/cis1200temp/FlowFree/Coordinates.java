package org.cis1200temp.FlowFree;

public record Coordinates(int x, int y) implements Comparable<Coordinates> {

    @Override
    public int compareTo(Coordinates o) {
        if (this.x == o.x() && this.y == o.y()) {
            return 0;
        } else if (Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2)) >= Math
                .sqrt(Math.pow(o.x, 2) + Math.pow(o.y, 2))) {
            return 1;
        } else {
            return -1;
        }
    }
}
