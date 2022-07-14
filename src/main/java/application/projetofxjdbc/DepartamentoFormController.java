package application.projetofxjdbc;

import application.projetofxjdbc.Util.Constrants;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DepartamentoFormController implements Initializable {

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

}
