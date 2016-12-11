/*
 * File: HUD.java
 * Author: Fredrik Johansson
 * Date: 2016-12-10
 */
package Normal.hud;

import Normal.Library;
import Normal.placable.Jumper;
import Normal.placable.Mover;
import Normal.placable.Placeable;
import Normal.placable.Switch;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HUD {


    public interface ChangeCursorListener {
        void onChange(Image image);
    }


    private List<Button> buttons = new ArrayList<>();
    private int x;
    private int y;
    private Button selected;
    private ChangeCursorListener cursorListener;
    private Image bulldozerCursor = Library.loadImage("bulldozer_cursor");

    public HUD(int x, int y, ChangeCursorListener cursorListener) {
        this.x = x;
        this.y = y;
        this.cursorListener = cursorListener;


        int startY = 100;
        int startX = 50;
        selected = new Button("mover", Mover.class, startX, startY, 50, 50);
        buttons.add(selected);
        startY++;
        buttons.add(new Button("spinner", Mover.class, startX, startY+75, 50, 50));
        startY++;
        buttons.add(new Button("switch", Switch.class, startX, startY+75*2, 50, 50));
        startY++;
        buttons.add(new Button("jumper", Jumper.class, startX, startY+75*3, 50, 50));
        startY++;
        buttons.add(new Button("bulldozer", null, startX, startY+75*6, 50, 50));
    }

    public void click(int mouseX, int mouseY) {
        for (Button button : buttons) {
            if (button.inside(mouseX-x, mouseY-y)) {
                selected = button;
                if (selected.getType() == null) {
                    cursorListener.onChange(bulldozerCursor);
                } else {
                    cursorListener.onChange(null);
                }
            }
        }
    }

    public void tick() {

    }

    public void draw(Graphics g) {
        buttons.forEach(button -> {
            if (button.equals(selected)) {
                g.setColor(Color.cyan);
                g.draw3DRect(x+button.getPosition().getDrawX()-(int)button.getSize().getWidth()*3/4,
                             y+button.getPosition().getDrawY()-(int)button.getSize().getHeight()*3/4,
                             (int)(button.getSize().getWidth()*1.5),
                             (int)(button.getSize().getHeight()*1.5), false);
            }
            button.draw(g, x, y);
        });
    }

    public Class<? extends Placeable> selected() {
        return selected.getType();
    }
}
