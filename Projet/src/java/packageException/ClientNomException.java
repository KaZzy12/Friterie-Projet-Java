package packageException;

public class ClientNomException extends Exception {
    
    private final String message;
    
    public ClientNomException(int numErr){
        if(numErr == 0){
            message = "Le nombre de caractère maximum autorisé pour le nom est de 20";
        }
        else{
            if(numErr ==1){
               message = "Veuillez entrer un nom de famille";
            }
            else{
               message = "Le nom entré contient un ou plusieurs caractères invalides";
            }
       }
            
    }
    public String getMessage(){
        return message;
    }


}
