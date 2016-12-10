/*
 * File: Mail.java
 * Author: Fredrik Johansson
 * Date: 2016-12-10
 */
package Normal.mail;

import Normal.Entity;
import Normal.Position;
import Normal.Sprite;

public abstract class Mail extends Entity {

    protected boolean international;
    protected Type letterType;
    protected Position animation;



    public Mail(Position position) {
        super(position);
        animation = position.copy();
    }

    public boolean isInternational() { return international; }

    public abstract Type getType();

    public void animate() {
        sprite.update();
        double animX = 0.8 * animation.getX() + 0.2 * getX();
        double animY = 0.8 * animation.getY() + 0.2 * getY();
        animation.update(animX, animY);
    }
}
