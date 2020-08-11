
package modelo.dao;

import ConexionBD.clsConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pckClases.clsClientes;


public class clienteDAO {
    
    public static ArrayList<clsClientes> obtenerTodosClientes() throws ClassNotFoundException{
        
        String SQL = "SELECT * FROM clientes ";
        
        Connection cnn = clsConexion.mtdAbrirBD();
        ArrayList<clsClientes> list = new ArrayList();
        
        
        PreparedStatement ps = null;
        ResultSet rs = null;

        
        try {
            ps= cnn.prepareStatement(SQL);
            rs= ps.executeQuery();
            
            while(rs.next()){
                clsClientes us = new clsClientes();
                us = new clsClientes();
                us.setCodigo(rs.getInt("Codigo"));
                us.setRUC(rs.getString("RUC"));
                us.setNombre(rs.getString("Nombre"));
                us.setDireccion(rs.getString("Direccion"));
                us.setTelefono(rs.getString("Telefono"));
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
    
    
    public static clsClientes obtenerUno(int codigo) throws ClassNotFoundException {
        
        String sql = "SELECT * FROM clientes WHERE Codigo = ?";
        
        Connection cnn = clsConexion.mtdAbrirBD();
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        clsClientes c = null;
        
        try {
            ps = cnn.prepareStatement(sql);
            ps.setInt(1, codigo);
            rs = ps.executeQuery();
            
            while(rs.next()){
                c = new clsClientes();
                
                c.setCodigo(rs.getInt("Codigo"));
                c.setRUC(rs.getString("RUC"));
                c.setNombre(rs.getString("Nombre"));
                c.setDireccion(rs.getString("Direccion"));
                c.setTelefono(rs.getString("Telefono"));
                c.setCorreo(rs.getString("Correo"));
                
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
    
    public static int mtdModificarDatoCliente(clsClientes c) throws ClassNotFoundException{
        String sql = "UPDATE clientes set RUC= ?, Nombre= ?, Direccion =?, Telefono = ?, Correo = ? WHERE Codigo= ?" ;
        
        Connection cn = clsConexion.mtdAbrirBD();
        
        PreparedStatement ps = null;
        int res = 0;
        
        try {
            ps = cn.prepareStatement(sql);
            
            ps.setString(1, c.getRUC());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getDireccion());
            ps.setString(4, c.getTelefono());
            ps.setString(5, c.getCorreo());
            ps.setInt(6, c.getCodigo());
            
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
    
    public static int mtdAgregarDatoCliente (clsClientes c) throws ClassNotFoundException{
        String strSQL = "INSERT INTO clientes( RUC, Nombre, Direccion, Telefono, Correo )VALUES( ?, ?, ?, ?, ?)";
        
        Connection cn = clsConexion.mtdAbrirBD();
        PreparedStatement ps= null;
        int res = 0;
        try {
            ps = cn.prepareStatement(strSQL);
            
            ps.setString(1, c.getRUC());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getDireccion());
            ps.setString(4, c.getTelefono());
            ps.setString(5, c.getCorreo());

            
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
    
    public static void mtdBuscarRegistroCliente(String pDato) throws ClassNotFoundException{
        String strSQL = "SELECT * FROM clientes WHERE Codigo = ?";
        Connection cn = clsConexion.mtdAbrirBD();
        clsClientes cliente = null;
	PreparedStatement ps;
        
	try {
		ps = cn.prepareStatement(strSQL);
		ps.setString(1, pDato);

		ResultSet rs = ps.executeQuery();
		while(rs.next()){
                        cliente = new clsClientes();
                    
			cliente.setCodigo(rs.getInt("Codigo"));
                        cliente.setRUC(rs.getString("RUC"));
			cliente.setNombre(rs.getString("Nombre"));
                        cliente.setDireccion(rs.getString("Direccion"));
                        cliente.setTelefono(rs.getString("Telefono"));
                        cliente.setCorreo(rs.getString("Correo"));
                        
                
		}
		rs.close();
		ps.close();
	} catch(Exception e) {
		System.out.println(e.getMessage());
	}
    } 
    
    public static int eliminar(int cod) throws ClassNotFoundException {
        
        String sql = "DELETE FROM clientes where Codigo=?";
        
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
