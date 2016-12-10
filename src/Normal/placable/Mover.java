/*
 * File: Mover.java
 * Author: Fredrik Johansson
 * Date: 2016-12-10
 */
package Normal.placable;

import Normal.Entity;

import java.awt.event.MouseEvent;
import java.util.Collection;

public class Mover extends Placable {

    private static final int MOVE_AMOUNT = 1;

    @Override
    public void sense(Collection<Entity> sensed) {
        for (Entity entity : sensed) {
            getDirection().addToPosition(entity.getPosition(), MOVE_AMOUNT);
        }
    }

    @Override
    public void tick() {

    }

    @Override
    public void click(MouseEvent event) {

    }
}
