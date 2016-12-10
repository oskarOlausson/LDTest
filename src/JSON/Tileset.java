package JSON;

import Normal.Library;

import java.awt.*;

/**
 * Created by oskar on 2016-12-07.
 * Used for parsing the json file
 */
public class Tileset {
    private int columns;
    private int firstgid;
    private String image;
    private int imageHeight;
    private int imageWidth;
    private int margin;
    private String name;
    private int spacing;
    private int tilecount;
    private int tileheight;
    private int tilewidth;

    public Image getImage(int index) {
        return Library.loadTile(image, index - 1, tilewidth, tileheight);
    }

    public String getName() {
        return name;
    }
    public int getTileWidth() {
        return tilewidth;
    }

    public int getTileHeight() {
        return tileheight;
    }
}
