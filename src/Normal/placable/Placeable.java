/*
 * File: Placable.java
 * Author: Fredrik Johansson
 * Date: 2016-12-10
 */
package Normal.placable;

import Normal.*;
import Normal.mail.Mail;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public abstract class Placeable extends Entity {


    public Placeable(Sprite sprite, Position position) {
        super(sprite, position);
    }

    public Placeable(Sprite sprite) {
        super(sprite);
    }

    public abstract void sense(Collection<Mail> sensed,
                               Tile tile);

    public Tile getGoTo(Tile tile, List<Mail> mail) {
        return tile.getNeighbours().get(direction);
    }

    public Direction getDirection(Mail mail) {
        return direction;
    }
}
