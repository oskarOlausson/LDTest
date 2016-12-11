package Normal;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by oskar on 2016-12-03.
 * This classes has some inputs and outputs
 */
class TextBox {

    private String string;
    private static final Font fontNormal = new Font("Sans-Serif", Font.PLAIN, 20);

    private static final Color blockColor       = new Color(0,35,150,240);
    private static final Color textColor        = new Color(140, 140, 190);
    private final Dimension size = new Dimension(350, 100);

    public TextBox(String string) {
        this.string = string;
    }

    public void setString(String s) {
        string = s;
    }


    public void draw(Graphics g, int x, int y) {
        x = (int) (x - size.getWidth() / 2);
        y = (int) (y - size.getHeight() / 2);

        g.setFont(fontNormal);

        FontMetrics fm = g.getFontMetrics();

        g.setColor(blockColor);

        g.fillRect(x, y, (int) size.getWidth(), (int) size.getHeight());

        int stringX, stringY;

        stringY = (int) (y + size.getHeight() / 2 - fm.getHeight() / 2 + fm.getAscent());

        String[] lines = string.split("\\r?\\n");

        g.setColor(textColor);

        for (int i = 0; i < lines.length; i++) {
            stringX = (int) (x + size.getWidth()  / 2 - fm.stringWidth(lines[i]) / 2);
            g.drawString(lines[i], stringX, (int) (stringY + ((i + .5) - lines.length / 2f) * fm.getHeight()));
        }
    }

    public int getHeight() {
        return (int) size.getHeight();
    }

}

