package packageModel;

import java.util.*;

public class Commande {
    private Integer id;
    private String statut, modePayement;
    private GregorianCalendar date;
    private Client client;
    private Utilisateur utilisateur;
    private Boolean aEmporte;  
    //////////   CONSTRUCTEUR    //////////
    public Commande (int i, GregorianCalendar d, String s, Boolean aE, String mP, Utilisateur u, Client c){
        id = i;
        statut = s;
        modePayement = mP;
        date = d;
        client = c;
        utilisateur = u;
        aEmporte = aE;
    }
    // sans client
    public Commande (int i, String s, String mP, GregorianCalendar d, Utilisateur u, Boolean aE){
        id = i;
        statut = s;
        modePayement = mP;
        date = d;
        utilisateur = u;
        aEmporte = aE;
    }
    // sans mode de payement
    public Commande (int i, String s, GregorianCalendar d, Client c, Utilisateur u, Boolean aE){
        id = i;
        statut = s;
        date = d;
        client = c;
        utilisateur = u;
        aEmporte = aE;
    }
    // sans client ni mode de payement
    public Commande (int i, String s, GregorianCalendar d, Utilisateur u, Boolean aE){
        id = i;
        statut = s;
        date = d;
        utilisateur = u;
        aEmporte = aE;
    }  
    // getteurs
    public Integer getId(){
        return id;
    }
    
    public GregorianCalendar getDateCmd (){
        return date;
    }
    
    public String getStatut(){
        return statut;
    }
    
    public Boolean getAEmporter(){
        return aEmporte;
    }
    
    public Utilisateur getUtilisateur()
    {
        return utilisateur;
    }
    
    public String getIdUser (){
        return utilisateur.getLogin();
    }
    
    public String getModePayement(){
        return modePayement;
    }
    
    public int getIdCli(){
        return client.getId();
    }
}
