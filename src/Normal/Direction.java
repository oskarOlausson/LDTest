/*
 * File: Direction.java
 * Author: Fredrik Johansson
 * Date: 2016-12-10
 */
package Normal;

public enum Direction {
    NORTH(0),
    EAST(1),
    SOUTH(2),
    WEST(3);

    private int value;
    Direction(int value) {
        this.value = value;
    }

    public void turnClockwise() {
        value = (value+1) % 4;
    }

    public void turnCounterClockwise() {
        value = (value+3) % 4;
    }

    public void turnAround() {
        value = (value+2) % 4;
    }

    public void addToPosition(Position position, int addAmount) {
        switch (this) {
            case NORTH:
                position.setY(position.getY() - addAmount);
                break;
            case EAST:
                position.setX(position.getX() + addAmount);
                break;
            case SOUTH:
                position.setY(position.getY() + addAmount);
                break;
            case WEST:
                position.setX(position.getX() - addAmount);
                break;
        }
    }

    public double toRad() {
        switch (this) {
            case NORTH:
                return 0;
            case EAST:
                return Math.PI/2;
            case SOUTH:
                return Math.PI;
            case WEST:
                return -Math.PI/2;
        }
        return 0; // won't happen
    }
}
