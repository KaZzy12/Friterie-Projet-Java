
package packageModel;


public class Categorie {
    
    private Integer id;
    private String libelle;  
    //////////   CONSTRUCTEUR    ////////// 
    public Categorie (int i, String lib){
        setId(i);
        setLibelle(lib);
    }
    //////////  setteurs / getteurs ////////
    public void setId (int i){
        id = i; // faudra faire appel a une methode pour auto incrementer l id
    }
    
    public void setLibelle (String lib){
        libelle = lib;
    }
    
    public int getId (){
        return id;
    }
    
    public String getLibelle(){
        return libelle;
    }
}
