
package ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pckEmpleados.clsEmpleados;

public class clsConexion {
    
    private Connection objcnn = null;
    private Statement objStt = null;
    private ResultSet objRst = null;
    
    //Variables de identificación de la BD
    private String strServer = null;
    private String strBD = null;
    private String strUsuario = null;
    private String strPassword = null;
    
    //auxiliares
    private String strControlador = null;
    private String strSQL = null;
    
    public void mtdAbrirBD () throws ClassNotFoundException{
      //strControlador = "com.mysql.jdbc.Driver";
        strServer = "jdbc:mysql://localhost:3306/";
        strBD = "bdalmacen";
        strUsuario = "root";
        strPassword = "7699";
        
        try {

            strControlador = "org.mariadb.jdbc.Driver";
            
            Class.forName(strControlador);
            
            objcnn = DriverManager.getConnection(
                     strServer + strBD,
                     strUsuario,
                     strPassword
            );
            
            System.out.println("Conexion exitosa a la BD");
            
        } catch (SQLException eSQL) {
            System.out.println("ERROR de conexion a la BD: " + eSQL);
        }
        
    }
    
    public void mtdCerrarBD (){
        try {
            objcnn.close();
            System.out.println("Cierre de base de datos exitoso");
        } catch (SQLException eSQlCerrar) {
            System.out.println("Eror de cierre a la BD: " + eSQlCerrar);
        }
    }
    
    public void mtdAgregarDato ( String pCod, String pDNI, String pNombre, String pAp_pat,
                                 String pAp_mat, String pDireccion, String pCorreo, 
                                 String pTelef, String pSexo, String pCargo, String pAr_trab){
        strSQL = "INSERT INTO empleados VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps;
                 
        try {
            ps = objcnn.prepareStatement(strSQL);
            ps.setString(1, pCod);
            ps.setString(2, pDNI);
            ps.setString(3, pNombre);
            ps.setString(4, pAp_pat);
            ps.setString(5, pAp_mat);
            ps.setString(6, pDireccion);
            ps.setString(7, pCorreo);
            ps.setString(8, pTelef);
            ps.setString(9, pSexo);
            ps.setString(10, pCargo);
            ps.setString(11, pAr_trab);
            
            System.out.println("registro grabado");
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en escritura del registro");
        }
    }
    public void mtdObtenerDatosTabla(){
        strSQL = "SELECT * FROM empleados";
        PreparedStatement st;
        
        try {
            st = objcnn.prepareStatement(strSQL);
           
            objRst = st.executeQuery();
            System.out.println("Apertura exitosa de la tabla");
            st.close();
        } catch (Exception e) {
            System.out.println("Error de apertura");
        }
    }
    public clsEmpleados mtdBuscarRegistro(String pDato) {
	strSQL = "SELECT * FROM empleados WHERE Nombres = ?";
        clsEmpleados embqd = null;
	PreparedStatement ps;
	try {
		ps = objcnn.prepareStatement(strSQL);
		ps.setString(1, pDato);

		ResultSet rs = ps.executeQuery();
		while(rs.next()){
                        embqd= new clsEmpleados();
                    
			embqd.setCodigo(rs.getString("Codigo"));
                        embqd.setDni(rs.getString("DNI"));
			embqd.setNombre(rs.getString("Nombres"));
			embqd.setAp_p(rs.getString("Ap_pat"));
                        embqd.setAp_m(rs.getString("Ap_mat"));
                        embqd.setDireccion(rs.getString("Direccion"));
                        embqd.setCorreo(rs.getString("Correo"));
                        embqd.setTelefono(rs.getString("Telefono"));
                        embqd.setSexo(rs.getString("Sexo"));
                        embqd.setCargo(rs.getString("Cargo"));
                        embqd.setArea_tra(rs.getString("Area_Trabajo"));
		}
		rs.close();
		ps.close();
	} catch(Exception e) {
		System.out.println(e.getMessage());
	}
        return embqd;
    }
    public ArrayList<clsEmpleados> obtenerTodos(){
        ArrayList<clsEmpleados> list = new ArrayList();
        String SQL = "SELECT * FROM empleados ";
        PreparedStatement ps;
        ResultSet rs;
        clsEmpleados us;
        
        try {
            ps= objcnn.prepareStatement(SQL);
            rs= ps.executeQuery();
            
            while(rs.next()){
                us = new clsEmpleados();
                us.setCodigo(rs.getString("Codigo"));
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
            ps.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(clsConexion.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return list;
    }
}