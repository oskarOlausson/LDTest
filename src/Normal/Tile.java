package Normal;

import Normal.mail.*;
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
    private boolean international = false;
    private Type letterType = Type.LETTER;
    private Image wantImage = null;
    private boolean active = false;
    private int score = 0;
    private int scoreMax = 3;
    private int wantOffset = 110;

    private Class<? extends Placeable> type;
    private Color goodColor = Library.hsvColor(70, 200, 200);
    private Color badColor = Library.hsvColor(240, 200, 200);

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

    public boolean hasNewMail() {
        return !(newOnTop.isEmpty());
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

    public void setPostType(boolean international, Type letterType) {
        this.international = international;
        this.letterType = letterType;

        int xx, yy;
        if (international) {
            yy = 100;
        } else yy = 0;

        switch (letterType) {
            case SMALL_BOX:
                xx = 100;
                break;
            case BIG_BOX:
                xx = 200;
                break;
            default:
                xx = 0;
        }

        if (direction.equals(Direction.NORTH))  {
            wantImage = Library.loadTile("wantNorth", xx, yy, 100, 100);
            wantOffset = 10;
        }
        else {
            wantImage = Library.loadTile("want", xx, yy, 100, 100);
            wantOffset = -110;
        }

    }

    public void activateSender() {
        active = true;
    }

    public void deActivateSender() {
        active = false;
    }

    @Override
    public void step() {
        if (special) {
            if (ingoing) {
                timer.update();
                if (timer.isDone() && active) {
                    Mail mail;

                    switch(letterType) {
                        case LETTER:    mail = new Letter(getX(), getY(), international);   break;
                        case SMALL_BOX: mail = new SmallBox(getX(), getY(), international); break;
                        case BIG_BOX:   mail = new BigBox(getX(), getY(), international);   break;
                        default: mail = new Letter(getX(), getY(), international);          break;
                    }

                    List list = new ArrayList<Tile>();
                    list.add(this);
                    onTop.add(mail);
                    if (neighbours.get(direction).moveMaid(list, direction)) {
                        actualMove(direction);
                    }
                    else onTop.remove(mail);
                    timer.restart();
                }
            }
            else {
                sprite.setImageIndex(0);
                if (hasMail()) {
                    for (Mail m: onTop) {
                        if (m.getType() == letterType && m.getInternational() == international) {
                            score ++;
                            sprite.setImageIndex(2);
                        }
                        else {
                            sprite.setImageIndex(1);
                            score = Math.max(0, score - 1);
                        }
                    }
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
        if (type == null) {
            placed = null;
        } else {
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

    public void drawWant(Graphics g) {
        if (special && active) {
            DrawFunctions.drawImage(g, wantImage, getX() - 50, getY() + wantOffset);
            if (!ingoing) {
                int width = getWidth() / scoreMax;
                for (int i = 0; i < scoreMax; i++) {

                    if (i < score) g.setColor(goodColor);
                    else g.setColor(badColor);
                    g.fillOval(getX() - getWidth() / 2 + i * width, getY() + wantOffset, width, width);
                }
            }
        }

    }
    public void addNeighbours(Direction direction, Tile tile) {
        neighbours.put(direction, tile);
    }

    public void addMail(Mail mail) {
        newOnTop.add(mail);
    }

    public void actualMove(Direction direction) {
        actualMove(neighbours.get(direction), direction);
    }

    public void actualMove(Tile neighbour, Direction direction) {
        if (neighbour != null) {
            onTop.forEach(m -> {
                m.setDirection(direction);
                m.getPosition().update(neighbour.getX(), neighbour.getY());
                neighbour.addMail(m);
            });
            onTop.clear();
        }
        else System.err.println("Could not move");
    }

    public void addLetter(MouseEvent event) {
        onTop.add(new Letter( position.getDrawX(), position.getDrawY(), true));
    }

    public boolean moveMaid(List<Tile> tiles, Direction direction) {

        if (special) {
            return !ingoing;
        }

        if (!tiles.contains(this)) {
            tiles.add(this);

            Tile goTo = null;
            boolean success = false;

            if (placed != null) {
                Direction direction2;
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

            if (goTo.hasNewMail()) return false;
        }
        else return false;

        actualMove(direction);
        return true;
    }

    public int getScore() {
        return score;
    }

    public boolean isActive() {
        return active;
    }
}
