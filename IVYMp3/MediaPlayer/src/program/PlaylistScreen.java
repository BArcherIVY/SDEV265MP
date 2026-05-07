package program;

import fileControl.Annex;
import fileControl.Playlist;
import fileControl.Song;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class PlaylistScreen {

    private BorderPane root;
    private FlowPane playlistPane;
    private VBox songList;
    private Playlist selectedPlaylist;
    private MusicBox musicBox;
    public PlaylistScreen(MusicBox musicBox) {
        this.musicBox = musicBox;
        root = new BorderPane();
        root.setStyle(
                "-fx-background-color: #383838;"
        );

        VBox content = new VBox(25);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.TOP_CENTER);

        // TITLE
        Label title = new Label("Playlists");
        title.setStyle(
                "-fx-text-fill: white;" +
                "-fx-font-size: 30px;" +
                "-fx-font-weight: bold;"
        );

        // NEW PLAYLIST BUTTON
        Label newPlaylistButton =
                new Label("+ New Playlist");
        newPlaylistButton.setStyle(
                "-fx-text-fill: #FD4CAD;" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold;"
        );

        newPlaylistButton.setOnMouseEntered(e -> {
            newPlaylistButton.setStyle(
                    "-fx-text-fill: #ff7bc8;" +
                    "-fx-font-size: 18px;" +
                    "-fx-font-weight: bold;"
            );
        });

        newPlaylistButton.setOnMouseExited(e -> {
            newPlaylistButton.setStyle(
                    "-fx-text-fill: #FD4CAD;" +
                    "-fx-font-size: 18px;" +
                    "-fx-font-weight: bold;"
            );
        });

        // CREATE PLAYLIST
        newPlaylistButton.setOnMouseClicked(e -> {
            TextInputDialog dialog =
                    new TextInputDialog();
            dialog.setTitle(
                    "Create Playlist"
            );
            dialog.setHeaderText(
                    "Enter Playlist Name"
            );
            dialog.setContentText(
                    "Playlist:"
            );
            Optional<String> result =
                    dialog.showAndWait();
            if (
                    result.isPresent() &&
                    !result.get().trim().isEmpty()
            ) {

                String playlistName =
                        result.get().trim();
                Playlist playlist =
                        new Playlist(
                                playlistName
                        );

                Annex.playlists.put(
                        playlist.name,
                        playlist
                );

                // SAVE PLAYLIST
                playlist.savePlaylist(
                        "playlists/" +
                        playlist.name +
                        ".txt"
                );

                selectedPlaylist =
                        playlist;
                refreshPlaylists();
                refreshSongs(musicBox);
            }
        });

        content.getChildren().addAll(
                title,
                newPlaylistButton
        );

        // PLAYLIST GRID
        playlistPane = new FlowPane();
        playlistPane.setHgap(20);
        playlistPane.setVgap(20);
        playlistPane.setPrefWrapLength(1200);
        playlistPane.setAlignment(Pos.CENTER);

        // SONG LIST
        songList = new VBox(10);

        // DEFAULT PLAYLIST
        if (
                Annex.playlists.containsKey(
                        "Favorites"
                )
        ) {

            selectedPlaylist =
                    Annex.playlists.get(
                            "Favorites"
                    );

        } else if (
                !Annex.playlists.isEmpty()
        ) {

            selectedPlaylist =
                    new ArrayList<>(
                            Annex.playlists.values()
                    ).get(0);
        }

        refreshPlaylists();
        refreshSongs(musicBox);
        content.getChildren().addAll(
                playlistPane,
                songList
        );

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

    private void refreshPlaylists() {
        playlistPane.getChildren().clear();

        
        Playlist favorites =
                Annex.playlists.get(
                        "Favorites"
                );

        if (favorites != null) {
            playlistPane.getChildren().add(
                    createPlaylistCard(
                            favorites
                    )
            );
        }

        
        for (Playlist playlist :
                Annex.playlists.values()) {

            if (
                    playlist.name.equals(
                            "Favorites"
                    )
            ) {
                continue;
            }

            VBox card =
                    createPlaylistCard(playlist);
            playlistPane.getChildren().add(card);
        }
    }

    private VBox createPlaylistCard(
            Playlist playlist
    ) {

        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(20));
        card.setPrefWidth(200);
        card.setStyle(
                "-fx-background-color: #444444;" +
                "-fx-background-radius: 15px;"
        );

        // SELECTED PLAYLIST GLOW
        if (playlist == selectedPlaylist) {

            card.setStyle(
                    "-fx-background-color: #444444;" +
                    "-fx-background-radius: 15px;" +
                    "-fx-effect: dropshadow(gaussian, #FD4CAD, 25, 0.5, 0, 0);"
            );
        }

        Label playlistName =
                new Label(playlist.name);

        playlistName.setStyle(
                "-fx-text-fill: white;" +
                "-fx-font-size: 22px;" +
                "-fx-font-weight: bold;"
        );

        Label songCount =
                new Label(
                        playlist.tracks.size() +
                        " Songs"
                );

        songCount.setStyle(
                "-fx-text-fill: lightgray;" +
                "-fx-font-size: 15px;"
        );

        card.getChildren().addAll(
                playlistName,
                songCount
        );

        // DELETE BUTTON
        if (!playlist.name.equals("Favorites")) {

            Label deleteButton =
                    new Label("Delete");

            deleteButton.setStyle(
                    "-fx-text-fill: #ff5c5c;" +
                    "-fx-font-size: 14px;" +
                    "-fx-font-weight: bold;"
            );

            deleteButton.setOnMouseEntered(e -> {
                deleteButton.setStyle(
                        "-fx-text-fill: #ff8b8b;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;"
                );
            });

            deleteButton.setOnMouseExited(e -> {
                deleteButton.setStyle(
                        "-fx-text-fill: #ff5c5c;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;"
                );
            });

            deleteButton.setOnMouseClicked(e -> {

                // DELETE PLAYLIST FILE
                new File(
                        "playlists/" +
                        playlist.name +
                        ".txt"
                ).delete();

                Annex.playlists.remove(
                        playlist.name
                );

                if (
                        selectedPlaylist == playlist
                ) {

                    selectedPlaylist =
                            Annex.playlists.get(
                                    "Favorites"
                            );
                }

                refreshPlaylists();
                refreshSongs(musicBox);
                e.consume();
            });
            card.getChildren().add(
                    deleteButton
            );
        }

        // CLICK PLAYLIST
        card.setOnMouseClicked(e -> {
            selectedPlaylist = playlist;
            refreshPlaylists();
            refreshSongs(Main.musicBox);
        });

        // HOVER
        card.setOnMouseEntered(e -> {
            if (playlist != selectedPlaylist) {
                card.setStyle(
                        "-fx-background-color: #555555;" +
                        "-fx-background-radius: 15px;"
                );
            }
        });

        card.setOnMouseExited(e -> {
            if (playlist != selectedPlaylist) {
                card.setStyle(
                        "-fx-background-color: #444444;" +
                        "-fx-background-radius: 15px;"
                );
            }
        });

        return card;
    }

    private void refreshSongs(
            MusicBox musicBox
    ) {

        songList.getChildren().clear();
        if (selectedPlaylist == null) {
            return;
        }

        List<Song> songs =
                selectedPlaylist.tracks;
        for (int i = 0; i < songs.size(); i++) {
            Song song = songs.get(i);
            HBox row = createSongRow(
                    song,
                    songs,
                    i,
                    musicBox
            );

            songList.getChildren().add(row);
        }
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

        // REMOVE BUTTON
        Label removeButton =
                new Label("X");
        removeButton.setStyle(
                "-fx-text-fill: #ff5c5c;" +
                "-fx-font-size: 20px;" +
                "-fx-font-weight: bold;"
        );

        removeButton.setOnMouseEntered(e -> {
            removeButton.setStyle(
                    "-fx-text-fill: #ff8b8b;" +
                    "-fx-font-size: 20px;" +
                    "-fx-font-weight: bold;"
            );
        });

        removeButton.setOnMouseExited(e -> {
            removeButton.setStyle(
                    "-fx-text-fill: #ff5c5c;" +
                    "-fx-font-size: 20px;" +
                    "-fx-font-weight: bold;"
            );
        });

        // REMOVE SONG
        removeButton.setOnMouseClicked(e -> {
            selectedPlaylist.tracks.remove(song);

            // SAVE PLAYLIST
            selectedPlaylist.savePlaylist(
                    "playlists/" +
                    selectedPlaylist.name +
                    ".txt"
            );

            refreshSongs(musicBox);
            refreshPlaylists();
            System.out.println(
                    "Removed " +
                    song.title +
                    " from " +
                    selectedPlaylist.name
            );

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
                removeButton
        );

        // PLAY SONG
        row.setOnMouseClicked(e -> {
            musicBox.setPlaylist(
                    songs,
                    currentIndex
            );
        });

        // HOVER
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