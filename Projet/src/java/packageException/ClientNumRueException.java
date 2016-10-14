package packageException;

public class ClientNumRueException extends Exception{
    private final String message;
    public ClientNumRueException(int numErr){
        if(numErr == 0){
            message = "Le numéro de rue entré n'est pas valide.\nIl doit être au maximum de 9999, et peut contenir une lettre en fin de numéro";
        }
        else{
            message = "Veuillez entrer un numéro de rue.";
        }
    }
    public String getMessage(){
        return message;
    }
}
