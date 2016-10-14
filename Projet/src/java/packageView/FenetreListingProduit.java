package packageView;
import packageController.*;
import packageModel.*;
import packageException.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class FenetreListingProduit extends JPanel{
    private Control control;
    private ArrayList<Produit> listProduit = new ArrayList<Produit>();
    private JTable table;
    private JPanel panelBoutton;
    private JFrame fenetreListe;
    private JButton boutRetour;
    private Image icone;
    
    public FenetreListingProduit (){
        try{
            control = new Control();
            fenetreListe = new JFrame();
            Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            Integer largeur = (int)tailleEcran.getWidth();
            fenetreListe.setBounds((largeur/2)-700,40,1400,500); 
            fenetreListe.setLayout(new BorderLayout());
            //icone
            icone = Toolkit.getDefaultToolkit().getImage("iconeFriterie.png");
            fenetreListe.setIconImage(icone);
            
            listProduit = control.getProduits();
            
            ModelProduit model = new ModelProduit(listProduit);
            
            table = new JTable(model);
            
            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane defilant = new JScrollPane(table);                                
            fenetreListe.add(defilant, BorderLayout.CENTER);
            panelBoutton = new JPanel();
            boutRetour = new JButton("Retour");
            panelBoutton.add(boutRetour);
            MyGestRetour clickRetour = new MyGestRetour();
            boutRetour.addActionListener(clickRetour);
            fenetreListe.add(panelBoutton,BorderLayout.SOUTH);
            fenetreListe.setVisible(true);
            fenetreListe.setAlwaysOnTop(true);
        }
        catch (ConnexionException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
        }
        catch (SingletonException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Erreur de connexion unique",JOptionPane.ERROR_MESSAGE);
        }  
    }
    private class MyGestRetour implements ActionListener{
        public void actionPerformed (ActionEvent e){
            fenetreListe.dispose();
        }
    }
}