package program;

import fileControl.Annex;
import javafx.application.Platform;

public class Main {
	public static void main(String[] args) {

		Platform.startup(() -> {
		}); // Stops JavaFX from freaking out because no GUI

		MusicBox mb = new MusicBox();
		String filePath = Annex.ChooseFile();

		if (filePath != null) {
			Annex.RunAnnex(filePath); // Sitting there for 20 mins wondering why annex isn't working.... I didn't call
										// it.
			System.out.println("DEBUG: Map size is: " + Annex.albumMap.size());
			System.out.println("--- Library Contents ---");

			for (String name : Annex.albumMap.keySet()) {
				System.out.println("Album found: " + name);
			}

		} else {
			System.out.println("FUCK!");
			return;
		}

	}
}
