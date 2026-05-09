import org.junit.jupiter.api.*;

import fileControl.Playlist;

import static org.junit.jupiter.api.Assertions.*;

import java.beans.Transient;
import java.util.*;

public class testCases {

    // MP3 Import Test
    @Test
    void testImportMP3() {
        MusicPlayer player = new MusicPlayer();
        boolean result = player.importSong("test.mp3");

        assertTrue(result, "MP3 file should be imported successfully");
        assertEquals(1, player.getLibrary().size());
    }

    // Invalid File Import Test
    @Test 
    void testImportInvalidFile() {
        MusicPlayer player = new MusicPlayer();
        boolean result = player.importSong("test.txt");

        assertFalse(result, "Non-MP3 file should not be imported");
    }

    // Display Library Test
    @Test 
    void testDisplayLibrary() {
        MusicPlayer player = new MusicPlayer();
        player.importSong("song1.mp3");
        player.importSong("song2.mp3");

        List<String> library = player.getLibrary();
        assertEquals(2, library.size());
    }

    // Sort by Album Test
    @Test 
    void testSortByAlbum() {
        MusicPlayer player = new MusicPlayer();

        player.addSong(new Song("Song1", "AlbumB"));
        player.addSong(new Song("Song2", "AlbumA"));

        player.sortByAlbum();

        List<Song> songs = player.getLibrary();
        assertEquals("AlbumA", songs.get(0).getAlbum());
    }

    // Play Song Test
    @Test
    void testPlaySong() {
        MusicPlayer player = new MusicPlayer();
        Song song = new Song("TestSong", "Album1");

        player.addSong(song);
        player.playMp3(song);

        assertTrue(player.isPlaying());
    }

    // Pause Playback Test
    @Test 
    void testPausePlayback() {
        MusicPlayer player = new MusicPlayer();
        Song song = new Song("TestSong", "Album1");

        player.addSong(song);
        player.playMp3(song);
        player.pauseMp3();

        assertFalse(player.isPlaying());
    }

    // Stop Playback Test
    @Test 
    void testStopPlayback()  {
        MusicPlayer player = new MusicPlayer();
        Song song = new Song("TestSong", "Album1");

        player.addSong(song);
        player.playMp3(song);
        player.stopMp3();

        assertFalse(player.isPlaying());
        assertEquals(0, player.getCurrentPosition());
    }

    // Create Playlist Test
    @Test 
    void testCreatePlaylist() {
        MusicPlayer player = new MusicPlayer();
        player.createPlaylist("My Playlist");

        assertNotNull(player.getPlaylist("My Playlist"));
    }

    // Add Song to Playlist Test
    @Test
    void testAddSongToPlaylist() {
        MusicPlayer player = new MusicPlayer();
        Song song = new Song("TestSong", "Album1");

        playlist.addSong(song);
        player.createPlaylist("My Playlist");
        player.addToPlaylist("My Playlist", song);

        assertEquals(1, player.getPlaylist("My Playlist").size());
    }

    // Remove Song from Playlist Test
    @Test 
    void testRemoveSongFromPlaylist() {
        MusicPlayer player = new MusicPlayer();
        Song song = new Song("TestSong", "Album1");

        player.createPlaylist("My Playlist");
        player.addToPlaylist("My Playlist", song);
        player.removeFromPlaylist("My Playlist", song);

        assertEquals(0, player.getPlaylist("My Playlist").size());
    }

    // Song Object Creation Test
    @Test 
    void testSongObjectCreation() {
        Song song = new Song("Title", "Album");

        assertEquals("Title", song.getTitle());
        assertEquals("Album", song.getAlbum());
    }

    // Module Interaction Test
    @Test 
    void testModuleInteraction() {
        MusicPlayer player = new MusicPlayer();
        Playlist playlist = new Playlist("My Playlist");

        Song song = new Song("TestSong", "Album1");
        playlist.addSong(song);

        assertEquals(1, playlist.getSongs().size());
    }
}
