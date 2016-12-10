/*
 * File: HUD.java
 * Author: Fredrik Johansson
 * Date: 2016-12-10
 */
package Normal.hud;

import Normal.placable.Placable;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class HUD {

    private List<Button> buttons = new ArrayList<>();

    public HUD() {
        buttons.add(new Button("mover"));
    }

    public void click(MouseEvent event) {

    }

    public void tick() {

    }

    public void draw(Graphics g) {
        buttons.forEach(button -> button.draw(g));
    }

    public Class<Placable> selected() {
        return null;
    }
}
