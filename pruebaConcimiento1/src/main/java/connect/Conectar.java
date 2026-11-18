package connect;
import java.sql.*;
public class Conectar {

   private static String URL = "jdbc:postgresql://localhost:5432/amigurumisv2";
   private static String USER = "postgres";
   private static String PASSWORD = "contraseña";

    public static Connection conexionDb() {
         Connection con = null;
         try{
           con = DriverManager.getConnection(URL,USER,PASSWORD);
         }catch (SQLException e){
             System.out.println(e);
         }
         return con;
    }
}
