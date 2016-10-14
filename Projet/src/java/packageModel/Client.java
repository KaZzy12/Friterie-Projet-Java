
package packageModel;
import packageException.*;

public class Client {    
    private Integer id, codePostal;
    private String nom, prenom, rue, numero, ville;
     //////////   CONSTRUCTEUR    //////////
    public Client (int i, String cP, String n, String pre, String r, String num, String v ) throws ClientNomException, ClientPrenomException,  ClientCodePostalException, ClientRueException, ClientVilleException, ClientNumRueException{
        setId(i);
        setCodePostal(cP);
        setNom(n);
        setPrenom(pre);
        setRue(r);
        setNumero(num);
        setVille(v);
    }
    // sans adresse
    public Client (int i, String n, String pre) throws ClientNomException, ClientPrenomException{
        setId(i);
        setNom(n);
        setPrenom(pre);
    }
    //Provenant de la BD
    public Client (int id, String nom, String prenom, String rue, String numero, int codePostal, String ville){
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.rue = rue;
        this.numero = numero;
        this.codePostal = codePostal;
        this.ville = ville;
    } 
    //////////  setteurs / getteurs ////////  
    public void setId (int i){
        id = i; // autoincrementation
    }
    
    public void setCodePostal (String cP) throws ClientCodePostalException{
        // Doit être compris entre 1000 et 9999
        Integer numErr = 1;
        if(cP.length() == 0){
            throw new ClientCodePostalException(numErr);
        }
        numErr = 0;
        if(cP.length() != 4){
            throw new ClientCodePostalException(numErr);
        }
        else{
            String exprReg = "[0-9]+";
            if(!cP.matches(exprReg)){
                throw new ClientCodePostalException(numErr);
            }
            else{
                if(cP.compareTo("1000") < 0){
                    throw new ClientCodePostalException(numErr);
                }
            }
            codePostal = Integer.parseInt(cP); 
        }
    }
    
    public void setNom (String n) throws ClientNomException{            
        Integer numErr = 0;
        String exprReg = "[a-zA-ZàäáâéëèêîïíìôöóòûüúùÀÄÁÂÉËÈÊÎÏÍÌÔÖÓÒÛÜÚÙç -]+";
        // si le nom est trop grand
        if(n.length() > 20){
            throw new ClientNomException(numErr);
        }
        // si rien n'est retourné
        numErr = 1;
        if(n.length() == 0){
            throw new ClientNomException(numErr);
        }
        // verifie si plusieurs espaces ou tirets consécutifs
        numErr = 2;
        if(!verifChaine(n)){
            throw new ClientNomException(numErr);
        }
        // ne peux contenir que les caractères suivants : a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,é,è,ê,ë,ô,ò,ó,ö,î,ì,ï,í,à,â,á,ä,ù,û,ú,ü,ç,- 
        if(!n.matches(exprReg)){
            throw new ClientNomException(numErr);
        }
        nom = n;
    }
    
    public void setPrenom (String pre) throws ClientPrenomException{
        Integer numErr = 0;
        String exprReg = "[a-zA-ZàäáâéëèêîïíìôöóòûüúùÀÄÁÂÉËÈÊÎÏÍÌÔÖÓÒÛÜÚÙç -]+";
        // si le prenom est trop grand
        if(pre.length() > 20){
            throw new ClientPrenomException(numErr);
        }
        // si rien n'est retourné
        numErr = 1;
        if(pre.length() == 0){
            throw new ClientPrenomException(numErr);
        }
        // verifie si plusieurs espaces ou tirets consécutifs
        numErr = 2;
        if(!verifChaine(pre)){
            throw new ClientPrenomException(numErr);
        }
        // ne peux contenir que les caractères suivants : a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,é,è,ê,ë,ô,ò,ó,ö,î,ì,ï,í,à,â,á,ä,ù,û,ú,ü,ç,- 
        if(!pre.matches(exprReg)){
            throw new ClientPrenomException(numErr);
        }
        prenom = pre; // voir check nom
    }
    
