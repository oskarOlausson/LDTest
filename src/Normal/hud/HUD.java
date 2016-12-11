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


    private static final Color background = new Color(91, 150, 232);

    private List<Button> buttons = new ArrayList<>();
    private int x;
    private int y;
    private Button selected;
    private final int width;
    private final int height;
    private ChangeCursorListener cursorListener;
    private Image bulldozerCursor = Library.loadImage("bulldozer_cursor");

    public HUD(int x, int y, int width, int height, ChangeCursorListener cursorListener) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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
        buttons.add(new Button("bulldozer", null, startX, height-100, 50, 50));
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
        g.setColor(background);
        g.fillRect(x, y, width, height);
        buttons.forEach(button -> {

            if (button.equals(selected)) {
                g.setColor(new Color(0, 0, 0, 50));
                g.fillRect(x+button.getPosition().getDrawX()-(int)button.getSize().getWidth()*3/4,
                        y+button.getPosition().getDrawY()-(int)button.getSize().getHeight()*3/4,
                        (int)(button.getSize().getWidth()*1.5),
                        (int)(button.getSize().getHeight()*1.5));
                g.setColor(background);
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
