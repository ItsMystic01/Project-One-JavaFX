package mystical.corps.mist;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mystical.corps.mist.ToDo.ToDo;
import mystical.corps.mist.ToDo.ToDoManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class MainController {

    public TextField search_bar;
    @FXML
    private Button filter, plus, gear;
    @FXML
    private ContextMenu contextMenu;

    private final ToDoManager TODO_MANAGER = new ToDoManager();

    @FXML
    private ListView<String> todolist;

    public void addItem(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("add_item.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void gearItem(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("card_view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public void initialize() throws SQLException {

        Image menu = new Image("menu.png");
        ImageView menuView = new ImageView(menu);
        menuView.setFitWidth(32);
        menuView.setFitHeight(32);
        filter.setGraphic(menuView);

        Image plusImage = new Image("plus.png");
        ImageView plusView = new ImageView(plusImage);
        plusView.setFitWidth(32);
        plusView.setFitHeight(32);
        plus.setGraphic(plusView);

        Image gearImage = new Image("gear.png");
        ImageView gearView = new ImageView(gearImage);
        gearView.setFitWidth(32);
        gearView.setFitHeight(32);
        gear.setGraphic(gearView);

        if(TODO_MANAGER.isDBClosed()) { return; }

        ArrayList<String> todoListTitles = new ArrayList<>();

        ArrayList<ToDo> todoList = TODO_MANAGER.getAllToDos();

        for(ToDo todo : todoList) {
            todoListTitles.add(todo.getTitle());
        }

        todolist.getItems().addAll(todoListTitles);

        todolist.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                String selectedItem = todolist.getSelectionModel().getSelectedItem();
                if(selectedItem == null) { return; }
                try {
                    viewItem(TODO_MANAGER.getItem(selectedItem), event);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void searchBar() {
        search_bar.setOnKeyTyped(keyEvent -> {
            try {
                todolist.getItems().removeAll(TODO_MANAGER.getAllToDos().stream().toList().stream().map(ToDo::getTitle).toList());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            String searchedText = search_bar.getText();
            List<ToDo> sortedByTitle;
            try {
                sortedByTitle = TODO_MANAGER.getAllToDos().stream().sorted(Comparator.comparing(ToDo::getTitle)).toList();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            List<String> strings = new ArrayList<>(sortedByTitle.stream().map(ToDo::getTitle).toList());

            strings.removeIf(item -> !item.contains(searchedText));

            todolist.getItems().addAll(strings);
        });
    }

    private void viewItem(ToDo toDo, MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view_item.fxml"));
        Parent root = loader.load();

        ViewItem viewItem = loader.getController();
        viewItem.loadDataFromMain(toDo);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void filterItems() {
        contextMenu.show(filter, Side.BOTTOM, 0, 0);
    }

    public void byName() throws SQLException {
        if(search_bar.getLength() == 0) {
            List<ToDo> sortedByTitle = TODO_MANAGER.getAllToDos().stream().sorted(Comparator.comparing(ToDo::getTitle)).toList();
            List<String> strings = sortedByTitle.stream().map(ToDo::getTitle).toList();

            todolist.getItems().removeAll(strings);
            todolist.getItems().addAll(strings);
        } else {
            List<ToDo> sortedByTitle = TODO_MANAGER.getAllToDos().stream().sorted(Comparator.comparing(ToDo::getTitle)).toList();
            List<String> strings = new ArrayList<>(sortedByTitle.stream().map(ToDo::getTitle).toList());

            todolist.getItems().removeAll(strings);

            strings.removeIf(item -> !item.contains(search_bar.getText()));

            todolist.getItems().addAll(strings);
        }
    }

    public void byDate() throws SQLException {
        if(search_bar.getLength() == 0) {
            List<ToDo> sortedByDate = TODO_MANAGER.getAllToDos().stream().sorted(Comparator.comparing(ToDo::getStartDate)).toList();
            List<String> strings = sortedByDate.stream().map(ToDo::getTitle).toList();

            todolist.getItems().removeAll(strings);
            todolist.getItems().addAll(strings);
        } else {
            List<ToDo> sortedByDate = TODO_MANAGER.getAllToDos().stream().sorted(Comparator.comparing(ToDo::getStartDate)).toList();
            List<String> strings = new ArrayList<>(sortedByDate.stream().map(ToDo::getTitle).toList());

            todolist.getItems().removeAll(strings);

            strings.removeIf(item -> !item.contains(search_bar.getText()));

            todolist.getItems().addAll(strings);
        }
    }

    public void byPriority() throws SQLException {
        if(search_bar.getLength() == 0) {
            List<ToDo> sortedByPriority = TODO_MANAGER.getAllToDos().stream().sorted(Comparator.comparing(ToDo::getPriority)).toList();
            List<String> strings = sortedByPriority.stream().map(ToDo::getTitle).toList();

            todolist.getItems().removeAll(strings);
            todolist.getItems().addAll(strings);
        } else {
            List<ToDo> sortedByPriority = TODO_MANAGER.getAllToDos().stream().sorted(Comparator.comparing(ToDo::getPriority)).toList();
            List<String> strings = new ArrayList<>(sortedByPriority.stream().map(ToDo::getTitle).toList());

            todolist.getItems().removeAll(strings);

            strings.removeIf(item -> !item.contains(search_bar.getText()));

            todolist.getItems().addAll(strings);
        }
    }

    public void byCategory() throws SQLException {
        if(search_bar.getLength() == 0) {
            List<ToDo> sortedByCategory = TODO_MANAGER.getAllToDos().stream().sorted(Comparator.comparing(ToDo::getCategory)).toList();
            List<String> strings = sortedByCategory.stream().map(ToDo::getTitle).toList();

            todolist.getItems().removeAll(strings);
            todolist.getItems().addAll(strings);
        } else {

            List<ToDo> sortedByCategory = TODO_MANAGER.getAllToDos().stream().sorted(Comparator.comparing(ToDo::getCategory)).toList();
            List<String> strings = new ArrayList<>(sortedByCategory.stream().map(ToDo::getTitle).toList());

            todolist.getItems().removeAll(strings);

            strings.removeIf(item -> !item.contains(search_bar.getText()));

            todolist.getItems().addAll(strings);
        }
    }
}