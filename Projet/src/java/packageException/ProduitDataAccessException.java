package packageException;

public class ProduitDataAccessException extends Exception{
    private String message;
    
    public ProduitDataAccessException (){ 
        message = "Le produit à composer n'est pas dans la BD";
    }
    public String getMessage(){
        return message;
    }
}
