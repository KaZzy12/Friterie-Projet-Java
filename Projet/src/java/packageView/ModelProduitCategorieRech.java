package packageView;

import packageModel.*;
import java.util.*;
import javax.swing.table.AbstractTableModel;

public class ModelProduitCategorieRech extends AbstractTableModel{
    private final ArrayList<String> nomColonnes = new ArrayList <String>();
    private ArrayList<ObjetRechercheProduitsCategorie> listProduit = new ArrayList<ObjetRechercheProduitsCategorie>();
    
    public ModelProduitCategorieRech (ArrayList<ObjetRechercheProduitsCategorie> produits){
        listProduit = produits;
        nomColonnes.add("Libelle Produit");
        nomColonnes.add("Libelle Categorie");
        nomColonnes.add("Identifiant Produit");
        nomColonnes.add("Quantite vendue");    
    }
    public int getColumnCount () {
        return nomColonnes.size();
    }    
    public int getRowCount() {
        return listProduit.size();
    }    
    public String getColumnName(int col){
        return nomColonnes.get(col);
    }    
    public Object getValueAt (int ligne , int col){
        ObjetRechercheProduitsCategorie produit = listProduit.get(ligne);
        switch(col){
            case 0 : return produit.getLibProduit();
            case 1 : return produit.getLibCategorie();
            case 2 : return produit.getIdProduit();
            case 3 : return produit.getQte();
           
            default : return null;
        }
    }
    
    public Class getColumnClass (int col){
        Class c;
        switch(col){
            case 0 : c = String.class;
                     break;
            case 1 : c = String.class;
                     break;
            case 2 : c = Integer.class;
                     break;
            case 3 : c = Integer.class;
                     break;
            
            default: c = String.class;
        }
        return c;
    }
}