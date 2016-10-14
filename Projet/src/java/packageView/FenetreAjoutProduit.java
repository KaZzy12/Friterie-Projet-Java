package packageView;

import packageModel.*;
import packageException.*;
import packageController.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FenetreAjoutProduit {
    private Produit produitAjout, produitModif, prod;
    private JPanel panneauFormulaire, panneauBoutton, panneauTable;
    private JTextField zoneIdentifiant, zoneLibelle, zonePrix, zonePourcReduc, zoneQteStock, zoneQteMinStock;
    private JSpinner dateFinReducSpinner, qte;
    private JCheckBox boxSurgele, boxViandeMitraillette, boxComposant;
    private JComboBox categories, compose;
    private JLabel labelQte, labelCompose;
    private FenetrePrincipale fen;
    private ArrayList <Categorie> listeCategorie = new ArrayList <Categorie> ();
    private ArrayList<Produit> listeProduit = new ArrayList<Produit>(), listeProduitCompo = new ArrayList<Produit>();
    private ArrayList<CompositionProduit> listCompo = new ArrayList<CompositionProduit>();
    private ArrayList<ObjetTableComposantAjout> listeCompoTable = new ArrayList<ObjetTableComposantAjout>();
    private Control control;
    private JButton boutValidation, boutAddCompo, boutSuppCompo;
    private ModelProduitComposant model;
    private JTable tableau;
    private JScrollPane defilant;
    
    public FenetreAjoutProduit (FenetrePrincipale fenetre){
        fen = fenetre;
        control = new Control();
        fen.getCont().removeAll();
        JLabel labelTitre = new JLabel("<html><font size=\"16\"><b>Formulaire d'ajout d'un nouveau produit</b></font><br><br><br></html>");
        panneauTable = new JPanel();
        panneauFormulaire = new JPanel();
        panneauFormulaire.setLayout(new GridLayout(12,2,3,3));
        JLabel labelLibelle = new JLabel("Libelle* : ");
        labelLibelle.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        JLabel labelPrix = new JLabel("Prix* : ");
        labelPrix.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        JLabel labelPourcReduc = new JLabel("% Réduction : ");
        labelPourcReduc.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        JLabel labelQteStock = new JLabel("Quantité en stock : ");
        labelQteStock.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        JLabel labelQteMinStock = new JLabel("Quantité stock minimale : ");
        labelQteMinStock.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        JLabel labelDateFinReduc = new JLabel("Date Fin Réduction : ");
        labelDateFinReduc.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        
        zoneLibelle = new JTextField(20);
        zoneLibelle.setToolTipText("Caractères valides (a-zA-ZàäáâéëèêîïíìôöóòûüúùÀÄÁÂÉËÈÊÎÏÍÌÔÖÓÒÛÜÚÙç -)");
        zonePrix = new JTextField(7);
        zonePrix.setToolTipText("ex : 99999.99");
        zonePourcReduc = new JTextField(4);
        zonePourcReduc.setToolTipText("ex : 100.00");
        zoneQteStock = new JTextField(4);
        zoneQteMinStock = new JTextField(4);

        panneauFormulaire.add(labelLibelle);
        panneauFormulaire.add(zoneLibelle);
        panneauFormulaire.add(labelPrix);
        panneauFormulaire.add(zonePrix);
        panneauFormulaire.add(labelPourcReduc);
        panneauFormulaire.add(zonePourcReduc);
        panneauFormulaire.add(labelQteStock);
        panneauFormulaire.add(zoneQteStock);
        panneauFormulaire.add(labelQteMinStock);
        panneauFormulaire.add(zoneQteMinStock);
        panneauFormulaire.add(labelDateFinReduc);
        
        SpinnerDateModel dateModel;
        Calendar cal = Calendar.getInstance();
        Date now = cal.getTime();
        cal.add(Calendar.YEAR, -100);
        Date startDate = cal.getTime();
        dateFinReducSpinner = new JSpinner(); // Déclaration JSpinner
        dateModel = new SpinnerDateModel(now, startDate, null, Calendar.YEAR); // Déclaration model
        dateFinReducSpinner.setModel(dateModel); // Modification du model
        //Affichage date
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateFinReducSpinner, "dd/MM/yyyy");
        dateFinReducSpinner.setEditor(editor);
        panneauFormulaire.add(dateFinReducSpinner);
        try{
            listeCategorie = getCategories();
            listeProduit = control.getProduits();
        }
        catch (SingletonException se){
            JOptionPane.showMessageDialog(null,se.getMessage(),"Erreur connexion unique",JOptionPane.ERROR_MESSAGE);
        }
        catch (ConnexionException ce){
            JOptionPane.showMessageDialog(null,ce.getMessage(),"Erreur de connexion",JOptionPane.ERROR_MESSAGE);
        }
        String tabCategorie[] = new String[listeCategorie.size()];
        for(Integer j=0;j<listeCategorie.size();j++)
            tabCategorie[j] = listeCategorie.get(j).getLibelle();
        String tabProduit[] = new String[listeProduit.size()];
        for(Integer j=0;j<listeProduit.size();j++)
            tabProduit[j] = listeProduit.get(j).getLibelle();
        JLabel labelCategorie = new JLabel("Catégorie : ");
        labelCategorie.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        categories = new JComboBox(tabCategorie);
        panneauFormulaire.add(labelCategorie);
        panneauFormulaire.add(categories);
        JLabel labelBlanc = new JLabel("");
        boxSurgele = new JCheckBox("Produit surgelé");
        boxViandeMitraillette = new JCheckBox("Viande à mitraillette");
        boxComposant = new JCheckBox("Composé");
        MonGestCompo gestComp = new MonGestCompo();
        boxComposant.addActionListener(gestComp);
        
        panneauFormulaire.add(boxSurgele);
        panneauFormulaire.add(labelBlanc);
        panneauFormulaire.add(boxViandeMitraillette);
        panneauFormulaire.add(labelBlanc);
        panneauFormulaire.add(boxComposant);
        
        // partie Composant
        labelCompose = new JLabel("Composant : ");
        labelCompose.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        compose = new JComboBox(tabProduit);
        labelQte = new JLabel("Quantité : ");
        labelQte.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        SpinnerModel modelNumber = new SpinnerNumberModel(1,1,99,1);
        qte = new JSpinner(modelNumber);
        boutAddCompo = new JButton("Ajouter Composant");
        MonGestAddCompo gestAddCompo = new MonGestAddCompo();
        boutAddCompo.addActionListener(gestAddCompo);
        boutSuppCompo = new JButton("Retirer Composant");
        MonGestSuppCompo gestSuppCompo = new MonGestSuppCompo();
        boutSuppCompo.addActionListener(gestSuppCompo);
        
        panneauBoutton = new JPanel();
        panneauBoutton.setLayout(new FlowLayout());
        
        JButton boutRetour = new JButton("Retour");
        MonGestRetour gestRet = new MonGestRetour();
        boutRetour.addActionListener(gestRet);
        
        JButton boutValidation = new JButton("Valider");
        MonGestValidation gestVal = new MonGestValidation();
        boutValidation.addActionListener(gestVal);
        
        panneauBoutton.add(boutValidation);
        panneauBoutton.add(boutRetour);
        fen.getCont().add(labelTitre);
        fen.getCont().add("North",panneauFormulaire);
        fen.getCont().add("Center",panneauTable);
        fen.getCont().add("South",panneauBoutton);
        fen.getCont().repaint();
        fen.setVisible(true);
    }
    public FenetreAjoutProduit (FenetrePrincipale fenetre, Produit pro){
        fen = fenetre;
        control = new Control();
        fen.getCont().removeAll();
        panneauTable = new JPanel();
        panneauFormulaire = new JPanel();
        panneauFormulaire.setLayout(new GridLayout(12,2,3,3));
        prod = pro;
        JLabel labelTitre = new JLabel("<html><font size=16><b>Formulaire de modification d'un produit</b></font><br><br><br></html>");
        
        JLabel labelLibelle = new JLabel("Libelle* : ");
        labelLibelle.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        JLabel labelPrix = new JLabel("Prix* : ");
        labelPrix.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        JLabel labelPourcReduc = new JLabel("% Réduction : ");
        labelPourcReduc.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        JLabel labelQteStock = new JLabel("Quantité en stock : ");
        labelQteStock.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        JLabel labelQteMinStock = new JLabel("Quantité stock minimale : ");
        labelQteMinStock.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        JLabel labelDateFinReduc = new JLabel("Date Fin Réduction : ");
        labelDateFinReduc.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        
        //panneauFormulaire.add(labelIdentifiant);
        zoneLibelle = new JTextField(20);
        zoneLibelle.setText(pro.getLibelle());
         
        zonePrix = new JTextField(7);
        zonePrix.setText(Double.toString(pro.getPrix()));
         
        zonePourcReduc = new JTextField(4);
        if (pro.getChoixReduc())
            zonePourcReduc.setText(Double.toString(pro.getPourcReduc()));
        
        zoneQteStock = new JTextField(4);
        if (pro.getChoixQte())
            zoneQteStock.setText(Integer.toString(pro.getQuantiteStock()));
       
        zoneQteMinStock = new JTextField(4);
        if (pro.getChoixQte())
            zoneQteMinStock.setText(Integer.toString(pro.getQuantiteStockMin()));

        //panneauFormulaire.add(zoneIdentifiant);
        panneauFormulaire.add(labelLibelle);
        panneauFormulaire.add(zoneLibelle);
        panneauFormulaire.add(labelPrix);
        panneauFormulaire.add(zonePrix);
        panneauFormulaire.add(labelPourcReduc);
        panneauFormulaire.add(zonePourcReduc);
        panneauFormulaire.add(labelQteStock);
        panneauFormulaire.add(zoneQteStock);
        panneauFormulaire.add(labelQteMinStock);
        panneauFormulaire.add(zoneQteMinStock);
        panneauFormulaire.add(labelDateFinReduc);
        SpinnerDateModel dateModel;
        Calendar cal = Calendar.getInstance();
        Date now = cal.getTime();
        cal.add(Calendar.YEAR, -100);
        Date startDate = cal.getTime();
        dateFinReducSpinner = new JSpinner(); // Déclaration JSpinner
        dateModel = new SpinnerDateModel(now, startDate, null, Calendar.YEAR); // Déclaration model
        dateFinReducSpinner.setModel(dateModel); // Modification du model
        //Affichage date
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateFinReducSpinner, "dd/MM/yyyy");
        dateFinReducSpinner.setEditor(editor);
        if (pro.getDateFinReduc() != null){
            Date date = new Date(pro.getDateFinReduc().getTimeInMillis());
            dateFinReducSpinner.setValue(date);
        }
        else
            dateFinReducSpinner.setValue(new Date());
            panneauFormulaire.add(dateFinReducSpinner);
        try{
            listeCategorie = getCategories();
            listeProduit = control.getProduits();
        }
        catch (SingletonException se){
            JOptionPane.showMessageDialog(null,se.getMessage(),"Erreur de connexion unique",JOptionPane.ERROR_MESSAGE);
        }
        catch (ConnexionException ce){
            JOptionPane.showMessageDialog(null,ce.getMessage(),"Erreur de connexion",JOptionPane.ERROR_MESSAGE);
        }
        String tabCategorie[] = new String[listeCategorie.size()];
        for(Integer j=0;j<listeCategorie.size();j++){
            tabCategorie[j] = listeCategorie.get(j).getLibelle();
        }
        String tabProduit[] = new String[listeProduit.size()];
        for(Integer j=0;j<listeProduit.size();j++){
            tabProduit[j] = listeProduit.get(j).getLibelle();
        }
        
        categories = new JComboBox(tabCategorie);
        categories.setSelectedItem(pro.getCategorie().getLibelle());
        JLabel labelCategorie = new JLabel("Catégorie : ");
        labelCategorie.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        panneauFormulaire.add(labelCategorie);
        panneauFormulaire.add(categories);
        
        boxSurgele = new JCheckBox("Produit surgelé");
        if (pro.getSurgele())
            boxSurgele.setSelected(true);
 
        boxViandeMitraillette = new JCheckBox("Viande à mitraillette");
        
        if (pro.getViandeMitraillette())
            boxViandeMitraillette.setSelected(true);
        boxComposant = new JCheckBox("Composé");
        MonGestCompo gestComp = new MonGestCompo();
        boxComposant.addActionListener(gestComp);

        try{
            listCompo = control.getCompoProd(pro.getId());
        }
        catch(ConnexionException e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
        }
        catch(SingletonException e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
        }
        JLabel labelBlanc = new JLabel("");
        panneauFormulaire.add(boxSurgele);
        panneauFormulaire.add(labelBlanc);
        panneauFormulaire.add(boxViandeMitraillette);
        panneauFormulaire.add(labelBlanc);
        panneauFormulaire.add(boxComposant);
        
        // partie Composant
        labelCompose = new JLabel("Composant : ");
        labelCompose.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        compose = new JComboBox(tabProduit);
        labelQte = new JLabel("Quantité : ");
        labelQte.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        SpinnerModel modelNumber = new SpinnerNumberModel(1,1,99,1);
        qte = new JSpinner(modelNumber);
        boutAddCompo = new JButton("Ajouter Composant");
        boutSuppCompo = new JButton("Retirer Composant");
        MonGestSuppCompo gestSuppCompo = new MonGestSuppCompo();
        MonGestAddCompo gestAddCompo = new MonGestAddCompo();
        boutAddCompo.addActionListener(gestAddCompo);
        boutSuppCompo.addActionListener(gestSuppCompo); 
        if (!listCompo.isEmpty()){
            boxComposant.doClick();
            for (int i = 0; i<listCompo.size(); i++){
                listeProduitCompo.add(listCompo.get(i).getComposant());
                ObjetTableComposantAjout OProd =new ObjetTableComposantAjout(listeProduitCompo.get(i).getLibelle(), listCompo.get(i).getQte(), listeProduitCompo.get(i).getId());
                listeCompoTable.add(OProd);
            }
            model = new ModelProduitComposant(listeCompoTable);
            tableau = new JTable(model);
            tableau.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);  
            tableau.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            defilant = new JScrollPane(tableau);
            panneauTable.removeAll();
            fen.getCont().repaint();
            panneauTable.add(defilant);
            
            fen.getCont().revalidate();
        }
        
        panneauBoutton = new JPanel();
        panneauBoutton.setLayout(new FlowLayout());
        
        JButton boutRetour = new JButton("Retour");
        MonGestRetourModif gestRet = new MonGestRetourModif();
        boutRetour.addActionListener(gestRet);
        
        JButton boutValidation = new JButton("Valider");
        MonGestModification gestModif = new MonGestModification();
        boutValidation.addActionListener(gestModif);
        
        panneauBoutton.add(boutValidation);
        panneauBoutton.add(boutRetour);
        fen.getCont().add("North", labelTitre);
        fen.getCont().add("North",panneauFormulaire);
        fen.getCont().add("Center",panneauTable);
        fen.getCont().add("South",panneauBoutton);
        fen.getCont().repaint();
        fen.setVisible(true);
    }
    
     private class MonGestModification implements ActionListener{
        public void actionPerformed(ActionEvent e){
            //CREATION PRODUIT
            Integer identifiant = prod.getId();
            String libelle = zoneLibelle.getText();
            String prix = (zonePrix.getText());
            String pourcReduc = zonePourcReduc.getText();
            if (pourcReduc.isEmpty())
                pourcReduc = null;
            String qteStock = zoneQteStock.getText();
            if (qteStock.isEmpty())
                qteStock = null;
            String qteMinStock = zoneQteMinStock.getText();
            if (qteMinStock.isEmpty())
                qteMinStock = null;
            boolean surgele = boxSurgele.isSelected();
            boolean viandeMitraillette = boxViandeMitraillette.isSelected();
            GregorianCalendar dateFinReduc;
            dateFinReduc = new GregorianCalendar();
            dateFinReduc.setTime((Date) dateFinReducSpinner.getValue());
            Integer indiceCategorie = categories.getSelectedIndex();
            Categorie categorieProduit = listeCategorie.get(indiceCategorie);
            
            Boolean reducChoix = true;
            Boolean qteChoix = true;
            
            if (pourcReduc == null){
                reducChoix = false;
            }
            if (qteStock == null || qteMinStock == null){
                qteChoix = false;
            } 
            try {
                if (reducChoix == true && qteChoix == true){//constructeur full
                    produitModif = new Produit(qteStock,  qteMinStock,  libelle,  prix,  pourcReduc,  dateFinReduc,  surgele,  categorieProduit,  viandeMitraillette);
                }                
                else{
                    if (reducChoix == true) //constructeur sans qte
                        produitModif = new Produit (libelle, prix, pourcReduc, dateFinReduc, surgele, categorieProduit, viandeMitraillette);
                    else{
                        if (qteChoix == true) //constructeur sans reduc
                            produitModif = new Produit (qteStock, qteMinStock, libelle, prix, surgele, categorieProduit, viandeMitraillette);
                        else //constructeur sans qte ni reduc{
                            produitModif = new Produit (libelle, prix, surgele, categorieProduit, viandeMitraillette);
                    }
                }
                control.updProduit(produitModif, identifiant); /// NEED UNE METHODE POUR Modif PRODUIT
                
                if (boxComposant.isSelected()){
                    Integer iProdComp = compose.getSelectedIndex();
                    Produit prodCompose = listeProduit.get(iProdComp);
                    for (int i = 0; i < model.getRowCount(); i++){ 
                        control.delComposition(identifiant);
                        control.setComposition(produitModif, listeCompoTable.get(i).getId() , listeCompoTable.get(i).getQte());  
                    }  
                } 
                JOptionPane.showMessageDialog(null,"La Modification s'est déroulée avec succès !","Confirmation modification",JOptionPane.INFORMATION_MESSAGE);
            }
            catch (SingletonException se){
                JOptionPane.showMessageDialog(null,se.getMessage(),"Erreur de connexion unique",JOptionPane.ERROR_MESSAGE);
            }
            catch (ConnexionException ce){
                JOptionPane.showMessageDialog(null,ce.getMessage(),"Erreur de connexion",JOptionPane.ERROR_MESSAGE);
            }
            catch (ProduitDataAccessException ede){
                JOptionPane.showMessageDialog(null,ede.getMessage(),"Erreur Composant",JOptionPane.ERROR_MESSAGE);
            }
            catch(ProduitLibException ple){
                JOptionPane.showMessageDialog(null,ple.getMessage(),"Erreur libelle",JOptionPane.ERROR_MESSAGE);
            }
            catch(ProduitPourcReducException ppre){
                JOptionPane.showMessageDialog(null,ppre.getMessage(),"Erreur pourcentage réduction",JOptionPane.ERROR_MESSAGE);
            } 
            catch(ProduitPrixException ppe){
                JOptionPane.showMessageDialog(null,ppe.getMessage(),"Erreur prix",JOptionPane.ERROR_MESSAGE);
            }
            catch(ProduitQteStockException pqs){
                JOptionPane.showMessageDialog(null,pqs.getMessage(),"Erreur quantité en stock",JOptionPane.ERROR_MESSAGE);
            } 
            catch(ProduitQteStockMinException pqsm){
                JOptionPane.showMessageDialog(null,pqsm.getMessage(),"Erreur quantité minimale en stock",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
     
    public ArrayList<Categorie> getCategories() throws SingletonException, ConnexionException{ //Obtenir la liste des categories dans la base de données
        return control.getCategories(); 
    }  
    private class MonGestRetour implements ActionListener{
        public void actionPerformed(ActionEvent e){
            fen.getCont().removeAll();
            fen.getCont().add(fen.getPanneau());
            fen.getCont().repaint();  
        }
    } 
    private class MonGestRetourModif implements ActionListener{
        public void actionPerformed(ActionEvent e){
            fen.getCont().removeAll();
            FenetreSuprimerProduit form = new FenetreSuprimerProduit(fen); 
        }
    } 
    private class MonGestCompo implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(boxComposant.isSelected()){
                panneauFormulaire.add(labelCompose);
                panneauFormulaire.add(compose);
                panneauFormulaire.add(labelQte);
                panneauFormulaire.add(qte);
                panneauFormulaire.add(boutAddCompo);
                panneauFormulaire.add(boutSuppCompo);
            }
            else{
                panneauFormulaire.remove(labelCompose);
                panneauFormulaire.remove(compose);
                panneauFormulaire.remove(labelQte);
                panneauFormulaire.remove(qte);
                panneauFormulaire.remove(boutAddCompo);
                panneauFormulaire.remove(boutSuppCompo);
                panneauTable.removeAll();
            }
            fen.getCont().revalidate();
            fen.getCont().repaint();
        }
    }
    private class MonGestSuppCompo implements ActionListener{
        public void actionPerformed(ActionEvent e){
            int compoASup = tableau.getSelectedRow();
            listeCompoTable.remove(compoASup);
            
            model = new ModelProduitComposant(listeCompoTable);
            tableau = new JTable(model);
            tableau.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);  
            tableau.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            defilant = new JScrollPane(tableau);
            panneauTable.removeAll();
            fen.getCont().repaint();
            panneauTable.add(defilant);
            fen.getCont().revalidate();
        }
    }
    private class MonGestAddCompo implements ActionListener{
        public void actionPerformed(ActionEvent e){
            boolean checkDeja = false;
            Integer iProdComp = compose.getSelectedIndex(), qteProd;
            listeProduitCompo.add(listeProduit.get(iProdComp));
            qteProd = (int)qte.getValue();
            ObjetTableComposantAjout OProd =new ObjetTableComposantAjout(listeProduit.get(iProdComp).getLibelle(), qteProd, listeProduit.get(iProdComp).getId());
            
            for (int i = 0; i < listeCompoTable.size(); i++){
                if (listeCompoTable.get(i).getLibelle().compareTo(OProd.getLibelle()) == 0)
                    checkDeja = true;
            }
            if (!checkDeja){
                listeCompoTable.add(OProd);

                model = new ModelProduitComposant(listeCompoTable);
                tableau = new JTable(model);
                tableau.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);  
                tableau.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                defilant = new JScrollPane(tableau);
                panneauTable.removeAll();
                fen.getCont().repaint();
                panneauTable.add(defilant);

                fen.getCont().revalidate();
                //fen.getCont().repaint();
            }
            else
                JOptionPane.showMessageDialog(null,"Ce produit est déjà présent dans la liste de composant","Erreur",JOptionPane.ERROR_MESSAGE);
        }
    }
    private class MonGestValidation implements ActionListener{
        public void actionPerformed(ActionEvent e){
            //CREATION PRODUIT
            //Integer identifiant = Integer.parseInt(zoneIdentifiant.getText());
            String libelle = zoneLibelle.getText();
            
            String prix = (zonePrix.getText());
            String pourcReduc = zonePourcReduc.getText();
            String qteStock = zoneQteStock.getText();
            String qteMinStock = zoneQteMinStock.getText();
            Boolean surgele = boxSurgele.isSelected();
            Boolean viandeMitraillette = boxViandeMitraillette.isSelected();
            GregorianCalendar dateFinReduc;
            dateFinReduc = new GregorianCalendar();
            dateFinReduc.setTime((Date) dateFinReducSpinner.getValue());
            
            Integer indiceCategorie = categories.getSelectedIndex();
            Categorie categorieProduit = listeCategorie.get(indiceCategorie);
            
            Boolean reducChoix = true;
            Boolean qteChoix = true;
            
            if (pourcReduc.length() == 0)
                reducChoix = false;
            if (qteStock.length() == 0 || qteMinStock.length() == 0)
                qteChoix = false;
            try {
                if (reducChoix == true && qteChoix == true)//constructeur full
                    produitAjout = new Produit(qteStock,  qteMinStock,  libelle,  prix,  pourcReduc,  dateFinReduc,  surgele,  categorieProduit,  viandeMitraillette);
                else{
                    if (reducChoix == true) //constructeur sans qte
                        produitAjout = new Produit (libelle, prix, pourcReduc, dateFinReduc, surgele, categorieProduit, viandeMitraillette);
                    else{
                        if (qteChoix == true) //constructeur sans reduc
                            produitAjout = new Produit (qteStock, qteMinStock, libelle, prix, surgele, categorieProduit, viandeMitraillette);
                        else //constructeur sans qte ni reduc
                            produitAjout = new Produit (libelle, prix, surgele, categorieProduit, viandeMitraillette);
                    }
                }
                control.setProduit(produitAjout);
                if (boxComposant.isSelected()){
                    Integer iProdComp = compose.getSelectedIndex();
                    Produit prodCompose = listeProduit.get(iProdComp);
                    for (int i = 0; i < model.getRowCount(); i++) 
                        control.setComposition(produitAjout, listeCompoTable.get(i).getId() , listeCompoTable.get(i).getQte());    
                }    
                JOptionPane.showMessageDialog(null,"L'ajout s'est déroulé avec succès !","Confirmation ajout",JOptionPane.INFORMATION_MESSAGE);
            }
            catch (SingletonException se){
                JOptionPane.showMessageDialog(null,se.getMessage(),"Erreur de connection unique",JOptionPane.ERROR_MESSAGE);
            }
            catch (ConnexionException ce){
                JOptionPane.showMessageDialog(null,ce.getMessage(),"Erreur de connection",JOptionPane.ERROR_MESSAGE);
            }
            catch (ProduitDataAccessException ede){
                JOptionPane.showMessageDialog(null,ede.getMessage(),"Erreur composant",JOptionPane.ERROR_MESSAGE);
            } 
            catch(ProduitLibException ple){
                JOptionPane.showMessageDialog(null,ple.getMessage(),"Erreur libelle",JOptionPane.ERROR_MESSAGE);
            }
            catch(ProduitPourcReducException ppre){
                JOptionPane.showMessageDialog(null,ppre.getMessage(),"Erreur pourcentage réduction",JOptionPane.ERROR_MESSAGE);
            }
            catch(ProduitPrixException ppe){
                JOptionPane.showMessageDialog(null,ppe.getMessage(),"Erreur prix",JOptionPane.ERROR_MESSAGE);
            }     
            catch(ProduitQteStockException pqs){
                JOptionPane.showMessageDialog(null,pqs.getMessage(),"Erreur quantité en stock",JOptionPane.ERROR_MESSAGE);
            }    
            catch(ProduitQteStockMinException pqsm){
                JOptionPane.showMessageDialog(null,pqsm.getMessage(),"Erreur quantité minimale en stock",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}