module mystical.corps.mist {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.xerial.sqlitejdbc;


    opens mystical.corps.mist to javafx.fxml;
    opens mystical.corps.mist.ToDo;
    exports mystical.corps.mist.ToDo;
    exports mystical.corps.mist;
}