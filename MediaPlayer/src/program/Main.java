package program;

import java.util.Scanner;

import fileControl.Annex;
import javafx.application.Platform;

public class Main {
	public static void main(String[] args) {

		Platform.startup(() -> {
		}); // Stops JavaFX from freaking out because no GUI

		MusicBox mb = new MusicBox();
		// do annex shits
		String filePath = Annex.ChooseFile();
		if (filePath != null) {
			Annex.RunAnnex(filePath);
			mb.playMP3(Annex.finalMap.get("Bad Moons.mp3"));
			System.out.println("Playing... Press ENTER to stop.");
			new Scanner(System.in).nextLine();
			mb.stopMp3();

		} else {
			System.out.println("FUCK!");
			return;
		}

	}
}
