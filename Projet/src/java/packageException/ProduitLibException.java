package packageException;

public class ProduitLibException extends Exception{  
    private final String message;
    
    public ProduitLibException(int numErr){
        if(numErr == 0){
            message = "Le nombre de caractère maximum autorisé pour le libellé est de 30";
        }
        else{
            if(numErr ==1){
               message = "Veuillez entrer un libellé";
            }
            else{
               message = "Le libellé entré contient un ou plusieurs caractères invalides";
            }
       }      
    }
    public String getMessage(){
        return message;
    }
}
