package packageView;

import packageController.*;
import packageModel.*;
import packageException.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class FenetreRech3 extends JFrame{
    private FenetrePrincipale fen;
    private Control ctrl;
    private ArrayList<ObjetRechercheProduitsCategorie> produitRech = new ArrayList<ObjetRechercheProduitsCategorie>();
    private ArrayList<Categorie> listCategories = new ArrayList<Categorie>();
    private JTable resultat;
    private JPanel panneauPrinc,panneauForm, panneauBoutList;
    private JList jListCategorie, jListCategorieOk;
    private JButton boutRech, boutRetour, boutCopier;
    private ListModel modelOk;
    
    public FenetreRech3(FenetrePrincipale fenP){
        fen = fenP;
        fen.getCont().removeAll();
        ctrl = new Control();
        JLabel labelTitre = new JLabel("<html>Recherche des produits achetés par catégorie<br><br><br><br>Veuillez sélectionner une ou plusieurs catégories <br><br></html>");
      
        panneauForm = new JPanel();
        panneauForm.setLayout(new GridLayout( 1, 3, 5, 5));
        panneauPrinc = new JPanel();
        panneauPrinc.setLayout(new GridLayout(3,1,10,10));
        panneauBoutList = new JPanel();
        
        try{
            listCategories = ctrl.getCategories();
        }
        catch (SingletonException se){
            JOptionPane.showMessageDialog(null,se.getMessage(),"Erreur connexion unique",JOptionPane.ERROR_MESSAGE);
        }
        catch (ConnexionException ce){
            JOptionPane.showMessageDialog(null,ce.getMessage(),"Erreur connexion",JOptionPane.ERROR_MESSAGE);
        }
        String tabClient[] = new String[listCategories.size()];
        for(Integer j=0;j<listCategories.size();j++){
            tabClient[j] = listCategories.get(j).getLibelle();
        }
        
        jListCategorie = new JList(tabClient);
        jListCategorieOk = new JList();
        
        jListCategorie.setVisibleRowCount(7);
        jListCategorie.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        jListCategorieOk.setVisibleRowCount(7);
        jListCategorieOk.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        boutCopier = new JButton("Copie >>>");
        
        panneauBoutList.add(boutCopier);
        panneauForm.add(new JScrollPane(jListCategorie));
        panneauForm.add(panneauBoutList);
        panneauForm.add(new JScrollPane(jListCategorieOk));
        
        boutRech = new JButton("Recherche");
        boutRetour = new JButton("Retour");
        
        JPanel pannBout = new JPanel();
        pannBout.add(boutRech);
        pannBout.add(boutRetour);
        panneauPrinc.add(labelTitre);
        panneauPrinc.add(panneauForm);
        panneauPrinc.add(pannBout);
        
        fen.getCont().add(panneauPrinc);
        fen.getCont().repaint();
        fen.setVisible(true);
        
        MonGestCopie cop = new MonGestCopie();
        boutCopier.addActionListener(cop);
        
        MonGestRetour clickRetour = new MonGestRetour();
        boutRetour.addActionListener(clickRetour);
        
        MonGestRecherche clickRech = new MonGestRecherche();
        boutRech.addActionListener(clickRech); 
    }
    
    private class MonGestCopie implements ActionListener{
        public void actionPerformed(ActionEvent e){ 
            jListCategorieOk.setListData(jListCategorie.getSelectedValues());
            panneauForm.repaint();
        }
    }
    private class MonGestRetour implements ActionListener{
        public void actionPerformed(ActionEvent e){
            fen.getCont().removeAll();
            fen.getCont().add(fen.getPanneau());
            fen.getCont().repaint();  
        }
    }
    private class MonGestRecherche implements ActionListener{
        public void actionPerformed(ActionEvent e){
            // ouvrir une nouvelle fenètre qui contiendra la liste des produits
            try{                
                modelOk = jListCategorieOk.getModel();
                
                String tabValOk[] = new String[modelOk.getSize()];
                for(Integer i = 0; i < modelOk.getSize(); i++)
                    tabValOk[i] = (String)modelOk.getElementAt(i);
                produitRech = ctrl.getProduitsCategoriesRech(tabValOk);
                ModelProduitCategorieRech model = new ModelProduitCategorieRech(produitRech);
                 
                resultat = new JTable(model);
                resultat.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); 
                FenetreResultatRecherche fenResult = new FenetreResultatRecherche(resultat);    
            }
            catch (SingletonException se){
                JOptionPane.showMessageDialog(null,se.getMessage(),"Erreur connexion unique",JOptionPane.ERROR_MESSAGE);
            }
             catch (ConnexionException ce){
                JOptionPane.showMessageDialog(null,ce.getMessage(),"Erreur connexion",JOptionPane.ERROR_MESSAGE);
            }
        }
    }  
}