package packageException;

public class ClientVilleException extends Exception{
    private final String message;
    
    public ClientVilleException(int numErr){
        if(numErr == 0){
            message = "Le nombre de caractère maximum autorisé pour le nom de la ville est de 30";
        }
        else{
            if(numErr ==1){
               message = "Veuillez entrer un nom de ville";
            }
            else{
               message = "Le nom de ville entré contient un ou plusieurs caractères invalides";
            }
       }    
    }
    public String getMessage(){
        return message;
    }
}
