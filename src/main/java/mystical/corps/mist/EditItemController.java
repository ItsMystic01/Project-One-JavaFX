package mystical.corps.mist;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mystical.corps.mist.ToDo.ToDo;
import mystical.corps.mist.ToDo.ToDoManager;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static mystical.corps.mist.ToDo.ToDoMethods.*;

public class EditItemController {

    @FXML
    private DatePicker startingDate, endingDate;
    @FXML
    private TextField titleBox, description;
    @FXML
    private ChoiceBox<String> priority, category;
    @FXML
    private Button returnToMain;
    private final String[] priorityOptions = { "High", "Medium", "Low"};
    private final String[] categoryOptions = { "Work", "Study", "Personal"};

    private ToDo toDo;
    public void initialize(ToDo toDo) {
        this.toDo = toDo;
        priority.getItems().addAll(priorityOptions);
        category.getItems().addAll(categoryOptions);

        resetAll();

        loadImage(returnToMain);
    }

    @FXML
    public void editItem(ActionEvent event) throws SQLException, IOException {
        ToDoManager toDoManager = new ToDoManager();
        String priorityValue = priority.getValue();
        String categoryValue = category.getValue();

        LocalDate startingDatePicked = startingDate.getValue();
        LocalDate endingDatePicked = endingDate.getValue();
        String startingFormattedDate = startingDatePicked.format(DateTimeFormatter.ofPattern("MMM-dd-yyyy"));
        String endingFormattedDate = endingDatePicked.format(DateTimeFormatter.ofPattern("MMM-dd-yyyy"));

        String titleText = titleBox.getText();
        String descriptionText = description.getText();

        if(checkInputs(priorityValue, categoryValue, titleText, descriptionText)) {
            Image image = new Image("icon.png");
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(48);
            imageView.setFitWidth(48);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Missing Information - Input Fields");
            alert.setHeaderText(null);
            alert.setContentText("Make sure to fill up every fields to create an item.");

            alert.setGraphic(imageView);
            alert.showAndWait();
            return;
        }

        toDoManager.editItem(toDo.title(), titleText, descriptionText, priorityValue, categoryValue, startingFormattedDate, endingFormattedDate);
        changeScene(this.getClass(), event, "main.fxml");
    }

    @FXML
    public void resetAll() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-dd-yyyy");

        titleBox.setText(toDo.title());
        description.setText(toDo.description());
        startingDate.setValue(LocalDate.parse(toDo.startDate(), formatter));
        endingDate.setValue(LocalDate.parse(toDo.endDate(), formatter));
        priority.setValue(toDo.priority());
        category.setValue(toDo.category());
    }

    @FXML
    public void returnToMain(ActionEvent event) throws IOException {
        changeScene(this.getClass(), event, "main.fxml");
    }

    public boolean checkInputs(String... strings) {
        boolean finalOutcome = false;

        for(String textField : strings) {
            if(textField.isEmpty()) { return true; }
        }

        return finalOutcome;
    }
}
