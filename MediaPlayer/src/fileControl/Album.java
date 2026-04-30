package fileControl;

import java.util.ArrayList;
import java.util.List;

public class Album {
	public String name;
	public List<Song> tracks; // list of tracks in songs

	public Album(String name) { // instance
		this.name = name;
		this.tracks = new ArrayList<>();
	}

	public void addSong(Song song) {
		this.tracks.add(song);
	}

}
