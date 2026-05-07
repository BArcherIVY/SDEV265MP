package program;

import fileControl.Song;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class MiniPlayer {
    private BorderPane root;
    private Label songTitle;
    private Label currentTimeLabel;
    private Label totalTimeLabel;
    private Button playPauseButton;
    private Slider progressBar;
    private Slider volumeSlider;
    private boolean playing = true;
    public MiniPlayer(MusicBox musicBox) {

        root = new BorderPane();
        root.setPadding(new Insets(12));
        root.setStyle(
                "-fx-background-color: #2b2b2b;"
        );

        // song title
        songTitle = new Label("No Song Playing");
        songTitle.setStyle(
                "-fx-text-fill: white;" +
                "-fx-font-size: 15px;" +
                "-fx-font-weight: bold;"
        );

        // song time
        currentTimeLabel = new Label("0:00");
        totalTimeLabel = new Label("0:00");
        currentTimeLabel.setStyle(
                "-fx-text-fill: white;"
        );

        totalTimeLabel.setStyle(
                "-fx-text-fill: white;"
        );

        // progress bar
        progressBar = new Slider();
        progressBar.setPrefWidth(850);
        progressBar.setStyle(
                "-fx-accent: #FD4CAD;"
        );

        // progress section
        HBox progressSection = new HBox(10);
        progressSection.setAlignment(Pos.CENTER);
        progressSection.getChildren().addAll(
                currentTimeLabel,
                progressBar,
                totalTimeLabel
        );

        // volume
        volumeSlider = new Slider(
                0,
                1,
                musicBox.getVolume()
        );

        volumeSlider.setOrientation(
                Orientation.VERTICAL
        );

        volumeSlider.setPrefHeight(90);
        volumeSlider.setStyle(
                "-fx-accent: #5E4892;"
        );

        volumeSlider.valueProperty().addListener(
                (obs, oldVal, newVal) -> {
            musicBox.setVolume(
                    newVal.doubleValue()
            );
        });

      
        Label volumeIcon = new Label("🔊");
        volumeIcon.setStyle(
                "-fx-text-fill: white;" +
                "-fx-font-size: 15px;"
        );

        VBox volumeBox = new VBox(5);
        volumeBox.setAlignment(Pos.CENTER);
        volumeBox.getChildren().addAll(
                volumeSlider,
                volumeIcon
        );

        // buttons
        Button previousButton = new Button("⏮");
        previousButton.setOnAction(e -> {
            musicBox.previousSong();
        });

        playPauseButton = new Button("⏸");
        Button nextButton = new Button("⏭");
        nextButton.setOnAction(e -> {

            musicBox.nextSong();
        });

        addHoverEffect(
                previousButton,
                "#FD4CAD",
                "#d93b93",
                22,
                80,
                80
        );

        addHoverEffect(
                nextButton,
                "#FD4CAD",
                "#d93b93",
                22,
                80,
                80
        );

        addHoverEffect(
                playPauseButton,
                "#5E4892",
                "#493774",
                28,
                100,
                100
        );

        // play and pause
        playPauseButton.setOnAction(e -> {
            if (playing) {
                musicBox.pauseMp3();
                playPauseButton.setText("▶");
                playing = false;
            } else {
                musicBox.resumeMp3();
                playPauseButton.setText("⏸");
                playing = true;
            }
        });

        // controls
        HBox controls = new HBox(20);
        controls.setAlignment(Pos.CENTER);
        controls.getChildren().addAll(
                previousButton,
                playPauseButton,
                nextButton,
                volumeBox
        );

        // Song selection
        HBox songSection = new HBox();
        songSection.setAlignment(Pos.CENTER_LEFT);
        songSection.setPrefWidth(350);
        songSection.getChildren().add(songTitle);

       
        HBox centerControls = new HBox();
        centerControls.setAlignment(Pos.CENTER);
        centerControls.getChildren().add(controls);

        
        Region rightSpacer = new Region();
        rightSpacer.setPrefWidth(350);

       
        BorderPane bottomRow = new BorderPane();
        bottomRow.setPadding(
                new Insets(10, 0, 0, 0)
        );

        bottomRow.setLeft(songSection);
        bottomRow.setCenter(centerControls);
        bottomRow.setRight(rightSpacer);

        // main layout
        VBox layout = new VBox(8);
        layout.getChildren().addAll(
                progressSection,
                bottomRow
        );

        root.setCenter(layout);
        
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(500), e -> {
                    MediaPlayer player =
                            musicBox.getMediaPlayer();
                    if (player != null) {
                        double currentSeconds =
                                player.getCurrentTime()
                                .toSeconds();
                        double totalSeconds =
                                player.getTotalDuration()
                                .toSeconds();
                        if (!progressBar.isValueChanging()) {
                            progressBar.setValue(
                                    currentSeconds
                            );
                        }
                        progressBar.setMax(
                                totalSeconds
                        );
                        currentTimeLabel.setText(
                                formatTime(currentSeconds)
                        );
                        totalTimeLabel.setText(
                                formatTime(totalSeconds)
                        );
                    }
                })
        );
        timeline.setCycleCount(
                Timeline.INDEFINITE
        );
        timeline.play();

        // Drag seek
        progressBar.valueChangingProperty()
        .addListener((obs, wasChanging, changing) -> {
            if (!changing) {

                musicBox.seek(
                        progressBar.getValue()
                );
            }
        });

        // click seek
        progressBar.setOnMousePressed(e -> {
            double mouseX = e.getX();
            double width = progressBar.getWidth();
            double percent = mouseX / width;
            double seekTime =
                    progressBar.getMax() * percent;
            progressBar.setValue(seekTime);

            musicBox.seek(seekTime);
        });
    }

    public void setSong(Song song) {
        songTitle.setText(song.title);
        progressBar.setValue(0);
        currentTimeLabel.setText("0:00");
    }

    private String formatTime(double seconds) {
        int mins = (int) seconds / 60;
        int secs = (int) seconds % 60;
        return String.format(
                "%d:%02d",
                mins,
                secs
        );
    }

    private void addHoverEffect(
            Button button,
            String normalColor,
            String hoverColor,
            int fontSize,
            int width,
            int height
    ) {

        button.setStyle(
                "-fx-background-color: " + normalColor + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-size: " + fontSize + "px;" +
                "-fx-background-radius: 100px;" +
                "-fx-min-width: " + width + "px;" +
                "-fx-min-height: " + height + "px;"
        );

        button.setOnMouseEntered(e -> {

            button.setStyle(
                    "-fx-background-color: " + hoverColor + ";" +
                    "-fx-text-fill: white;" +
                    "-fx-font-size: " + fontSize + "px;" +
                    "-fx-background-radius: 100px;" +
                    "-fx-min-width: " + width + "px;" +
                    "-fx-min-height: " + height + "px;"
            );
        });

        button.setOnMouseExited(e -> {

            button.setStyle(
                    "-fx-background-color: " + normalColor + ";" +
                    "-fx-text-fill: white;" +
                    "-fx-font-size: " + fontSize + "px;" +
                    "-fx-background-radius: 100px;" +
                    "-fx-min-width: " + width + "px;" +
                    "-fx-min-height: " + height + "px;"
            );
        });
    }

    public Parent getView() {
        return root;
    }
}