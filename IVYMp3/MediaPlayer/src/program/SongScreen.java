package program;

import fileControl.Album;
import fileControl.Annex;
import fileControl.Playlist;
import fileControl.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class SongScreen {
    private BorderPane root;
    public SongScreen(MusicBox musicBox) {
        root = new BorderPane();
        root.setStyle(
                "-fx-background-color: #383838;"
        );

        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.TOP_CENTER);
        Label title = new Label("All Songs");
        title.setStyle(
                "-fx-text-fill: white;" +
                "-fx-font-size: 30px;" +
                "-fx-font-weight: bold;"
        );

        content.getChildren().add(title);
        List<Song> allSongs =
                new ArrayList<>();
        for (Album album :
                Annex.albumMap.values()) {

            allSongs.addAll(album.tracks);
        }

        allSongs.sort((a, b) ->
                a.title.compareToIgnoreCase(b.title));
        VBox songList = new VBox(10);
        for (int i = 0; i < allSongs.size(); i++) {
            Song song = allSongs.get(i);
            HBox row = createSongRow(
                    song,
                    allSongs,
                    i,
                    musicBox
            );
            songList.getChildren().add(row);
        }

        content.getChildren().add(songList);
        ScrollPane scrollPane =
                new ScrollPane();
        scrollPane.setContent(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle(
                "-fx-background: #383838;" +
                "-fx-background-color: #383838;"
        );

        root.setCenter(scrollPane);
    }

    private HBox createSongRow(
            Song song,
            List<Song> songs,
            int currentIndex,
            MusicBox musicBox
    ) {

        Label songTitle =
                new Label(song.title);
        songTitle.setStyle(
                "-fx-text-fill: white;" +
                "-fx-font-size: 16px;"
        );

        Label albumName =
                new Label(song.albumName);
        albumName.setStyle(
                "-fx-text-fill: lightgray;" +
                "-fx-font-size: 14px;"
        );

        VBox textBox = new VBox(5);
        textBox.getChildren().addAll(
                songTitle,
                albumName
        );

        Label addButton = new Label("+");
        addButton.setStyle(
                "-fx-text-fill: #FD4CAD;" +
                "-fx-font-size: 24px;" +
                "-fx-font-weight: bold;"
        );

        addButton.setOnMouseEntered(e -> {
            addButton.setStyle(
                    "-fx-text-fill: #ff7bc8;" +
                    "-fx-font-size: 24px;" +
                    "-fx-font-weight: bold;"
            );
        });

        addButton.setOnMouseExited(e -> {
            addButton.setStyle(
                    "-fx-text-fill: #FD4CAD;" +
                    "-fx-font-size: 24px;" +
                    "-fx-font-weight: bold;"
            );
        });

        // ADD TO PLAYLIST
        addButton.setOnMouseClicked(e -> {
            List<String> playlistNames =
                    new ArrayList<>(
                            Annex.playlists.keySet()
                    );

            ChoiceDialog<String> dialog =
                    new ChoiceDialog<>(
                            playlistNames.get(0),
                            playlistNames
                    );

            dialog.setTitle(
                    "Add To Playlist"
            );

            dialog.setHeaderText(
                    "Choose Playlist"
            );

            dialog.setContentText(
                    "Playlist:"
            );

            Optional<String> result =
                    dialog.showAndWait();

            if (result.isPresent()) {

                Playlist selectedPlaylist =
                        Annex.playlists.get(
                                result.get()
                        );

                if (
                        !selectedPlaylist.tracks
                        .contains(song)
                ) {

                    selectedPlaylist.tracks.add(
                            song
                    );

                    selectedPlaylist.savePlaylist(
                            "playlists/" +
                            selectedPlaylist.name +
                            ".txt"
                    );

                    System.out.println(
                            "Added " +
                            song.title +
                            " to " +
                            selectedPlaylist.name
                    );
                }
            }

            e.consume();
        });

        Region spacer = new Region();
        HBox.setHgrow(
                spacer,
                Priority.ALWAYS
        );

        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(14));
        row.setSpacing(20);
        row.setStyle(
                "-fx-background-color: #444444;" +
                "-fx-background-radius: 10px;"
        );

        row.getChildren().addAll(
                textBox,
                spacer,
                addButton
        );

        row.setOnMouseClicked(e -> {

            musicBox.setPlaylist(
                    songs,
                    currentIndex
            );
        });

        row.setOnMouseEntered(e -> {

            row.setStyle(
                    "-fx-background-color: #555555;" +
                    "-fx-background-radius: 10px;"
            );
        });

        row.setOnMouseExited(e -> {

            row.setStyle(
                    "-fx-background-color: #444444;" +
                    "-fx-background-radius: 10px;"
            );
        });

        return row;
    }

    public Parent getView() {
        return root;
    }
}