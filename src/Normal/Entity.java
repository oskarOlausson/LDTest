package Normal;

import java.awt.*;
import java.awt.event.MouseEvent;

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
    protected Direction direction = Direction.NORTH;

    protected double dx = 0;
    protected double dy = 0;

    public Entity(Sprite sprite, Position position) {
        this.sprite = sprite;
        this.position = position;
    }

    public Entity(Sprite sprite) {
        this.sprite = sprite;
        this.position = new Position(0, 0);
        this.size = new Dimension(sprite.getWidth(), sprite.getHeight());
    }


    public abstract void tick();

    public abstract void step();

    public abstract void click(MouseEvent event);

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
    public void setPosition(int x, int y) {
        position.update(x, y);
    }
    public void addToPosition(int x, int y) {
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

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    //sets flag to be removed when time comes
    public void remove() {
        remove = true;
    }
    public boolean ifRemove() {
        return remove;
    }

    public void draw(Graphics g) {
        if (sprite != null) {
            DrawFunctions.drawImage(g, sprite.getImage(), getX(), getY(), scaleX, scaleY, direction.toRad());
        }
    }
}
