
package modelo.dao;

import ConexionBD.clsConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pckClases.clsUsuarios;

public class usuariosDAO {
    
    public static ArrayList<clsUsuarios> obtenerTodosUsuarios() throws ClassNotFoundException{
        ArrayList<clsUsuarios> list = new ArrayList();
        String SQL = "SELECT * FROM usuarios ";
        PreparedStatement ps=null;
        ResultSet rs=null;
        clsUsuarios us;
        Connection cnn = clsConexion.mtdAbrirBD();
        
        try {
            ps= cnn.prepareStatement(SQL);
            rs= ps.executeQuery();
            
            while(rs.next()){
                us = new clsUsuarios();
                us.setID(rs.getInt("ID"));
                us.setClave(rs.getString("Clave"));
                us.setUsuario(rs.getString("Usuarios"));
                us.setCorreo(rs.getString("Correo"));
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
    
    public static clsUsuarios obtenerUnoUsuarios(int id) throws ClassNotFoundException {
        
        String sql = "SELECT * FROM usuarios WHERE ID = ?";
        
        Connection cnn = clsConexion.mtdAbrirBD();
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        clsUsuarios u = null;
        
        try {
            ps = cnn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            while(rs.next()){
                u = new clsUsuarios();
                
                u.setID(rs.getInt("ID"));
                u.setClave(rs.getString("Clave"));
                u.setUsuario(rs.getString("Usuario"));
                u.setCorreo(rs.getString("Correo"));
                
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
            
        return u;
    }
    
    public static int mtdAgregarDatoUsuarios ( clsUsuarios u) throws ClassNotFoundException{
        String strSQL = "INSERT INTO usuarios VALUES( ?, ?, ?)";
        Connection cn = clsConexion.mtdAbrirBD();
        PreparedStatement ps;
        int res = 0;
        try {
            ps = cn.prepareStatement(strSQL);
            ps.setString(1, u.getClave());
            ps.setString(2, u.getUsuario());
            ps.setString(3, u.getCorreo());
           
            System.out.println("registro grabado");
            
            res = ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return res;
    }
    
    public static int mtdModificarDatoUsuario(clsUsuarios u) throws ClassNotFoundException{
        String sql = "UPDATE usuarios set Clave= ?, Usuario =?, Correo = ? WHERE ID= ?" ;
        
        Connection cn = clsConexion.mtdAbrirBD();
        
        PreparedStatement ps = null;
        int res = 0;
        
        try {
            ps = cn.prepareStatement(sql);
            
            ps.setString(1, u.getClave());
            ps.setString(2, u.getUsuario());
            ps.setString(3, u.getCorreo());
            ps.setInt(4, u.getID());
            
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
    
    public static int eliminar(int cod) throws ClassNotFoundException {
        
        String sql = "DELETE FROM usuarios where ID=?";
        
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

    public static int mtdModificarDatoUsuario(String usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
