package packageException;

public class ClientPrenomException extends Exception{
    private final String message;
    public ClientPrenomException(int numErr){
        if(numErr == 0){
            message = "Le nombre de caractère maximum autorisé pour le prénom est de 20";
        }
        else{
            if(numErr ==1){
               message = "Veuillez entrer un prénom";
            }
            else{
               message = "Le prénom entré contient un ou plusieurs caractères invalides";
            }
       }   
    }
    public String getMessage(){
        return message;
    }
}
