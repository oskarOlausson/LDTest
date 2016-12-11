/*
 * File: Mover.java
 * Author: Fredrik Johansson
 * Date: 2016-12-10
 */
package Normal.placable;

import Normal.Position;
import Normal.Sprite;
import Normal.Tile;
import Normal.mail.Mail;
import Normal.mail.Type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Jumper extends Placeable {

    private static final int MOVE_AMOUNT = 50;

    public Jumper(int x, int y) {
        super(new Sprite("jumper", 50, 50), new Position(x, y));
    }

    @Override
    public void sense(Collection<Mail> sensed, Tile tile) {
        if (sensed.size() == 0) return;

        List<Tile> list = new ArrayList<>();
        list.add(tile);

        Tile target = tile.getNeighbours().get(direction);
        Type t = new ArrayList<>(sensed).get(0).getType();

        if (t == Type.BIG_BOX || t == Type.SMALL_BOX) {
            target = target.getNeighbours().get(direction);
            if (t == Type.SMALL_BOX) {
                Tile target2 = target.getNeighbours().get(direction);
                if (target2 != null) {
                    target = target2;
                }
            }
        }

        if (target.moveMaid(list, direction)) {
            tile.actualMove(target);
            sprite.setImageSpeed(0.5);
        }
    }

    @Override
    public void tick() {
        sprite.update();
        if (sprite.looped()) {
            sprite.pause();
        }
    }

    @Override
    public void step() {

    }
}
