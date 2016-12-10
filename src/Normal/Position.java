package Normal;

/**
 * Created by oskar on 2016-12-09.
 * This classes has some inputs and outputs
 */
public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(double x, double y) {
        this.x = (int) x;
        this.y = (int) y;
    }

    public void update(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double distanceToPosition(Position p) {
        return Math.sqrt(Math.pow(p.getX() - getX(), 2) - Math.pow(p.getY() - getY(), 2));
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDrawX() {
        return x;
    }

    public int getDrawY() {
        return y;
    }

    public Position copy() {
        return new Position(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (x != position.x) return false;
        return y == position.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
