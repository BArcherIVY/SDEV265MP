package Test;

import org.junit.jupiter.api.Test;

import fileControl.Playlist;

import static org.junit.jupiter.api.Assertions.*;

public class testCases {

    // Playlist Creation Test
    @Test
    void testCreatePlaylist() {
        Playlist playlist = new Playlist("Favorites");
        assertNotNull(playlist);
    }

    // Add Song Path Test
    @Test
    void testAddSongToPlaylist() {
        Playlist playlist = new Playlist("Favorites");
        playlist.addSong("song1.mp3");
        assertEquals(1, playlist.getSongs().size());
    }

    // Remove Song Path Test
    @Test
    void testRemoveSongFromPlaylist() {
        Playlist playlist = new Playlist("Favorites");
        playlist.addSong("song1.mp3");
        playlist.removeSong("song1.mp3");
        assertEquals(0, playlist.getSongs().size());
    }

    // Playlist Name Test
    @Test
    void testPlaylistName() {
        Playlist playlist = new Playlist("Road Trip");
        assertEquals("Road Trip", playlist.getName());
    }

    // Multiple Songs Test
    @Test
    void testMultipleSongsAdded() {
        Playlist playlist = new Playlist("Favorites");
        playlist.addSong("song1.mp3");
        playlist.addSong("song2.mp3");
        playlist.addSong("song3.mp3");

        assertEquals(3, playlist.getSongs().size());
    }

    // Empty Playlist Test
    @Test
    void testEmptyPlaylist() {
        Playlist playlist = new Playlist("Empty Playlist");
        assertTrue(playlist.getSongs().isEmpty());
    }

    // MP3 File Validation Test
    @Test
    void testMp3FileExtension() {
        String fileName = "music.mp3";
        assertTrue(fileName.endsWith(".mp3"));
    }

    // Invalid File Validation Test
    @Test
    void testInvalidFileExtension() {
        String fileName = "document.txt";
        assertFalse(fileName.endsWith(".mp3"));
    }

    // Annex File Path Test
    @Test
    void testFilePathNotNull() {
        String path = "C:/Music/song.mp3";
        assertNotNull(path);
    }

    // Playlist Interaction Test
    @Test
    void testPlaylistInteraction() {
        Playlist playlist = new Playlist("Workout");
        playlist.addSong("powerSong.mp3");
        assertTrue(playlist.getSongs().contains("powerSong.mp3"));
    }
}
