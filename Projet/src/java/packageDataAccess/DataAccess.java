package packageDataAccess;

import packageInterface.*;
import packageException.*;
import packageModel.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class DataAccess implements InterfaceDataAccess{
    private Utilisateur user;
    // GETTEURS
    public Utilisateur getConnect (Utilisateur connect) throws ConnexionException, SingletonException{
        try{
            Connection connexion = Singleton.getInstance();
            String instructionSQL = "select * from Utilisateur where Login = ? and Password = ? ";                
            PreparedStatement prepStat = connexion.prepareStatement(instructionSQL);
            prepStat.setString(1,connect.getLogin());               
            prepStat.setString(2,connect.getPassword());
            ResultSet donnees = prepStat.executeQuery();
            while (donnees.next()){
                user = new Utilisateur(donnees.getString("login"), donnees.getString("password"), donnees.getString("nom"), donnees.getString("prenom"), donnees.getString("fonction"));
            }
        }
        catch(SQLException e){
            throw new ConnexionException(e.getMessage());
        }
        
        return user;   
    } 
    public ArrayList<Produit> getProduits() throws ConnexionException, SingletonException{
        ArrayList<Produit> listProds = new ArrayList<Produit>();
        Produit prod;
        java.sql.Date dateEd;
        try{
            Connection connexion = Singleton.getInstance();
        
            String requeteSQL = "SELECT p.* ,c.* FROM Produit p, Categorie c WHERE c.idCateg = p.idCateg";
            PreparedStatement prepStat = connexion.prepareStatement(requeteSQL);
            ResultSet donnees = prepStat.executeQuery();
            while (donnees.next()){
                dateEd = donnees.getDate(5);
                Integer qte,qteMin;
                Boolean choixQte = true, choixReduc = false;
                qte = donnees.getInt(8);
                if  (donnees.wasNull())
                    choixQte = false;
                qteMin = donnees.getInt(9);
                if  (donnees.wasNull())
                    choixQte = false;
                if (dateEd != null){
                    choixReduc = true;
                    GregorianCalendar gc = new GregorianCalendar();
                    gc.setTime(dateEd);
                    prod = new Produit (donnees.getInt(1), donnees.getInt(8), donnees.getInt(9), donnees.getString(2), donnees.getDouble(3), donnees.getDouble(4), gc, donnees.getBoolean(6), new Categorie(donnees.getInt(10), donnees.getString(12)), donnees.getBoolean(7));    
                }
                else{
                    prod = new Produit (donnees.getInt(1), donnees.getInt(8), donnees.getInt(9), donnees.getString(2), donnees.getDouble(3), donnees.getDouble(4), null, donnees.getBoolean(6), new Categorie(donnees.getInt(10), donnees.getString(12)), donnees.getBoolean(7));   
                }
                prod.setChoixQte(choixQte);
                prod.setChoixReduc(choixReduc);
                listProds.add(prod);
            }
        }
        catch (SQLException e){
            throw new ConnexionException(e.getMessage());
        }
        return listProds;
    }
    public ArrayList<Produit> getProduitsQteMin() throws ConnexionException, SingletonException{
        ArrayList<Produit> listProds = new ArrayList<Produit> ();
        Produit prod;
        java.sql.Date dateEd;
        try{
            Connection connexion = Singleton.getInstance();
            String requeteSQL = "SELECT * FROM Produit WHERE qteStock < qteMinStock";
            PreparedStatement prepStat = connexion.prepareStatement(requeteSQL);
            ResultSet donnees = prepStat.executeQuery();
            while (donnees.next()){
                dateEd = donnees.getDate(5);
                if (dateEd != null){
                    GregorianCalendar gc = new GregorianCalendar();
                    gc.setTime(dateEd);
                    prod = new Produit (donnees.getInt(1), donnees.getInt(8), donnees.getInt(9), donnees.getString(2), donnees.getDouble(3), donnees.getDouble(4), gc, donnees.getBoolean(6), donnees.getInt(10), donnees.getBoolean(7));
                }
                else
                    prod = new Produit (donnees.getInt(1), donnees.getInt(8), donnees.getInt(9), donnees.getString(2), donnees.getDouble(3), donnees.getDouble(4), null, donnees.getBoolean(6), donnees.getInt(10), donnees.getBoolean(7));
                listProds.add(prod);
            }
        }
        catch (SQLException e){
            throw new ConnexionException(e.getMessage());
        }
        return listProds;
    }
    
    public ArrayList<Categorie> getCategories() throws ConnexionException, SingletonException{
        ArrayList<Categorie> listCateg = new ArrayList<Categorie>();
        Categorie categ;
        try{
            Connection connexion = Singleton.getInstance();
            String requeteSQL = "SELECT * FROM Categorie";
            PreparedStatement prepStat = connexion.prepareStatement(requeteSQL);
            ResultSet donnees = prepStat.executeQuery();
        
            while (donnees.next()){
                categ = new Categorie (donnees.getInt(1), donnees.getString(2));
                listCateg.add(categ);
            }
        }
        catch (SQLException e){
            throw new ConnexionException(e.getMessage());
        }
        return listCateg;
    }
    
    public ArrayList<Integer> getNbProduitsRelieProduit(int id) throws ConnexionException, SingletonException{
        ArrayList<Integer> listId = new ArrayList<Integer>();
        try{
            Connection connexion = Singleton.getInstance();
            String requeteSQL = "SELECT C_P_idProduit FROM CompositionProduit WHERE idProduit = ?";
            PreparedStatement prepStat = connexion.prepareStatement(requeteSQL);
            prepStat.setInt(1, id);
            ResultSet donnees = prepStat.executeQuery();
            while (donnees.next()){
                listId.add(donnees.getInt(1));
            }    
        }
        catch (SQLException e){
            throw new ConnexionException(e.getMessage());
        }
        return listId;
    }
    
    public ArrayList<Integer> getNbCommandesRelieProduit(int id) throws ConnexionException, SingletonException{
        ArrayList<Integer> listId = new ArrayList<Integer>();
        try{
            Connection connexion = Singleton.getInstance();
            String requeteSQL = "SELECT idCommande FROM LigneArticle WHERE idProduit = ?";
            PreparedStatement prepStat = connexion.prepareStatement(requeteSQL);
            prepStat.setInt(1, id);
            ResultSet donnees = prepStat.executeQuery();
            while (donnees.next()){
                listId.add(donnees.getInt(1));
            }      
        }
        catch (SQLException e){
            throw new ConnexionException(e.getMessage());
        }
        return listId;
    }
    
    public ArrayList<String> getLoginUsers() throws ConnexionException, SingletonException{
        ArrayList<String> login = new ArrayList<String>();
        try{
            Connection connexion = Singleton.getInstance();
            String instructionSQL = "Select login FROM Utilisateur";
            PreparedStatement prepStat = connexion.prepareStatement(instructionSQL);
            ResultSet donnees = prepStat.executeQuery();
            while (donnees.next()){
                login.add(donnees.getString(1));
            }
        }
        catch (SQLException e){
            throw new ConnexionException(e.getMessage());
        }
        return login;
    }
    
    public ArrayList<Client> getClients() throws ConnexionException, SingletonException{
        ArrayList<Client> clients = new ArrayList<Client>();
        try{
            Connection connexion = Singleton.getInstance();
            String instructionSQL = "Select * FROM Client";
            PreparedStatement prepStat = connexion.prepareStatement(instructionSQL);
            ResultSet donnees = prepStat.executeQuery();
            while (donnees.next()){
                Client cli = new Client (donnees.getInt(1), donnees.getString(2), donnees.getString(3), donnees.getString(4),donnees.getString(5),donnees.getInt(6),donnees.getString(7));
                clients.add(cli);
            }
        }
        catch (SQLException e){
            throw new ConnexionException(e.getMessage());
        }
        return clients;
    }
    public ArrayList<CompositionProduit> getCompoProd (int idProd) throws ConnexionException, SingletonException{
        ArrayList<CompositionProduit> listComp = new ArrayList<CompositionProduit>();
        CompositionProduit compo;
        java.sql.Date dateEd;
        try{
            Connection connexion = Singleton.getInstance();
            String instructionSQL = "SELECT c.C_P_IDPRODUIT, c.QTE, p.* FROM CompositionProduit c, Produit p WHERE C_P_idProduit = ? AND p.IDPRODUIT = c.IDPRODUIT";
            PreparedStatement prepStat = connexion.prepareStatement(instructionSQL);
            prepStat.setInt(1, idProd);
            ResultSet donnees = prepStat.executeQuery();
            while (donnees.next()){
                dateEd = donnees.getDate(7);                
                if (dateEd != null){
                    GregorianCalendar gc = new GregorianCalendar();
                    gc.setTime(dateEd);
                    compo = new CompositionProduit(donnees.getInt(1), donnees.getInt(2), new Produit(donnees.getInt(3),donnees.getInt(10),donnees.getInt(11), donnees.getString(4), donnees.getDouble(5), donnees.getDouble(6), gc, donnees.getBoolean(8), donnees.getInt(12), donnees.getBoolean(9)));
                }
                else
                    compo = new CompositionProduit(donnees.getInt(1), donnees.getInt(2), new Produit(donnees.getInt(3),donnees.getInt(10),donnees.getInt(11), donnees.getString(4), donnees.getDouble(5), donnees.getDouble(6), null, donnees.getBoolean(8), donnees.getInt(12), donnees.getBoolean(9)));
                listComp.add(compo);
            }        
        }
        catch (SQLException e){
            throw new ConnexionException(e.getMessage());
        }
        return listComp;
    }
 
    public ArrayList<Commande> getCommandes() throws ConnexionException, SingletonException{
        ArrayList<Commande> commandes = new ArrayList<Commande>();
        java.sql.Date dateEd;
        try{
            Connection connexion = Singleton.getInstance();
            String instructionSQL = "SELECT c.*, u.*, cl.* FROM Commande c LEFT JOIN Utilisateur u on u.login = c.idUtilisateur LEFT JOIN Client cl on cl.idClient = c.idCli";
            PreparedStatement prepStat = connexion.prepareStatement(instructionSQL);
            ResultSet donnees = prepStat.executeQuery();
            
            while (donnees.next()){
                dateEd = donnees.getDate(2);                
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTime(dateEd);
                Commande com = new Commande (donnees.getInt(1),gc , donnees.getString(3), donnees.getBoolean(4), donnees.getString(5), new Utilisateur(donnees.getString(8), donnees.getString(9), donnees.getString(10), donnees.getString(11), donnees.getString(12)), new Client(donnees.getInt(13), donnees.getString(14), donnees.getString(15), donnees.getString(16), donnees.getString(17), donnees.getInt(18), donnees.getString(19)));
                commandes.add(com);
            }
        }
        catch (SQLException e){
            throw new ConnexionException(e.getMessage());
        }
        return commandes;
    }
    
    public ArrayList<Produit> getProduitsCommande(int idCmd) throws ConnexionException, SingletonException
    {
        ArrayList<Produit> produits = new ArrayList<Produit>();
        java.sql.Date dateEd;
        Produit prod;
        try{   
            Connection connexion = Singleton.getInstance();
            String instructionSQL = "SELECT p.*, l.qte FROM Produit p, LigneArticle l WHERE l.idCommande = ? and p.idProduit = l.idProduit";
            PreparedStatement prepStat = connexion.prepareStatement(instructionSQL);
            prepStat.setInt(1, idCmd);
            ResultSet donnees = prepStat.executeQuery();
            
            while (donnees.next()){
                dateEd = donnees.getDate(5);    
                Integer qte,qteMin;
                Boolean choixQte = true, choixReduc = false;
                qte = donnees.getInt(8);
                if  (donnees.wasNull())
                    choixQte = false;
                qteMin = donnees.getInt(9);
                if  (donnees.wasNull())
                    choixQte = false;
                
                if (dateEd != null){
                    choixReduc = true;
                    GregorianCalendar gc = new GregorianCalendar();
                    gc.setTime(dateEd);
                    prod = new Produit (donnees.getInt(1), donnees.getInt(8), donnees.getInt(9), donnees.getString(2), donnees.getDouble(3), donnees.getDouble(4), gc, donnees.getBoolean(6), donnees.getInt(10), donnees.getBoolean(7));
                }
                else
                    prod = new Produit (donnees.getInt(1), donnees.getInt(8), donnees.getInt(9), donnees.getString(2), donnees.getDouble(3), donnees.getDouble(4), null, donnees.getBoolean(6), donnees.getInt(10), donnees.getBoolean(7));
                
                prod.setNb(donnees.getInt(11));
                produits.add(prod);      
            }
        }
        catch (SQLException e){
            throw new ConnexionException(e.getMessage());
        }
        return produits;
    }
    public ArrayList<Produit> getProduitsCateg(String categ) throws ConnexionException, SingletonException{
        ArrayList<Produit> listProds = new ArrayList<Produit>();
        java.sql.Date dateEd;
        Produit prod;
        try{
            Connection connexion = Singleton.getInstance();
            String instructionSQL = "SELECT p.* FROM Produit p, Categorie c WHERE c.libCateg = ? and p.idCateg = c.idCateg";
            PreparedStatement prepStat = connexion.prepareStatement(instructionSQL);
            prepStat.setString(1, categ);
            ResultSet donnees = prepStat.executeQuery();
            while (donnees.next()){
                dateEd = donnees.getDate(5);
                if (dateEd != null){
                    GregorianCalendar gc = new GregorianCalendar();
                    gc.setTime(dateEd);
                    prod = new Produit (donnees.getInt(1), donnees.getInt(8), donnees.getInt(9), donnees.getString(2), donnees.getDouble(3), donnees.getDouble(4), gc, donnees.getBoolean(6), donnees.getInt(10), donnees.getBoolean(7));
                }
                else
                    prod = new Produit (donnees.getInt(1), donnees.getInt(8), donnees.getInt(9), donnees.getString(2), donnees.getDouble(3), donnees.getDouble(4), null, donnees.getBoolean(6), donnees.getInt(10), donnees.getBoolean(7));
                listProds.add(prod);
            }
        }
        catch (SQLException e){
            throw new ConnexionException(e.getMessage());
        }
        return listProds;
    }
    // SETTEURS
    public void setProduit(Produit produit) throws ConnexionException, SingletonException{
        try{
            Connection connexion = Singleton.getInstance();
            String instructionSQL = "INSERT INTO Produit (libProduit, prix, surgele, viandeMitraillette, idCateg) VALUES(?,?,?,?,?)";
            PreparedStatement prepStat = connexion.prepareStatement(instructionSQL);              
            prepStat.setString(1,produit.getLibelle());
            prepStat.setDouble(2,produit.getPrix());
            prepStat.setBoolean(3,produit.getSurgele());
            prepStat.setBoolean(4,produit.getViandeMitraillette());
            prepStat.setInt(5,produit.getCategorie().getId());                
            prepStat.executeUpdate();
            if (produit.getDateFinReduc() != null){
                instructionSQL = "UPDATE Produit set dateFinReduc = ? where libProduit = ?";
                prepStat = connexion.prepareStatement(instructionSQL);
                prepStat.setDate(1,new java.sql.Date(produit.getDateFinReduc().getTimeInMillis()));
                prepStat.setString(2,produit.getLibelle());
                prepStat.executeUpdate();
            } 
            if (produit.getPourcReduc() != null){
                instructionSQL = "UPDATE Produit set pourcReduc = ? where libProduit = ?";
                prepStat = connexion.prepareStatement(instructionSQL);
                prepStat.setDouble(1,produit.getPourcReduc());
                prepStat.setString(2,produit.getLibelle());
                prepStat.executeUpdate();
            }    
            if (produit.getQuantiteStock() != null){
                instructionSQL = "UPDATE Produit set qteStock = ? where libProduit = ?";
                prepStat = connexion.prepareStatement(instructionSQL);
                prepStat.setInt(1,produit.getQuantiteStock());
                prepStat.setString(2,produit.getLibelle());
                prepStat.executeUpdate();
            }           
            if (produit.getQuantiteStockMin() != null){
             instructionSQL = "UPDATE Produit set qteMinStock = ? where libProduit = ?";
                prepStat = connexion.prepareStatement(instructionSQL);
                prepStat.setInt(1,produit.getQuantiteStockMin());
                prepStat.setString(2,produit.getLibelle());
                prepStat.executeUpdate();
            }
        }    
        catch (SQLException e){
            throw new ConnexionException(e.getMessage());
        }
    }
    
    public void setCommande (Commande cmd) throws SQLException, SingletonException{
        java.sql.Date sqlD = new java.sql.Date(cmd.getDateCmd().getTimeInMillis());
        int numero = 0;
        
        Connection connexion = Singleton.getInstance();
        String instructionSQL = "INSERT INTO Commande (dateCmd, statut, aEmporter, idUtilisateur) values (?,?,?,?)";
        PreparedStatement prepStat = connexion.prepareStatement(instructionSQL);
        prepStat.setDate(1, sqlD);
        prepStat.setString(2, cmd.getStatut());
        prepStat.setBoolean(3, cmd.getAEmporter());
        prepStat.setString(4, cmd.getIdUser());
        prepStat.executeUpdate();
        ResultSet key = prepStat.getGeneratedKeys();
        while(key.next())
            numero = key.getInt(1);
        if (cmd.getModePayement() != null){
            instructionSQL = "UPDATE Commande set modePayement = ? where idCmd = ?";
            prepStat = connexion.prepareStatement(instructionSQL);
            prepStat.setString(1,cmd.getModePayement());
            prepStat.setInt(2,numero);
            prepStat.executeUpdate();
        }
        if (cmd.getIdCli() != 0){
            instructionSQL = "UPDATE Commande set idCli = ? where idCmd = ?";
            prepStat = connexion.prepareStatement(instructionSQL);
            prepStat.setInt(1,cmd.getIdCli());
            prepStat.setInt(2,numero);
            prepStat.executeUpdate();
        }   
    }
    
    public void setComposition (Produit comp, int idCompose, int qte) throws ProduitDataAccessException, ConnexionException, SingletonException{
        int idComp = 0;
        try{
            Connection connexion = Singleton.getInstance();
            String instructionSQL = "SELECT idProduit FROM Produit WHERE libProduit = ?"; 
            PreparedStatement prepStat = connexion.prepareStatement(instructionSQL);
            prepStat.setString(1,comp.getLibelle());
            ResultSet donnees = prepStat.executeQuery();
            while (donnees.next()){
                idComp = donnees.getInt(1);
            }
            if (idComp != 0){
                instructionSQL = "INSERT INTO CompositionProduit VALUES (?,?,?)"; 
                prepStat = connexion.prepareStatement(instructionSQL);
                prepStat.setInt(1,idComp);
                prepStat.setInt(2,idCompose);
                prepStat.setInt(3,qte);
                prepStat.executeUpdate();
            }
            else
                throw new ProduitDataAccessException();
        }
        catch (SQLException e){
            throw new ConnexionException(e.getMessage());
        } 
    }
    // Update
    public void updProduit (Produit prod,Integer identifiant) throws ConnexionException, SingletonException{
        try{
            java.sql.Date sqlD;

            Connection connexion = Singleton.getInstance();
            String instructionSQL = "UPDATE Produit SET libProduit = ?, prix = ?, surgele = ?, viandeMitraillette = ?, idCateg = ? where idProduit = ?"; 
            PreparedStatement prepStat = connexion.prepareStatement(instructionSQL);
            prepStat.setString(1,prod.getLibelle());
            prepStat.setDouble(2,prod.getPrix());
            prepStat.setInt(5,prod.getCategorie().getId());
            prepStat.setInt(6,identifiant);
            prepStat.setBoolean(3,prod.getSurgele());
            prepStat.setBoolean(4,prod.getViandeMitraillette());
            prepStat.executeUpdate(); 
            if (prod.getChoixReduc() == true){
                sqlD = new java.sql.Date(prod.getDateFinReduc().getTimeInMillis());
                instructionSQL = "UPDATE Produit SET pourcreduc = ?, datefinreduc = ? WHERE idproduit = ?";
                prepStat = connexion.prepareStatement(instructionSQL);
                prepStat.setDouble(1,prod.getPourcReduc());
                prepStat.setDate(2,sqlD);
                prepStat.setInt(3,identifiant);
                prepStat.executeUpdate(); 
            }
            else{
                instructionSQL = "UPDATE Produit SET pourcreduc = NULL, datefinreduc = NULL WHERE idproduit = ?";  
                prepStat = connexion.prepareStatement(instructionSQL);
                prepStat.setInt(1,identifiant);
                prepStat.executeUpdate();
            }   
            if (prod.getChoixQte() == true){
                instructionSQL = "UPDATE Produit SET qtestock = ?, qteminstock = ? WHERE idproduit = ?";
                prepStat = connexion.prepareStatement(instructionSQL);
                prepStat.setInt(1,prod.getQuantiteStock());
                prepStat.setInt(2,prod.getQuantiteStockMin());
                prepStat.setInt(3,identifiant);
                prepStat.executeUpdate(); 
            }
            else{
                instructionSQL = "UPDATE Produit SET qtestock = NULL, qteminstock = NULL WHERE idproduit = ?";
                prepStat = connexion.prepareStatement(instructionSQL);
                prepStat.setInt(1,identifiant);
                prepStat.executeUpdate();
            } 
        }  
        catch (SQLException e){
            throw new ConnexionException(e.getMessage());
        } 
    }
    // Supression
    public void delProduit (String libelle) throws SingletonException, ConnexionException{
        try{
            Connection connexion = Singleton.getInstance();
            String instructionSQL = "DELETE FROM Produit where libProduit = ?"; 
            PreparedStatement prepStat = connexion.prepareStatement(instructionSQL);
            prepStat.setString(1,libelle);         
            prepStat.executeUpdate();           
        }
        catch (SQLException e){
            throw new ConnexionException(e.getMessage());
        }
    }
    
    public void delComposition(int id) throws SingletonException, ConnexionException
    {
         try{
            Connection connexion = Singleton.getInstance();
            String instructionSQL = "DELETE FROM compositionproduit where C_P_idproduit = ?"; 
            PreparedStatement prepStat = connexion.prepareStatement(instructionSQL);
            prepStat.setInt(1,id);         
            prepStat.executeUpdate();
        }
        catch (SQLException e){
            throw new ConnexionException(e.getMessage());
        }
    }
    
    // Recherches
    
    public ArrayList<Produit> getRechProduits (GregorianCalendar dateDeb, GregorianCalendar dateFin, String vendeur) throws ConnexionException, SingletonException{
        ArrayList<Produit> listProds = new ArrayList<Produit>();
        Produit prod;
        java.sql.Date dateEd;
        try{
            Connection connexion = Singleton.getInstance();
            String instructionSQL = "Select p.*, ca.* FROM Produit p, Commande c, Utilisateur u, LigneArticle l, Categorie ca where u.login = ? and c.idUtilisateur = u.login and l.idCommande = c.idCmd and p.idProduit = l.idProduit and ca.idCateg = p.idCateg and c.dateCmd between ? and ?";
            PreparedStatement prepStat = connexion.prepareStatement(instructionSQL);
            prepStat.setString(1,vendeur);
            prepStat.setDate(2,new java.sql.Date(dateDeb.getTimeInMillis()));
            prepStat.setDate(3,new java.sql.Date(dateFin.getTimeInMillis()));
            ResultSet donnees = prepStat.executeQuery();

            while (donnees.next()){
                dateEd = donnees.getDate(5);
                Integer qte,qteMin;
                Boolean choixQte = true, choixReduc = false;
                qte = donnees.getInt(8);
                if  (donnees.wasNull())
                    choixQte = false;
                qteMin = donnees.getInt(9);
                if  (donnees.wasNull())
                    choixQte = false;     
                if (dateEd != null){
                    choixReduc = true;
                    GregorianCalendar gc = new GregorianCalendar();
                    gc.setTime(dateEd);
                    prod = new Produit (donnees.getInt(1), donnees.getInt(8), donnees.getInt(9), donnees.getString(2), donnees.getDouble(3), donnees.getDouble(4), gc, donnees.getBoolean(6), new Categorie(donnees.getInt(10), donnees.getString(12)), donnees.getBoolean(7));      
                }
                else{
                    prod = new Produit (donnees.getInt(1), donnees.getInt(8), donnees.getInt(9), donnees.getString(2), donnees.getDouble(3), donnees.getDouble(4), null, donnees.getBoolean(6), new Categorie(donnees.getInt(10), donnees.getString(12)), donnees.getBoolean(7));
                   
                }
                prod.setChoixQte(choixQte);
                prod.setChoixReduc(choixReduc);
                listProds.add(prod);
            }                          
        }
        catch (SQLException e){
            throw new ConnexionException(e.getMessage());
        }
        return listProds;
    }
    public ArrayList<Produit> getProduitsClient(int idClient)throws ConnexionException, SingletonException{
        ArrayList<Produit> prods = new ArrayList<Produit>();
        Produit prod;
        Integer qte, qteMin;
        java.sql.Date dateEd;
        try{
            Connection connexion = Singleton.getInstance();
            String instructionSQL = "Select p.* , ca.* from Client c, Commande cm, LigneArticle l, Produit p, Categorie ca Where idCli = ? and c.idClient = idCli and cm.idCli = c.idClient and l.idCommande = cm.idCmd and p.idProduit = l.idProduit and ca.idCateg = p.idCateg";            
            PreparedStatement prepStat = connexion.prepareStatement(instructionSQL);
            prepStat.setInt(1, idClient);
            ResultSet donnees = prepStat.executeQuery();
            while (donnees.next()){
                dateEd = donnees.getDate(5);
                Boolean choixQte = true, choixReduc = false;
                qte = donnees.getInt(8);
                if  (donnees.wasNull())
                    choixQte = false;
                qteMin = donnees.getInt(9);
                if  (donnees.wasNull())
                    choixQte = false;
                
                if (dateEd != null){
                    choixReduc = true;
                    GregorianCalendar gc = new GregorianCalendar();
                    gc.setTime(dateEd);
                    prod = new Produit (donnees.getInt(1), donnees.getInt(8), donnees.getInt(9), donnees.getString(2), donnees.getDouble(3), donnees.getDouble(4), gc, donnees.getBoolean(6), new Categorie(donnees.getInt(10), donnees.getString(12)), donnees.getBoolean(7));         
                }
                else{
                    prod = new Produit (donnees.getInt(1), donnees.getInt(8), donnees.getInt(9), donnees.getString(2), donnees.getDouble(3), donnees.getDouble(4), null, donnees.getBoolean(6), new Categorie(donnees.getInt(10), donnees.getString(12)), donnees.getBoolean(7));                
                }
                prod.setChoixQte(choixQte);
                prod.setChoixReduc(choixReduc);
                prods.add(prod);
            }
        }
        catch (SQLException e){
            throw new ConnexionException(e.getMessage());
        }
        return prods;
    }
    
    public ArrayList<ObjetRechercheProduitsCategorie> getProduitsCategoriesRech(String []tabValOk) throws ConnexionException, SingletonException{
        ArrayList<ObjetRechercheProduitsCategorie> prods = new ArrayList<ObjetRechercheProduitsCategorie>();
        ObjetRechercheProduitsCategorie prod;
        int i; 
        try{
            Connection connexion = Singleton.getInstance();   
            for (i = 0; i < tabValOk.length; i++){
                String instructionSQL = "select v.LIBPRODUIT,v.LIBCATEG, v.idproduit ,sum(v.qte) as comptage from vuerecherche v where v.libCateg = ? group by v.idproduit,v.libproduit, v.LIBCATEG";
                PreparedStatement prepStat = connexion.prepareStatement(instructionSQL);
                prepStat.setString(1, tabValOk[i]);
                ResultSet donnees = prepStat.executeQuery();
                while (donnees.next()){
                   prod = new ObjetRechercheProduitsCategorie(donnees.getString(1), donnees.getString(2), donnees.getInt(3), donnees.getInt(4));
                   prods.add(prod);
                }
            }
        }
        catch (SQLException e){
            throw new ConnexionException(e.getMessage());
        }
        return prods;
    }
    
    public void fermerConnection() throws SingletonException{
        try{
           Singleton.getInstance().close();
        }
        catch(SQLException e){
            throw new SingletonException(e.getMessage());
        }
    }
}
