package Normal;

import Normal.mail.BigBox;
import Normal.mail.Letter;
import Normal.mail.Mail;
import Normal.placable.Placeable;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.List;

/**
 * Created by oskar on 2016-12-07.
 * This classes has some inputs and outputs
 */
public class Tile extends Entity {
    private Set<Mail> onTop = new HashSet<>();
    private Set<Mail> newOnTop = new HashSet<>();
    Placeable placed = null;
    private HashMap<Direction, Tile> neighbours = new HashMap<>();
    private boolean special = false;
    private boolean ingoing = false;
    private Timer timer;


    private Class<? extends Placeable> type;

    public Tile(BufferedImage image, int x, int y, int width, int height, boolean ingoing) {
        super(new Sprite(image, 50, 50), new Position(x, y), new Dimension(width, height));
        special = true;

        if (ingoing) {
            this.ingoing = true;
            timer = new Timer(5);
        }
    }

    public boolean hasMail() {
        return (!onTop.isEmpty());
    }

    public Tile(BufferedImage image, int x, int y, int width, int height) {
        super(new Sprite(image), new Position(x, y), new Dimension(width, height));
    }

    @Override
    public void tick() {
        if (placed != null) {
            placed.tick();
        }
        onTop.forEach(Mail::tick);
    }

    public boolean isSpecial() {
        return special;
    }

    public void correctDirection() {
        if (neighbours.get(Direction.NORTH) != null)        direction = Direction.NORTH;
        else if (neighbours.get(Direction.WEST) != null)    direction = Direction.WEST;
        else if (neighbours.get(Direction.SOUTH) != null)   direction = Direction.SOUTH;
        else if (neighbours.get(Direction.EAST) != null)    direction = Direction.EAST;
    }

    public void newMail() {
        if (newOnTop.size() == 0) return;

        boolean move = false;
        Tile goTo = neighbours.get(getDirection());
        List<Tile> list = new ArrayList<>();
        list.add(this);

        if (goTo == null) return;
        if (goTo.hasMail()) {
            if (goTo.moveMaid(list, getDirection())) {
                move = true;
            }
        }
        else move = true;

        if (move) {
            actualMove(direction);
        }
    }

    @Override
    public void step() {
        if (special) {
            if (ingoing) {
                timer.update();
                if (timer.isDone()) {
                    newOnTop.add(new Letter(getX(), getY(),false));
                    newMail();
                    timer.restart();
                }
            }
            else {
                if (hasMail()) {
                    onTop.clear();
                }
            }
        }

        List<Mail> toRemove = new ArrayList<>();

        if (placed != null) placed.sense(onTop, this);

        toRemove.forEach((mail) -> {
            onTop.remove(mail);

            Tile neighbour = neighbours.get(mail.getDirection());
            if (neighbour != null) {
                neighbour.addMail(mail);
            }
        });
    }


    public HashMap<Direction, Tile> getNeighbours() {
        return neighbours;
    }

    public Entity click() {
        if (!special) {
            placed = createPlacable();
            return placed;
        }
        return null;
    }


    public void stepFinished() {
        onTop.addAll(newOnTop);
        newOnTop.clear();
    }

    public void setPlacableType(Class<? extends Placeable> type) {
        this.type = type;
    }

    private Placeable createPlacable() {
        try {
            return type.getDeclaredConstructor(int.class, int.class).newInstance(position.getDrawX(), position.getDrawY());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void draw(Graphics g) {
        DrawFunctions.drawImage(g, getImage(), position.getDrawX(), position.getDrawY(), 1, 1, direction.toRad());
    }

    public void drawPlaced(Graphics g) {
        if (placed != null) placed.draw(g);
    }

    public void drawOnTop(Graphics g) {
        onTop.forEach(entity -> entity.draw(g));
    }
    public void addNeighbours(Direction direction, Tile tile) {
        neighbours.put(direction, tile);
    }

    public void addMail(Mail mail) {
        newOnTop.add(mail);
    }

    public void actualMove(Direction direction) {
        Tile neighbour = neighbours.get(direction);
        if (neighbour != null) {
            onTop.forEach(m -> {
                m.setDirection(direction);
                direction.addToPosition(m.getPosition(), getWidth());
            });
            onTop.forEach(neighbour::addMail);
            onTop.clear();
        }
        else System.err.println("Could not move");
    }

    public void addLetter(MouseEvent event) {
        onTop.add(new Letter( position.getDrawX(), position.getDrawY(), true));
    }

    public boolean moveMaid(List<Tile> tiles, Direction direction) {

        if (!tiles.contains(this)) {
            tiles.add(this);
            Direction direction2;
            Tile goTo;
            boolean success = false;

            if (placed != null) {
                direction2 = placed.getDirection();
                goTo = neighbours.get(direction2);
                if (goTo == null) {

                }
                else if (goTo.hasMail()) {
                    if (goTo.moveMaid(tiles, direction2)) {
                        success = true;
                        direction = direction2;
                    }
                }
                else {
                    success = true;
                    direction = direction2;
                }
            }

            if (!success) {
                goTo = neighbours.get(direction);

                if (goTo == null) {
                    return false;
                }
                if (goTo.hasMail()) {
                    if (!goTo.moveMaid(tiles, direction)) return false;
                }
            }
        }
        else return false;

        actualMove(direction);
        return true;
    }
}
