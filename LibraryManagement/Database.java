package LibraryManagement;

import java.sql.Connection;
import java.sql.DriverManager;

class Database {

    static Connection conn = null;
    
    public static Connection getConnection()   {

        try {
        
            Class.forName("com.mysql.jdbc.Driver");
        
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryManagement", "root", "");

        }   catch(Exception e)  {   e.printStackTrace();    }

        return (Connection)conn;

    }

}