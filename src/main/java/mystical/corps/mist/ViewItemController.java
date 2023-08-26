package mystical.corps.mist;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import mystical.corps.mist.ToDo.ToDo;

import java.io.IOException;

import static mystical.corps.mist.ToDo.ToDoMethods.*;

public class ViewItemController {

    @FXML
    public Button returnToMain;
    @FXML
    public ScrollPane descriptionBox;
    @FXML
    public Label title, startingDate, endingDate, priority, category;
    @FXML
    public TextArea descriptionContent;

    public void loadDataFromMain(ToDo todo) {
        loadImage(returnToMain);

        title.setText(todo.title());
        startingDate.setText(todo.startDate());
        endingDate.setText(todo.endDate());
        priority.setText(todo.priority());
        category.setText(todo.category());
        descriptionContent.setText(todo.description());
    }

    @FXML
    public void returnToMain(ActionEvent event) throws IOException { changeScene(this.getClass(), event, "main.fxml"); }

}
