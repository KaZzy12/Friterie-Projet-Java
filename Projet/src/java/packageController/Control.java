package packageController;

import packageBusiness.*;
import packageModel.*;
import packageException.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Control {
    private final Business business;
    
    public Control(){
        business = new Business();
    }
    
    public Utilisateur getConnect (Utilisateur user) throws ConnexionException, SingletonException{
        return business.getConnect(user);
    }
    
    public ArrayList<Categorie> getCategories() throws SingletonException, ConnexionException{
        return business.getCategories();
    }
    
    public void setProduit(Produit produit) throws ConnexionException, SingletonException{
        business.setProduit(produit);
    }
    
    public ArrayList<Produit> getProduits() throws ConnexionException, SingletonException{
        return business.getProduits();
    }
    
    public void delProduit (String lib) throws SingletonException, ConnexionException{
        business.delProduit(lib);
    }
    public ArrayList<Integer> getNbProduitsRelieProduit(int id) throws ConnexionException, SingletonException{
        return business.getNbProduitsRelieProduit(id);
    }
    
    public ArrayList<Integer> getNbCommandesRelieProduit(int id) throws ConnexionException, SingletonException{
        return business.getNbCommandesRelieProduit(id);
    }
    
    public void updProduit (Produit prod, int identifiant) throws ConnexionException, SingletonException{
        business.updProduit(prod, identifiant);
    }
    
    public void fermerConnection() throws SingletonException{
        business.fermerConnection();
    }
    
    public ArrayList<String> getLoginUsers() throws ConnexionException, SingletonException{
        return business.getLoginUsers();
    }
    
    public ArrayList<Produit> getRechProduits (GregorianCalendar dateDeb, GregorianCalendar dateFin, String vendeur) throws ConnexionException, SingletonException{
        return business.getRechProduits(dateDeb, dateFin, vendeur);
    }
    
    public ArrayList<Client> getClients() throws ConnexionException, SingletonException{
        return business.getClients();
    }
    
    public ArrayList<Produit> getProduitsClient(int idClient)throws ConnexionException, SingletonException{
        return business.getProduitsClient(idClient);
    }
    
    public ArrayList<ObjetRechercheProduitsCategorie> getProduitsCategoriesRech(String []tabValOk) throws ConnexionException, SingletonException{
        return business.getProduitsCategoriesRech(tabValOk);
    }
    
    public void setComposition (Produit comp, int idCompose, int qte) throws ProduitDataAccessException, ConnexionException, SingletonException{
        business.setComposition(comp, idCompose, qte);
    }
    
    public ArrayList<Produit> getProduitsQteMin() throws ConnexionException, SingletonException{
        return business.getProduitsQteMin();
    }
    
    public ArrayList<CompositionProduit> getCompoProd (int idProd) throws ConnexionException, SingletonException{
        return business.getCompoProd(idProd);
    }
    
    public void delComposition(int id) throws SingletonException, ConnexionException{
        business.delComposition(id);
    }
    
    public ArrayList<Commande> getCommandes() throws ConnexionException, SingletonException{
        return business.getCommandes();
    }
    
    public ArrayList<Produit> getProduitsCommande(int idCmd) throws ConnexionException, SingletonException{
        return business.getProduitsCommande(idCmd);
    }
    
    public Double getPrixTotal(ArrayList<Produit> produitsCommande, GregorianCalendar dateCmd){
        return business.getPrixTotal(produitsCommande, dateCmd);
    }
    public ArrayList<Produit> getProduitsCateg(String categ) throws ConnexionException, SingletonException{
        return business.getProduitsCateg(categ);
    }
}
