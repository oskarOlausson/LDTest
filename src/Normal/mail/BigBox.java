/*
 * File: Letter.java
 * Author: Fredrik Johansson
 * Date: 2016-12-10
 */
package Normal.mail;

import Normal.Position;
import Normal.Sprite;

public class BigBox extends Mail {

    public BigBox(int x, int y, boolean international) {
        super(new Position(x, y));

        this.international = international;

        String path = "bigBoxIn";
        if (international) path = "bigBoxOut";
        setSprite(new Sprite(path));
    }

    @Override
    public void step() {

    }

    @Override
    public Type getType() {
        return Type.BIG_BOX;
    }

}
