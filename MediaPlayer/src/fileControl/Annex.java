package fileControl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.swing.JFileChooser;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

public class Annex {
	public static Map<String, String> finalMap = new HashMap<>();

	public static int RunAnnex(String Folder) {
		Path targetFolder = Paths.get(Folder);
		Map<String, String> fileMap = new HashMap<>(); // I LOVE HASHMAPS!!!!!

		try (Stream<Path> stream = Files.walk(targetFolder)) {
			stream.filter(Files::isRegularFile).filter(path -> path.toString().toLowerCase().endsWith(".mp3")) // make
																												// sure
																												// it's
																												// an
																												// MP3
					.forEach(path -> {
						try {
							AudioFile audioFile = AudioFileIO.read(path.toFile());
							Tag tag = audioFile.getTag();

							String songTitle = (tag != null) ? tag.getFirst(FieldKey.TITLE) : null; // Check title

							if (songTitle == null || songTitle.isEmpty()) {
								songTitle = path.getFileName().toString();
							}

							fileMap.put(songTitle, path.toAbsolutePath().toString()); // HASHMAPS!!!
						} catch (Exception e) {
							// if no tag, use filename.
							fileMap.put(path.getFileName().toString(), path.toAbsolutePath().toString());
						}
					});

			// print list to log and return 1.
			fileMap.forEach((name, path) -> System.out.println(name + " -> " + path));
			finalMap = fileMap;
			return 1;

		} catch (IOException e) {
			System.err.println("Error! " + targetFolder.toString() + " can't be found. How did the user manage that?");
			return -1;
		}
	}

	public static String ChooseFile() {
		JFileChooser chooser = new JFileChooser(); // file prompt
		chooser.setDialogTitle("Select a Folder");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int result = chooser.showOpenDialog(null);

		if (result == JFileChooser.APPROVE_OPTION) {

			File selectedFile = chooser.getSelectedFile();
			System.out.println("Selected path: " + selectedFile.getAbsolutePath());
			return selectedFile.getAbsolutePath();
		} else {

			return null;

		}
	}
}
