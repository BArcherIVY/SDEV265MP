package program;
import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicBox {
	
	private MediaPlayer mediaPlayer;
	
	public void playMP3(String path) {
		if (mediaPlayer != null) { //stop current Mp3.
			mediaPlayer.stop();
		}
		
		File file = new File(path);
        String mediaUri = file.toURI().toString();
        Media hit = new Media(mediaUri); //cause it plays the hits!
        
        mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();
	}
	
	public void pauseMp3() { //self documenting code below!!

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
	
	
}
