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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Switch extends Placeable {

    private static final int MOVE_AMOUNT = 50;


    public Switch(int x, int y) {
        super(new Sprite("switch", 50), new Position(x, y));
    }

    @Override
    public void sense(Collection<Mail> sensed, Tile tile) {
        if (sensed.size() == 0) return;

        List<Tile> list = new ArrayList<>();

        tile.moveMaid(list, getDirection());
    }

    @Override
    public Direction getDirection(Mail mail) {
        if (mail.getInternational()) {
            return (direction.turnCounter());
        }
        return direction;
    }


    @Override
    public Tile getGoTo(Tile tile, List<Mail> mail) {
        return tile.getNeighbours().get(getDirection(mail.get(0)));
    }

    @Override
    public void tick() {

    }

    @Override
    public void step() {

    }
}
