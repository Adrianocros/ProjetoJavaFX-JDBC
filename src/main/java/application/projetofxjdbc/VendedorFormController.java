package application.projetofxjdbc;

import application.projetofxjdbc.Util.Alerts;
import application.projetofxjdbc.Util.Constrants;
import application.projetofxjdbc.Util.Utils;
import application.projetofxjdbc.db.DbException;
import application.projetofxjdbc.listeners.DataChangeListener;
import application.projetofxjdbc.model.entities.Departamento;
import application.projetofxjdbc.model.entities.Vendedor;
import application.projetofxjdbc.model.exceptions.ValidationException;
import application.projetofxjdbc.model.services.DepartmentService;
import application.projetofxjdbc.model.services.VendedorService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class VendedorFormController implements Initializable {

    private Vendedor entity;

    private VendedorService service;

    private DepartmentService departmentService;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    //Declaração dos componentes da tela
    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtEmail;

    @FXML
    private DatePicker dpDataNasc;

    @FXML
    private TextField txtSalarioBase;

    @FXML
    private ComboBox<Departamento> comboBoxDepartamento;

    @FXML
    private Label labelErroName;

    @FXML
    private Label labelErroEmail;

    @FXML
    private Label labelErroDataNasc;

    @FXML
    private Label labelErroSalarioBase;

    @FXML
    private Button btSalvar;

    @FXML
    private Button btCancelar;

    private ObservableList<Departamento> obsList;

    //instnacia do depto
    public void setVendedor(Vendedor entity){
        this.entity = entity;
    }

    //Ingeta Departamento e Vendedor
    public void setServices(VendedorService service, DepartmentService DepartmentService){
        this.service = service;
        this.departmentService = departmentService;
    }

    //Escrevena lista
    public void subscribeDataChangeListener(DataChangeListener listener){
        dataChangeListeners.add(listener);
    }

    //Tratamento dos eventos
    @FXML
    public void onBtSaveAction(ActionEvent event){
        if(entity == null){
            throw new IllegalStateException("Valor null na entidade");
        }
        if(service == null){
            throw new IllegalStateException("Vendedor esta nullo");
        }
        try {
            entity = getFormDate();
            service.saveOrUpdate(entity);//savando no banco
            notifiDataChangeListeners();
            Utils.currentStage(event).close();//Fechando a janela apos salvar
        }
        catch (ValidationException e){
            setErrorMessage(e.getErros());
        }
        catch (DbException e){
            e.getMessage();
            Alerts.showAlert("Erro ao salvar!",null,e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    private void notifiDataChangeListeners() {
        for(DataChangeListener listener : dataChangeListeners){
            listener.onDataChanged();
        }
    }


    //Pega os dados do formulario
    private Vendedor getFormDate() {
        Vendedor obj = new Vendedor();

        ValidationException exception = new ValidationException("Erro na validação");

        obj.setId(Utils.tryParseToInt(txtId.getText()));
        if(txtNome.getText() == null || txtNome.getText().trim().equals("")){
            exception.addErro("Nome","O campo não pode ser vazio!");
        }
        obj.setNome(txtNome.getText());

        if(exception.getErros().size() > 0){
         throw exception;
        }
        return  obj;
    }

    @FXML
    public void onBtCancelarAction(ActionEvent event){
        Utils.currentStage(event).close();//Fecha janela
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializacaoNodes();
    }

    private void  inicializacaoNodes(){
        Constrants.setTextFieldInteger(txtId);
        Constrants.setTextFieldMaxLength(txtNome,70);
        Constrants.setTextFieldDouble(txtSalarioBase);
        Constrants.setTextFieldMaxLength(txtEmail,100);
        Utils.formatDatePicker(dpDataNasc,"dd/MM/yyyy");

        initializeComboBoxDepartment();
    }
    public void updateFormData(){
        if(entity == null){
            throw new IllegalStateException("Entity was null");
        }
        txtId.setText(String.valueOf(entity.getId()));
        txtNome.setText(entity.getNome());
        txtEmail.setText(entity.getEmail());
        Locale.setDefault(Locale.US);
        txtSalarioBase.setText(String.format("%.2f",entity.getSalarioBase()));
        if(entity.getDataNasc() != null){
            dpDataNasc.setValue(LocalDate.ofInstant(entity.getDataNasc().toInstant(), ZoneId.systemDefault()));
        }
        //Preecher o combox
        if(entity.getDepartamento() == null){
            comboBoxDepartamento.getSelectionModel().selectFirst();//Seleciona o primeiro Dpto caso seja novo cadastro
        }else {
            comboBoxDepartamento.setValue(entity.getDepartamento());
        }
    }

    //Carregando os Dept do banco
    public void loadAssociatedObject(){
        if (departmentService == null) {
            throw new IllegalStateException("DepartmentService was null");
        }
        List<Departamento> list = departmentService.findAll();
        obsList = FXCollections.observableArrayList(list);
        comboBoxDepartamento.setItems(obsList);
    }

    //Responsavel por retornar a mensagem de erro na tela
    private void setErrorMessage(Map<String, String> erros){
        Set<String> fields = erros.keySet();
        if(fields.contains("Nome")){
            labelErroName.setText(erros.get("Nome"));
        }
    }

    //Metodo para inicializar o comboBox
    private void initializeComboBoxDepartment() {
        Callback<ListView<Departamento>, ListCell<Departamento>> factory = lv -> new ListCell<Departamento>() {
            @Override
            protected void updateItem(Departamento item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getNome());
            }
        };
        comboBoxDepartamento.setCellFactory(factory);
        comboBoxDepartamento.setButtonCell(factory.call(null));
    }
}
