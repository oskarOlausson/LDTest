/*
 * File: Mover.java
 * Author: Fredrik Johansson
 * Date: 2016-12-10
 */
package Normal.placable;

import Normal.Direction;
import Normal.Position;
import Normal.Sprite;
import Normal.Tile;
import Normal.mail.Mail;
import Normal.mail.Type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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

        Tile target = getGoTo(tile, new ArrayList<>(sensed));

        if (target == null) {
            return;
        }

        if (target.moveMaid(list, direction)) {
            sensed.forEach(mail -> mail.setFlying(getPosition()));
            tile.actualMove(target, direction);
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

    @Override
    public Tile getGoTo(Tile owner, List<Mail> mail) {
        HashMap<Direction, Tile> neighbours = owner.getNeighbours();

        Tile tile = neighbours.get(direction);
        if (tile == null) return null;
        if (mail.get(0).getType().equals(Type.BIG_BOX)) return tile;

        Tile tile2 = tile.getNeighbours().get(direction);
        if (tile2 == null) return tile;
        if (mail.get(0).getType().equals(Type.SMALL_BOX)) return tile2;

        Tile tile3 = tile2.getNeighbours().get(direction);
        if (tile3 == null) return tile2;
        return tile3;
    }
}
