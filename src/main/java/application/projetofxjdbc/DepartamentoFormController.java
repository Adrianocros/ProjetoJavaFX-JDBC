package application.projetofxjdbc;

import application.projetofxjdbc.Util.Constrants;
import application.projetofxjdbc.model.entities.Departamento;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DepartamentoFormController implements Initializable {

    private Departamento entity;


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

    //Tratamento dos eventos
    @FXML
    public void onBtSaveAction(){
        System.out.println("Departamento cadastrado!");
    }

    @FXML
    public void onBtCancelarAction(){
        System.out.println("Processo cancelado!");
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
