package application.projetofxjdbc;

import application.projetofxjdbc.Util.Alerts;
import application.projetofxjdbc.Util.Utils;
import application.projetofxjdbc.model.entities.Departamento;
import application.projetofxjdbc.model.services.DepartmentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DepartamentoListController implements Initializable {

    private DepartmentService service;

    //Referencia para a tela do Lista de Depto
    @FXML
    private TableView<Departamento> tableViewDepartamento;

    @FXML
    private TableColumn<Departamento, Integer> tableColumnId;

    @FXML
    private TableColumn<Departamento, String> tableColumnNome;

    @FXML
    private Button btNovo;

    private ObservableList<Departamento> obsList;


    //Trabamento dos eventos do clik do botão
    @FXML
    public void onBtNovoAction(ActionEvent event){
        Stage parentState = Utils.currentStage(event);
        createdDialogForm("FormularioDepartamento.fxml",parentState);
    }

    //Ingetando a dependencia
    public void setDepartamentoService(DepartmentService service){
        this.service = service;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
        
    }

    //Iniciar o comportamento das colunas da tabela
    private void initializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

    }

    //Acessa o service carrega os dpto e jogo os dto na obsList
    public void updateTableView(){
        if(service == null){
            throw new IllegalStateException("O Serviço esta vazio");
        }
        List<Departamento> list = service.findAll();
        obsList = FXCollections.observableArrayList(list);//Pega os dados da lista
        tableViewDepartamento.setItems(obsList);
    }

    //Metodo para o formulario
    private void createdDialogForm(String absolutName,Stage parentStage){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absolutName));
            Pane pane = loader.load();
            //Novo stade para sobrepor a tela inicial
            Stage dialogstage = new Stage();
            dialogstage.setTitle("Cadastro de Departamento");
            dialogstage.setScene(new Scene(pane));
            dialogstage.setResizable(false);//Janela nao redimenciona
            dialogstage.initOwner(parentStage);//Stage Pai da janela
            dialogstage.initModality(Modality.WINDOW_MODAL);//Janela modal ou não
            dialogstage.showAndWait();

        }catch (IOException e){
            Alerts.showAlert("IOException", "Erro ao carregar a View", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
