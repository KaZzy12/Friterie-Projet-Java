package packageModel;

public class Utilisateur {
    private String login, password, nom, prenom, fonction;
    
    public Utilisateur (String l, String pass, String n, String pre, String f){
        setLogin(l);
        setPassword(pass);
        setNom(n);
        setPrenom(pre);
        setFonction(f);   
    } 
    public Utilisateur (String l, String pass){
        setLogin(l);
        setPassword(pass);   
    }
    public void setLogin(String l){
        login = l;
    }
    public void setPassword(String pass){
        password = pass;
    }
    public void setNom(String n){
        nom = n;
    }
    public void setPrenom(String pre){
        prenom = pre;
    }
    public void setFonction(String f){
        fonction = f;
    } 
    public String getLogin (){
        return login;
    }   
    public String getPassword (){
        return password;
    }
    public String getNom(){
        return nom;
    }
    public String getPrenom(){
        return prenom;
    }
}