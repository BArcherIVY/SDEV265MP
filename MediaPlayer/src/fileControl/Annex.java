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

public class Annex { // Please note, nothing in the annex should be getting saved on close.
	public static Map<String, Album> albumMap = new HashMap<>();
	public static Map<String, String> pathMap = new HashMap<>();

	public static int RunAnnex(String Folder) {
		Path targetFolder = Paths.get(Folder);
		Map<String, String> fileMap = new HashMap<>(); // I LOVE HASHMAPS!!!!!

		System.out.println("Running Annex..");

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

							String songTitle = (tag != null) ? tag.getFirst(FieldKey.TITLE) : null; // check titles
							String albumName = (tag != null) ? tag.getFirst(FieldKey.ALBUM) : null;

							if (songTitle == null || songTitle.isEmpty()) { // check both cases... not exactly sure
								songTitle = path.getFileName().toString();
							}

							sortSong(songTitle, path.toAbsolutePath().toString(), albumName); // we're putting it in
																								// both of these.
							// having a list that contains only paths could be handy in the future.
							fileMap.put(songTitle, path.toAbsolutePath().toString());
						} catch (Exception e) {
							// if no tag, use filename.
							fileMap.put(path.getFileName().toString(), path.toAbsolutePath().toString());
						}
					});

			// print list to log and return 1.
			fileMap.forEach((name, path) -> System.out.println(name + " -> " + path));
			pathMap = fileMap;
			return 1;

		} catch (IOException e) {
			System.err.println("Error! " + targetFolder.toString() + " can't be found. How did the user manage that?");
			return -1;
		}
	}

	// just brings up a menu to choose a folder
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

	public static void sortSong(String title, String path, String albumName) {

		// if it has a name, name it x, if not. default to unknown album.
		String name = (albumName == null || albumName.isEmpty()) ? "Unknown Album" : albumName;
		if (!albumMap.containsKey(name)) { // creates album dynamically.
			albumMap.put(name, new Album(name));
		}

		Song newSong = new Song(title, path, name); // add song to album anyway.
		albumMap.get(name).addSong(newSong);
		System.out.println("Added song " + title + " to " + albumName);

	}

}
