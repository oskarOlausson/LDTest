/*
 * File: Placable.java
 * Author: Fredrik Johansson
 * Date: 2016-12-10
 */
package Normal.placable;

import Normal.Entity;

import java.util.Collection;

public abstract class Placable extends Entity {
    public abstract void sense(Collection<Entity> sensed);
}
