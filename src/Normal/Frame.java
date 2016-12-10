package Normal;
/*
 * Created by oskar on 2016-11-17.
 * This is the main class
 */
import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    World world;

    private Frame() {
        world = new World();

        add(world);
        initUI();
    }

    private void initUI() {
        getContentPane().setPreferredSize(new Dimension(Constants.WIDTH.value, Constants.HEIGHT.value));
        setResizable(false);
        pack();

        setTitle("LD-jam");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void run(){
        world.run();
    }

    public static void main(String[] args) {
        Frame ex = new Frame();

        /*Some piece of code*/
        ex.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
            }
        });

        ex.setVisible(true);
        //runs the program
        ex.run();
    }
}