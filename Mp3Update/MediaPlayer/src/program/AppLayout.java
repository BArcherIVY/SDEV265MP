package program;

import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class AppLayout {
    private BorderPane root;
    private MiniPlayer miniPlayer;
    public AppLayout(MusicBox musicBox) {
        root = new BorderPane();
        root.setStyle(
                "-fx-background-color: #383838;"
        );

        miniPlayer = new MiniPlayer(musicBox);
        root.setBottom(
                miniPlayer.getView()
        );
    }

    public void setCenter(Parent content) {
        root.setCenter(content);
    }

    public MiniPlayer getMiniPlayer() {
        return miniPlayer;
    }

    public Parent getView() {
        return root;
    }
}