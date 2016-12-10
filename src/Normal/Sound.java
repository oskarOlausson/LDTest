package Normal;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {

    private Clip clip = null;
    private Thread t = null;
    private double volume = 0;
    private boolean play = false;

    public Sound() {
    }

    /**
     * Stops the sound, if you play after this, it will continue from the paused position
     */
    public void pause() {
        play = false;
        if (clip != null) clip.stop();
    }

    public void play() {
        play = true;
        if (clip != null) {
            if (clip.isActive()) clip.start();
            else playFrom(0);
        }
    }

    public double percentDone() {

        return 100 * (clip.getFramePosition() / (double) clip.getFrameLength());

    }

    /**
     * Restarts the sound
     */
    public void reset() {
        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0);
            play();
        }
    }

    /**
     * If you dont want to play from the beggining
     * @param time in seconds
     */
    public void playFrom(double time) {
        play = true;
        if (clip != null) {
            clip.setMicrosecondPosition((long) time * 1000);
            clip.start();
        }
    }

    /**
     *
     * @param percent 0 is no sound, 1 is all the sound (you can go slightly past if you are really need)
     */
    public void setVolume(double percent) {
        volume = (1 - percent) * -80;
        if (clip != null) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue((float) volume);
        }
    }

    private void init() {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue((float) volume);
        if (!play) pause();
    }

    /**
     * This loads the sounds
     * @param url
     * @return
     */
    public synchronized Thread loadSound(String url) {

        if (url.length() < 4 || !url.substring(url.length() - 4, url.length() - 3).equals(".")) {
            url += ".wav";
        }

        final String path = "../" + url;

        t = new Thread(new Runnable() {

            // The wrapper thread is unnecessary, unless it blocks on the
            // Clip finishing; see comments.
            public void run() {
                try {
                    clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            getClass().getResourceAsStream(path));
                    clip.open(inputStream);
                    clip.start();
                    init();

                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        });

        t.start();

        return t;
    }

}