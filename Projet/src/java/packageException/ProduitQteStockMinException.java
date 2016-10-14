package packageException;

public class ProduitQteStockMinException extends Exception{    
    private final String message;
    
    public ProduitQteStockMinException(int numErr){  
        switch (numErr){
            case 0 : message = "Valeur maximale dépassée pour la quantité de stock minimale!";
            break;
            
            case 1 : message = "Veuillez entrer une quantité de stock minimale!";
            break;
            
            default : message = "Utilisation d'un caractère invalide pour la quantité de stock minimale!";
            break;     
        }     
    }
    public String getMessage(){
        return message;
    }
}
