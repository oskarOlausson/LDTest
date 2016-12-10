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


    public Tile(BufferedImage image, int x, int y, int width, int height) {
        super(new Sprite(image), new Position(x, y), new Dimension(width, height));
    }

    @Override
    public void tick() {

    }

    @Override
    public void step() {
        for (Placable placable : placed) {
            placable.sense(onTop);
        }

        Iterator<Mail> iter = onTop.iterator();
        while (iter.hasNext()) {
            Mail mail = iter.next();
            if (!mail.collides(this)) {
                iter.remove();

                Tile neighbour = null;
                if (mail.getX() < getX()) {
                    neighbour = neighbours.get(Direction.WEST);
                } else if (mail.getX() > getX()) {
                    neighbour = neighbours.get(Direction.EAST);
                } else  if (mail.getY() < getY()) {
                    neighbour = neighbours.get(Direction.SOUTH);
                } else  if (mail.getY() > getY()) {
                    neighbour = neighbours.get(Direction.NORTH);
                }

                if (neighbour != null) {
                    neighbour.addMail(mail);
                }
            }
        }
    }

    @Override
    public void click(MouseEvent event) {
        placed.add(new Mover(position.getX()+(int)size.getWidth()/2, position.getY()+(int)size.getHeight()/2));
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(getImage(), position.getX(), position.getY(), null);
        placed.forEach(entity -> entity.draw(g));
        onTop.forEach(entity -> entity.draw(g));
    }

    public void addNeighbours(Direction direction, Tile tile) {
        neighbours.put(direction, tile);
    }

    public void addMail(Mail mail) {
        onTop.add(mail);
    }

    public void addLetter(MouseEvent event) {
        onTop.add(new Letter(position.getX()+(int)size.getWidth()/2, position.getY()+(int)size.getHeight()/2));
    }
}
