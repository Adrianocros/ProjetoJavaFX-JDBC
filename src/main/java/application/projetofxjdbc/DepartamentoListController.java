package application.projetofxjdbc;

import application.projetofxjdbc.model.entities.Departamento;
import application.projetofxjdbc.model.services.DepartmentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
    public void onBtNovoAction(){
        System.out.println("Botão novo clicado");
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
}
