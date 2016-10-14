package packageException;

public class ProduitPourcReducException extends Exception{   
    private final String message;
    
    public ProduitPourcReducException(int numErr){  
        switch (numErr){
            case 0 : message = "Veuillez entrer un pourcentage de réduction !";
            break;
            
            case 1 : message = "Nombre maximal de caractère pour le pourcentage de réduction excédé !";
            break;
            
            case 2 : message = "Le pourcentage de réduction contient un ou plusieurs caractères invalides !";
            break;
            
            case 3 : message = " ',' rencontrée au début ou a la fin !";
            break;
            
            case 5 : message = "La valeur maximale pour le pourcentage de réduction est de 100.00% !";
            break;
            
            default : message = "Format non respecté pour le pourcentage de réduction !";
            break;           
        }    
    }
    public String getMessage(){
        return message;
    }
}