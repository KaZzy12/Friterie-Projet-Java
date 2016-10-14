package packageView;

import packageController.Control;
import packageModel.*;
import packageException.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class FenetreCommande {
    private FenetrePrincipale fen;
    private Control ctrl;
    private ArrayList<Produit> listProd, listFrites, listHam, listBro, listSna, listSau, listBoi, listAss;
    private JPanel panneauCateg, panneauArt, panneauPrinc;
    private JButton frites, hamburgers, brochettes, snacks, sauces, boissons, assiettes;
    private int i;
    
    public FenetreCommande (FenetrePrincipale fenP){
        try{         
            fen = fenP;
            fen.getCont().removeAll();
            ctrl = new Control();
            MonGestCateg gestCateg = new MonGestCateg();
            // Pre-load les listes pour gain de temps
            listProd = ctrl.getProduits();
            listFrites = new ArrayList<Produit>();
            listHam = new ArrayList<Produit>(); 
            listBro = new ArrayList<Produit>(); 
            listSna = new ArrayList<Produit>(); 
            listSau = new ArrayList<Produit>(); 
            listBoi = new ArrayList<Produit>(); 
            listAss = new ArrayList<Produit>();
            for(i=0;i<listProd.size();i++){
                if (listProd.get(i).getCategorie().getLibelle().equals("Frites"))
                    listFrites.add(listProd.get(i));
                if (listProd.get(i).getCategorie().getLibelle().equals("Hamburgers"))
                    listHam.add(listProd.get(i));
                if (listProd.get(i).getCategorie().getLibelle().equals("Brochettes"))
                    listBro.add(listProd.get(i));
                if (listProd.get(i).getCategorie().getLibelle().equals("Snacks"))
                    listSna.add(listProd.get(i));
                if (listProd.get(i).getCategorie().getLibelle().equals("Sauces"))
                    listSau.add(listProd.get(i));
                if (listProd.get(i).getCategorie().getLibelle().equals("Boissons"))
                    listBoi.add(listProd.get(i));
                if (listProd.get(i).getCategorie().getLibelle().equals("Assiettes"))
                    listAss.add(listProd.get(i));      
            }
            System.out.println(listAss.get(0).getLibelle());

            panneauPrinc = new JPanel();
            panneauPrinc.setLayout(new GridLayout(1,2));
            panneauCateg = new JPanel();
            panneauCateg.setLayout(new GridLayout(7,1));
            panneauArt = new JPanel();
            panneauArt.setLayout(new GridLayout(10,10));

            //boutons Categ
            frites = new JButton("Frites");
            frites.addActionListener(gestCateg);
            panneauCateg.add(frites);
            hamburgers = new JButton("Hamburgers");
            hamburgers.addActionListener(gestCateg);
            panneauCateg.add(hamburgers);
            brochettes = new JButton("Brochettes");
            brochettes.addActionListener(gestCateg);
            panneauCateg.add(brochettes);
            snacks = new JButton("Snacks");
            snacks.addActionListener(gestCateg);
            panneauCateg.add(snacks);
            sauces = new JButton("Sauces");
            sauces.addActionListener(gestCateg);
            panneauCateg.add(sauces);
            boissons = new JButton("Boissons");
            boissons.addActionListener(gestCateg);
            panneauCateg.add(boissons);
            assiettes = new JButton("Assiettes");
            assiettes.addActionListener(gestCateg);
            panneauCateg.add(assiettes);


            panneauPrinc.add(panneauCateg);
            panneauPrinc.add(panneauArt);
            fen.getCont().add("North",panneauPrinc);
            fen.getCont().repaint();
            fen.setVisible(true);
        }
        catch (ConnexionException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
        }
        catch (SingletonException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Erreur de connexion unique",JOptionPane.ERROR_MESSAGE);
        }  
    }
    
    private class MonGestCateg implements ActionListener{
        public void actionPerformed(ActionEvent e){
            ArrayList<Produit> listArt = new ArrayList<Produit>();
            JButton button;
            panneauArt.removeAll();
            listArt.clear();
            switch(((JButton)e.getSource()).getText()){
                case "Frites" :
                    listArt = listFrites;
                    break;
                case "Hamburgers" :
                    listArt = listHam;
                    break;
                case "Brochettes" :
                    listArt = listBro;
                    break;
                case "Snacks" :
                    listArt = listSna;
                    break;
                case "Sauces" :
                    listArt = listSau;
                    break;
                case "Boissons" :
                    listArt = listBoi;
                    break;
                case "Assiettes" :
                    listArt = listAss;
                    break;
            }
            for(i=0;i<listArt.size();i++){
                button = new JButton(listArt.get(i).getLibelle());
                panneauArt.add(button); 
            }  

            fen.getCont().repaint();                 
        }
    }
}
