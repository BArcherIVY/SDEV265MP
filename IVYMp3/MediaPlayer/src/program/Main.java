package program;

import fileControl.Annex;
import fileControl.Playlist;

import java.io.File;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main {
    public static Stage mainStage;
    public static AppLayout appLayout;
    public static MusicBox musicBox;
    public static void main(String[] args) {
        Platform.startup(() -> {
            mainStage = new Stage();
            musicBox = new MusicBox();
            String filePath =
                    Annex.ChooseFile();
            if (filePath != null) {
                Annex.RunAnnex(filePath);

                // PLAYLIST FOLDER
                File playlistFolder =
                        new File("playlists");
                if (!playlistFolder.exists()) {
                    playlistFolder.mkdir();
                }

                // LOAD PLAYLISTS
                File[] playlistFiles =
                        playlistFolder.listFiles();
                if (playlistFiles != null) {
                    for (File file :
                            playlistFiles) {
                        if (
                                file.getName()
                                .endsWith(".txt")
                        ) {
                            String playlistName =
                                    file.getName()
                                    .replace(
                                            ".txt",
                                            ""
                                    );
                            Playlist playlist =
                                    new Playlist(
                                            playlistName
                                    );
                            playlist.loadFromDisk(
                                    file.getPath()
                            );
                        }
                    }
                }

                // CREATE FAVORITES IF MISSING
                if (
                        !Annex.playlists
                        .containsKey(
                                "Favorites"
                        )
                ) {

                    Playlist favorites =
                            new Playlist(
                                    "Favorites"
                            );

                    Annex.playlists.put(
                            favorites.name,
                            favorites
                    );

                    favorites.savePlaylist(
                            "playlists/Favorites.txt"
                    );
                }

                // MAIN APP LAYOUT
                appLayout =
                        new AppLayout(musicBox);
                ArtistScreen artistScreen =
                        new ArtistScreen(musicBox);
                appLayout.setCenter(
                        artistScreen.getView()
                );

                Scene scene = new Scene(
                        appLayout.getView(),
                        1200,
                        700
                );

                mainStage.setTitle(
                        "MusicBox"
                );

                mainStage.setScene(scene);
                mainStage.show();

            } else {

                System.out.println(
                        "Error selecting folder."
                );
            }
        });
    }
}