package mystical.corps.mist;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mystical.corps.mist.ToDo.ToDoManager;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static mystical.corps.mist.ToDo.ToDoMethods.*;

public class AddItemController implements Initializable {

    @FXML
    private DatePicker startingDate, endingDate;
    @FXML
    private TextField titleBox;
    @FXML
    private TextArea description;
    @FXML
    private ChoiceBox<String> priority, category;
    @FXML
    private Button returnToMain;
    private final String[] priorityOptions = { "High", "Medium", "Low"};
    private final String[] categoryOptions = { "Work", "Study", "Personal"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        priority.getItems().addAll(priorityOptions);
        category.getItems().addAll(categoryOptions);

        startingDate.setValue(LocalDate.now());
        endingDate.setValue(LocalDate.now());
        priority.setValue("Priority");
        category.setValue("Category");

        loadImage(returnToMain);
    }

    @FXML
    public void addItem() throws SQLException {
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

        toDoManager.addItem(titleText, descriptionText, priorityValue, categoryValue, startingFormattedDate, endingFormattedDate);
        clearTexts();
    }

    @FXML
    public void resetAll() { clearTexts(); }

    @FXML
    public void returnToMain(ActionEvent event) throws IOException { changeScene(this.getClass(), event, "main.fxml"); }

    public boolean checkInputs(String... strings) {
        boolean finalOutcome = false;

        for(String textField : strings) {
            if(textField.isEmpty() || priority.getValue().equals("Priority") || category.getValue().equals("Category")) { return true; }
        }

        return finalOutcome;
    }

    public void clearTexts() {
        titleBox.clear();
        description.clear();
        priority.setValue("Priority");
        category.setValue("Category");
        startingDate.setValue(LocalDate.now());
        endingDate.setValue(LocalDate.now());
    }
}
