module application.projetofxjdbc {
    requires javafx.controls;
    requires javafx.fxml;


    opens application.projetofxjdbc to javafx.fxml;
    exports application.projetofxjdbc;
}