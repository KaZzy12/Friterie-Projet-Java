package packageView;

import packageModel.*;
import packageController.*;
import packageException.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class FenetreSuprimerProduit {
    private JPanel panneauBouton;
    private JButton boutSupp, boutRetour, boutModif;
    private JTable tableau;
    private Control control;
    private JFrame fenetreListe;
    private ModelProduit model;
    private JScrollPane defilant;
    private ArrayList<Produit> listProduit = new ArrayList<Produit>();
    private FenetrePrincipale fen;
    
    public FenetreSuprimerProduit(FenetrePrincipale fenP){
        fen = fenP;
        fen.getCont().removeAll();
        JLabel labelTitre = new JLabel("<html><font size=\"16\"><b>Table de supression/modification de produit</b></font><br><br>Veuillez selectionner le produit directement depuis le tableau<br></html>");
        try{
            panneauBouton = new JPanel();
            
            boutSupp = new JButton("Supprimer");
            MyGestSupp clickSupp = new MyGestSupp();
            boutSupp.addActionListener(clickSupp);
            
            boutRetour = new JButton("Retour");
            MonGestRetour clickRetour = new MonGestRetour();
            boutRetour.addActionListener(clickRetour);
            
            boutModif = new JButton("Modifier");
            MyGestModif clickModif = new MyGestModif();
            boutModif.addActionListener(clickModif);
            
            panneauBouton.add(boutModif);
            panneauBouton.add(boutSupp);
            panneauBouton.add(boutRetour);
            
            control = new Control();
            fenetreListe = new JFrame();
            Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            Integer largeur = (int)tailleEcran.getWidth();
            fenetreListe.setBounds((largeur/2)-700,40,1400,500); 
            fenetreListe.setLayout(new BorderLayout());
            control = new Control();
            listProduit = control.getProduits();
            model = new ModelProduit(listProduit);
            tableau = new JTable(model);
            tableau.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);  
            tableau.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            defilant = new JScrollPane(tableau);                                
            fen.getCont().add(labelTitre);
            fenP.getCont().add("North",defilant);
            fenP.getCont().add("South",panneauBouton);
            fenP.getCont().repaint();
            fenP.setVisible(true);
        }
        catch (ConnexionException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Erreur connexion",JOptionPane.ERROR_MESSAGE);
        }
        catch (SingletonException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Erreur connexion unique",JOptionPane.ERROR_MESSAGE);
        }   
    }
    
    
    
    private class MonGestRetour implements ActionListener{
        public void actionPerformed(ActionEvent e){
            fen.getCont().removeAll();
            fen.getCont().add(fen.getPanneau());
            fen.getCont().repaint();  
        }
    }  
    private class MyGestModif implements ActionListener{
        public void actionPerformed (ActionEvent e){
            ArrayList<Integer> listPro = new ArrayList<Integer>();
            for(int indice = 0; indice < listProduit.size(); indice++){
                if(tableau.isRowSelected(indice)){
                    Produit produitChoisi = listProduit.get(indice);
                    FenetreAjoutProduit form = new FenetreAjoutProduit(fen, produitChoisi);
                }
            } 
        }
    } 
    private class MyGestSupp implements ActionListener{
        public void actionPerformed(ActionEvent e){
            // Aller supprimer le produit selectionné dans la BD
            ArrayList<Integer> listPro = new ArrayList<Integer>();
            try{
                for(int indice = 0; indice < listProduit.size(); indice++){
                    if(tableau.isRowSelected(indice)){
                        Produit produitChoisi = listProduit.get(indice);
                        ArrayList<Integer> listeProduitsRelie = control.getNbProduitsRelieProduit(produitChoisi.getId()); //a creer en bd !!!
                        ArrayList<Integer> listeCommandesRelie = control.getNbCommandesRelieProduit(produitChoisi.getId()); // a creer en bd !!!
                        if(listeProduitsRelie.isEmpty()){   
                            control.delProduit(produitChoisi.getLibelle());
                            JOptionPane.showMessageDialog(null,"Le produit a bien été supprimé de la base de données","Confirmation suppression",
                            JOptionPane.INFORMATION_MESSAGE);
                            listPro.add(indice);
                        }
                        else{
                            Integer choix = JOptionPane.showConfirmDialog(null, produitChoisi.getLibelle()+" compose d'autres produits ou commandes.\n"+ "Voulez-vous tout de même le supprimer (ainsi que tous les produits/commandes qu'il compose) ?","Confirmation suppression",JOptionPane.YES_NO_OPTION);
                            if(choix == JOptionPane.YES_OPTION){
                                control.delProduit(produitChoisi.getLibelle());
                                JOptionPane.showMessageDialog(null,"Le produit a bien été supprimé de la base de données","Confirmation suppression",
                                JOptionPane.INFORMATION_MESSAGE);
                                listPro.add(indice);
                            }
                        }
                    }  
                }
                // On supprime à  partir de la fin (sinon cela pose un problème car ca se décale)
                for(int i = listPro.size()-1; i >= 0; i--){
                    listProduit.remove(Integer.parseInt(listPro.get(i)+""));
                }
                model.fireTableDataChanged();
            }
            catch (ConnexionException ex){
                JOptionPane.showMessageDialog(null,ex.getMessage(),"Erreur connexion unique",JOptionPane.ERROR_MESSAGE);
            }
            catch (SingletonException ex){
                JOptionPane.showMessageDialog(null,ex.getMessage(),"Erreur connexion unique",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}