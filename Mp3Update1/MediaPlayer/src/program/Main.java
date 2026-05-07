package program;

import fileControl.Annex;
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
            String filePath = Annex.ChooseFile();

            if (filePath != null) {
                Annex.RunAnnex(filePath);

                // Main App layout
                appLayout = new AppLayout(musicBox);

                // this is really the only screen right now
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
                
                mainStage.setTitle("MusicBox");
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