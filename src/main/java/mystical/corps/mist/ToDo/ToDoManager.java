package mystical.corps.mist.ToDo;

import com.google.gson.Gson;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class ToDoManager {

    private final SQLiteManager SQLITE_MANAGER = new SQLiteManager(this);

    public void addItem(String title, String description, String priority, String category, String startingDate, String endDate) throws SQLException {

        ToDo newItem = new ToDo();
        newItem.setTitle(title);
        newItem.setDescription(description);
        newItem.setPriority(priority);
        newItem.setCategory(category);
        newItem.setStartDate(startingDate);
        newItem.setEndDate(endDate);

        String gson = new Gson().toJson(newItem);
        SQLITE_MANAGER.addItem(title, gson);
    }

    public void editItem(String previousTitle,
            String title, String description, String priority,
            String category, String startingDate, String endDate) throws SQLException {

        ToDo newItem = new ToDo();
        newItem.setTitle(title);
        newItem.setDescription(description);
        newItem.setPriority(priority);
        newItem.setCategory(category);
        newItem.setStartDate(startingDate);
        newItem.setEndDate(endDate);

        SQLITE_MANAGER.updateItem(previousTitle, newItem);
    }

    public ToDo convertToObject(String serializedObject) {
        return new Gson().fromJson(serializedObject, ToDo.class);
    }

    public ArrayList<ToDo> getAllToDos() throws SQLException {
        return SQLITE_MANAGER.getAllItem();
    }

    public ToDo getItem(String title) {
        try {
            return SQLITE_MANAGER.getItem(title);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteItem(String title) throws SQLException {
        SQLITE_MANAGER.deleteItem(title);
    }

    public boolean isDBClosed() {
        return SQLITE_MANAGER.isDBClosed();
    }

    public static Comparator<ToDo> getComparator(String methodName) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMM-dd-yyyy");

        return switch (methodName) {
            case "name" -> Comparator.comparing(ToDo::getTitle);
            case "date" -> Comparator.<ToDo, LocalDate>comparing(toDo -> LocalDate.parse(toDo.getStartDate(), dateTimeFormatter)).reversed();
            case "priority" -> Comparator.comparing(ToDo::getPriority);
            case "category" -> Comparator.comparing(ToDo::getCategory);
            default -> throw new IllegalArgumentException("Invalid sorting method: " + methodName);
        };
    }

}
