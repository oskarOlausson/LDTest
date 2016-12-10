package Normal;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Created by oskar on 2016-12-09.
 * This classes has some inputs and outputs
 */
public class Mover extends Entity {

    private int dx = 0;
    private int dy = 0;
    Sound s;

    public Mover(int x, int y) {
        super(new Sprite("player", 32), new Position(x, y));
        setDirection(Direction.EAST);
        s = new Sound();
        s.loadSound("Wavewave");
        s.play();
    }

    public void input(final List<Integer> list) {
        if (list.contains(KeyEvent.VK_LEFT)) {
            dx = -1;
        }
        else if (list.contains(KeyEvent.VK_RIGHT)) {
            dx = 1;
        }
        else dx = 0;

        if (list.contains(KeyEvent.VK_UP)) {
            dy = -1;
        }
        else if (list.contains(KeyEvent.VK_DOWN)) {
            dy = 1;
            if (s.percentDone() > 10) s.reset();
        }
        else dy = 0;
    }

    @Override
    public void tick() {

        addToPosition(dx, dy);
        sprite.update();

    }

    @Override
    public void step() {

    }

    @Override
    public Entity click(MouseEvent event) {
        return null;
    }


    @Override
    public void draw(Graphics g) {
        DrawFunctions.drawImage(g, getImage(), getX(), getY(), scaleX, scaleY, direction.toRad());
    }
}
