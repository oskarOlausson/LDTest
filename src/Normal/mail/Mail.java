/*
 * File: Mail.java
 * Author: Fredrik Johansson
 * Date: 2016-12-10
 */
package Normal.mail;

import Normal.DrawFunctions;
import Normal.Entity;
import Normal.Library;
import Normal.Position;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Mail extends Entity {

    protected boolean international;
    protected Type letterType;
    protected Position animation;
    protected double directionNumber;
    protected double directionNumberAnimate;
    protected Image shadow;

    private double scaleX = 1;
    private double scaleY = 1;

    private boolean isFlaying;
    private Position startFlyPosition;

    public Mail(Position position) {
        super(position);
        animation = position.copy();
    }

    public void createShadow() {
        shadow = Library.tint((BufferedImage) sprite.getImage(), 0, 0, 0, 1);
    }

    public abstract Type getType();

    @Override
    public void tick() {
        animate();
    }

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
        double animX = 0.9 * animation.getX() + 0.1 * getX();
        double animY = 0.9 * animation.getY() + 0.1 * getY();
        animation.update(animX, animY);


        flyingAnimation();
    }

    private void flyingAnimation() {
        if (isFlaying) {
            if (animation.distanceToPosition(getPosition()) < 10) {
                isFlaying = false;
                scaleX = 1;
                scaleY = 1;
            } else {
                double scale;
                double totalLenght = getPosition().distanceToPosition(startFlyPosition);
                double currentLength = animation.distanceToPosition(startFlyPosition);

                if (currentLength > totalLenght/2) {
                    currentLength -= totalLenght/2;
                    scale = (totalLenght/2 - currentLength)/(totalLenght/2)+1;
                } else {
                    scale = (totalLenght/2 + currentLength)/(totalLenght/2);
                }

                scale = Math.sqrt(scale);
                scaleX = scale;
                scaleY = scale;
            }
        }
    }

    public void setFlying(Position startPosition) {
        isFlaying = true;
        startFlyPosition = startPosition;
    }

    public void drawShadow(Graphics g) {
        DrawFunctions.drawImage(g, shadow, (int) (animation.getDrawX() + 2 * scaleX), (int) (animation.getDrawY() + 2 * scaleY), 1, 1, directionNumberAnimate);
    }

    @Override
    public void draw(Graphics g) {
        DrawFunctions.drawImage(g, getImage(), animation.getDrawX(), animation.getDrawY(), scaleX, scaleY, directionNumberAnimate);
    }

    public boolean getInternational() {
        return international;
    }
}
