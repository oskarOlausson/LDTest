package Normal;

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
    private Map<Class<? extends Placeable>, Placeable> placed = new HashMap<>();
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
        placed.forEach((type, placeable) -> placeable.tick());
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

    @Override
    public void step() {
        if (special) {
            if (ingoing) {
                timer.update();
                if (timer.isDone()) {
                    Tile giveTo = neighbours.get(direction);
                    giveTo.addMail(new Letter(giveTo.getX(), giveTo.getY(), true));
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
        for (Placeable placable : placed.values()) {
            placable.sense(onTop, this, toRemove::add);
        }
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

    @Override
    public Entity click(MouseEvent event) {
        if (!special) {
            Placeable p = createPlacable();
            placed.put(type, p);
            return p;
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
        placed.forEach((type, entity) -> entity.draw(g));
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

    public void addLetter(MouseEvent event) {
        onTop.add(new Letter( position.getDrawX(), position.getDrawY(), true));
    }
}
