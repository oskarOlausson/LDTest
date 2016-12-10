package Normal;
import java.awt.*;

/**
 * For easy object creation, make this
 * Created by oskar on 2016-10-16.
 */
public abstract class Entity {

    //position variables
    protected Position position;
    protected Dimension size;
    protected Sprite sprite;
    protected double scaleX = 1;
    protected double scaleY = 1;
    protected double direction = 0;

    protected double dx = 0;
    protected double dy = 0;

    public abstract void tick();

    //if the object should be removed
    protected boolean remove = false;

    public Rectangle getCollisionBox() {
        return getCollisionBox(position, getWidth(), getHeight());
    }
    public Rectangle getCollisionBox(Position position) {
        return getCollisionBox(position, getWidth(), getHeight());
    }
    public Rectangle getCollisionBox(Position position, int width, int height) {
        return new Rectangle(position.getDrawX() - width / 2, position.getDrawY() - height / 2, width, height);
    }

    public boolean collides(Entity other) {
        return getCollisionBox().contains(other.getCollisionBox());
    }
    public boolean collides(Rectangle other) {
        return getCollisionBox().contains(other);
    }

    public int getX() {return position.getDrawX();}
    public int getY() {return position.getDrawY();}

    public Position getPosition() {
        return position.copy();
    }
    public void setPosition(double x, double y) {
        position.update(x, y);
    }
    public void addToPosition(double x, double y) {
        position.update(position.getX() + x, position.getY() + y);
    }

    public int getWidth() {
        return (int) (size.getWidth() * scaleX);
    }
    public int getHeight() {
        return (int) (size.getHeight() * scaleY);
    }

    public Image getImage(){
        return sprite.getImage();
    }
    protected double getDirection(){
        return direction;
    }

    //sets flag to be removed when time comes
    public void remove() {
        remove = true;
    }
    public boolean ifRemove() {
        return remove;
    }

    public void draw(Graphics g) {
        DrawFunctions.drawImage(g, sprite.getImage(), getX(), getY(), scaleX, scaleY, direction);
    }
}