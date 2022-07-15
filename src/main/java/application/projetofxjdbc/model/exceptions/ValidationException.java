package application.projetofxjdbc.model.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    //Essa excessao carrega os erros

    private Map<String, String> erros = new HashMap<>();

    public ValidationException(String msg){
        super(msg);
    }

    public Map<String, String> getErros(){
        return erros;
    }

    //metodo permite add elemento na coleção
    public void addErro(String fieldNome, String errorMessage){
        erros.put(fieldNome,errorMessage);
    }

}
