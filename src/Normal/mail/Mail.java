/*
 * File: Mail.java
 * Author: Fredrik Johansson
 * Date: 2016-12-10
 */
package Normal.mail;

import Normal.DrawFunctions;
import Normal.Entity;
import Normal.Position;
import Normal.Sprite;

import java.awt.*;

public abstract class Mail extends Entity {

    protected boolean international;
    protected Type letterType;
    protected Position animation;
    protected double directionNumber;
    protected double directionNumberAnimate;

    public Mail(Position position) {
        super(position);
        animation = position.copy();
    }

    public boolean isInternational() { return international; }

    public abstract Type getType();

    public void animate() {
        directionNumber = direction.toRad();

        if (directionNumberAnimate < directionNumber) {
            if (directionNumberAnimate + Math.PI < directionNumber) {
                directionNumberAnimate += Math.PI * 2;
                directionNumberAnimate = Math.max(directionNumber, directionNumberAnimate - Math.PI / 18);
            }
            else directionNumberAnimate = Math.min(directionNumber, directionNumberAnimate + Math.PI / 18);

        }
        else if (directionNumberAnimate > directionNumber){
            if (directionNumberAnimate - Math.PI > directionNumber) {
                directionNumberAnimate -= Math.PI * 2;
                directionNumberAnimate = Math.min(directionNumber, directionNumberAnimate + Math.PI / 18);
            }
            else directionNumberAnimate = Math.max(directionNumber, directionNumberAnimate - Math.PI / 18);

        }

        sprite.update();
        double animX = 0.8 * animation.getX() + 0.2 * getX();
        double animY = 0.8 * animation.getY() + 0.2 * getY();
        animation.update(animX, animY);
    }

    @Override
    public void draw(Graphics g) {

        DrawFunctions.drawImage(g, getImage(), animation.getDrawX(), animation.getDrawY(), 1, 1, directionNumberAnimate);
    }

    public boolean getInternational() {
        return international;
    }
}
