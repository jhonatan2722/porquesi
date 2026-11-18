package dao;
import connect.Conectar;
import models.Amigurumi;

import  java.sql.*;
import java.util.ArrayList;

public class AmigurumiDao {

    public ArrayList<Amigurumi> verAmigurumis(){
        String sql = "SELECT * FROM amigurumis";
        ArrayList<Amigurumi>amigurumis = new ArrayList<>();
        try( Connection con = Conectar.conexionDb();
           PreparedStatement pstm = con.prepareStatement(sql);
           ){
              try(ResultSet rs = pstm.executeQuery()){
                  while(rs.next()){
                      Amigurumi amigurumi = new Amigurumi(rs.getInt("id"),rs.getString("descripcion"),rs.getBigDecimal("precio"));
                      amigurumis.add(amigurumi);
                  }
              }
        }catch (SQLException e){
             System.out.println(e);
        }
        return amigurumis;}

    public void guardarAmigurumis(Amigurumi amigurumi){
        String sql = "INSERT INTO amigurumis (descripcion, precio) VALUES (?,?);";
        try( Connection con = Conectar.conexionDb();
             PreparedStatement pstm = con.prepareStatement(sql)
        ){
            pstm.setString(1,amigurumi.getDescripcion());
            pstm.setBigDecimal(2,amigurumi.getPrecio());
            pstm.executeUpdate();
        }catch (SQLException e){
            System.out.println(e);
        }
    }

    public int actualizarAmigurumis(Amigurumi amigurumi, int id){
        String sql = "UPDATE amigurumis SET descripcion = ?, precio = ? WHERE id = ?";
        int filas = 0;
        try(Connection con = Conectar.conexionDb();
          PreparedStatement pstm = con.prepareStatement(sql)){
            pstm.setString(1, amigurumi.getDescripcion());
            pstm.setBigDecimal(2,amigurumi.getPrecio());
            pstm.setInt(3,id);
            filas = pstm.executeUpdate();
        }catch (SQLException e){
         System.out.println(e);
        }
       return filas;
    }


    public int borrarAmigurumis(int id){
        int filas = 0;
        try(Connection con = Conectar.conexionDb();
          PreparedStatement pstm = con.prepareStatement("DELETE FROM amigurumis WHERE id = ?")
        ){
            pstm.setInt(1,id);
            filas = pstm.executeUpdate();
        }catch(SQLException e){
            System.out.println(e);
        }

        return filas;
    }
}
