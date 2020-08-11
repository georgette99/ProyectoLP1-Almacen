
package modelo.dao;

import ConexionBD.clsConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pckClases.clsEmpleados;

public class empleadosDAO {
    
    public static ArrayList<clsEmpleados> obtenerTodos() throws ClassNotFoundException{
        ArrayList<clsEmpleados> list = new ArrayList();
        String SQL = "SELECT * FROM empleados ";
        PreparedStatement ps = null;
        ResultSet rs = null;
        clsEmpleados us;
        Connection cnn = clsConexion.mtdAbrirBD();
        try {
            ps= cnn.prepareStatement(SQL);
            rs= ps.executeQuery();
            
            while(rs.next()){
                us = new clsEmpleados();
                us.setCodigo(rs.getInt("Codigo"));
                us.setDni("DNI");
                us.setNombre(rs.getString("Nombres"));
                us.setAp_p(rs.getString("Ap_pat"));
                us.setAp_m(rs.getString("Ap_mat"));
                us.setDireccion(rs.getString("Direccion"));
                us.setCorreo(rs.getString("Correo"));
                us.setTelefono(rs.getString("Telefono"));
                us.setSexo(rs.getString("Sexo"));
                us.setCargo(rs.getString("Cargo"));
                us.setArea_tra(rs.getString("Area_trabajo"));
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
    
    
    public static clsEmpleados obtenerUno(int codigo) throws ClassNotFoundException {
        
        String sql = "SELECT * FROM empleados WHERE Codigo = ?";
        
        Connection cnn = clsConexion.mtdAbrirBD();
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        clsEmpleados e = null;
        
        try {
            ps = cnn.prepareStatement(sql);
            ps.setInt(1, codigo);
            rs = ps.executeQuery();
            
            while(rs.next()){
                e = new clsEmpleados();
                
                e.setCodigo(rs.getInt("Codigo"));
                e.setDni(rs.getString("DNI"));
                e.setNombre(rs.getString("Nombres"));
                e.setAp_p(rs.getString("Ap_pat"));
                e.setAp_m(rs.getString("Ap_mat"));
                e.setDireccion(rs.getString("Direccion"));
                e.setCorreo(rs.getString("Correo"));
                e.setTelefono(rs.getString("Telefono"));
                e.setSexo(rs.getString("Sexo"));
                e.setCargo(rs.getString("Cargo"));
                e.setArea_tra(rs.getString("Area_trabajo"));
                
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
            
        return e;
    }    
    
    public static int mtdAgregarDato (clsEmpleados e) throws ClassNotFoundException{
        String strSQL = "INSERT INTO empleados( DNI, Nombres, Ap_pat, Ap_mat, Direccion, Correo, Telefono, Sexo, Cargo, Area_Trabajo)"
                + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection cn = clsConexion.mtdAbrirBD();
        PreparedStatement ps= null;
        int res = 0;       
        try {
            
            ps = cn.prepareStatement(strSQL);
            
            ps.setString(1, e.getDni());
            ps.setString(2, e.getNombre());
            ps.setString(3, e.getAp_p());
            ps.setString(4, e.getAp_m());
            ps.setString(5, e.getDireccion());
            ps.setString(6, e.getCorreo());
            ps.setString(7, e.getTelefono());
            ps.setString(8, e.getSexo());
            ps.setString(9, e.getCargo());
            ps.setString(10, e.getArea_tra());
            
            res = ps.executeUpdate();
            ps.close();
        } 
        catch (SQLException ex) {
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
    
    public static int mtdModificarDatoEmpleado(clsEmpleados e) throws ClassNotFoundException{
        String sql = "UPDATE empleados set DNI= ?, Nombres= ?, Ap_pat= ?, Ap_mat= ?,"
                + "Direccion = ?, Correo = ?, Telefono = ?, Sexo = ?, Cargo = ?, Area_Trabajo = ? WHERE Codigo= ?" ;
        
        Connection cn = clsConexion.mtdAbrirBD();
        
        PreparedStatement ps = null;
        int res = 0;
        
        try {
            ps = cn.prepareStatement(sql);
            
            ps.setString(1, e.getDni());
            ps.setString(2, e.getNombre());
            ps.setString(3, e.getAp_p());
            ps.setString(4, e.getAp_m());
            ps.setString(5, e.getDireccion());
            ps.setString(6, e.getCorreo());
            ps.setString(7, e.getTelefono());
            ps.setString(8, e.getSexo());
            ps.setString(9, e.getCargo());
            ps.setString(10, e.getArea_tra());
            ps.setInt(11, e.getCodigo());
            
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
        
        String sql = "DELETE FROM empleados where Codigo=?";
        
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
