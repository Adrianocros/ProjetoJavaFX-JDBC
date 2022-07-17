package application.projetofxjdbc;

import application.projetofxjdbc.Util.Alerts;
import application.projetofxjdbc.model.services.DepartmentService;
import application.projetofxjdbc.model.services.VendedorService;
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
import java.util.function.Consumer;

public class MainViewController implements Initializable {

    @FXML
    private MenuItem menuItemVendedores;

    @FXML
    private MenuItem menuItemDepartamentos;

    @FXML
    private MenuItem menuItemSobre;

    @FXML
    public void onMenuItemVendedoresAction() {
        loadView("ListaVendedores.fxml", (VendedorListController controller) -> {
            //Inicialização do controller
            controller.setVendedorService(new VendedorService());
            controller.updateTableView();
        });
    }

    @FXML
    public void onMenuItemDepartamentosAction() {
        loadView("ListaDepartamento.fxml", (DepartamentoListController controller) -> {
            //Inicialização do controller
            controller.setDepartamentoService(new DepartmentService());
            controller.updateTableView();
        });
    }

    @FXML
    public void onMenuItemSobreAction() {
        loadView("Sobre.fxml", x -> {});
    }

    @Override
    public void initialize(URL uri, ResourceBundle rb) {
    }

    // synchronized - Para nao interromper a aplicação
    private synchronized <T> void loadView(String absolutName, Consumer<T> initializinAction){
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

            //Ativando e executa a função Consumer e retorna o controlador que chamou
            T controller = loader.getController();
            initializinAction.accept(controller);

        } catch (IOException e) {
            Alerts.showAlert("Encontrado um erro !","Erro ao carregar a pagina",e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}

