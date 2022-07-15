module application.projetofxjdbc {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens application.projetofxjdbc to javafx.fxml;
    exports application.projetofxjdbc;
    exports application.projetofxjdbc.model.services;
    opens application.projetofxjdbc.model.services to javafx.fxml;
    exports application.projetofxjdbc.model.entities;
    opens application.projetofxjdbc.model.entities to javafx.fxml;
    exports application.projetofxjdbc.listeners;
    opens application.projetofxjdbc.listeners to javafx.fxml;
}