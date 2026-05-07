package program;

import java.io.File;
import java.util.List;

import fileControl.Song;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class MusicBox {
    private MediaPlayer mediaPlayer;
    private double currentVolume = 0.5;
    private List<Song> currentPlaylist;
    private int currentSongIndex = 0;
    public void setPlaylist(List<Song> songs, int startIndex) {
        currentPlaylist = songs;
        currentSongIndex = startIndex;
        playCurrentSong();
    }

    private void playCurrentSong() {
        if (currentPlaylist == null || currentPlaylist.isEmpty()) {
            return;
        }

        Song currentSong =
                currentPlaylist.get(currentSongIndex);
        playMP3(currentSong.path);

        if (Main.appLayout != null &&
            Main.appLayout.getMiniPlayer() != null) {
            Main.appLayout
                    .getMiniPlayer()
                    .setSong(currentSong);
        }
    }

    public void nextSong() {
        if (currentPlaylist == null ||
            currentPlaylist.isEmpty()) {
            return;
        }

        currentSongIndex++;
        if (currentSongIndex >= currentPlaylist.size()) {
            currentSongIndex = 0;
        }

        playCurrentSong();
    }

    public void previousSong() {
        if (currentPlaylist == null ||
            currentPlaylist.isEmpty()) {
            return;
        }

        currentSongIndex--;
        if (currentSongIndex < 0) {
            currentSongIndex =
                    currentPlaylist.size() - 1;
        }

        playCurrentSong();
    }

    public void playMP3(String path) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        File file = new File(path);
        String mediaUri =
                file.toURI().toString();
        Media hit = new Media(mediaUri);
        mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.setVolume(currentVolume);
        mediaPlayer.setOnEndOfMedia(() -> {
            nextSong();
        });

        mediaPlayer.play();
    }

    public void pauseMp3() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public void resumeMp3() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    public void stopMp3() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void seek(double seconds) {
        if (mediaPlayer != null) {
            mediaPlayer.seek(
                    Duration.seconds(seconds)
            );
        }
    }

    public void setVolume(double volume) {
        currentVolume = volume;
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }

    public double getVolume() {
        return currentVolume;
    }
}