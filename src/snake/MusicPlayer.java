package snake;


import javazoom.jl.player.Player;
import javazoom.jl.decoder.JavaLayerException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MusicPlayer {

    private List<String> playlist;
    private String sonido;
    private Player player;
    private Thread musicThread;
    private boolean isPlaying;
    private boolean stopRequested;

    public MusicPlayer(List<String> playlist) {
        this.playlist = new ArrayList<>(playlist);
        Collections.shuffle(this.playlist);  // Mezclar la lista de reproducción
        isPlaying = false;
        stopRequested = false;
    }

    public MusicPlayer(String sonido) {
        this.sonido = sonido;
        isPlaying = false;
        stopRequested = false;
    }

    public void play() {
        if (isPlaying) {
            return;
        }

        isPlaying = true;
        stopRequested = false;
        musicThread = new Thread(() -> {
            while (!stopRequested) {
                for (String song : playlist) {
                    if (stopRequested) {
                        break;
                    }
                    try {
                        InputStream is = getClass().getResourceAsStream(song);
                        player = new Player(is);
                        player.play();
                    } catch (JavaLayerException e) {
                        e.printStackTrace();
                    }
                }
            }
            isPlaying = false;
        });

        musicThread.start();
    }

    public void playEffects() {
        if (isPlaying) {
            return;
        }

        isPlaying = true;
        stopRequested = false;
        musicThread = new Thread(() -> {
            try {
                InputStream is = getClass().getResourceAsStream(sonido);
                player = new Player(is);
                player.play();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
            isPlaying = false;
        });

        musicThread.start();
    }

    public void stop() {
        stopRequested = true;
        if (player != null) {
            player.close();
        }
        isPlaying = false;
    }
}
