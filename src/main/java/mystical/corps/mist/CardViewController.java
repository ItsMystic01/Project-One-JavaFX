package mystical.corps.mist;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mystical.corps.mist.ToDo.ToDo;
import mystical.corps.mist.ToDo.ToDoManager;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CardViewController implements Initializable {

    @FXML
    public TilePane tilePane;

    @FXML
    public ScrollPane scrollPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ToDoManager toDoManager = new ToDoManager();


        ArrayList<ToDo> todoList;
        try {
            todoList = toDoManager.getAllToDos();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        tilePane.setHgap(10);
        tilePane.setVgap(10);

        for (ToDo item : todoList) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("card_template.fxml"));
            VBox card;

            try {
                card = loader.load();

                CardTemplate cardTemplate = loader.getController();
                cardTemplate.initialize(item);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            card.setOnMouseClicked(mouseEvent -> { onSelectPane(item); });
            tilePane.getChildren().add(card);
        }

        Insets insets = new Insets(10);
        scrollPane.setPadding(insets);
    }

    public void onSelectPane(ToDo clickedTodo) {
        System.out.println("Yow, card " + clickedTodo.getTitle() + " was clicked");
    }


}
