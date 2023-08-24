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
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isDBClosed() {
        try { return CONNECTION.isClosed();
        } catch (SQLException e) { return true; }
    }

//    Managing Contents in the database

    public void addItem(String title, String serializedObject) throws SQLException {
        String insertQuery = "INSERT INTO TODO (title, todo) VALUES (?, ?)";
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(insertQuery);

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

        while (resultSet.next()) {
            todoList.add(TODO_MANAGER.convertToObject(resultSet.getString("todo")));
        }

        return todoList;
    }

    public ToDo getItem(String title) throws SQLException {
        Statement statement = CONNECTION.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT todo FROM TODO WHERE title = '" + title + "'");

        if(resultSet.next()) {
            return TODO_MANAGER.convertToObject(resultSet.getString("todo"));
        } else {
            return null;
        }
    }

}
