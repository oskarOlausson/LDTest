/*
 * File: HUD.java
 * Author: Fredrik Johansson
 * Date: 2016-12-10
 */
package Normal.hud;

import Normal.Level;
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
        void onChangeCursor(Image image);
    }


    private static final Color BACKGROUND = new Color(104, 81, 52);
    private static final Color BACKGROUND_LIGHT = new Color(204, 153, 90, 100);

    private List<PlacableSelectable> select = new ArrayList<>();
    private List<Button> buttons = new ArrayList<>();
    private int x;
    private int y;
    private PlacableSelectable selected;
    private final int width;
    private final int height;
    private ChangeCursorListener cursorListener;
    private Image bulldozerCursor = Library.loadImage("bulldozer_cursor");

    public HUD(int x, int y, int width, int height, ChangeCursorListener cursorListener, Level level) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.cursorListener = cursorListener;


        int startY = 100;
        int startX = 50;
        selected = new PlacableSelectable("mover", Mover.class, startX, startY, 50, 50);
        select.add(selected);
        startY++;
        select.add(new PlacableSelectable("spinner", Mover.class, startX, startY+75, 50, 50));
        startY++;
        select.add(new PlacableSelectable("switch", Switch.class, startX, startY+75*2, 50, 50));
        startY++;
        select.add(new PlacableSelectable("jumper", Jumper.class, startX, startY+75*3, 50, 50));
        startY++;
        select.add(new PlacableSelectable("bulldozer", null, startX, height-100, 50, 50));

        buttons.add(new Button("playPause", startX, height-200, 50, 50, level));
    }

    public void click(int mouseX, int mouseY) {
        for (PlacableSelectable selectable : select) {
            if (selectable.inside(mouseX-x, mouseY-y)) {
                selected = selectable;
                if (selected.getType() == null) {
                    cursorListener.onChangeCursor(bulldozerCursor);
                } else {
                    cursorListener.onChangeCursor(null);
                }
            }
        }

        for (Button button : buttons)  {
            if (button.inside(mouseX-x, mouseY-y)) {
                button.click();
            }
        }
    }

    public void tick() {
        buttons.forEach(Button::tick);
    }

    public void draw(Graphics g) {
        g.setColor(BACKGROUND);
        g.fillRect(x, y, width, height);
        g.setColor(BACKGROUND_LIGHT);
        g.fillRect(x+7, y+7, width-14, height-14);

        select.forEach(selectable -> {

            if (selectable.equals(selected)) {
                g.setColor(new Color(0, 0, 0, 50));
                g.fillRect(x+selectable.getPosition().getDrawX()-(int)selectable.getSize().getWidth()*3/4,
                        y+selectable.getPosition().getDrawY()-(int)selectable.getSize().getHeight()*3/4,
                        (int)(selectable.getSize().getWidth()*1.5),
                        (int)(selectable.getSize().getHeight()*1.5));
                g.setColor(BACKGROUND);
                g.draw3DRect(x+selectable.getPosition().getDrawX()-(int)selectable.getSize().getWidth()*3/4,
                             y+selectable.getPosition().getDrawY()-(int)selectable.getSize().getHeight()*3/4,
                             (int)(selectable.getSize().getWidth()*1.5),
                             (int)(selectable.getSize().getHeight()*1.5), false);

            }
            selectable.draw(g, x, y);
        });

        buttons.forEach(button -> button.draw(g, x, y));
    }

    public Class<? extends Placeable> selected() {
        return selected.getType();
    }
}
