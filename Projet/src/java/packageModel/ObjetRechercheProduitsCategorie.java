package packageModel;

public class ObjetRechercheProduitsCategorie {
    private String libProduit, libCategorie;
    private Integer idProduit, qte;
    
    public ObjetRechercheProduitsCategorie(String lP, String lC, Integer id, Integer q){
        libProduit = lP;
        libCategorie = lC;
        idProduit = id;
        qte = q;     
    }  
    public String getLibProduit(){
        return libProduit;
    } 
    public String getLibCategorie(){
        return libCategorie;
    }
    public int getIdProduit(){
        return idProduit;
    } 
    public int getQte(){
        return qte;
    }       
    public void setLibProduit (String lP){
        libProduit = lP;
    }
    public void setLibCategorie (String lC){
        libCategorie = lC;
    }
    public void setIdProduit(int id){
        idProduit = id;
    }
    public void setQte(int q){
        qte = q;
    }
}
