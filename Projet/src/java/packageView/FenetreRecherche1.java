package packageView;

import packageController.*;
import packageModel.*;
import packageException.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class FenetreRecherche1 extends JPanel{
    private FenetrePrincipale fen;
    private Control ctrl;
    private ArrayList<Produit> produitRech = new ArrayList<Produit>();
    private ArrayList<String> listLogin = new ArrayList<String>();
    private JTable resultat;
    private JLabel labelUtil, labelDateDeb, labelDateMax;
    private JPanel panneauPrinc,panneauForm;
    private JSpinner dateMin, dateMax;
    private JComboBox comboBoxUser;
    private JButton boutRech, boutRetour;
    
    public FenetreRecherche1 (FenetrePrincipale fenP)
    {
        fen = fenP;
        fen.getCont().removeAll();
        ctrl = new Control();
        JLabel labelTitre = new JLabel("<html>Recherche des produits vendus par un utilisateur durant une certaine période<br><br><br><br><br><br></html>");
        panneauPrinc = new JPanel();
        panneauPrinc.setLayout(new GridLayout(2,1,5,5));
        panneauForm = new JPanel();
        panneauForm.setLayout(new GridLayout(4,2,5,5));
        labelUtil = new JLabel("Utilisateur : ");
        labelDateDeb= new JLabel("entre :");
        labelDateMax = new JLabel("et : ");
        try{
            listLogin = ctrl.getLoginUsers();
        }
        catch (SingletonException se){
            JOptionPane.showMessageDialog(null,se.getMessage(),"Erreur connexion unique",JOptionPane.ERROR_MESSAGE);
        }
        catch (ConnexionException ce){
            JOptionPane.showMessageDialog(null,ce.getMessage(),"Erreur connexion",JOptionPane.ERROR_MESSAGE);
        }
        
        String tabUser[] = new String[listLogin.size()];
        for(Integer j=0;j<listLogin.size();j++){
            tabUser[j] = listLogin.get(j);
        }
        
        comboBoxUser = new JComboBox(tabUser);
     
        SpinnerDateModel dateModel, dateModel2;
        Calendar cal = Calendar.getInstance();
        Date now = cal.getTime();
        cal.add(Calendar.YEAR, -100);
        Date startDate = cal.getTime();
        dateMin = new JSpinner(); // Déclaration JSpinner
        dateMax = new JSpinner();
        dateModel = new SpinnerDateModel(now, startDate, now, Calendar.YEAR); // DÃ©claration modÃ¨le
        dateMin.setModel(dateModel); // Modification du modèle
        dateModel2 = new SpinnerDateModel(now, startDate, now, Calendar.YEAR);
        dateMax.setModel(dateModel2);
        
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateMin, "dd/MM/yyyy");
        dateMin.setEditor(editor);
        JSpinner.DateEditor editor2 = new JSpinner.DateEditor(dateMax, "dd/MM/yyyy");
        dateMax.setEditor(editor2);
        
        boutRech = new JButton("Recherche");
        boutRetour = new JButton("Retour");
        panneauForm.add(labelUtil);
        panneauForm.add(comboBoxUser);
        panneauForm.add(labelDateDeb);
        panneauForm.add(dateMin);
        panneauForm.add(labelDateMax);
        panneauForm.add(dateMax);

        
        panneauForm.add(boutRech);
        panneauForm.add(boutRetour);
        panneauPrinc.add(labelTitre);
        panneauPrinc.add(panneauForm);
        //fen.getCont().add(labelTitre);
        //fen.getCont().add("North",panneauForm);
        fen.getCont().add(panneauPrinc);
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
                GregorianCalendar dateRecMin = new GregorianCalendar();
                GregorianCalendar dateRecMax = new GregorianCalendar();
                dateRecMin.setTime((Date) dateMin.getValue());
                dateRecMax.setTime((Date) dateMax.getValue());
                
                Integer indiceLogin = comboBoxUser.getSelectedIndex();
                String login = listLogin.get(indiceLogin);

                produitRech = ctrl.getRechProduits(dateRecMin, dateRecMax, login);
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