package packageView;
import packageController.*;
import packageModel.*;
import packageException.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class FenetreRech2 extends JPanel{
    private FenetrePrincipale fen;
    private Control ctrl;
    private ArrayList<Produit> produitRech = new ArrayList<Produit>();
    private ArrayList<Client> listClient = new ArrayList<Client>();
    private JTable resultat;
    private JPanel panneauPrinc,panneauForm;
    private JComboBox comboBoxClient;
    private JButton boutRech, boutRetour;
    
    public FenetreRech2 (FenetrePrincipale fenP){
        fen = fenP;
        fen.getCont().removeAll();
        JLabel labelTitre = new JLabel("<html>Recherche des produits achetés par un client<br><br><br><br>Veuillez sélectionner un client <br><br></html>");
        ctrl = new Control();
        panneauPrinc = new JPanel();
        panneauPrinc.setLayout(new BorderLayout());
        panneauForm = new JPanel();
        panneauForm.setLayout(new GridLayout(2,1,5,5));
        try{
            listClient = ctrl.getClients();
        }
        catch (SingletonException se){
            JOptionPane.showMessageDialog(null,se.getMessage(),"Erreur connexion unique",JOptionPane.ERROR_MESSAGE);
        }
        catch (ConnexionException ce){
            JOptionPane.showMessageDialog(null,ce.getMessage(),"Erreur connexion",JOptionPane.ERROR_MESSAGE);
        }

        String tabClient[] = new String[listClient.size()];
        for(Integer j=0;j<listClient.size();j++){
            tabClient[j] = listClient.get(j).getNom()+" "+listClient.get(j).getPrenom();
        }
        
        comboBoxClient = new JComboBox(tabClient);
        
        boutRech = new JButton("Recherche");
        boutRetour = new JButton("Retour");
        
        panneauForm.add(comboBoxClient);
        JPanel panneauPrinc = new JPanel();
        panneauPrinc.setLayout(new GridLayout(2,1));
        JPanel pannBout = new JPanel();
        pannBout.add(boutRech);
        pannBout.add(boutRetour);
        panneauForm.add(pannBout);
        panneauPrinc.add(labelTitre);
        panneauPrinc.add(panneauForm);
        fen.getCont().add("North",panneauPrinc);
        fen.getCont().repaint();
        fen.setVisible(true);
        
        MonGestRetour clickRetour = new MonGestRetour();
        boutRetour.addActionListener(clickRetour);
        
        MonGestRecherche clickRech = new MonGestRecherche();
        boutRech.addActionListener(clickRech);
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
                Integer indiceLogin = comboBoxClient.getSelectedIndex();
                Integer idClient = listClient.get(indiceLogin).getId();
                produitRech = ctrl.getProduitsClient(idClient);
                ModelProduit model = new ModelProduit(produitRech);
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