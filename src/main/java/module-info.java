module com.example.kursov_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.kursov_project to javafx.fxml;
    exports com.example.kursov_project;
}