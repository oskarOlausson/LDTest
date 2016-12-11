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

public class Letter extends Mail {

    public Letter(int x, int y, boolean international) {
        super(new Position(x, y));
        this.international = international;

        String path = "postIn";
        if (international) path = "postOut";
        setSprite(new Sprite(path));
    }

    @Override
    public void step() {

    }

    @Override
    public Type getType() {
        return Type.LETTER;
    }


}
