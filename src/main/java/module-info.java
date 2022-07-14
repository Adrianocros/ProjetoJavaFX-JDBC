module application.projetofxjdbc {
    requires javafx.controls;
    requires javafx.fxml;


    opens application.projetofxjdbc to javafx.fxml;
    exports application.projetofxjdbc;
    exports application.projetofxjdbc.model.services;
    opens application.projetofxjdbc.model.services to javafx.fxml;
    exports application.projetofxjdbc.model.entities;
    opens application.projetofxjdbc.model.entities to javafx.fxml;
}