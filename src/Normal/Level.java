package Normal;


import JSON.JsonParser;
import JSON.Tileset;
import JSON.Wrapper;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by oskar on 2016-12-07.
 * This classes has some inputs and outputs
 */
public class Level {

    private int[][] levelData;
    private Wrapper wrapper;
    private List<Tile> tiles = new ArrayList<>();

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
    }

    public void draw(Graphics g) {
        for(Tile t: tiles) {
            t.draw(g);
        }
    }
}
