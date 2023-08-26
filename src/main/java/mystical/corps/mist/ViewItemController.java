package mystical.corps.mist;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import mystical.corps.mist.ToDo.ToDo;

import java.io.IOException;

import static mystical.corps.mist.ToDo.ToDoMethods.loadImage;
import static mystical.corps.mist.ToDo.ToDoMethods.returnToMainPage;

public class ViewItemController {

    public Button returnToMain;
    public ScrollPane descriptionBox;
    public Label title, startingDate, endingDate, priority, category;
    public TextArea descriptionContent;

    public void loadDataFromMain(ToDo todo) {
        loadImage(returnToMain);

        title.setText(todo.getTitle());
        startingDate.setText(todo.getStartDate());
        endingDate.setText(todo.getEndDate());
        priority.setText(todo.getPriority());
        category.setText(todo.getCategory());
        descriptionContent.setText(todo.getDescription());
    }

    public void returnToMain(ActionEvent event) throws IOException {
        returnToMainPage(this.getClass(), event);
    }

}
