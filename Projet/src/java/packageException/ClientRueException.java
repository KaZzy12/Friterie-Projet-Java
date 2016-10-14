package packageException;

public class ClientRueException extends Exception{  
    private final String message;
    
    public ClientRueException(int numErr){
        if(numErr == 0){
            message = "Le nombre de caractère maximum autorisé pour le nom de la rue est de 30";
        }
        else{
            if(numErr ==1){
               message = "Veuillez entrer un nom de rue";
            }
            else{
               message = "Le nom de rue entré contient un ou plusieurs caractères invalides";
            }
       }           
    }
    public String getMessage(){
        return message;
    }
}
