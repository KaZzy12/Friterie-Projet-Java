package packageException;

public class ProduitQteStockException extends Exception{  
    private final String message;
    
    public ProduitQteStockException(int numErr){    
        switch (numErr){
            case 0 : message = "Valeur maximale dépassée pour la quantité de stock!";
            break;
            
            case 1 : message = "Veuillez entrer une quantité de stock !";
            break;
            
            default : message = "Utilisation d'un caractère invalide pour la quantité de stock !";
            break;    
        }   
    }
    public String getMessage(){
        return message;
    }
}
