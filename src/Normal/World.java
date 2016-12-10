package Normal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The object that controls all graphics and all things seen on screen
 */

public class World extends JPanel implements ActionListener, MouseListener {

    private boolean running = false;
    private ReentrantLock lock = new ReentrantLock();
    private Level level = new Level("island");
    private List<Integer> keys = new ArrayList<>();
    private List<Mover> movers = new ArrayList<>();

    public World() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        movers.add(new Mover(48, 48));
    }

    //Frame rate back end
    public synchronized void run(){
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D / Constants.SECOND.value;

        long secondTimer = System.currentTimeMillis();
        double delta = 0;
        long now;
        running = true;

        while(running){
            now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;

            while(delta >=1){
                lock.lock();
                try {
                    tick();
                }finally {
                    lock.unlock();
                }
                delta -= 1;
            }

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            repaint();

            if (System.currentTimeMillis() - secondTimer > 1000){
                secondTimer += 1000;
            }
        }
    }

    public void tick(){
        movers.get(0).input(keys);
        movers.get(0).tick();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        lock.lock();
        try {
            level.draw(g);
        } finally {
            lock.unlock();
        }

        movers.get(0).draw(g);
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //for buttons and such
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        if (SwingUtilities.isLeftMouseButton(event)) {
            System.out.println("Left");
        } else if (SwingUtilities.isRightMouseButton(event)) {
            System.out.println("Right");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * This handles input
     */
    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            //System.out.println("before: " + keys);
            int key;
            for (int i = 0; i< keys.size(); i++){
                key = keys.get(i);
                if (key == e.getKeyCode()){
                    keys.remove(i);
                    i -= 1;
                }
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            keys.add(e.getKeyCode());
        }
    }
}