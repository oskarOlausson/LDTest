/*
 * File: Button.java
 * Author: Fredrik Johansson
 * Date: 2016-12-10
 */
package Normal.hud;

import Normal.DrawFunctions;
import Normal.Sprite;

import java.awt.*;

public class Button {

    private Sprite sprite;

    public Button(String image) {
        sprite = new Sprite(image);
    }

    public void draw(Graphics g) {
        DrawFunctions.drawImage(g, sprite.getImage(), 0, 0);
    }
}
