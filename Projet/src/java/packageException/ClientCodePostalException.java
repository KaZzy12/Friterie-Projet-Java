package packageException;

public class ClientCodePostalException extends Exception{
    private final String message;
    public ClientCodePostalException(int numErr){
        if(numErr == 0){
            message = "Le code postal entré n'est pas valide.\n Vérifiez qu'il soit bien compris entre 1000 et 9999";
        }
        else{
            message = "Veuillez entrer un code postal";
        }
    }
    public String getMessage(){
        return message;
    }
    
}

