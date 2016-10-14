package packageBusiness;

import packageDataAccess.*;
import packageModel.*;
import packageException.*;
import packageInterface.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Business {
    private final InterfaceDataAccess dataBase;
    
    public Business(){
        dataBase = new DataAccess();
    }
    
    public Utilisateur getConnect (Utilisateur user) throws ConnexionException, SingletonException{
        return dataBase.getConnect(user);
    }
    
    public ArrayList<Categorie> getCategories() throws SingletonException, ConnexionException{
        return dataBase.getCategories();
    }
    
    public void setProduit(Produit produit) throws ConnexionException, SingletonException{
        dataBase.setProduit(produit);
    }
    
    public ArrayList<Produit> getProduits() throws ConnexionException, SingletonException{
        return dataBase.getProduits();
    }
    
    public void delProduit (String lib) throws SingletonException, ConnexionException{
        dataBase.delProduit(lib);
    }
    
    public ArrayList<Integer> getNbProduitsRelieProduit(int id) throws ConnexionException, SingletonException{
        return dataBase.getNbProduitsRelieProduit(id);
    }
    
    public ArrayList<Integer> getNbCommandesRelieProduit(int id) throws ConnexionException, SingletonException{
        return dataBase.getNbCommandesRelieProduit(id);
    }
    
    public void updProduit (Produit prod, int identifiant) throws ConnexionException, SingletonException{
        dataBase.updProduit(prod, identifiant);
    }
    
    public void fermerConnection() throws SingletonException{
        dataBase.fermerConnection();
    }
    
    public ArrayList<String> getLoginUsers() throws ConnexionException, SingletonException{
        return dataBase.getLoginUsers();
    }
    
    public ArrayList<Produit> getRechProduits (GregorianCalendar dateDeb, GregorianCalendar dateFin, String vendeur) throws ConnexionException, SingletonException{
        return dataBase.getRechProduits(dateDeb, dateFin, vendeur);
    }
    
    public ArrayList<Client> getClients() throws ConnexionException, SingletonException{
        return dataBase.getClients();
    }
    
    public ArrayList<Produit> getProduitsClient(int idClient)throws ConnexionException, SingletonException{
        return dataBase.getProduitsClient(idClient);
    }
    
    public ArrayList<ObjetRechercheProduitsCategorie> getProduitsCategoriesRech(String []tabValOk) throws ConnexionException, SingletonException{
        return dataBase.getProduitsCategoriesRech(tabValOk);
    }
    
    public void setComposition (Produit comp, int idCompose, int qte) throws ProduitDataAccessException, ConnexionException, SingletonException{
        dataBase.setComposition(comp, idCompose, qte);
    }
    
    public ArrayList<Produit> getProduitsQteMin() throws ConnexionException, SingletonException{
        return dataBase.getProduitsQteMin();
    }
    
    public ArrayList<CompositionProduit> getCompoProd (int idProd) throws ConnexionException, SingletonException{
        return dataBase.getCompoProd(idProd);
    }
    
    public void delComposition(int id) throws SingletonException, ConnexionException{
        dataBase.delComposition(id);
    }
    
    public ArrayList<Commande> getCommandes() throws ConnexionException, SingletonException{
        return dataBase.getCommandes();
    }
    
    public ArrayList<Produit> getProduitsCommande(int idCmd) throws ConnexionException, SingletonException{
        return dataBase.getProduitsCommande(idCmd);
    }
     
    public Double getPrixTotal(ArrayList<Produit> produitsCommande, GregorianCalendar dateCmd){
        Double prixTotal = 0.0;
        for (int i = 0; i < produitsCommande.size(); i++){
            if (produitsCommande.get(i).getDateFinReduc() != null && dateCmd.getTimeInMillis() > produitsCommande.get(i).getDateFinReduc().getTimeInMillis())
                prixTotal += ((produitsCommande.get(i).getPrix() * ((100 - produitsCommande.get(i).getPourcReduc()) / 100)) * produitsCommande.get(i).getNombre());
            else{
                prixTotal += produitsCommande.get(i).getPrix() * produitsCommande.get(i).getNombre();
            }
        }
        return prixTotal;
    }
    public ArrayList<Produit> getProduitsCateg(String categ) throws ConnexionException, SingletonException{
        return dataBase.getProduitsCateg(categ);
    }
}
