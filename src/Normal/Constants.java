package Normal;

/**
 * Created by oskar on 2016-12-07.
 * Has some valuable constants that are nice to reach from anywhere
 */
public enum Constants {

    WIDTH (1000), //height of screen
    HEIGHT (800), //width of screen
    SECOND (60), //how many ticks in one second
    HUD_WIDTH (100),
    TICKS_PER_STEP(30);

    public int value;
    Constants(int value) {
        this.value = value;
    }
}
