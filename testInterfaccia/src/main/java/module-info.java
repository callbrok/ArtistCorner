module com.example.testinterfaccia {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;

    opens com.example.testinterfaccia to javafx.fxml;
    exports com.example.testinterfaccia;
}