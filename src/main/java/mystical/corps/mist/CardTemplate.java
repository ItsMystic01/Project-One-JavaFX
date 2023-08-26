package mystical.corps.mist;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mystical.corps.mist.ToDo.ToDo;
import mystical.corps.mist.ToDo.ToDoManager;

import java.io.IOException;
import java.sql.SQLException;

import static mystical.corps.mist.ToDo.ToDoMethods.returnToMainPage;

public class CardTemplate {

    @FXML
    public VBox main_box;
    @FXML
    public Label box_title, box_starting_date, box_ending_date, box_priority, box_category;
    public Button editButton, deleteButton;

    public void initialize(ToDo item, ToDoManager toDoManager) {
        box_title.setText(item.getTitle());
        box_starting_date.setText(item.getStartDate());
        box_ending_date.setText(item.getEndDate());
        box_priority.setText(item.getPriority());
        box_category.setText(item.getCategory());

        editButton.setOnMouseClicked(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("edit_item.fxml"));

            try {
                Parent root = loader.load();
                EditItemController editItemController = loader.getController();
                editItemController.initialize(item);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        deleteButton.setOnMouseClicked(event -> {
            try {
                toDoManager.deleteItem(item.getTitle());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                returnToMainPage(this.getClass(), event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }
}
