package packageModel;

import packageException.*;
import java.util.*;

public class Produit {
    private Integer id, quantiteStock, quantiteStockMin, idCat, nombre;
    private String libelle;
    private Double prix, pourcReduc;
    private GregorianCalendar dateFinReduc;
    private Boolean surgele, viandeMitraillette, choixReduc, choixQte;
    private Categorie categorie;
    
    public Produit (String qS, String qSM, String lib, String p, String pR, GregorianCalendar d, Boolean s, Categorie cat, Boolean cM) throws ProduitLibException, ProduitQteStockException, ProduitQteStockMinException, ProduitPrixException, ProduitPourcReducException{  
        setQuantiteStock(qS);
        setQuantiteStockMin(qSM);
        setLibelle(lib);
        setPrix(p);
        setPourcReduc(pR);
        dateFinReduc = d;
        surgele = s;
        categorie = cat;
        viandeMitraillette = cM;
        choixReduc = true;
        choixQte = true;   
    }
    // sans reduction ( pourcentage et date )
    public Produit (String qS, String qSM, String lib, String p, Boolean s, Categorie cat, Boolean cM) throws ProduitLibException, ProduitQteStockException, ProduitQteStockMinException, ProduitPrixException{      
        setQuantiteStock(qS);
        setQuantiteStockMin(qSM);
        setLibelle(lib);
        setPrix(p);
        surgele = s;
        categorie = cat;
        viandeMitraillette = cM;
        choixQte = true;
        choixReduc = false;
    }
    // sans qte ( en stock et minimale )
    public Produit (String lib, String p, String pR, GregorianCalendar d, Boolean s, Categorie cat, Boolean cM) throws ProduitLibException, ProduitPrixException, ProduitPourcReducException{
       setLibelle(lib);
        setPrix(p);
        setPourcReduc(pR);
        dateFinReduc = d;
        surgele = s;
        categorie = cat;
        viandeMitraillette = cM;
        choixReduc = true;
        choixQte = false;
    }
    // sans qte ni reduction
    public Produit (String lib, String p, boolean s, Categorie cat, boolean cM) throws ProduitLibException, ProduitPrixException{   
        setLibelle(lib);
        setPrix(p);
        surgele = s;
        categorie = cat;
        viandeMitraillette = cM;
        choixReduc = false;
        choixQte = false;
    }
    // prod venant de la bd
    public Produit (Integer i, Integer qS, Integer qSM, String lib, Double p, Double pR, GregorianCalendar d, Boolean s, Categorie cat, Boolean cM){
        id = i;
        quantiteStock = qS;
        quantiteStockMin = qSM;
        libelle = lib;
        prix = p;
        pourcReduc = pR;
        dateFinReduc = d;
        surgele = s;
        categorie = cat;
        viandeMitraillette = cM;
        if (pR == null)
            choixReduc = false;
        else 
            choixReduc = true;
        if (qS == null || qSM == null)
            choixQte = false;
        else
            choixQte = true;     
    }    
    //prod Thread 
    public Produit (Integer i, Integer qS, Integer qSM, String lib, Double p, Double pR, GregorianCalendar d, Boolean s, int cat, Boolean cM){
        id = i;
        quantiteStock = qS;
        quantiteStockMin = qSM;
        libelle = lib;
        prix = p;
        pourcReduc = pR;
        dateFinReduc = d;
        surgele = s;
        idCat = cat;
        viandeMitraillette = cM;
    }
    public void setNb(int nb){
        nombre = nb;
    }   
    public void setId(int i){
        id = i; //id = controller.getId >> business >> dataaccess. Select
    } 
    public void setQuantiteStock(String qS) throws ProduitQteStockException{
        Integer numErr = 1; // si rien est entré
        if(qS.length() == 0){
            throw new ProduitQteStockException(numErr);
        }
        if(qS.length() > 4){
            numErr = 0; // si chaine trop grande 
            throw new ProduitQteStockException(numErr);
        }
        String exprReg = "[0-9]+"; 
        if(!qS.matches(exprReg)){
            numErr = 2; // si contient carct invalide
            throw new ProduitQteStockException(numErr);
        }        
        quantiteStock = Integer.parseInt(qS); 
    }
    public void setQuantiteStockMin(String qSM) throws ProduitQteStockMinException{
        Integer numErr = 1; // si rien est entré
        if(qSM.length() == 0){
            throw new ProduitQteStockMinException(numErr);
        }
        if(qSM.length() > 4){
            numErr = 0; // si chaine trop grande 
            throw new ProduitQteStockMinException(numErr);
        }
        String exprReg = "[0-9]+";  
        if(!qSM.matches(exprReg)){
            numErr = 2; // si contient carct invalide
            throw new ProduitQteStockMinException(numErr);
        }
        quantiteStockMin = Integer.parseInt(qSM); 
    }
    public void setLibelle(String lib) throws ProduitLibException{
        Integer numErr = 0;
        String exprReg = "[a-zA-ZàäáâéëèêîïíìôöóòûüúùÀÄÁÂÉËÈÊÎÏÍÌÔÖÓÒÛÜÚÙç -]+";
        // si le libelle est trop grand
        if(lib.length() > 30){
            throw new ProduitLibException(numErr);
        }
        // si rien n'est retourné
        numErr = 1;
        if(lib.length() == 0){
            throw new ProduitLibException(numErr);
        }
        // verifie si plusieurs espaces ou tirets consécutifs
        numErr = 2;
        if(!verifChaine(lib)){
            throw new ProduitLibException(numErr);
        }
        // ne peux contenir que les caractères suivants : a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,é,è,ê,ë,ô,ò,ó,ö,î,ì,ï,í,à,â,á,ä,ù,û,ú,ü,ç,- 
        if(!lib.matches(exprReg)){
            throw new ProduitLibException(numErr);
        }
        libelle = lib; 
    }
    public void setPrix(String p) throws ProduitPrixException{
        Integer numErr = 0, nbVirg = 0; // rien en entré
        String ch1, ch2, exprReg = "[0-9.]+", exprRegNum = "[0-9]+";
        if (p.length() == 0)
            throw new ProduitPrixException(numErr);
        numErr = 1;  // chaine trop longue      
        if (p.length() > 8)// 8 car on compte la virgule
            throw new ProduitPrixException(numErr);          
        numErr = 2;// caract invalide
        if(!p.matches(exprReg))
            throw new ProduitPrixException(numErr);  
        numErr = 3; // ',' au début ou a la fin 
        if(p.charAt(0) == '.' || p.charAt(p.length()-1) == '.')
            throw new ProduitPrixException(numErr);
        numErr = 4; // format non repecté   
        int posVirg = 0;
        for (int i=0; i < p.length()-1 ; i++){
            if (p.charAt(i) == '.'){ // compter les virgules pour etre sur qu il n y en ai qu une  
                nbVirg++;
                posVirg = i;
            }
        }
        if (nbVirg > 1)
            throw new ProduitPrixException(numErr);
        if (nbVirg == 1){
            ch1 = p.substring(0, posVirg);
            ch2 = p.substring(posVirg, p.length());
            if (ch1.length() > 5)
                throw new ProduitPrixException(1);
            if (!ch1.matches(exprRegNum) ) // check ch1 composé uniquement de chiffre
                throw new ProduitPrixException(numErr);
            if (ch2.length() > 3)
                throw new ProduitPrixException(1); 
        }
        else{
            if(!p.matches(exprRegNum))
                throw new ProduitPrixException(numErr);
        }
        prix = Double.parseDouble(p); 
    }
    public void setPourcReduc(String pR) throws ProduitPourcReducException{
        Integer numErr = 0, nbVirg = 0; // rien en entré
        String ch1, ch2, exprReg = "[0-9.]+", exprRegNum = "[0-9]+";
        if (pR.length() == 0)
            throw new ProduitPourcReducException(numErr);
        numErr = 1;  // chaine trop longue      
        if (pR.length() > 5)// 5 car on compte la virgule
            throw new ProduitPourcReducException(numErr);            
        numErr = 2;// caract invalide
        if(!pR.matches(exprReg))
            throw new ProduitPourcReducException(numErr);
        numErr = 3; // ',' au début ou a la fin 
        if(pR.charAt(0) == '.' || pR.charAt(pR.length()-1) == '.')
            throw new ProduitPourcReducException(numErr);
        numErr = 4; // format non repecté       
        int posVirg = 0;
        for (int i=0; i < pR.length()-1 ; i++){
            if (pR.charAt(i) == '.'){ // compter les virgules pour etre sur qu il n y en ai qu une 
                nbVirg++;
                posVirg = i;
            }
        }
        if (nbVirg > 1)
            throw new ProduitPourcReducException(numErr);      
        if (nbVirg == 1){
            ch1 = pR.substring(0, posVirg);
            ch2 = pR.substring(posVirg, pR.length());
            if (ch1.length() > 3)
                throw new ProduitPourcReducException(1);
            if (!ch1.matches(exprRegNum) ) // check ch1 composé uniquement de chiffre
                throw new ProduitPourcReducException(numErr);
            if (ch2.length() > 3)
                throw new ProduitPourcReducException(1);
        }
        else{
            if(!pR.matches(exprRegNum))
                throw new ProduitPourcReducException(numErr);
        }
        double pRDouble = Double.valueOf(pR);
        numErr = 5;
        if(pRDouble > 100.0)
            throw new ProduitPourcReducException(numErr);
        pourcReduc = Double.parseDouble(pR); 
    }   
    public Boolean verifChaine(String chaine){
        Boolean verif = true;
        String exprCaract = "[a-zA-ZàäáâéëèêîïíìôöóòûüúùÀÄÁÂÉËÈÊÎÏÍÌÔÖÓÒÛÜÚÙç]+";
        for(int i=0; i<chaine.length();i++){
            if(chaine.charAt(i) == ' ' || chaine.charAt(i) == '-'){
                if( (i+1) < chaine.length()){ // si ce n'est pas le dernier caractère
                    // Vérifier si le suivant est aussi un espace ou un tiret
                    if(!(chaine.charAt(i+1)+"").matches(exprCaract))
                        verif = false;
                }
            }
        }
        // Si le premier ou dernier caractère est un espace ou un tiret, alors erreur
        if(chaine.charAt(0) == ' ' || chaine.charAt(0) == '-'|| chaine.charAt(chaine.length()-1) == ' ' || chaine.charAt(chaine.length()-1) == '-'){
            verif = false;
        }
        return verif;
    }
   
    public int getId(){
        return id;
    }
    public String getLibelle(){
        return libelle;
    }
    public Double getPrix(){
        return prix;
    }
    public Boolean getSurgele(){
        return surgele;
    }
    public Categorie getCategorie(){
        return categorie;
    }
    public Integer getQuantiteStock(){
        return quantiteStock;
    }
    public Integer getQuantiteStockMin(){
        return quantiteStockMin;
    }
    public Double getPourcReduc(){
        return pourcReduc;
    }
    public GregorianCalendar getDateFinReduc(){
        return dateFinReduc;
    }
    public Boolean getViandeMitraillette(){
        return viandeMitraillette;
    }
    public Boolean getChoixQte(){
        return choixQte;
    }
    public Boolean getChoixReduc(){
        return choixReduc;
    }
    public Integer getNombre(){
        return nombre;
    }
    public void setNombre(Integer nb){
        nombre = nb;
    }
    public void setChoixReduc(boolean b){
        choixReduc = b;
    }
    public void setChoixQte(boolean b){
        choixQte = b;
    }
}