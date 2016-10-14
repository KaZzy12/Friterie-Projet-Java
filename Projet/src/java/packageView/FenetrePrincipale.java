package packageView;

import packageController.*;
import packageException.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class FenetrePrincipale extends JFrame{
    
    private final Container cont;
    private final JMenuBar barre;
    private final JMenu application, ajout, recherche, listing, supprModif, tacheMetier, commandes;
    private final JMenuItem menuPrincipal, quitter, aProduit, ICommande, rech1, rech2, rech3, lProduit, supprModifSM, genererFacture, deconnexion;
    private final JPanel panneau;
    private final Control control;
    private final Image icone;
    private Integer hFen = 1000, lFen = 800;
    private JLabel labelThread, msgMenuPrinc;
    private MyThread myT;
    private FenetrePrincipale fen = this;
    
    public FenetrePrincipale(){
        control = new Control();
        panneau = new JPanel();
        panneau.setLayout(new FlowLayout());
        myT = new MyThread(this);
        myT.start();
        // icone 
        icone = Toolkit.getDefaultToolkit().getImage("iconeFriterie.png");
        this.setIconImage(icone);

        labelThread = new JLabel("<html></br></br></br></br></br></br></br></br></br></br><div color='red'>Attention, il reste ");
        msgMenuPrinc = new JLabel("<html><br><br><br><br><br><div color='blue'><font size=\"24\"><center>COXYBURGER</center></font></div></html>");

        Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        Integer largeur = (int)tailleEcran.getWidth();
        setBounds((largeur/2)-(lFen/2),20,hFen,lFen);
        setVisible(true);
        
        MyWindowListener window = new MyWindowListener();
        this.addWindowListener(window);
        // ***************   Creation barre menu *******************
        barre = new JMenuBar();
        setJMenuBar(barre);
        // menu application
        application = new JMenu ("Application");
        barre.add(application);
        // sous menus
        menuPrincipal = new JMenuItem ("Menu Principal");
        application.add(menuPrincipal);
        deconnexion = new JMenuItem ("Deconnexion");
        application.add(deconnexion);
        quitter = new JMenuItem ("Quitter");
        application.add(quitter);
        // menu ajout
        ajout = new JMenu ("Ajout");
        barre.add(ajout);
        //sous menu
        aProduit = new JMenuItem ("Ajouter Produit");
        ajout.add(aProduit); 
        // Menu Commandes
        commandes =  new JMenu("Commandes");
        barre.add(commandes);
        // Sous menu
        ICommande = new JMenuItem ("Interface commande");
        commandes.add(ICommande);
        // Menu suppression Modification
        supprModif = new JMenu ("Supprimer/Modifier");
        barre.add(supprModif);
        // sous menu
        supprModifSM = new JMenuItem ("Supprimer/Modifier Produit");
        supprModif.add(supprModifSM);
        //menu recherche
        recherche = new JMenu ("Recherches");
        barre.add(recherche);
        //sous menu 
        rech1 = new JMenuItem ("Recherche produits selon le vendeur");
        recherche.add(rech1);
        rech2 = new JMenuItem ("Recherche habitudes clients");
        recherche.add(rech2);
        rech3 = new JMenuItem ("Recherche ventes par catégorie");
        recherche.add(rech3);
        //menu listing
        listing = new JMenu ("Listing");
        barre.add(listing);
        //sous menu
        lProduit = new JMenuItem ("Lister Produits");
        listing.add(lProduit);
        //menu metier
        tacheMetier = new JMenu ("Tache Metier");
        barre.add(tacheMetier);
        //sous menu
        genererFacture = new JMenuItem ("Generer Facture");
        tacheMetier.add(genererFacture);
        // *************************************************************
        cont = getContentPane();
        cont.setLayout(new FlowLayout());
        
        panneau.add(msgMenuPrinc);
        cont.add(panneau);
        cont.setVisible(true);
        
        // gestion des action menu / boutons
        MyGestMenuPrincipal clickMenuPrincipal = new MyGestMenuPrincipal();
        menuPrincipal.addActionListener(clickMenuPrincipal);
        MyGestMenuInterfCommande clickIntCommande = new MyGestMenuInterfCommande();
        ICommande.addActionListener(clickIntCommande);
        MyGestMenuAjoutProduit clickAjoutProduit = new MyGestMenuAjoutProduit();
        aProduit.addActionListener(clickAjoutProduit);
        MyGestMenuRecherche1 clickRech1 = new MyGestMenuRecherche1();
        rech1.addActionListener(clickRech1);
        MyGestMenuRecherche2 clickRech2 = new MyGestMenuRecherche2();
        rech2.addActionListener(clickRech2);
        MyGestMenuRecherche3 clickRech3 = new MyGestMenuRecherche3();
        rech3.addActionListener(clickRech3);
        MyGestMenuListProduit clickListProduit = new MyGestMenuListProduit();
        lProduit.addActionListener(clickListProduit);
        MyGestMenuSupprModifProduit clickSupprModifProduit = new MyGestMenuSupprModifProduit();
        supprModifSM.addActionListener(clickSupprModifProduit);
        MyGestMenuGenererFacture clickGenererFacture= new MyGestMenuGenererFacture();
        genererFacture.addActionListener(clickGenererFacture);
        MyGestMenuDeconnexion clickDeconnexion = new MyGestMenuDeconnexion();
        deconnexion.addActionListener(clickDeconnexion);
        MyGestQuitter clickQuitter  = new MyGestQuitter();
        quitter.addActionListener(clickQuitter);
    } 
    private class MyGestMenuSupprModifProduit implements ActionListener{
        public void actionPerformed(ActionEvent e){
            cont.removeAll();
            FenetreSuprimerProduit form = new FenetreSuprimerProduit(FenetrePrincipale.this);
        }
    }
    private class MyGestMenuInterfCommande implements ActionListener{
        public void actionPerformed(ActionEvent e){
            cont.removeAll();
            FenetreCommande form = new FenetreCommande(FenetrePrincipale.this);
        }  
    }
    private class MyGestMenuListProduit implements ActionListener{
        public void actionPerformed(ActionEvent e){
            FenetreListingProduit form = new FenetreListingProduit();  
            cont.removeAll();
            cont.add(panneau);
            cont.repaint();
        }
    }
    private class MyGestMenuRecherche3 implements ActionListener{
        public void actionPerformed(ActionEvent e){
            cont.removeAll();
            FenetreRech3 form = new FenetreRech3(FenetrePrincipale.this);
        }
    }
    private class MyGestMenuRecherche2 implements ActionListener{
        public void actionPerformed(ActionEvent e){
            cont.removeAll();
            FenetreRech2 form = new FenetreRech2(FenetrePrincipale.this);
        }
    }
    
    private class MyGestMenuRecherche1 implements ActionListener{
        public void actionPerformed(ActionEvent e){
            cont.removeAll();
            FenetreRecherche1 form = new FenetreRecherche1(FenetrePrincipale.this);
        }
    }
    private class MyGestMenuAjoutProduit implements ActionListener{
        public void actionPerformed(ActionEvent e){
            cont.removeAll();
               FenetreAjoutProduit form = new FenetreAjoutProduit(FenetrePrincipale.this);
        }
    }
    private class MyGestMenuGenererFacture implements ActionListener{
        public void actionPerformed(ActionEvent e){
            cont.removeAll();
            FenetreTacheMetier form = new FenetreTacheMetier(FenetrePrincipale.this);
        }
    }    
    private class MyWindowListener extends WindowAdapter{
        public void windowClosing(WindowEvent e){
            try{
                control.fermerConnection();
                myT.interrupt();
                System.exit(0);
            }
            catch(SingletonException errFerm){
                JOptionPane.showMessageDialog(null,errFerm.getMessage(),"Erreur Fermeture connexion",JOptionPane.ERROR_MESSAGE); 
            }   
        }
    } 
    public JLabel getLabelThread(){
        return labelThread;
    }    
    public void setLabelThread (JLabel labelThread){
        this.labelThread = labelThread;
    } 
    private class MyGestMenuPrincipal implements ActionListener{
        public void actionPerformed(ActionEvent e){
            cont.removeAll();
            cont.add(panneau);
            cont.repaint();  
        }
    } 
    private class MyGestQuitter implements ActionListener{
        public void actionPerformed(ActionEvent e){
            try{
                control.fermerConnection();
                myT.interrupt();
                System.exit(0);
            }
            catch(SingletonException errFerm){
                JOptionPane.showMessageDialog(null,errFerm.getMessage(),"Erreur Fermeture connexion",JOptionPane.ERROR_MESSAGE); 
            }
        }
    }
    private class MyGestMenuDeconnexion implements ActionListener{
        public void actionPerformed(ActionEvent e){
                myT.stop();
                fen.dispose();
                FenetreConnexion form = new FenetreConnexion();
        }
    } 
    public Container getCont(){
        return cont;
    } 
    public JPanel getPanneau(){
        return panneau;
    }
}