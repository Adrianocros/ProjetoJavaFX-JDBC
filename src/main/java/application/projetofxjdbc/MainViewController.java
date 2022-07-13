package application.projetofxjdbc;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @FXML
    private MenuItem menuItemVendedores;

    @FXML
    private MenuItem menuItemDepartamentos;

    @FXML
    private MenuItem menuItemSobre;

    @FXML
    public void onMenuItemVendedoresAction() {
        System.out.println("onMenuItemSellerAction");
    }

    @FXML
    public void onMenuItemDepartamentosAction() {
        System.out.println("onMenuItemDepartmentAction");
    }

    @FXML
    public void onMenuItemSobreAction() {
        System.out.println("onMenuItemAboutAction");
    }

    @Override
    public void initialize(URL uri, ResourceBundle rb) {
    }
}

