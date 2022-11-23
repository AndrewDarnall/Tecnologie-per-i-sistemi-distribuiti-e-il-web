import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

//Accertarsi di girare lo script di creazione della base di dati prima di girare il programma

public class Myseql {
 
    //URL DB, puo' essere reso parametrico
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/libri";


    public static void main(String[] args) {

        
        Connection aConnection = null;
        Statement aStatement = null;
        ResultSet aResultSet = null;

        

        Properties info = new Properties();
        info.put("user","gb");
        info.put("password","gb");



        try{   

            aConnection = DriverManager.getConnection(DATABASE_URL,info);

            if(aConnection != null) {
                System.out.println("Successfully connected to the MySQL server!");
            } else {
                System.err.println("Error, something went wrong while connecting to MySQL");
            }

            aStatement = aConnection.createStatement();
            String query = "select * from Libri";
            aResultSet = aStatement.executeQuery(query);

            ResultSetMetaData metaData = aResultSet.getMetaData();
            int numberOfCulumns = metaData.getColumnCount();
            System.out.println("Executing query");

            
            System.out.println("---------------- Results ----------------");
            
            System.out.println("BookName\t\tPrice");
            while(aResultSet.next()) {
                System.out.printf("%-8s\t\t%-8s\n",aResultSet.getString("BookName"),aResultSet.getString("Price"));
            }

            System.out.println("----------------------------------------");



        } catch (SQLException e) {

            e.printStackTrace();

        }
    }

}
/**
 * Per compilare
 * 
 * javac -cp mysql-connector-j-8.0.31.jar:. Myseql.java
 * 
 * Per eseguire 
 * 
 * java -cp mysql-connector-j-8.0.31.jar:. Myseql
 * 
 * 
 * il ':.' si mettono per includere la classe Myseql dopo aver caricato il ByteCode
 * del connettore, scaricato dal mirror ufficiale di MySQL: https://dev.mysql.com/downloads/connector/j/
 * 
 * ATTENZIONE: se NON avete SOLAMENTE il file .jar (preso dalla cartella del connettore scaricato da MySQL)
 * javac dara' problemi.
 * 
 * NON e' un esempio professionale visto che per gestire le query probabilmente si usano API
 * e stored procedures/cursori.
 * 
 */