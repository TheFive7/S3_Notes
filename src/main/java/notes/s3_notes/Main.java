package notes.s3_notes;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        Scene scene = new Scene(new Menu(), 800, 800);
        stage.setTitle("Notes S3");
        stage.setScene(scene);
        stage.show();
    }

    /*
    TODO
    - Avoir plusieurs utilisateurs, chacun son fichier de sauvegarde et ses notes
    */

    public static void main(String[] args) {launch();}
}