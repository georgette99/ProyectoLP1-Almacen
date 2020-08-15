
package modelo.dao;

import ConexionBD.clsConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pckClases.clsProveedores;

public class proveedoresDAO {
    public static ArrayList<clsProveedores> obtenerTodosProveedores() throws ClassNotFoundException{
        ArrayList<clsProveedores> list = new ArrayList();
        String SQL = "SELECT * FROM proveedores ";
        PreparedStatement ps = null;
        ResultSet rs = null;
        clsProveedores us;
        Connection cnn = clsConexion.mtdAbrirBD();
        
        try {
            ps= cnn.prepareStatement(SQL);
            rs= ps.executeQuery();
            
            while(rs.next()){
                us = new clsProveedores();
                us.setCodigo(rs.getInt("Codigo"));
                us.setNombre(rs.getString("Nombre"));
                us.setDireccion(rs.getString("Direccion"));
                us.setTelefono(rs.getString("Telefono"));
                us.setCorreo(rs.getString("Correo"));
                list.add(us);
            }
            cnn.close();
            ps.close();
            rs.close();

        } catch (SQLException ex) {
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
    
    public static clsProveedores obtenerUno(int codigo) throws ClassNotFoundException {
        
        String sql = "SELECT * FROM proveedores WHERE Codigo = ?";
        
        Connection cnn = clsConexion.mtdAbrirBD();
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        clsProveedores p = null;
        
        try {
            ps = cnn.prepareStatement(sql);
            ps.setInt(1, codigo);
            rs = ps.executeQuery();
            
            while(rs.next()){
                p = new clsProveedores();
                
                p.setCodigo(rs.getInt("Codigo"));
                p.setNombre(rs.getString("Nombre"));
                p.setDireccion(rs.getString("Direccion"));
                p.setTelefono(rs.getString("Telefono"));
                p.setCorreo(rs.getString("Correo"));
                
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
            
        return p;
    }
    
    public static int mtdAgregarDatoProveedores (clsProveedores p) throws ClassNotFoundException{
        String strSQL = "INSERT INTO proveedores ( Nombre, Direccion, Telefono, Correo) VALUES( ?, ?, ?, ?)";
        Connection cn = clsConexion.mtdAbrirBD();
        PreparedStatement ps;
        int res = 0;        
        try {
            ps = cn.prepareStatement(strSQL);
            
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDireccion());
            ps.setString(3, p.getTelefono());
            ps.setString(4, p.getCorreo());
            
            System.out.println("registro grabado");
            res = ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en escritura del registro");
        }
        return res;
    }
    
    public static int mtdModificarDatoProveedor(clsProveedores p) throws ClassNotFoundException{
        String sql = "UPDATE proveedores set Nombre= ?, Direccion =?, Telefono = ?, Correo = ? WHERE Codigo= ?" ;
        
        Connection cn = clsConexion.mtdAbrirBD();
        
        PreparedStatement ps = null;
        int res = 0;
        
        try {
            ps = cn.prepareStatement(sql);
            
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDireccion());
            ps.setString(3, p.getTelefono());
            ps.setString(4, p.getCorreo());
            ps.setInt(5, p.getCodigo());
            
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
        
        String sql = "DELETE FROM proveedores where Codigo=?";
        
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
    
    public static ArrayList<clsProveedores> mtdBuscarRegistroProveedores(int pDato)throws ClassNotFoundException{
        String strSQL = "SELECT * FROM proveedores WHERE Codigo = ?";
        Connection cn = clsConexion.mtdAbrirBD();
        
        ArrayList<clsProveedores> lista = new ArrayList<clsProveedores>();
        
	PreparedStatement ps;
        
	try {
		ps = cn.prepareStatement(strSQL);
		ps.setInt(1, pDato);

		ResultSet rs = ps.executeQuery();
		while(rs.next()){
                    clsProveedores prov = new clsProveedores();
                    
			prov.setCodigo(rs.getInt("Codigo"));
			prov.setNombre(rs.getString("Nombre"));
                        prov.setDireccion(rs.getString("Direccion"));
                        prov.setTelefono(rs.getString("Telefono"));
                        prov.setCorreo(rs.getString("Correo"));
                        
                        lista.add(prov);
                
		}
		rs.close();
		ps.close();
	} catch(Exception e) {
		System.out.println(e.getMessage());
	}
        return lista;
    }
}
