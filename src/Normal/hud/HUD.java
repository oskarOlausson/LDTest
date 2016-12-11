/*
 * File: HUD.java
 * Author: Fredrik Johansson
 * Date: 2016-12-10
 */
package Normal.hud;

import Normal.Constants;
import Normal.placable.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HUD {



    private List<Button> buttons = new ArrayList<>();
    private int x;
    private int y;
    private Button selected;

    public HUD(int x, int y) {
        this.x = x;
        this.y = y;

        selected = new Button("mover", Mover.class, 25, 25, Constants.HUD_WIDTH.value, 50);
        buttons.add(selected);
        buttons.add(new Button("spinner", Mover.class, 25, 75, Constants.HUD_WIDTH.value, 50));
        buttons.add(new Button("switch", Switch.class, 25, 125, Constants.HUD_WIDTH.value, 50));
        buttons.add(new Button("jumper", Jumper.class, 25, 175, Constants.HUD_WIDTH.value, 50));
    }

    public void click(int mouseX, int mouseY) {
        for (Button button : buttons) {
            if (button.inside(mouseX-x, mouseY-y)) {
                selected = button;
            }
        }
    }

    public void tick() {

    }

    public void draw(Graphics g) {
        buttons.forEach(button -> {
            if (button.equals(selected)) {
                g.setColor(Color.cyan);
                g.draw3DRect(x+button.getPosition().getDrawX()-(int)button.getSize().getWidth()/2-1,
                             y+button.getPosition().getDrawY()-(int)button.getSize().getHeight()/2-1,
                             (int)button.getSize().getWidth(),
                             (int)button.getSize().getHeight(), false);
            }
            button.draw(g, x, y);
        });
    }

    public Class<? extends Placeable> selected() {
        return selected.getType();
    }
}