    public void setRue (String r) throws ClientRueException{
        Integer numErr = 0;
        String exprReg = "[a-zA-ZàäáâéëèêîïíìôöóòûüúùÀÄÁÂÉËÈÊÎÏÍÌÔÖÓÒÛÜÚÙç -]+";
        // si la rue est trop grande (taille de la chaine)
        if(r.length() > 20){
            throw new ClientRueException(numErr);
        }
        // si rien n'est retourné
        numErr = 1;
        if(r.length() == 0){
            throw new ClientRueException(numErr);
        }
        // verifie si plusieurs espaces ou tirets consécutifs
        numErr = 2;
        if(!verifChaine(r)){
            throw new ClientRueException(numErr);
        }
        // ne peux contenir que les caractères suivants : a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,é,è,ê,ë,ô,ò,ó,ö,î,ì,ï,í,à,â,á,ä,ù,û,ú,ü,ç,- 
        if(!r.matches(exprReg)){
            throw new ClientRueException(numErr);
        }
        rue = r; 
    }
    
    public void setNumero (String num) throws ClientNumRueException{
        // Vérification du numéro dans la rue
        // Ne peut contenir que des chiffres (maximum 3 chiffres) ainsi qu'une lettre !
        Integer numErr = 1;
        String exprRegChiffre ="[0-9]+";
        String exprRegLettre = "[a-zA-Z0-9]";
        if(numero.length() == 0){
            throw new ClientNumRueException(numErr);
        }
        numErr = 0;
        if(numero.length() > 5){
            throw new ClientNumRueException(numErr);
        }
        else{
            if(numero.length() == 4){
                if(numero.substring(3,4).matches(exprRegChiffre)){
                    throw new ClientNumRueException(numErr);
                }
            }
            String partie1 = " ",partie2 = " ";
            if(numero.length() == 1 || numero.length() == 2){
                // partie 1 = les caractères jusqu'au dernier NON INCLUS (Ne dois contenir que des chiffres)
                partie1 = numero.substring(0,1);
            }
            else
                partie1 = numero.substring(0,numero.length()-1);
            // partie 2 = Dernier caractère ( pour tester que c'est bien un chiffre OU une lettre)
            partie2 = numero.substring(numero.length()-1,numero.length());
            if(!partie1.matches(exprRegChiffre) || !partie2.matches(exprRegLettre)){
                throw new ClientNumRueException(numErr);
            }
        }
        numero = num; // 1 lettre max a la fin pas de symbole
    }
    
    public void setVille (String v) throws ClientVilleException
    {
        Integer numErr = 0;
        String exprReg = "[a-zA-ZàäáâéëèêîïíìôöóòûüúùÀÄÁÂÉËÈÊÎÏÍÌÔÖÓÒÛÜÚÙç -]+";
        // si la ville est trop grande (taille de la chaine)
        if(v.length() > 20){
            throw new ClientVilleException(numErr);
        }
        // si rien n'est retourné
        numErr = 1;
        if(v.length() == 0){
            throw new ClientVilleException(numErr);
        }
        // verifie si plusieurs espaces ou tirets consécutifs
        numErr = 2;
        if(!verifChaine(v)){
            throw new ClientVilleException(numErr);
        }
        // ne peux contenir que les caractères suivants : a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,é,è,ê,ë,ô,ò,ó,ö,î,ì,ï,í,à,â,á,ä,ù,û,ú,ü,ç,- 
        if(!v.matches(exprReg)){
            throw new ClientVilleException(numErr);
        }
        ville = v; 
    }
    
    public Boolean verifChaine(String chaine){
        Boolean verif = true;
        String exprCaract = "[a-zA-ZàäáâéëèêîïíìôöóòûüúùÀÄÁÂÉËÈÊÎÏÍÌÔÖÓÒÛÜÚÙç]+";
        for(int i=0; i<chaine.length();i++){
            if(chaine.charAt(i) == ' ' || chaine.charAt(i) == '-'){
                if( (i+1) < chaine.length()){ // si ce n'est pas le dernier caractère
                    // Vérifier si le suivant est aussi un espace ou un tiret
                    if(!(chaine.charAt(i+1)+"").matches(exprCaract)){
                        verif = false;
                    }
                }
            }
        }
        // Si le premier ou dernier caractère est un espace ou un tiret, alors erreur
        if(chaine.charAt(0) == ' ' || chaine.charAt(0) == '-'||
           chaine.charAt(chaine.length()-1) == ' ' || chaine.charAt(chaine.length()-1) == '-'){
            verif = false;
        }
        return verif;
    }
    
    public int getId(){
        return id;
    }
    
    public String getNom(){
        return nom;
    }
    
    public String getPrenom(){
        return prenom;
    }
}
