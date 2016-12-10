package Normal;

/**
 * Created by oskar on 2016-12-09.
 * This classes has some inputs and outputs
 */
public class Position {
    private double x;
    private double y;

    public Position(int x, int y) {
        this((double) x, (double) y);
    }

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void update(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distanceToPosition(Position p) {
        return Math.sqrt(Math.pow(p.getX() - getX(), 2) - Math.pow(p.getY() - getY(), 2));
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getDrawX() {
        return (int) x;
    }

    public int getDrawY() {
        return (int) y;
    }

    public Position copy() {
        return new Position(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (Double.compare(position.getX(), getX()) != 0) return false;
        return Double.compare(position.getY(), getY()) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(getX());
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getY());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
