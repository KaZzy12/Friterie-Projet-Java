package packageView;

import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import packageController.Control;
import packageModel.*;
import packageException.*;

public class MyThread extends Thread {
    private final FenetrePrincipale fen;
    private ArrayList<Produit> listProd;
    private Control control;
    private JLabel labQte;
    
    public MyThread (FenetrePrincipale fen){
        super("ThreadProjet");
        this.fen = fen;
        control = new Control();
    }
    
    public void run(){
        listProd = new ArrayList<Produit> ();
        while  (true){
            try{
                Thread.sleep(3000);
                listProd = control.getProduitsQteMin();
                String ch = "<html><br><br><br><br><br><br><br><br><br><br><div color='red'>Attention, il reste ";
                if (listProd != null){
                    for (int i = 0; i < listProd.size(); i++){
                        ch += listProd.get(i).getQuantiteStock()+" "+listProd.get(i).getLibelle()+" ";
                        labQte = new JLabel(ch);
                        fen.getPanneau().remove(fen.getLabelThread());
                        fen.setLabelThread(labQte);
                        fen.getPanneau().add(fen.getLabelThread());
                        fen.getPanneau().repaint();
                        fen.validate();
                    }
                }
                else{
                    fen.getPanneau().remove(fen.getLabelThread());
                    fen.getPanneau().repaint();
                }
            }
            catch (ConnexionException c){
                JOptionPane.showMessageDialog(null,c.getMessage(),"Erreur connexion unique",JOptionPane.ERROR_MESSAGE);
            }
            catch (SingletonException s){
                JOptionPane.showMessageDialog(null,s.getMessage(),"Erreur connexion unique",JOptionPane.ERROR_MESSAGE);
            }
            catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null," Problème dans le Thread !","Erreur",JOptionPane.ERROR_MESSAGE);
            } 
        }       
    }
}