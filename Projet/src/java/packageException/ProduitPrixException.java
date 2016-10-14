package packageException;

public class ProduitPrixException extends Exception{ 
    private final String message;
    
    public ProduitPrixException(int numErr){
        switch (numErr){
            case 0 : message = "Veuillez entrer un prix !";
            break;
            
            case 1 : message = "Nombre maximal de caractère pour le prix excédé !";
            break;
            
            case 2 : message = "Le prix contient un ou plusieurs caractères invalides !";
            break;
            
            case 3 : message = " ',' rencontrée au début ou a la fin !";
            break;
            
            default : message = "Format non respecté pour le prix !";
            break;          
        }      
    }
    public String getMessage(){
        return message;
    }
}
