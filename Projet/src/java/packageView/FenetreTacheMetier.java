package packageView;

import packageController.*;
import packageModel.*;
import packageException.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class FenetreTacheMetier extends JPanel{
    private FenetrePrincipale fen;
    private Control control;
    private JPanel panneauRech;
    private ArrayList<Commande> listeCommande = new ArrayList<Commande>();
    private ArrayList<Produit> produitsCommande = new ArrayList<Produit>();
    private JComboBox commandes;
    private JButton boutonGenerer;
    
    public FenetreTacheMetier(FenetrePrincipale fenP){
        fen = fenP;
        control = new Control();
        fen.getCont().removeAll();
        panneauRech = new JPanel();
        panneauRech.setLayout(new FlowLayout());
        JLabel labelTitre = new JLabel("<html><font size=\"16\"><b>Gestionnaire de facture</b></font><br><br><br></html>");
        JLabel commandeRecherche = new JLabel ("Choisir la commande: ");
        commandeRecherche.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        try{
            listeCommande = control.getCommandes(); 
        }       
        catch(ConnexionException e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Erreur connexion",JOptionPane.ERROR_MESSAGE);
        }
        catch(SingletonException e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Erreur connexion unique",JOptionPane.ERROR_MESSAGE);
        }  
        String tabCommandes[] = new String[listeCommande.size()];
        
        for(Integer j=0;j<listeCommande.size();j++){
                tabCommandes[j] = "Commande "+listeCommande.get(j).getId()+" par "+listeCommande.get(j).getUtilisateur().getNom()+" "+listeCommande.get(j).getUtilisateur().getPrenom();
        }
        commandes = new JComboBox(tabCommandes);
        
        panneauRech.add(commandeRecherche);
        panneauRech.add(commandes);
        boutonGenerer = new JButton("Générer la facture");
        MyGestGenererFacture clickRech = new MyGestGenererFacture();
        boutonGenerer.addActionListener(clickRech);
        JPanel panneauPrinc = new JPanel();
        panneauPrinc.setLayout(new BorderLayout());
        JPanel panneauBout = new JPanel();
        panneauBout.setLayout(new FlowLayout());
        panneauBout.add(boutonGenerer);
        JLabel labelVide = new JLabel("<html><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br></html>");
        panneauRech.add(labelVide);
        panneauPrinc.add(labelTitre, BorderLayout.PAGE_START);
        panneauPrinc.add(panneauRech, BorderLayout.CENTER);
        panneauPrinc.add(panneauBout, BorderLayout.PAGE_END);
        fen.getCont().add(panneauPrinc);
        fen.getCont().repaint();
        fenP.setVisible(true);
    }
    
    private class MyGestGenererFacture implements ActionListener{
        public void actionPerformed(ActionEvent e){
            try{
                Integer indiceSelectionne = commandes.getSelectedIndex();
                Commande com = listeCommande.get(indiceSelectionne);
                String u = listeCommande.get(indiceSelectionne).getUtilisateur().getNom() +" "+ listeCommande.get(indiceSelectionne).getUtilisateur().getPrenom();
                produitsCommande = control.getProduitsCommande(com.getId()); //need les produits d une commande et ajoute le nombre dans nombre de la class produit!!!! 
                
                Double prixTotal = control.getPrixTotal(produitsCommande, com.getDateCmd()); // need le prix total d une commande calcul a faire dans la couche business!!!!
                
                genererFacture (u, produitsCommande, prixTotal, com);
            } 
            catch(ConnexionException ce){
                JOptionPane.showMessageDialog(null,ce.getMessage(),"Erreur de connexion",JOptionPane.ERROR_MESSAGE);
            }
            catch(SingletonException se){
                JOptionPane.showMessageDialog(null,se.getMessage(),"Erreur de connexion unique",JOptionPane.ERROR_MESSAGE);
            }
        }    
    }
     
    public void genererFacture (String u, ArrayList<Produit> produits, Double prixTot, Commande com){
        try{
            String produitImprimable;
            File fichier = new File("facture_"+com.getId()+".txt");
            FileWriter fw = new FileWriter (fichier);

            fw.write("\t\t\t\tCOXYBURGER"+ "\r\n\r\n");
            fw.write("commande : " + com.getId()+"\r\n\r\n");
            for(Integer i=0;i<produits.size();i++){
                produitImprimable = produits.get(i).getLibelle();
                for (int j = 0; j < 30 - produits.get(i).getLibelle().length(); j++)
                    produitImprimable += " ";
                fw.write("\r\n"+produits.get(i).getNombre()+"*"+produitImprimable+"\t\t\t\t"+produits.get(i).getPrix() + "€");
            }
            fw.write("\r\n\r\n-------------------------------------------------------------------");
            fw.write("\r\nTotal :    "+prixTot + "€");
            fw.write("\r\n\r\n\r\n\t\tVous avez été servi par "+u);
            fw.write("\r\n\t\tMerci pour votre visite et à bientôt!");
            fw.close();
            JOptionPane.showMessageDialog(null,"La facture a été imprimée avec succès dans le fichier "+fichier,"Confirmation ecriture",JOptionPane.INFORMATION_MESSAGE);
        }
        catch (IOException errFichier){
                JOptionPane.showMessageDialog(null,errFichier.getMessage(),"Erreur Ecriture",JOptionPane.ERROR_MESSAGE); 
        }
    }
}