package application.projetofxjdbc;

import application.projetofxjdbc.Util.Alerts;
import application.projetofxjdbc.Util.Utils;
import application.projetofxjdbc.db.DbIntegrityException;
import application.projetofxjdbc.listeners.DataChangeListener;
import application.projetofxjdbc.model.entities.Departamento;
import application.projetofxjdbc.model.services.DepartmentService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class DepartamentoListController implements Initializable, DataChangeListener {

    private DepartmentService service;


    //Referencia para a tela do Lista de Depto
    @FXML
    private TableView<Departamento> tableViewDepartamento;

    @FXML
    private TableColumn<Departamento, Integer> tableColumnId;

    @FXML
    private TableColumn<Departamento, String> tableColumnNome;

    @FXML
    private TableColumn<Departamento, Departamento> tableColumnEdit;

    @FXML
    private TableColumn<Departamento, Departamento> tableColumnRenove;

    @FXML
    private Button btNovo;

    private ObservableList<Departamento> obsList;


    //Trabamento dos eventos do clik do botão
    @FXML
    public void onBtNovoAction(ActionEvent event){
        Stage parentState = Utils.currentStage(event);
        Departamento obj = new Departamento();
        createdDialogForm(obj,"FormularioDepartamento.fxml",parentState);
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
        initEditButtons();//acrescenta o botão edit em cada linha da tabela.
        initRemoveButtons();//acrescenta o botão remover em cada linha da tabela.
    }

    //Metodo para o formulario
    private void createdDialogForm(Departamento obj,String absolutName,Stage parentStage){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absolutName));
            Pane pane = loader.load();

            DepartamentoFormController controller = loader.getController();
            controller.setDepartamento(obj);
            controller.setDepartamentService(new DepartmentService());
            controller.subscribeDataChangeListener(this);
            controller.updateFormData();


            //Novo stade para sobrepor a tela inicial
            Stage dialogstage = new Stage();
            dialogstage.setTitle("Cadastro de Departamento");
            dialogstage.setScene(new Scene(pane));
            dialogstage.setResizable(false);//Janela nao redimenciona
            dialogstage.initOwner(parentStage);//Stage Pai da janela
            dialogstage.initModality(Modality.WINDOW_MODAL);//Janela modal ou não
            dialogstage.showAndWait();

        }catch (IOException e){
            e.printStackTrace();
            Alerts.showAlert("IOException", "Erro ao carregar a View", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    //Atualiza os dados da tabela
    @Override
    public void onDataChanged() {
        updateTableView();
    }

    private void initEditButtons() {
        tableColumnEdit.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnEdit.setCellFactory(param -> new TableCell<Departamento, Departamento>() {
            private final Button button = new Button("edit");
            @Override
            protected void updateItem(Departamento obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(
                        event -> createdDialogForm(
                                obj, "FormularioDepartamento.fxml",Utils.currentStage(event)));
            }
        });
    }

    private void initRemoveButtons(){
        tableColumnRenove.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnRenove.setCellFactory(param -> new TableCell<Departamento, Departamento>(){
            private final Button button = new Button("Remover");

            @Override
            protected void updateItem(Departamento obj, boolean empty){
                super.updateItem(obj, empty);
                if(obj == null){
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> removeEntity(obj));
            }
        });
    }

    //removendo o depto
    private void removeEntity(Departamento obj) {
        Optional<ButtonType> result = Alerts.showConfirmation("confirmação", "Deseja remover o departamento ?");

        //verifica se confima ou não a delação
        if(result.get() == ButtonType.OK){
            if(service == null){
                throw new IllegalStateException("Servçoa esta null!");
            }
            try {
                service.remove(obj);
                updateTableView();
            }catch (DbIntegrityException e){
                Alerts.showAlert("Erro ao remover", null,e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

}
