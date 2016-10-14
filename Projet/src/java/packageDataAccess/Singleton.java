package packageDataAccess;

import packageException.*;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Singleton { 
    private static Connection connexionUnique;
    public static Connection getInstance() throws SingletonException{ 
        if (connexionUnique == null){
            try{
                Context ctx = new InitialContext();
                DataSource source = (DataSource)ctx.lookup("jdbc/friterie");
                connexionUnique = source.getConnection();
            }
            catch(SQLException ex){
                throw new SingletonException(ex.getMessage());
            } 
            catch(NamingException ex){
                throw new SingletonException(ex.getMessage());
            }
        }
        return connexionUnique;
    }
    
    public static void fermerConnexion() throws SingletonException{
        if(connexionUnique != null){
            try{
                connexionUnique.close();         
            }
            catch(SQLException e){
                throw new SingletonException(e.getMessage());
            }
        }
    }
}
