package application.projetofxjdbc;

import application.projetofxjdbc.Util.Alerts;
import application.projetofxjdbc.Util.Constrants;
import application.projetofxjdbc.Util.Utils;
import application.projetofxjdbc.db.DbException;
import application.projetofxjdbc.listeners.DataChangeListener;
import application.projetofxjdbc.model.entities.Departamento;
import application.projetofxjdbc.model.services.DepartmentService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DepartamentoFormController implements Initializable {

    private Departamento entity;

    private DepartmentService service;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    //Declaração dos componentes da tela
    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNome;

    @FXML
    private Label labelErroName;

    @FXML
    private Button btSalvar;

    @FXML
    private Button btCancelar;

    //instnacia do depto
    public void setDepartamento(Departamento entity){
        this.entity = entity;
    }

    public void setDepartamentService(DepartmentService service){
        this.service = service;
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
            throw new IllegalStateException("Serivço esta nullo");
        }
        try {
            entity = getFormDate();
            service.saveOrUpdate(entity);//savando no banco
            notifiDataChangeListeners();
            Utils.currentStage(event).close();//Fechando a janela apos salvar
        }catch (DbException e){
            Alerts.showAlert("Erro ao salvar!",null,e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    private void notifiDataChangeListeners() {
        for(DataChangeListener listener : dataChangeListeners){
            listener.onDataChanged();
        }
    }


    //Pega os dados do formulario
    private Departamento getFormDate() {
        Departamento obj = new Departamento();
        obj.setId(Utils.tryParseToInt(txtId.getText()));
        obj.setNome(txtNome.getText());
        return obj;
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
        Constrants.setTextFieldMaxLength(txtNome,30);
    }
    public void updateFormData(){
        txtId.setText(String.valueOf(entity.getId()));
        txtNome.setText(String.valueOf(entity.getNome()));
    }
}
