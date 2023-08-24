package mystical.corps.mist;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import mystical.corps.mist.ToDo.ToDo;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewItem  {

    public Button returnToMain;
    public ScrollPane descriptionBox;
    public Label title, startingDate, endingDate, priority, category;
    public TextArea descriptionContent;

    public void loadDataFromMain(ToDo todo) {

        Image returnImage = new Image("return.png");
        ImageView returnImageView = new ImageView(returnImage);
        returnImageView.setFitWidth(32);
        returnImageView.setFitHeight(32);

        returnToMain.setGraphic(returnImageView);

        title.setText(todo.getTitle());
        startingDate.setText(todo.getStartDate());
        endingDate.setText(todo.getEndDate());
        priority.setText(todo.getPriority());
        category.setText(todo.getCategory());

        descriptionContent.setText(todo.getDescription());

    }

    public void returnToMain(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
