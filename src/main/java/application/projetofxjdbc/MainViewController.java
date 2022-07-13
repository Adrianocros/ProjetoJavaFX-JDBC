package application.projetofxjdbc;

import application.projetofxjdbc.Util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
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
        loadView("Sobre.fxml");
    }

    @Override
    public void initialize(URL uri, ResourceBundle rb) {
    }
    // synchronized - Para nao interromper a aplicação
    private synchronized void loadView(String absolutName){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absolutName));
            VBox newVbox = loader.load();

            Scene mainScene = Main.getMainScene();
            VBox mainVBox = (VBox)((ScrollPane) mainScene.getRoot()).getContent();//referencia para o VBox



            //Preservar o menuBar
            Node mainMenu = mainVBox.getChildren().get(0);//Primeiro filho da janela VBox
            mainVBox.getChildren().clear();
            mainVBox.getChildren().add(mainMenu);
            mainVBox.getChildren().addAll(newVbox.getChildren());

        } catch (IOException e) {
            Alerts.showAlert("Encontrado um erro !","Erro ao carregar a pagina",e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}

