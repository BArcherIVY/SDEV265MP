package fileControl;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

//playlist object
public class Playlist extends Album {

	public Playlist(String name) {
		super(name);

	}

	public void purgePlaylist() {
		// Java will throw an error if you remove things from a list while it's reaching
		// through

		this.tracks.removeIf(song -> { // this function lets java know that's ok.
			boolean exists = Files.exists(Paths.get(song.path));
			if (!exists) {
				System.out.println("Purging song " + song.title);
			}

			return !exists;
		});
	}

	public void savePlaylist(String fileName) {

		try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
			for (Song s : this.tracks) {
				writer.println(s.path); // Save only the path
			}
			System.out.println("Playlist saved to: " + fileName);
		} catch (IOException e) {
			System.err.println("Failed to save playlist: " + e.getMessage());
		}
	}

	public void loadFromDisk(String fileName) {
		try {
			// read file
			List<String> paths = Files.readAllLines(Paths.get(fileName));

			// clear tracks
			this.tracks.clear();

			for (String pathStr : paths) {
				if (Files.exists(Paths.get(pathStr))) {

					String tempTitle = Paths.get(pathStr).getFileName().toString();
					Song loadedSong = new Song(tempTitle, pathStr, "Loaded Playlist");

					this.tracks.add(loadedSong);
				}
			}
			System.out.println("Loaded " + this.tracks.size() + " songs from " + fileName);
		} catch (IOException e) {
			System.err.println("Could not load playlist: " + e.getMessage());
		}
		Annex.playlists.put(this.name, this); // put the playlists in the annex.
	}

}