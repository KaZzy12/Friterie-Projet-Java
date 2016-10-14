package packageModel;

public class LigneArticle {
    private Integer id, quantite;
    private Produit produit;
    ////////////     contructeur      /////////
    public LigneArticle (int i, int qte, Produit p){
        id = i;
        quantite = qte;
        produit = p;
    } 
}
