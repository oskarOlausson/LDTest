package Normal;

import Normal.mail.Letter;
import Normal.mail.Mail;
import Normal.placable.Mover;
import Normal.placable.Placable;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/**
 * Created by oskar on 2016-12-07.
 * This classes has some inputs and outputs
 */
public class Tile extends Entity {
    private List<Mail> onTop = new ArrayList<>();
    private List<Placable> placed = new ArrayList<>();
    private HashMap<Direction, Tile> neighbours = new HashMap<>();
    private boolean special = false;
    private boolean ingoing = false;
    private Timer timer;

    public Tile(BufferedImage image, int x, int y, int width, int height, boolean ingoing) {
        super(new Sprite(image, 50, 50), new Position(x, y), new Dimension(width, height));
        special = true;

        if (ingoing) {
            this.ingoing = true;
            timer = new Timer(5);
        }


    }

    public Tile(BufferedImage image, int x, int y, int width, int height) {
        super(new Sprite(image), new Position(x, y), new Dimension(width, height));
    }

    @Override
    public void tick() {
        placed.forEach(Placable::tick);
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



        if (ingoing) {
            timer.update();
            if (timer.isDone()) {
                onTop.add(new Letter( position.getDrawX(), position.getDrawY(), true));
                timer.restart();
            }
        }

        List<Mail> toRemove = new ArrayList<>();
        for (Placable placable : placed) {
            placable.sense(onTop, toRemove::add);
        }
        toRemove.forEach((mail) -> {
            onTop.remove(mail);
            neighbours.get(mail.getDirection()).addMail(mail);
        });
    }

    @Override
    public void click(MouseEvent event) {
        if (!special) placed.add(new Mover(position.getDrawX()+(int)size.getWidth()/2, position.getDrawY()+(int)size.getHeight()/2));
    }

    @Override
    public void draw(Graphics g) {
        DrawFunctions.drawImage(g, getImage(), position.getDrawX(), position.getDrawY(), 1, 1, direction.toRad());
    }

    public void drawPlaced(Graphics g) {
        placed.forEach(entity -> entity.draw(g));
    }

    public void drawOnTop(Graphics g) {
        onTop.forEach(entity -> entity.draw(g));
    }

    public void addNeighbours(Direction direction, Tile tile) {
        neighbours.put(direction, tile);
    }

    public void addMail(Mail mail) {
        onTop.add(mail);
    }

    public void addLetter(MouseEvent event) {
        onTop.add(new Letter( position.getDrawX() + getWidth() / 2, position.getDrawY() + getWidth() / 2, true));
    }
}
