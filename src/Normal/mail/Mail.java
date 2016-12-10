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

    public Mail(Sprite sprite) {
        super(sprite);
    }

    public Mail(Sprite sprite, Position position) {
        super(sprite, position);
    }
}
