package Normal;

/**
 * Created by oskar on 2016-12-07.
 * Has some valuable constants that are nice to reach from anywhere
 */
public enum Constants {

    WIDTH (800), //height of screen
    HEIGHT (600), //width of screen
    SECOND (60), //how many ticks in one second
    TICKS_PER_STEP(30);

    int value;
    Constants(int value) {
        this.value = value;
    }
}
