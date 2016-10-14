

package packageView;
import packageModel.*;
import java.util.*;
import javax.swing.table.AbstractTableModel;


public class ModelProduitComposant extends AbstractTableModel{
    private final ArrayList<String> nomColonnes = new ArrayList <String>();
    private ArrayList<ObjetTableComposantAjout> listProduit = new ArrayList<ObjetTableComposantAjout>();
    
    public ModelProduitComposant (ArrayList<ObjetTableComposantAjout> produits)
    {
        listProduit = produits;
        nomColonnes.add("Libelle Produit");
        nomColonnes.add("Quantite Produit");
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
        ObjetTableComposantAjout produit = listProduit.get(ligne);
        switch(col){
            case 0 : return produit.getLibelle();
            case 1 : return produit.getQte();
           
            default : return null;
        }
    }  
    public Class getColumnClass (int col){
        Class c;
        switch(col){
            case 0 : c = String.class;
                     break;
            case 1 : c = Integer.class;
                     break;
            
            default: c = String.class;
        }
        return c;
    }
}