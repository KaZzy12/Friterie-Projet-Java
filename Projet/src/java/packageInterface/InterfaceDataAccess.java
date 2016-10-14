
package packageInterface;
import packageModel.*;
import packageException.*;
import java.util.*;


public interface InterfaceDataAccess {
    public Utilisateur getConnect (Utilisateur user) throws ConnexionException, SingletonException;
    public ArrayList getCategories() throws SingletonException, ConnexionException;
    public void setProduit(Produit produit) throws ConnexionException, SingletonException;
    public ArrayList<Produit> getProduits() throws ConnexionException, SingletonException;
    public void delProduit (String libelle) throws ConnexionException, SingletonException;
    public ArrayList<Integer> getNbProduitsRelieProduit(int id) throws ConnexionException, SingletonException;
    public ArrayList<Integer> getNbCommandesRelieProduit(int id) throws ConnexionException, SingletonException;
    public void updProduit (Produit prod,Integer identifiant) throws ConnexionException, SingletonException;
    public void fermerConnection() throws SingletonException;
    public ArrayList<String> getLoginUsers() throws ConnexionException, SingletonException;
    public ArrayList<Produit> getRechProduits (GregorianCalendar dateDeb, GregorianCalendar dateFin, String vendeur) throws ConnexionException, SingletonException;
    public ArrayList<Client> getClients() throws ConnexionException, SingletonException;
    public ArrayList<Produit> getProduitsClient(int idClient)throws ConnexionException, SingletonException;
    public ArrayList<ObjetRechercheProduitsCategorie> getProduitsCategoriesRech(String []tabValOk) throws ConnexionException, SingletonException;
    public void setComposition (Produit comp, int idCompose, int qte) throws ProduitDataAccessException, ConnexionException, SingletonException;
    public ArrayList<Produit> getProduitsQteMin() throws ConnexionException, SingletonException;
    public ArrayList<CompositionProduit> getCompoProd (int idProd) throws ConnexionException, SingletonException;
    public void delComposition(int id) throws SingletonException, ConnexionException;
    public ArrayList<Commande> getCommandes() throws ConnexionException, SingletonException;
    public ArrayList<Produit> getProduitsCommande(int idCmd) throws ConnexionException, SingletonException;
    public ArrayList<Produit> getProduitsCateg(String categ) throws ConnexionException, SingletonException;
}
