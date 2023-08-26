package mystical.corps.mist;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) { launch(); }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Image icon = new Image("icon.png");
        stage.getIcons().add(icon);

        stage.setTitle("Mist | To-Do");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

}