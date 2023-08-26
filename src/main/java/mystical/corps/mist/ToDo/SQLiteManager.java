package mystical.corps.mist.ToDo;

import com.google.gson.Gson;

import java.sql.*;
import java.util.ArrayList;

public class SQLiteManager {

    private final Connection CONNECTION;
    private final ToDoManager TODO_MANAGER;
    public SQLiteManager(ToDoManager toDoManager) {
        TODO_MANAGER = toDoManager;
        CONNECTION = sqliteConnector();
        if (CONNECTION == null) { System.exit(1); }
    }

    public static Connection sqliteConnector() {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:todo.db");
        } catch (Exception e) { return null; }
    }

    public boolean isDBClosed() {
        try { return CONNECTION.isClosed();
        } catch (SQLException e) { return true; }
    }

    public void addItem(String title, String serializedObject) throws SQLException {
        PreparedStatement preparedStatement = CONNECTION.prepareStatement("INSERT INTO TODO (title, todo) VALUES (?, ?)");
        preparedStatement.setString(1, title);
        preparedStatement.setString(2, serializedObject);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        CONNECTION.close();
    }

    public ArrayList<ToDo> getAllItem() throws SQLException {
        ArrayList<ToDo> todoList = new ArrayList<>();
        Statement statement = CONNECTION.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM TODO");
        while (resultSet.next()) { todoList.add(TODO_MANAGER.convertToObject(resultSet.getString("todo"))); }

        return todoList;
    }

    public ToDo getItem(String title) throws SQLException {
        PreparedStatement preparedStatement = CONNECTION.prepareStatement("SELECT todo FROM TODO WHERE title = ?");
        preparedStatement.setString(1, title);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) { return TODO_MANAGER.convertToObject(resultSet.getString("todo")); }
        else { return null; }
    }

    public void deleteItem(String title) throws SQLException {
        PreparedStatement preparedStatement = CONNECTION.prepareStatement("DELETE FROM todo WHERE title = ?");
        preparedStatement.setString(1, title);
        preparedStatement.executeUpdate();
    }

    public void updateItem(String previousTitle, ToDo newItem) throws SQLException {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement("UPDATE todo SET title = ?, todo = ? WHERE title = ?")) {
            preparedStatement.setString(1, newItem.title());
            preparedStatement.setString(2, new Gson().toJson(newItem));
            preparedStatement.setString(3, previousTitle);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected < 0) {
                System.out.println("No item found to update.");
            }
        }
    }

}
