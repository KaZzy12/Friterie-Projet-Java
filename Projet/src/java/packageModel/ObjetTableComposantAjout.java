package packageModel;

public class ObjetTableComposantAjout {
    private String libelle;
    private Integer qte, id;
    
    public ObjetTableComposantAjout(String lib, int q, int id){
        libelle = lib;
        qte = q;
        this.id = id;
    }
    public String getLibelle (){
        return libelle;
    }
    public int getQte (){
        return qte;
    }
    public int getId (){
        return id;
    }
}
