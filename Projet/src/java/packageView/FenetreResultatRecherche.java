package packageView;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FenetreResultatRecherche extends JFrame{ 
    private final JPanel panBout;
    private final JButton boutRetour;  
    
    public FenetreResultatRecherche(JTable resultat){
        JScrollPane defilant = new JScrollPane(resultat);
        panBout = new JPanel();
        boutRetour = new JButton("Retour");
        panBout.add(boutRetour);
        
        MyGestRetour clickRetour = new MyGestRetour();
        boutRetour.addActionListener(clickRetour);
        this.add(defilant, BorderLayout.CENTER);
        this.add(panBout, BorderLayout.SOUTH);
        this.setVisible(true);    
        this.setTitle("Résultat de votre recherche");
        Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        Integer largeur = (int)tailleEcran.getWidth();
        this.setBounds((largeur/2)-700,40,1400,500);
        this.setVisible(true);
        this.repaint();
    }
    private class MyGestRetour implements ActionListener{
        public void actionPerformed (ActionEvent e){
            FenetreResultatRecherche.this.dispose();
        }
    } 
}