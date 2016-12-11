package JSON;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by oskar on 2016-12-07.
 * loads a json file and returns a wrapper with all the information
 */
public class JsonParser {

    public Wrapper loadJSON(String str) {

        String path = "/" + str;

        if (!path.substring(path.length() - 5).equals(".json")) path += ".json";
        URL url = getClass().getResource(path);

        int[][] array;

        String levelString = "";
        BufferedReader reader = null;
        try {
            if (url == null) throw new IOException();
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String add;
            do {
                add = reader.readLine();
                if (add != null) levelString += add + "\n";
            }
            while (add != null);
        } catch (IOException e) {
            System.err.println("Could not find file '" + path + "'");
        }

        Wrapper w = new Gson().fromJson(levelString, Wrapper.class);
        return w;
    }
}
