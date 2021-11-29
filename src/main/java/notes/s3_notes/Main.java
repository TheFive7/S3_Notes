package notes.s3_notes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(new Menu(), 800, 800);
        stage.setTitle("Notes S3");
        stage.setScene(scene);
        stage.show();
    }

    /*
    TODO
    - Avoir plusieurs utilisateurs, chacun son fichier de sauvegarde et ses notes
    */



    public static void main(String[] args) {
        launch();
    }
}