package packageView;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import packageController.*;
import packageException.*;
import packageModel.*;

public class FenetreConnexion extends JFrame{
    private Container cont;
    private JLabel labelIdentifiant, labelMDP, labelVide;
    private JTextField zoneTexteIdentifiant;
    private JPasswordField zoneMDP;
    private JButton boutonConnexion;
    private Utilisateur util;
    private Control control;
    public static String login;
    private final Image icone;
    
    public FenetreConnexion(){
        super("Connexion");
        setBounds(100,100,300,200);
        addWindowListener(new WindowAdapter()
            {public void windowClosing(WindowEvent e) {System.exit(0);} });
        
        // icone 
        icone = Toolkit.getDefaultToolkit().getImage("iconeFriterie.png");
        this.setIconImage(icone);
        
        control = new Control();
        
        labelIdentifiant = new JLabel("Identifiant : ");
        labelMDP = new JLabel("Mot de passe : ");
        
        zoneTexteIdentifiant = new JTextField(15);
        zoneMDP = new JPasswordField(20);
        
        labelVide = new JLabel(" ");
        
        cont = getContentPane();
        cont.setLayout(new GridLayout(3,2,1,1));
        cont.add(labelIdentifiant);
        cont.add(zoneTexteIdentifiant);
        cont.add(labelMDP);
        cont.add(zoneMDP);
        cont.add(labelVide);
        
        boutonConnexion = new JButton ("Connexion");
        GestionBouton g = new GestionBouton();
        boutonConnexion.addActionListener(g);
        cont.add(boutonConnexion);
        
        setVisible(true);
    }
    private class GestionBouton implements ActionListener{
        public void actionPerformed (ActionEvent e){
            connexion();
        }
    }
    public void connexion(){
        try {
            Utilisateur user = new Utilisateur (zoneTexteIdentifiant.getText(),new String(zoneMDP.getPassword()));
            util = control.getConnect(user);
            
            login = util.getLogin();
            cont.removeAll();
            FenetreConnexion.this.setVisible(false);
            FenetrePrincipale fp = new FenetrePrincipale();
            fp.setVisible(true);   
        }
        catch (SingletonException ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Erreur de connexion unique",JOptionPane.ERROR_MESSAGE);
        }
        catch (ConnexionException ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Erreur de connexion",JOptionPane.ERROR_MESSAGE);
        }
        catch(NullPointerException e)
        {
            JOptionPane.showMessageDialog(null,"Login/Mot de passe incorrect !","Erreur",JOptionPane.ERROR_MESSAGE);
        }
    } 
}