package application.projetofxjdbc;

import application.projetofxjdbc.Util.Alerts;
import application.projetofxjdbc.Util.Utils;
import application.projetofxjdbc.db.DbIntegrityException;
import application.projetofxjdbc.listeners.DataChangeListener;
import application.projetofxjdbc.model.entities.Vendedor;
import application.projetofxjdbc.model.services.DepartmentService;
import application.projetofxjdbc.model.services.VendedorService;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class VendedorListController implements Initializable, DataChangeListener {

    private VendedorService service;


    //Referencia para a tela do Lista de Depto
    @FXML
    private TableView<Vendedor> tableViewVendedor;

    @FXML
    private TableColumn<Vendedor, Integer> tableColumnId;

    @FXML
    private TableColumn<Vendedor, String> tableColumnNome;

    @FXML
    private TableColumn<Vendedor, String> tableColumnEmail;

    @FXML
    private TableColumn<Vendedor, Date> tableColumnDataNasc;

    @FXML
    private TableColumn<Vendedor, Double> tableColumnSalarioBase;

    @FXML
    private TableColumn<Vendedor, Vendedor> tableColumnEdit;

    @FXML
    private TableColumn<Vendedor, Vendedor> tableColumnRenove;

    @FXML
    private Button btNovo;

    private ObservableList<Vendedor> obsList;


    //Trabamento dos eventos do clik do botão
    @FXML
    public void onBtNovoAction(ActionEvent event){
        Stage parentState = Utils.currentStage(event);
        Vendedor obj = new Vendedor();
        createdDialogForm(obj,"FormularioVendedor.fxml",parentState);
    }

    //Ingetando a dependencia
    public void setVendedorService(VendedorService service){
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
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableColumnDataNasc.setCellValueFactory(new PropertyValueFactory<>("dataNasc"));
        Utils.formatTableColumnDate(tableColumnDataNasc, "dd/MM/yyyy");
        tableColumnSalarioBase.setCellValueFactory(new PropertyValueFactory<>("salarioBase"));
        Utils.formatTableColumnDouble(tableColumnSalarioBase, 2);
    }

    //Acessa o service carrega os dpto e jogo os dto na obsList
    public void updateTableView(){
        if(service == null){
            throw new IllegalStateException("O Serviço esta vazio");
        }
        List<Vendedor> list = service.findAll();
        obsList = FXCollections.observableArrayList(list);//Pega os dados da lista
        tableViewVendedor.setItems(obsList);
        initEditButtons();//acrescenta o botão edit em cada linha da tabela.
        initRemoveButtons();//acrescenta o botão remover em cada linha da tabela.
    }

    //Metodo para o formulario
    private void createdDialogForm(Vendedor obj,String absolutName,Stage parentStage){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absolutName));
            Pane pane = loader.load();

            VendedorFormController controller = loader.getController();
            controller.setVendedor(obj);
            controller.setServices(new VendedorService(), new DepartmentService());
            controller.loadAssociatedObject();//Carrega os dpto
            controller.subscribeDataChangeListener(this);
            controller.updateFormData();

            //Novo stade para sobrepor a tela inicial
            Stage dialogstage = new Stage();
            dialogstage.setTitle("Cadastro de Vendedor");
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
        tableColumnEdit.setCellFactory(param -> new TableCell<Vendedor, Vendedor>() {
            private final Button button = new Button("edit");
            @Override
            protected void updateItem(Vendedor obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(
                        event -> createdDialogForm(
                                obj, "FormularioVendedor.fxml",Utils.currentStage(event)));
            }
        });
    }

    private void initRemoveButtons(){
        tableColumnRenove.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnRenove.setCellFactory(param -> new TableCell<Vendedor, Vendedor>(){
            private final Button button = new Button("Remover");

            @Override
            protected void updateItem(Vendedor obj, boolean empty){
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
    private void removeEntity(Vendedor obj) {
        Optional<ButtonType> result = Alerts.showConfirmation("confirmação", "Deseja remover o Vendedor ?");

        //verifica se confima ou não a delação
        if(result.get() == ButtonType.OK){
            if(service == null){
                throw new IllegalStateException("Servço esta null!");
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
