package Normal;


import JSON.JsonParser;
import JSON.Tileset;
import JSON.Wrapper;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by oskar on 2016-12-07.
 * This classes has some inputs and outputs
 */
public class Level {

    private Tile[][] levelData;
    private Wrapper wrapper;
    private List<Tile> tiles = new ArrayList<>();
    private List<Entity> entities = new ArrayList<>();
    private int tickCounter = 0;

    public Level(String path) {
        JsonParser t = new JsonParser();
        wrapper = t.loadJSON(path);
        Wrapper.Layer l = wrapper.getLayer(0);
        Tileset ts = wrapper.getTileSet(l);
        int w = l.getWidth();
        int h = l.getHeight();
        int[] data = l.getData();
        levelData = new Tile[w][h];

        int xx = 0;
        int yy = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] == 0) {
                levelData[xx][yy] = null;
            } else {
                levelData[xx][yy] = new Tile(ts.getImage(data[i]), xx * ts.getTileWidth(), yy * ts.getTileHeight(), ts.getTileWidth(), ts.getTileHeight());
            }
            xx+=1;
            if (xx >= w) {
                xx = 0;
                yy += 1;
            }
        }
        for (xx = 0; xx < levelData.length; xx++) {
            Tile[] tileRow = levelData[xx];
            for (yy = 0; yy < tileRow.length; yy++) {
                Tile tile = tileRow[yy];
                if (tile != null) {
                    addNeighbours(levelData, xx, yy);
                }
            }
        }
    }

    public void tick() {
        entities.forEach(Entity::tick);

        for (Tile[] tileRow : levelData) {
            for (Tile tile : tileRow) {
                if (tile != null) {
                    tile.tick();
                }
            }
        }

        tickCounter++;
        if (tickCounter > Constants.TICKS_PER_STEP.value) {
            step();
            tickCounter = 0;
        }
    }

    public void step() {
        for (Tile[] tileRow : levelData) {
            for (Tile tile : tileRow) {
                if (tile != null) {
                    tile.step();
                }
            }
        }
    }

    public void draw(Graphics g) {
        for (int i = 0; i < 3; i ++) {
            for (Tile[] tileRow : levelData) {
                for (Tile tile : tileRow) {
                    if (tile != null) {
                        if (i == 0) tile.draw(g);
                        else if (i == 1) tile.drawPlaced(g);
                        else tile.drawOnTop(g);
                    }
                }
            }
        }
        entities.forEach(entity -> entity.draw(g));
    }

    private void forEachTile(Tile[][] levelData, Consumer<Tile> todo) {
        for (Tile[] tileRow : levelData) {
            for (Tile tile : tileRow) {
                if (tile != null) {
                    todo.accept(tile);
                }
            }
        }
    }

    public void leftClick(MouseEvent event) {
        int gridX = event.getX()/50;
        int gridY = event.getY()/50;
        if (levelData[gridX][gridY] != null) {
            levelData[gridX][gridY].click(event);
        }
    }

    public void rightClick(MouseEvent event) {
        int gridX = event.getX()/50;
        int gridY = event.getY()/50;
        if (levelData[gridX][gridY] != null) {
            levelData[gridX][gridY].addLetter(event);
        }
    }

    private void addNeighbours(Tile[][] levelData, int xx, int yy) {
        if (yy-1 >= 0 && levelData[xx][yy-1] != null) {
            levelData[xx][yy].addNeighbours(Direction.NORTH, levelData[xx][yy-1]);

        }
        if (xx+1 < levelData.length && levelData[xx+1][yy] != null) {
            levelData[xx][yy].addNeighbours(Direction.EAST, levelData[xx+1][yy]);

        }
        if (yy+1 < levelData[xx].length && levelData[xx][yy+1] != null) {
            levelData[xx][yy].addNeighbours(Direction.SOUTH, levelData[xx][yy+1]);

        }
        if (xx-1 >= 0 && levelData[xx-1][yy] != null) {
            levelData[xx][yy].addNeighbours(Direction.WEST, levelData[xx-1][yy]);
        }
    }
}
