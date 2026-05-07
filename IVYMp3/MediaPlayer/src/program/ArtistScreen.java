package program;

import fileControl.Album;
import fileControl.Annex;
import fileControl.Song;
import fileControl.Playlist;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.Tag;

public class ArtistScreen {

    private BorderPane root;
    private VBox songList;
    private HBox albumRow;
    private MusicBox musicBox;
    private Album selectedAlbum;

    public ArtistScreen(MusicBox musicBox) {
        this.musicBox = musicBox;
        root = new BorderPane();
        root.setStyle(
                "-fx-background-color: #383838;"
        );
        
        VBox content = new VBox(25);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.TOP_CENTER);
        Label title = new Label("Music Library");
        title.setStyle(
                "-fx-text-fill: white;" +
                "-fx-font-size: 28px;" +
                "-fx-font-weight: bold;"
        );

        Label albumCount = new Label(
                "Albums Found: " +
                Annex.albumMap.size()
        );

        albumCount.setStyle(
                "-fx-text-fill: white;" +
                "-fx-font-size: 16px;"
        );

        VBox header = new VBox(8);
        header.setAlignment(Pos.CENTER);
        header.getChildren().addAll(
                title,
                albumCount
        );

        albumRow = new HBox(40);
        albumRow.setAlignment(Pos.CENTER);
        songList = new VBox(10);
        if (!Annex.albumMap.isEmpty()) {
            selectedAlbum =
                    Annex.albumMap.values()
                    .iterator()
                    .next();
        }

        refreshAlbums();
        refreshSongs();
        content.getChildren().addAll(
                header,
                albumRow,
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

    private void refreshAlbums() {
        albumRow.getChildren().clear();
        List<Album> albums =
                new ArrayList<>(
                        Annex.albumMap.values()
                );

        albums.remove(selectedAlbum);
        int middle =
                albums.size() / 2;
        albums.add(middle, selectedAlbum);
        for (Album album : albums) {
            albumRow.getChildren().add(
                    createAlbumCard(album)
            );
        }
    }

    private void refreshSongs() {
        songList.getChildren().clear();
        if (selectedAlbum == null) {
            return;
        }

        List<Song> songs =
                selectedAlbum.tracks;
        for (int i = 0; i < songs.size(); i++) {
            Song song = songs.get(i);
            HBox row = createSongRow(
                    song,
                    songs,
                    i
            );

            songList.getChildren().add(row);
        }
    }

    private VBox createAlbumCard(Album album) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        try {
            Song firstSong =
                    album.tracks.get(0);
            File mp3File =
                    new File(firstSong.path);
            AudioFile audioFile =
                    AudioFileIO.read(mp3File);
            Tag tag = audioFile.getTag();
            if (
                    tag != null &&
                    tag.getFirstArtwork() != null
            ) {

                byte[] imageData =
                        tag.getFirstArtwork()
                        .getBinaryData();
                Image image = new Image(
                        new ByteArrayInputStream(
                                imageData
                        )
                );

                ImageView artwork =
                        new ImageView(image);
                artwork.setFitWidth(220);
                artwork.setFitHeight(220);
                artwork.setPreserveRatio(true);
                if (album == selectedAlbum) {
                    artwork.setStyle(
                            "-fx-effect: dropshadow(gaussian, #FD4CAD, 30, 0.5, 0, 0);"
                    );
                }

                Label title =
                        new Label(album.name);
                title.setStyle(
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 20px;" +
                        "-fx-font-weight: bold;"
                );

                card.getChildren().addAll(
                        artwork,
                        title
                );

            } else {

                Rectangle fallback =
                        new Rectangle(220, 220);
                fallback.setFill(
                        Color.BLACK
                );

                // SELECTED GLOW
                if (album == selectedAlbum) {
                    fallback.setStyle(
                            "-fx-effect: dropshadow(gaussian, #FD4CAD, 30, 0.5, 0, 0);"
                    );
                }

                Label unknownAlbum =
                        new Label(album.name);
                unknownAlbum.setStyle(
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 20px;" +
                        "-fx-font-weight: bold;"
                );

                card.getChildren().addAll(
                        fallback,
                        unknownAlbum
                );
            }

        } catch (Exception e) {
            Rectangle fallback =
                    new Rectangle(220, 220);
            fallback.setFill(
                    Color.BLACK
            );

            // SELECTED GLOW
            if (album == selectedAlbum) {
                fallback.setStyle(
                        "-fx-effect: dropshadow(gaussian, #FD4CAD, 30, 0.5, 0, 0);"
                );
            }

            Label unknownAlbum =
                    new Label(album.name);
            unknownAlbum.setStyle(
                    "-fx-text-fill: white;" +
                    "-fx-font-size: 20px;" +
                    "-fx-font-weight: bold;"
            );

            card.getChildren().addAll(
                    fallback,
                    unknownAlbum
            );
        }

        card.setOnMouseClicked(e -> {
            selectedAlbum = album;
            refreshAlbums();
            refreshSongs();
        });

        return card;
    }

    private HBox createSongRow(
            Song song,
            List<Song> songs,
            int currentIndex
    ) {

        Label songTitle =
                new Label(song.title);
        songTitle.setStyle(
                "-fx-text-fill: white;"
        );

        Label albumName =
                new Label(song.albumName);
        albumName.setStyle(
                "-fx-text-fill: white;"
        );

        Label duration =
                new Label("-");
        duration.setStyle(
                "-fx-text-fill: white;"
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

        // FIXED WIDTH COLUMNS
        VBox songColumn =
                new VBox(songTitle);
        songColumn.setPrefWidth(350);
        VBox albumColumn =
                new VBox(albumName);
        albumColumn.setPrefWidth(300);
        VBox durationColumn =
                new VBox(duration);
        durationColumn.setPrefWidth(80);
        HBox row = new HBox(30);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(12));
        row.setStyle(
                "-fx-background-color: #444444;" +
                "-fx-background-radius: 10px;"
        );

        row.getChildren().addAll(
                songColumn,
                albumColumn,
                durationColumn,
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