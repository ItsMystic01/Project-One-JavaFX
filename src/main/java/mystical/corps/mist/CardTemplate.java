package mystical.corps.mist;

import javafx.event.Event;
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
import mystical.corps.mist.ToDo.ToDoMethods;

import java.io.IOException;
import java.sql.SQLException;

public class CardTemplate extends ToDoMethods {

    @FXML
    public VBox mainBox;
    @FXML
    public Label box_title, box_starting_date, box_ending_date, box_priority, box_category;
    @FXML
    public Button editButton, deleteButton;

    public void initialize(ToDo item, ToDoManager toDoManager) {
        box_title.setText(item.title());
        box_starting_date.setText(item.startDate());
        box_ending_date.setText(item.endDate());
        box_priority.setText(item.priority());
        box_category.setText(item.category());

        editButton.setOnMouseClicked(event -> {
            try { loadController(item, event); }
            catch (IOException e) { throw new RuntimeException(e); }
        });

        deleteButton.setOnMouseClicked(event -> {
            try { toDoManager.deleteItem(item.title()); }
            catch (SQLException e) { throw new RuntimeException(e); }

            try { changeScene(this.getClass(), event, "main.fxml"); }
            catch (IOException e) { throw new RuntimeException(e); }
        });

    }

    @Override
    public void loadController(ToDo item, Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("edit_item.fxml"));
        Parent root = loader.load();

        EditItemController editItemController = loader.getController();
        editItemController.initialize(item);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
