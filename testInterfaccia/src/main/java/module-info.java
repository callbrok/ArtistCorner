module com.example.testinterfaccia {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires java.desktop;

    opens com.example.testinterfaccia to javafx.fxml;
    exports com.example.testinterfaccia;
    exports com.example.testinterfaccia.graphcontroller;
    opens com.example.testinterfaccia.graphcontroller to javafx.fxml;
}