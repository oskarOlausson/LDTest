package Normal;


import JSON.JsonParser;
import JSON.Tileset;
import JSON.Wrapper;
import Normal.hud.HUD;
import Normal.mail.Type;
import Normal.placable.Mover;

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

    private final List<Tile> outList = new ArrayList<>();
    private final List<Tile> inList = new ArrayList<>();
    private Tile[][] levelData;
    private Wrapper wrapper;
    private HUD hud;
    private List<Tile> tiles = new ArrayList<>();
    private List<Entity> entities = new ArrayList<>();
    private List<Decoration> decor = new ArrayList<>();

    private int tickCounter = 0;
    private Entity latest = null;

    public Level(String path, HUD.ChangeCursorListener cursorListener) {
        JsonParser t = new JsonParser();
        wrapper = t.loadJSON(path);
        Wrapper.Layer l = wrapper.getLayer(1);
        Tileset ts = wrapper.getTileSet(0);
        int w = l.getWidth();
        int h = l.getHeight();
        int[] data = l.getData();
        levelData = new Tile[w][h];

        int xx = 0;
        int yy = 0;
        Tile specialTile;
        for (int i = 0; i < data.length; i++) {
            if (data[i] == 0) {
                levelData[xx][yy] = null;
            } else {
                switch(data[i]) {
                    case 5: specialTile = levelData[xx][yy] = new Tile(Library.loadImage("input"), (int) ((xx + 0.5) * ts.getTileWidth()), (int) ((yy + 0.5) * ts.getTileHeight()), ts.getTileWidth(), ts.getTileHeight(), true);
                        inList.add(specialTile);
                        break;
                    case 6: specialTile = levelData[xx][yy] = new Tile(Library.loadImage("output"), (int) ((xx + 0.5) * ts.getTileWidth()), (int) ((yy + 0.5) * ts.getTileHeight()), ts.getTileWidth(), ts.getTileHeight(), false);
                        outList.add(specialTile);
                        break;
                    default: levelData[xx][yy] = new Tile(ts.getImage(data[i]), (int) ((xx + 0.5) * ts.getTileWidth()), (int) ((yy + 0.5) * ts.getTileHeight()), ts.getTileWidth(), ts.getTileHeight());
                }
            }
            xx+=1;
            if (xx >= w) {
                xx = 0;
                yy += 1;
            }
        }
        linkTilesWithNeighours();
        l = wrapper.getLayer(0);
        data = l.getData();

        boolean international;
        Type letterType;
        for (int i = 0; i < outList.size(); i++) {
            if (i % 3 == 0) {
                letterType = Type.LETTER;
            }
            else if (i % 3 == 1) {
                letterType = Type.SMALL_BOX;
            }
            else letterType = Type.BIG_BOX;

            if (i % 2 == 0) {
                international = true;
            }
            else international = false;

            outList.get(i).setPostType(international, letterType);
        }

        for (int i = 0; i < inList.size(); i++) {
            if (i % 3 == 0) {
                letterType = Type.LETTER;
            }
            else if (i % 3 == 1) {
                letterType = Type.SMALL_BOX;
            }
            else letterType = Type.BIG_BOX;

            if (i % 2 == 0) {
                international = true;
            }
            else international = false;

            inList.get(i).setPostType(international, letterType);
        }

        xx = 0;
        yy = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] != 0) {
                decor.add(new Decoration(ts.getImage(data[i]), (int) ((xx) * ts.getTileWidth()), (int) ((yy) * ts.getTileHeight())));
            }
            xx+=1;
            if (xx >= w) {
                xx = 0;
                yy += 1;
            }
        }
        hud = new HUD(Constants.WIDTH.value-Constants.HUD_WIDTH.value, 0, cursorListener);
    }

    private void linkTilesWithNeighours() {
        for (int xx = 0; xx < levelData.length; xx++) {
            Tile[] tileRow = levelData[xx];
            for (int yy = 0; yy < tileRow.length; yy++) {
                Tile tile = tileRow[yy];
                if (tile != null) {
                    addNeighbours(levelData, xx, yy);
                    if (tile.isSpecial()) {
                        tile.correctDirection();
                    }
                }
            }
        }
    }

    public void tick() {
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

        hud.tick();
    }

    public void step() {

        for (Tile[] tileRow : levelData) {
            for (Tile tile : tileRow) {
                if (tile != null) {
                    tile.step();
                }
            }
        }
        for (Tile[] tileRow : levelData) {
            for (Tile tile : tileRow) {
                if (tile != null) {
                    tile.stepFinished();
                }
            }
        }
    }

    public void draw(Graphics g) {


        decor.forEach(wall -> wall.draw(g));

        for (int i = 0; i < 4; i ++) {
            for (Tile[] tileRow : levelData) {
                for (Tile tile : tileRow) {
                    if (tile != null) {
                        if (i == 0) tile.draw(g);
                        else if (i == 1) tile.drawPlaced(g);
                        else if (i == 2) tile.drawOnTop(g);
                        else {
                            tile.drawWant(g);
                        }
                    }
                }
            }
        }



        hud.draw(g);

    }

    private void forEachTile(Consumer<Tile> todo) {
        for (Tile[] tileRow : levelData) {
            for (Tile tile : tileRow) {
                if (tile != null) {
                    todo.accept(tile);
                }
            }
        }
    }

    public void leftClick(int mouseX, int mouseY, boolean hold) {
        Entity now = null;
        int gridX = mouseX/50;
        int gridY = mouseY/50;
        boolean inBounds;
        inBounds = (gridX < levelData.length) && (gridX > 0) && (gridY < levelData[0].length) && (gridY > 0);

        if (inBounds && levelData[gridX][gridY] != null) {

            levelData[gridX][gridY].setPlacableType(hud.selected());

            if (!hold || (hud.selected() != null && hud.selected().equals(Mover.class))) now = levelData[gridX][gridY].click();

            if (latest != null) {
                double dist = new Position(mouseX, mouseY).distanceToPosition(latest.getPosition());
                if (dist < 60 && dist > 10) {
                    if (Math.abs(mouseX - latest.getX()) > Math.abs(mouseY - latest.getY())) {
                        if (latest.getX() < mouseX) latest.setDirection(Direction.EAST);
                        else latest.setDirection(Direction.WEST);
                    } else {
                        if (latest.getY() < mouseY) latest.setDirection(Direction.SOUTH);
                        else latest.setDirection(Direction.NORTH);
                    }
                    if (now != null) now.setDirection(latest.direction);
                }
            }

            if (now != null) latest = now;

        }
        else hud.click(mouseX, mouseY);
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

    public HUD getHud() {
        return hud;
    }
}
