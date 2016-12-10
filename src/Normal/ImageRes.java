package Normal;

import java.net.URL;

/**
 * Created by oskar on 2016-12-07.
 * This classes has some inputs and outputs
 */
public class ImageRes {

    private URL path;
    private String aim;
    private String fileEnd = ".png";

    public ImageRes(String string) {

        string = "../" + string;

        if (!string.substring(string.length() - 4, string.length() - 3).equals(".")) {
            string += fileEnd;
        }

        aim = "'" + string + "'";
        path = this.getClass().getResource(string);
    }

    public boolean isValid() {
        return path != null;
    }

    public URL getPath() {
        return path;
    }

    public String toString() {
        return aim;
    }
}
