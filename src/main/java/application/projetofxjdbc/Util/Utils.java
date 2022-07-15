package application.projetofxjdbc.Util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;


public class Utils {
    //PEga a tela e sobrepoe
    public static Stage currentStage(ActionEvent event){
        return (Stage) ((Node) event.getSource()).getScene().getWindow();//Acessa o stage onde o controller esta
    }

    //
    public static Integer tryParseToInt(String str){
        try {
            return Integer.parseInt(str);
        }catch (NumberFormatException e){
            return null;
        }
    }

}
