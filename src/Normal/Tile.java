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
            neighbours.get(mail.getDirection()).addMail(mail);
        });
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
