package JSON;

/**
 * Created by oskar on 2016-12-07.
 * Used for parsing the json file,
 * has all info about the levels made in Tiled
 * Has Layer which it stores all information regarding the place of the tiles
 */
public class Wrapper {

    private Layer[] layers;
    private int nextobjectid;
    private String orientation;
    private String renderorder;
    private int tileHeight;
    private Tileset[] tilesets;
    private int tilewidth;
    private int version;
    private int width;

    //find layer by index
    public Layer getLayer(int i) {
        return layers[i];
    }

    public Layer getLayer(String name) {
        for (int i = 0; i < layers.length; i++) {
            if (name.equals(layers[i].name)) return layers[i];
        }
        return null;
    }

    //find tileset by index
    public Tileset getTileSet(int i) {
        return tilesets[i];
    }

    //find the tileset that corresponds to a certain layer
    public Tileset getTileSet(Layer l) {
        for (int i = 0; i < layers.length; i++) {
            if (l.equals(layers[i])) return tilesets[i];
        }
        return null;
    }

    //find tileset by name
    public Tileset getTileSet(String name) {
        for (int i = 0; i < tilesets.length; i++) {
            if (name.equals(tilesets[i].getName())) return tilesets[i];
        }
        return null;
    }

    //Stores all information regarding the place of the tiles
    public class Layer {
        private int[] data;
        private int height;
        private String name;
        private double opacity;
        private String type;
        private boolean visible;
        private int width;
        private int x;
        private int y;

        //actual level info, stored as int array
        public int[] getData()   {return data;}
        public int   getHeight() {return height;}
        public int   getWidth()  {return width;}
    }

}
