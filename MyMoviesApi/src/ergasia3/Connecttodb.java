package ergasia3;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;


//κλάση που γίνεται αυτόματα η σύνδεση με την βάση δεδομένων
//με path αυτό που βρίσκεται η βάση.

public class Connecttodb {
    
    //Ορίζονται το μονοπάτι ο driver και κωδικός και χρήστης που είναι ίδια

    private static final String dburl = "jdbc:derby://localhost:1527/MyMovies";
    private static final String driver ="com.mysql.jdbc.Driver";
    private static final String user = "Ergasia3";
     private static final String pass = "12345";
    Connection conn = null; 
    

    
   // Η συνάρτηση connect με το flag f παίρνει ως παραμέτρους τα παραπάνω και
   // κάνει τη σύνεδεση με τη βάση αν είναι true αλλιώς την αποσυνδέεται.     
    
    public  void Connect(boolean f) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, Exception{  
    if(f){
               
       
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(dburl, user, pass);
        System.out.println("Connected!");
        
        
    }
    else{
        conn.close();
    }
    
    
    }
    
    
}
