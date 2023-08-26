package mystical.corps.mist.ToDo;

import com.google.gson.Gson;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

public class ToDoManager {

    private final SQLiteManager SQLITE_MANAGER = new SQLiteManager(this);

    public void addItem(String title, String description, String priority,
                        String category, String startingDate, String endDate) throws SQLException {
        ToDo newItem = new ToDo(title, description, priority, category, startingDate, endDate);
        String gson = new Gson().toJson(newItem);
        SQLITE_MANAGER.addItem(title, gson);
    }

    public void editItem(String previousTitle, String title, String description, String priority,
                         String category, String startingDate, String endDate) throws SQLException {
        ToDo updatedItem = new ToDo(title, description, priority, category, startingDate, endDate);
        SQLITE_MANAGER.updateItem(previousTitle, updatedItem);
    }

    public ToDo convertToObject(String serializedObject) { return new Gson().fromJson(serializedObject, ToDo.class); }

    public ArrayList<ToDo> getAllToDos() throws SQLException { return SQLITE_MANAGER.getAllItem(); }

    public ToDo getItem(String title) {
        try {
            return SQLITE_MANAGER.getItem(title);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteItem(String title) throws SQLException { SQLITE_MANAGER.deleteItem(title); }

    public boolean isDBClosed() { return SQLITE_MANAGER.isDBClosed(); }

    public static Comparator<ToDo> getComparator(String methodName) {
        return switch (methodName) {
            case "name" -> Comparator.comparing(ToDo::title);
            case "date" -> Comparator.<ToDo, LocalDate>comparing(toDo -> LocalDate.parse(toDo.startDate(),
                    DateTimeFormatter.ofPattern("MMM-dd-yyyy"))).reversed();
            case "priority" -> Comparator.comparing(ToDo::priority);
            case "category" -> Comparator.comparing(ToDo::category);
            default -> throw new IllegalArgumentException("Invalid sorting method: " + methodName);
        };
    }

}
