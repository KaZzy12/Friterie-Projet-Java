package packageModel;


public class CompositionProduit {
    private Integer quantite, idCompose;
    private Produit Composant;
    public CompositionProduit (int pC, int qte, Produit p){
        Composant = p;
        idCompose = pC;
        quantite = qte;
    }
    
    public Produit getComposant(){
        return Composant;
    }
    
    public int getQte(){
        return quantite;
    }
}
