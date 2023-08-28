package mystical.corps.mist;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mystical.corps.mist.ToDo.ToDo;
import mystical.corps.mist.ToDo.ToDoManager;
import mystical.corps.mist.ToDo.ToDoMethods;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainController extends ToDoMethods {

    @FXML
    public TextField search_bar;
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public TilePane tilePane;
    @FXML
    private Button view_mode, filter, plus, gear;
    @FXML
    private ContextMenu contextMenu;
    @FXML
    private ListView<String> todolist;

    private boolean view_status;
    private final ToDoManager TODO_MANAGER = new ToDoManager();
    private List<ToDo> todoListUpdated;
    private String mode_filter = "name";

    public void initialize() throws SQLException, IOException {
        ToDoMethods.loadImage(view_mode, filter, plus, gear);
        gear.setDisable(true);

        if(TODO_MANAGER.isDBClosed()) { return; }

        view_status = false;
        todoListUpdated = TODO_MANAGER.getAllToDos();
        changeViewMode();
    }

    @FXML
    public void changeViewMode() throws IOException {
        if(!view_status) {
            scrollPane.setVisible(true);
            todolist.setVisible(false);
            tileView();
            view_status = true;
            return;
        }
        scrollPane.setVisible(false);
        todolist.setVisible(true);
        listView();
        view_status = false;
    }

    @FXML
    public void filterItem(ActionEvent event) throws SQLException, IOException {
        MenuItem clickedItem = (MenuItem) event.getTarget();
        mode_filter = clickedItem.getId();
        getSortedByRequirement();
    }

    @FXML
    public void searchBar() {
        search_bar.setOnKeyTyped(keyEvent -> {
            try { getSortedByRequirement(); }
            catch (SQLException | IOException e) { throw new RuntimeException(e); }
        });
    }

    @FXML
    public void showFilterOptions() { contextMenu.show(filter, Side.BOTTOM, 0, 0); }

    public void listView() {
        todolist.getItems().clear();
        ArrayList<String> todoListTitles = new ArrayList<>();

        for(ToDo todo : todoListUpdated) { todoListTitles.add(todo.title()); }

        todolist.getItems().addAll(todoListTitles);

        todolist.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                String selectedItem = todolist.getSelectionModel().getSelectedItem();
                if(selectedItem == null) { return; }
                try { loadController(TODO_MANAGER.getItem(selectedItem), event); }
                catch (IOException e) { throw new RuntimeException(e); }
            }
        });
    }

    public void tileView() throws IOException {
        tilePane.getChildren().clear();
        tilePane.setHgap(10);
        tilePane.setVgap(10);

        for (ToDo item : todoListUpdated) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("card_template.fxml"));
            VBox card = loader.load();
            CardTemplate cardTemplate = loader.getController();
            cardTemplate.initialize(item, TODO_MANAGER);

            tilePane.getChildren().add(card);

            card.setOnMouseClicked(mouseEvent -> {
                if(mouseEvent.getClickCount() == 2) {
                    try { loadController(item, mouseEvent); }
                    catch (IOException e) { throw new RuntimeException(e); }
                }
            });
        }
    }

    public void getSortedByRequirement() throws SQLException, IOException {
        List<ToDo> sortedByRequirement = TODO_MANAGER.getAllToDos().stream().sorted(ToDoManager.getComparator(mode_filter))
                .filter(todo -> todo.title().contains(search_bar.getText())).toList();
        if(!view_status) {
            List<String> strings = sortedByRequirement.stream().map(ToDo::title).toList();
            todolist.getItems().clear();
            todolist.getItems().addAll(strings);
            todoListUpdated = sortedByRequirement;
            return;
        }

        todoListUpdated = sortedByRequirement;
        tileView();
    }

    public void addItem(ActionEvent event) throws IOException { changeScene(this.getClass(), event, "add_item.fxml"); }

    public void gearItem() {}

    @Override
    public void loadController(ToDo item, Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view_item.fxml"));
        Parent root = loader.load();

        ViewItemController viewItem = loader.getController();
        viewItem.loadDataFromMain(item);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}