/*
 * File: Placable.java
 * Author: Fredrik Johansson
 * Date: 2016-12-10
 */
package Normal.placable;

import Normal.Entity;
import Normal.Position;
import Normal.Sprite;

import java.util.Collection;

public abstract class Placable extends Entity {

    public Placable(Sprite sprite, Position position) {
        super(sprite, position);
    }

    public Placable(Sprite sprite) {
        super(sprite);
    }

    public abstract void sense(Collection<? extends Entity> sensed);
}
