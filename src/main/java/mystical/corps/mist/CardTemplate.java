package mystical.corps.mist;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import mystical.corps.mist.ToDo.ToDo;

public class CardTemplate {

    @FXML
    public VBox main_box;
    @FXML
    public Label box_title, box_starting_date, box_ending_date, box_priority, box_category;

    public void initialize(ToDo item) {
        box_title.setText(item.getTitle());
        box_starting_date.setText(item.getStartDate());
        box_ending_date.setText(item.getEndDate());
        box_priority.setText(item.getPriority());
        box_category.setText(item.getCategory());
    }
}
