package packageView;

import packageModel.*;
import java.util.*;
import javax.swing.table.AbstractTableModel;

public class ModelProduit extends AbstractTableModel{
    private final ArrayList<String> nomColonnes = new ArrayList <String>();
    private ArrayList<Produit> listProduit = new ArrayList<Produit>();
    
    public ModelProduit (ArrayList<Produit> produits){
        listProduit = produits;
        nomColonnes.add("Identifiant");
        nomColonnes.add("Libelle");
        nomColonnes.add("Prix");
        nomColonnes.add("Pourcentage Reduction");
        nomColonnes.add("Date Fin Reduction");
        nomColonnes.add("Quantité en Stock");
        nomColonnes.add("Quantité Minimale en Stock");
        nomColonnes.add("Catégorie");
        nomColonnes.add("Surgelé");
        nomColonnes.add("Viande Mitraillette");    
    }  
    public int getColumnCount (){
        return nomColonnes.size();
    }
    public int getRowCount(){
        return listProduit.size();
    }
    public String getColumnName(int col){
        return nomColonnes.get(col);
    }
    public Object getValueAt (int ligne , int col){
        Produit produit = listProduit.get(ligne);
        switch(col){
            case 0 : return produit.getId();
            case 1 : return produit.getLibelle();
            case 2 : return produit.getPrix();

            case 3 : {
                if (produit.getChoixReduc())
                    return produit.getPourcReduc();
                else
                    return null;
            }
            case 4 : {
                if (produit.getChoixReduc())
                    return produit.getDateFinReduc().getTimeInMillis();
                else
                    return null;
            }   
            case 5 : {
                if (produit.getChoixQte())
                    return produit.getQuantiteStock();
                else
                    return null;
            }          
            case 6 : {
                if (produit.getChoixQte())
                    return produit.getQuantiteStockMin();
                else
                    return null;
            }    
            case 7 : return produit.getCategorie().getLibelle();
            case 8 : return produit.getSurgele();
            case 9 : return produit.getViandeMitraillette();
            
            default : return null;
        }
    }
    
    public Class getColumnClass (int col){
        Class c;
        switch(col){
            case 0 : c = Integer.class;
                     break;
            case 1 : c = String.class;
                     break;
            case 2 : c = Double.class;
                     break;
            case 3 : c = Double.class;
                     break;
            case 4 : c = Date.class;
                     break;
            case 5 : c = Integer.class;
                     break;
            case 6 : c = Integer.class;
                     break;
            case 7 : c = String.class;
                     break;
            case 8 : c = Boolean.class;
                     break;
            case 9 : c = Boolean.class;
                     break;
            default: c = String.class;
        }
        return c;
    }
}