package packageException;

public class SingletonException extends Exception{
    private String message;
    
    public SingletonException (String m){
        message = m;
    }
    public String getMessage (){
        return message;
    }
}