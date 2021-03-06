package Normal;

import Normal.hud.HUD;
import Normal.placable.Mover;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The object that controls all graphics and all things seen on screen
 */

public class World extends JPanel implements ActionListener, MouseListener, HUD.ChangeCursorListener {

    private boolean running = false;
    private ReentrantLock lock = new ReentrantLock();
    private Level level = new Level("level", this);
    private List<Integer> keys = new ArrayList<>();
    private boolean mouseIsPressed = false;

    public World() {
        addKeyListener(new TAdapter());
        addMouseListener(this);
        setFocusable(true);
        setBackground(Color.BLACK);
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

        if (mouseIsPressed) {
            Point mouse = getMousePosition();

            if (mouse != null) level.leftClick((int) mouse.getX(), (int) mouse.getY(), true);
        }
        else if (level.getLatest() != null && !level.getLatest().getClass().equals(Mover.class)){
            level.resetLatest();
        }
        level.tick();
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

        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //for buttons and such
    }

    @Override
    public void mouseClicked(MouseEvent event) {

    }

    @Override
    public void mousePressed(MouseEvent event) {
        if (SwingUtilities.isLeftMouseButton(event)) {
            level.leftClick(event.getX(), event.getY(), mouseIsPressed);
            mouseIsPressed = true;
        } else if (SwingUtilities.isRightMouseButton(event)) {
            level.rightClick(event);
        }
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        if (SwingUtilities.isLeftMouseButton(event)) {
            mouseIsPressed = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent event) {

    }

    @Override
    public void mouseExited(MouseEvent event) {

    }

    @Override
    public void onChangeCursor(Image image) {
        if (image == null) {
            setCursor(Cursor.getDefaultCursor());
        } else {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Cursor c = toolkit.createCustomCursor(image , new Point(getX()+10, getY()+10), "img");
            setCursor (c);
        }
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