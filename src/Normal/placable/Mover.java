/*
 * File: Mover.java
 * Author: Fredrik Johansson
 * Date: 2016-12-10
 */
package Normal.placable;

import Normal.Entity;
import Normal.Position;
import Normal.Sprite;

import java.awt.event.MouseEvent;
import java.util.Collection;

public class Mover extends Placable {

    private static final int MOVE_AMOUNT = 50;

    public Mover(int x, int y) {
        super(new Sprite("player", 32), new Position(x, y));
    }

    @Override
    public void sense(Collection<? extends Entity> sensed) {
        for (Entity entity : sensed) {
            getDirection().addToPosition(entity.getPosition(), MOVE_AMOUNT);
        }
    }

    @Override
    public void tick() {

    }

    @Override
    public void step() {

    }

    @Override
    public void click(MouseEvent event) {

    }
}
