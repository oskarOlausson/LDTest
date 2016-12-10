package Normal;

import Normal.mail.Letter;
import Normal.mail.Mail;
import Normal.placable.Placable;

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
    private List<Placable> placed = new ArrayList<>();
    private HashMap<Direction, Tile> neighbours = new HashMap<>();
    private Class<? extends Placable> type;


    public Tile(BufferedImage image, int x, int y, int width, int height) {
        super(new Sprite(image), new Position(x, y), new Dimension(width, height));
    }

    @Override
    public void tick() {
        placed.forEach(Placable::tick);
    }

    @Override
    public void step() {
        List<Mail> toRemove = new ArrayList<>();
        for (Placable placable : placed) {
            placable.sense(onTop, toRemove::add);
        }
        toRemove.forEach((mail) -> {
            onTop.remove(mail);

            Tile neighbour = neighbours.get(mail.getDirection());
            if (neighbour != null) {
                neighbour.addMail(mail);
            }
        });
    }

    public void stepFinished() {
        onTop.addAll(newOnTop);
        newOnTop.clear();
    }

    public void setPlacableType(Class<? extends Placable> type) {
        this.type = type;
    }

    @Override
    public void click(MouseEvent event) {
        try {
            placed.add(type.getDeclaredConstructor(int.class, int.class).newInstance(position.getDrawX()+(int)size.getWidth()/2, position.getDrawY()+(int)size.getHeight()/2));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(getImage(), position.getDrawX(), position.getDrawY(), null);
        placed.forEach(entity -> entity.draw(g));
        onTop.forEach(entity -> entity.draw(g));
    }

    public void addNeighbours(Direction direction, Tile tile) {
        neighbours.put(direction, tile);
    }

    public void addMail(Mail mail) {
        newOnTop.add(mail);
    }

    public void addLetter(MouseEvent event) {
        onTop.add(new Letter( position.getDrawX() + getWidth() / 2, position.getDrawY() + getWidth() / 2, true));
    }
}
