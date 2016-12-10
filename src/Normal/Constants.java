package Normal;

/**
 * Created by oskar on 2016-12-07.
 * Has some valuable constants that are nice to reach from anywhere
 */
public enum Constants {

    WIDTH (320), //height of screen
    HEIGHT (320), //width of screen
    SECOND (60); //how many ticks in one second

    int value;
    Constants(int value) {
        this.value = value;
    }
}
