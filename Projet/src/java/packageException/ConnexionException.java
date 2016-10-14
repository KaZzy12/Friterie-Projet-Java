package packageException;

public class ConnexionException extends Exception{
    private String message;
    
    public ConnexionException (String m){
        message = m;
    }
    public String getMessage(){
        return message;
    }
}
