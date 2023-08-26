package mystical.corps.mist.ToDo;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class ToDoMethods {

    public static void loadImage(Button... buttons) {
        for(Button button : buttons) {
            Image buttonImg = new Image(button.getId() + ".png");
            ImageView buttonImgView = new ImageView(buttonImg);
            buttonImgView.setFitWidth(32);
            buttonImgView.setFitHeight(32);
            button.setGraphic(buttonImgView);
        }
    }

    public static void returnToMainPage(Class<?> controller, Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(controller.getResource("main.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
