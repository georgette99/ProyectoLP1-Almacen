
package modelo.dao;

import ConexionBD.clsConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pckClases.clsInventario;

public class inventarioDAO {
    public static ArrayList<clsInventario> obtenerTodosInventario() throws ClassNotFoundException{
        
        String SQL = "SELECT * FROM inventario ";
        
        Connection cnn = clsConexion.mtdAbrirBD();
        ArrayList<clsInventario> list = new ArrayList();
        
        
        PreparedStatement ps = null;
        ResultSet rs = null;

        
        try {
            ps= cnn.prepareStatement(SQL);
            rs= ps.executeQuery();
            
            while(rs.next()){
                clsInventario us = new clsInventario();
                us = new clsInventario();
                us.setCodigo_Inv(rs.getInt("Codigo"));
                us.setDescripcion(rs.getString("Descripcion"));
                us.setLinea(rs.getString("Linea"));
                us.setPrecio(rs.getString("Precio"));
                us.setStock(rs.getString("Stock"));
                us.setUnidad(rs.getString("Unidad"));
                list.add(us);
            }
            cnn.close();
            ps.close();
            rs.close();

        } catch (Exception ex) {
            Logger.getLogger(clsConexion.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                cnn.close();
                ps.close();
                rs.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return list;
    }
    
    
    public static clsInventario obtenerUno(int codigo) throws ClassNotFoundException {
        
        String sql = "SELECT * FROM inventario WHERE Codigo = ?";
        
        Connection cnn = clsConexion.mtdAbrirBD();
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        clsInventario c = null;
        
        try {
            ps = cnn.prepareStatement(sql);
            ps.setInt(1, codigo);
            rs = ps.executeQuery();
            
            while(rs.next()){
                c = new clsInventario();
                
                c.setCodigo_Inv(rs.getInt("Codigo"));
                c.setDescripcion(rs.getString("Descripcion"));
                c.setLinea(rs.getString("Linea"));
                c.setPrecio(rs.getString("Precio"));
                c.setStock(rs.getString("Stock"));
                c.setUnidad(rs.getString("Unidad"));
                
            }
   
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                cnn.close();
                ps.close();
                rs.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
            
        return c;
    }
    
    public static int mtdModificarDatoInventario(clsInventario c) throws ClassNotFoundException{
        String sql = "UPDATE inventario set Stock= ?, Descripcion= ?, Precio =?, Linea = ?, Unidad = ? WHERE Codigo= ?" ;
        
        Connection cn = clsConexion.mtdAbrirBD();
        
        PreparedStatement ps = null;
        int res = 0;
        
        try {
            ps = cn.prepareStatement(sql);
            
            ps.setString(1, c.getDescripcion());
            ps.setString(2, c.getLinea());
            ps.setString(3, c.getPrecio());
            ps.setString(4, c.getStock());
            ps.setString(5, c.getUnidad());
            ps.setInt(6, c.getCodigo_Inv());
            
            res = ps.executeUpdate();

        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                cn.close();
                ps.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return res;
    }
    
    public static int mtdAgregarDatoInventario (clsInventario c) throws ClassNotFoundException{
        String strSQL = "INSERT INTO inventario( Stock, Descripcion, Precio, Linea,Unidad)VALUES( ?, ?, ?, ?, ?)";
        
        Connection cn = clsConexion.mtdAbrirBD();
        PreparedStatement ps= null;
        int res = 0;
        try {
            ps = cn.prepareStatement(strSQL);
            
            ps.setString(1, c.getDescripcion());
            ps.setString(2, c.getLinea());
            ps.setString(3, c.getPrecio());
            ps.setString(4, c.getStock());
            ps.setString(5, c.getUnidad());

            
            res = ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en escritura del registro");
        }finally {
            try {
                cn.close();
                ps.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return res;
    }
    
    public static int eliminar(int cod) throws ClassNotFoundException {
        
        String sql = "DELETE FROM inventario where Codigo=?";
        
        Connection cn = clsConexion.mtdAbrirBD();
        
        PreparedStatement ps = null;
        int res = 0;
        try {
            ps = cn.prepareStatement(sql);
            
            // Establecer valores en los ?
            ps.setInt(1, cod);            
            
            res = ps.executeUpdate();
   
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                cn.close();
                ps.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        return res;
    }
    
}
