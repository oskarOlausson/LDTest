package Normal;


import JSON.JsonParser;
import JSON.Tileset;
import JSON.Wrapper;
import Normal.mail.Letter;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by oskar on 2016-12-07.
 * This classes has some inputs and outputs
 */
public class Level {

    private static final int TICKS_PER_STEP = 30;

    private int[][] levelData;
    private Wrapper wrapper;
    private List<Tile> tiles = new ArrayList<>();
    private List<Entity> entities = new ArrayList<>();
    private int tickCounter = 0;

    public Level(String path) {
        JsonParser t = new JsonParser();
        wrapper = t.loadJSON(path);
        Wrapper.Layer l = wrapper.getLayer("Tile Layer 1");
        Tileset ts = wrapper.getTileSet(l);
        int w = l.getWidth();
        int h = l.getHeight();
        int[] data = l.getData();
        levelData = new int[w][h];

        int xx = 0;
        int yy = 0;
        for (int i = 0; i < data.length; i++) {
            levelData[xx][yy] = data[i];
            tiles.add(new Tile(ts.getImage(levelData[xx][yy]), xx * ts.getTileWidth(), yy * ts.getTileHeight()));
            xx+=1;
            if (xx >= w) {
                xx = 0;
                yy += 1;
            }

        }

        entities.add(new Letter());
    }

    public void tick() {
        entities.forEach(Entity::tick);

        tickCounter++;
        if (tickCounter > TICKS_PER_STEP) {
            step();
            tickCounter = 0;
        }
    }

    public void step() {
        entities.forEach(Entity::step);
    }

    public void draw(Graphics g) {
        tiles.forEach(tile -> tile.draw(g));
        entities.forEach(entity -> entity.draw(g));
    }

    public void leftClick(MouseEvent event) {
        entities.add(new Mover(event.getX(), event.getY()));
    }

    public void rightClick(MouseEvent event) {

    }
}
