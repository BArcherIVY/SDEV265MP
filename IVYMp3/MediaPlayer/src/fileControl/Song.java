package fileControl;

public class Song {
	public String title; // All of this gets extracted from the metadata
	public String path;
	public String albumName;

	public Song(String title, String path, String albumName) {
		this.title = title;
		this.path = path;
		this.albumName = albumName;
	}
}
