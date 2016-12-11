/*
 * File: Letter.java
 * Author: Fredrik Johansson
 * Date: 2016-12-10
 */
package Normal.mail;

import Normal.DrawFunctions;
import Normal.Position;
import Normal.Sprite;

import java.awt.*;

public class SmallBox extends Mail {

    public SmallBox(int x, int y, boolean international) {
        super(new Position(x, y));

        this.international = international;

        String path = "smallBoxIn";
        if (international) path = "smallBoxOut";
        setSprite(new Sprite(path));
    }

    @Override
    public void tick() {
        animate();
    }

    @Override
    public void step() {

    }

    @Override
    public Type getType() {
        return Type.SMALL_BOX;
    }

}
