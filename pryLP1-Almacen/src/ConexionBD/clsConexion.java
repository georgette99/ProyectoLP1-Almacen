
package ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import pckClases.clsClientes;
import pckClases.clsEmpleados;
import pckClases.clsFacturas;
import pckClases.clsProveedores;
import pckClases.clsInventario;
import pckClases.clsUsuarios;

public class clsConexion {
    
    Connection objcnn = null;
    Statement objStt = null;
    ResultSet objRst = null;
    
    //Variables de identificación de la BD
    private String strServer = null;
    private String strBD = null;
    private String strUsuario = null;
    private String strPassword = null;
    
    //auxiliares
    private String strControlador = null;
    private String strSQL = null;
    
    public Connection getConnection(){
        return objcnn;
    }
    
    public Statement getStatement(){
        return objStt;
    }
    
    public ResultSet getResultSet(){
        return objRst;
    }
    
    public void mtdAbrirBD () throws ClassNotFoundException{
      //strControlador = "com.mysql.jdbc.Driver";
       strServer = "jdbc:mysql://localhost:3306/";
        strBD = "bdalmacen";
        strUsuario = "root";
        strPassword = "123";
        
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
    
    //EMPLEADOS
    public void mtdAgregarDato ( String pCod, String pDNI, String pNombre, String pAp_pat,
                                 String pAp_mat, String pDireccion, String pCorreo, 
                                 String pTelef, String pSexo, String pCargo, String pAr_trab){
        strSQL = "INSERT INTO empleados(DNI, Nombres, Ap_pat, Ap_mat, Direccion, Correo, Telefono, Sexo, Cargo, Area_Trabajo)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps;
                 
        try {
            ps = objcnn.prepareStatement(strSQL);
            ps.setString(1, pDNI);
            ps.setString(2, pNombre);
            ps.setString(3, pAp_pat);
            ps.setString(4, pAp_mat);
            ps.setString(5, pDireccion);
            ps.setString(6, pCorreo);
            ps.setString(7, pTelef);
            ps.setString(8, pSexo);
            ps.setString(9,pCargo);
            ps.setString(10,pAr_trab);
            
            System.out.println("registro grabado");
            ps.executeUpdate();
            ps.close();
        } 
        catch (SQLException e) {
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
	strSQL = "SELECT * FROM empleados WHERE Codigo = ?";
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
    public void mtdEliminarDatoEmpleados(String pCodigo){
        strSQL = "DELETE FROM empleados WHERE Codigo = '" + pCodigo + "'".trim();
        try{
            objStt = objcnn.createStatement();
            objStt.execute(strSQL); //INSERT ...
            System.out.println("Registro eliminado");
        }
        catch(SQLException eSQL){
            System.out.println("ERROR en la eliminacion: " + eSQL );
        }
    }
    
    //CLIENTES
    public void mtdAgregarDatoCliente ( String pCodigo, String pRUC, String pNombre,  String pDireccion, 
                                 String pTelefono, String pCorreo){
        strSQL = "INSERT INTO clientes (RUC, Nombre, Direccion, Telefono, Correo) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement ps;
                 
        try {
            ps = objcnn.prepareStatement(strSQL);
            ps.setString(1, pRUC);
            ps.setString(2, pNombre);
            ps.setString(3, pDireccion);
            ps.setString(4, pTelefono);
            ps.setString(5, pCorreo);

            
            System.out.println("registro grabado");
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en escritura del registro");
        }
    }
    public void mtdObtenerDatosTablaClientes(){
        strSQL = "SELECT *FROM clientes";
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
    public clsClientes mtdBuscarRegistroCliente(String pDato){
        strSQL = "SELECT * FROM clientes WHERE Codigo = ?";
        clsClientes cliente = null;
	PreparedStatement ps;
	try {
		ps = objcnn.prepareStatement(strSQL);
		ps.setString(1, pDato);

		ResultSet rs = ps.executeQuery();
		while(rs.next()){
                        cliente = new clsClientes();
                    
			cliente.setCodigo(rs.getString("Codigo"));
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
        return cliente;
    }  
    public ArrayList<clsClientes> obtenerTodosClientes(){
        ArrayList<clsClientes> list = new ArrayList();
        String SQL = "SELECT * FROM clientes ";
        PreparedStatement ps;
        ResultSet rs;
        clsClientes us;
        
        try {
            ps= objcnn.prepareStatement(SQL);
            rs= ps.executeQuery();
            
            while(rs.next()){
                us = new clsClientes();
                us.setCodigo(rs.getString("Codigo"));
                us.setRUC(rs.getString("RUC"));
                us.setNombre(rs.getString("Nombre"));
                us.setDireccion(rs.getString("Direccion"));
                us.setTelefono(rs.getString("Telefono"));
                us.setCorreo(rs.getString("Correo"));
                list.add(us);
            }
            ps.close();
            rs.close();

        } catch (Exception ex) {
            Logger.getLogger(clsConexion.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return list;
    }
    public void mtdModificarDatoCliente(String pCodigo, String pRUC, String pNombre, String pDireccion, String pTelefono, String pCorreo){
        strSQL = "UPDATE clientes set RUC='" + pRUC + "',Nombre='" + pNombre + "', "
                + "Direccion ='" +pDireccion +"', Telefono = '" + pTelefono +"', "
                + "Correo ='" +pCorreo+ "' WHERE Id=" + pCodigo;
        try {
            objStt = objcnn.prepareStatement(strSQL);
            objStt.execute( strSQL ); // INSERT ...
            System.out.println("Registro grabado");
        }
        catch(SQLException eSQL){
            System.out.println("Error en la modificacio + eSQL");
        }
    }
    
    public void mtdEliminarDatoCliente(String pCodigo){
        strSQL = "DELETE FROM clientes WHERE Codigo = '" + pCodigo + "'".trim();
        try{
            objStt = objcnn.createStatement();
            objStt.execute(strSQL); //INSERT ...
            System.out.println("Registro eliminado");
        }
        catch(SQLException eSQL){
            System.out.println("ERROR en la eliminacion: " + eSQL );
        }
    }
    
    //PROVEEDORES
    public void mtdAgregarDatoProveedores (String pCodigo, String pNombre, String pDireccion, String pTelefono, String pCorreo){
        strSQL = "INSERT INTO proveedores (Nombre, Direccion, Telefono, Correo) VALUES(?, ?, ?, ?)";
        PreparedStatement ps;
                 
        try {
            ps = objcnn.prepareStatement(strSQL);
            ps.setString(1, pNombre);
            ps.setString(2, pDireccion);
            ps.setString(3, pTelefono);
            ps.setString(4, pCorreo);
            
            System.out.println("registro grabado");
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en escritura del registro");
        }
        
    }
    public void mtdObtenerDatosTablaProveedores (){
        strSQL = "SELECT * FROM proveedores";
        PreparedStatement st;
        
        try {
            st = objcnn.prepareStatement(strSQL);
           
            objRst = st.executeQuery();
            System.out.println("Apertura exitosa de la tabla");
            st.close();
        } catch (SQLException e) {
            System.out.println("Error de apertura");
        }
    }
    public clsProveedores mtdBuscarRegistroProveedor(String pDato){
        strSQL = "SELECT * FROM clientes WHERE Codigo = ?";
        clsProveedores prov = null;
	PreparedStatement ps;
	try {
		ps = objcnn.prepareStatement(strSQL);
		ps.setString(1, pDato);

		ResultSet rs = ps.executeQuery();
		while(rs.next()){
                        prov= new clsProveedores();
                    
			prov.setCodigo(rs.getString("Codigo"));
                        prov.setNombre("Nombre");
			prov.setDireccion(rs.getString("Direccion"));
                        prov.setTelefono(rs.getString("Telefono"));
                        prov.setCorreo(rs.getString("Correo"));
		}
		rs.close();
		ps.close();
	} catch(Exception e) {
		System.out.println(e.getMessage());
	}
        return prov;
    }
    public ArrayList<clsProveedores> obtenerTodosProveedores(){
        ArrayList<clsProveedores> list = new ArrayList();
        String SQL = "SELECT * FROM proveedores ";
        PreparedStatement ps;
        ResultSet rs;
        clsProveedores us;
        
        try {
            ps= objcnn.prepareStatement(SQL);
            rs= ps.executeQuery();
            
            while(rs.next()){
                us = new clsProveedores();
                us.setCodigo(rs.getString("Codigo"));
                us.setNombre(rs.getString("Nombre"));
                us.setDireccion(rs.getString("Direccion"));
                us.setDireccion(rs.getString("Direccion"));
                us.setTelefono(rs.getString("Telefono"));
                us.setCorreo(rs.getString("Correo"));
                list.add(us);
            }
            ps.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(clsConexion.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return list;
    }
    public void mtdEliminarDatoProveedores(String pCodigo){
        strSQL = "DELETE FROM proveedores WHERE Codigo = '" + pCodigo + "'".trim();
        try{
            objStt = objcnn.createStatement();
            objStt.execute(strSQL); //INSERT ...
            System.out.println("Registro eliminado");
        }
        catch(SQLException eSQL){
            System.out.println("ERROR en la eliminacion: " + eSQL );
        }
    }
    
    //INVENTARIO
    public ArrayList<clsInventario> obtenerDatos(){
        ArrayList<clsInventario> list = new ArrayList();
        String SQL = "SELECT * FROM inventario ";
        PreparedStatement ps;
        ResultSet rs;
        clsInventario in;
        
        try {
            ps= objcnn.prepareStatement(SQL);
            rs= ps.executeQuery();
            
            while(rs.next()){
                in = new clsInventario();
                in.setCodigo_Inv(rs.getString("Cod_Inv"));
                in.setStock(rs.getString("Stock"));
                in.setDescripcion(rs.getString("Descripcion"));
                in.setPrecio(rs.getString("Precio"));
                in.setLinea(rs.getString("Linea"));
                in.setUnidad(rs.getString("Unidad"));
                list.add(in);
            }
            ps.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(clsConexion.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return list;
    }
    public void mtdAgregarProducto (String pCodIn, String pStock, String pDescripcion, String pPrecio,
                                 String pLinea, String pUnidad){
        strSQL = "INSERT INTO inventario VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps;
                 
        try {
            ps = objcnn.prepareStatement(strSQL);
            ps.setString(1, pCodIn);
            ps.setString(2, pStock);
            ps.setString(3, pDescripcion);
            ps.setString(4, pPrecio);
            ps.setString(5, pLinea);
            ps.setString(6, pUnidad);
            
            System.out.println("Registro grabado");
            ps.executeUpdate();
            ps.close();
            } 
            catch (Exception e) {
            System.out.println( "error " + e.getMessage());
        }
    }
    public void mtdObtenerProd(){
        strSQL = "SELECT * FROM inventario";
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
    public clsInventario mtdBuscarProducto(String pDato) {
	strSQL = "SELECT * FROM inventario WHERE Cod_Inv = ?";
        clsInventario prbqd = null;
	PreparedStatement ps;
	try {
		ps = objcnn.prepareStatement(strSQL);
		ps.setString(1, pDato);

		ResultSet rs = ps.executeQuery();
		while(rs.next()){
                        prbqd= new clsInventario();
                    
			prbqd.setCodigo_Inv(rs.getString("Cod_Inv"));
                        prbqd.setStock(rs.getString("Stock"));
			prbqd.setDescripcion(rs.getString("Descripcion"));
			prbqd.setDescripcion(rs.getString("Precio"));
                        prbqd.setLinea(rs.getString("Linea"));
                        prbqd.setUnidad(rs.getString("Unidad"));

		}
		rs.close();
		ps.close();
	} catch(Exception e) {
		System.out.println(e.getMessage());
	}
        return prbqd;
    }
    public void mtdEliminarProducto(String pCodigo){
        strSQL = "DELETE FROM inventario WHERE Codigo = '" + pCodigo + "'".trim();
        try{
            objStt = objcnn.createStatement();
            objStt.execute(strSQL); //INSERT ...
            System.out.println("Registro eliminado");
        }
        catch(SQLException eSQL){
            System.out.println("ERROR en la eliminacion: " + eSQL );
        }
    }

    //FACTURAS
    public void mtdAgregarDatof ( String pFEmi_Salida, int pCodigo,int pcodi_inven, int pIdfacsalida ){
        strSQL = "INSERT INTO factura_salida (FEmi_Salida , Codigo , codi_inven , Idfacsalida)"
                + "VALUES (?, ?, ?, ?)";
        PreparedStatement ps;
                 
        try {
            ps = objcnn.prepareStatement(strSQL);
            ps.setString(1, pFEmi_Salida);
            ps.setInt(2, pCodigo);
            ps.setInt(3, pcodi_inven);
            ps.setInt(4, pIdfacsalida);
            
            System.out.println("registro grabado");
            ps.executeUpdate();
            ps.close();
        } 
        catch (SQLException e) {
            System.out.println("Error en escritura del registro" + e.getMessage());
        }
    }
    public void mtdObtenerDatosTablaf(){
        strSQL = "SELECT * FROM factura_salida";
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
    
    public ArrayList<clsFacturas> obtenerTodosf(){
        ArrayList<clsFacturas> list = new ArrayList();
        String SQL = "SELECT * FROM factura_salida";
        PreparedStatement ps;
        ResultSet rs;
        clsFacturas us;
        
        try {
            ps= objcnn.prepareStatement(SQL);
            rs= ps.executeQuery();
            
            while(rs.next()){
                us = new clsFacturas();
                us.setIdfacsalida(rs.getString("Idfacsalida"));
               us.setCodigo(rs.getString("Codigo"));
               us.setCodi_inven(rs.getString("codi_inven"));
               us.setFEmi_Salida(rs.getString("FEmi_Salida"));
                list.add(us);
            }
            ps.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(clsConexion.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return list;
    }
    
    //USUARIOS
    public void mtdAgregarDatoUsuarios ( String pID, String pClave, String pUsuario,  String pCorreo){
        strSQL = "INSERT INTO usuarios (Clave , Usuario , Correo)VALUES(?, ?, ?)";
        PreparedStatement ps;
                 
        try {
            ps = objcnn.prepareStatement(strSQL);
            ps.setString(1, pClave);
            ps.setString(2, pUsuario);
            ps.setString(3, pCorreo);
           
            System.out.println("registro grabado");
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en escritura del registro" +e.getMessage());
        }
    }
    public void mtdObtenerDatosTablaUsuarios(){
        strSQL = "SELECT * FROM usuarios ";
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
    public clsUsuarios mtdBuscarRegistroUsuario(String pDato){
        strSQL = "SELECT * FROM usuarios WHERE ID = ?";
        clsUsuarios usuario = null;
	PreparedStatement ps;
	try {
		ps = objcnn.prepareStatement(strSQL);
		ps.setString(1, pDato);

		ResultSet rs = ps.executeQuery();
		while(rs.next()){
                        usuario = new clsUsuarios();
			usuario.setID(rs.getString("ID"));
                        usuario.setClave(rs.getString("Clave"));
			usuario.setUsuario(rs.getString("Usuario"));
                        usuario.setCorreo(rs.getString("Correo"));
                      
                       
		}
		rs.close();
		ps.close();
	} catch(Exception e) {
		System.out.println(e.getMessage());
	}
        return usuario;
    }  
    public ArrayList<clsUsuarios> obtenerTodosUsuarios(){
        ArrayList<clsUsuarios> list = new ArrayList();
        String SQL = "SELECT * FROM usuarios ";
        PreparedStatement ps;
        ResultSet rs;
        clsUsuarios us;
        
        try {
            ps= objcnn.prepareStatement(SQL);
            rs= ps.executeQuery();
            
            while(rs.next()){
                us = new clsUsuarios();
                us.setID(rs.getString("ID"));
                us.setClave(rs.getString("Clave"));
                us.setUsuario(rs.getString("Usuario"));
                us.setCorreo(rs.getString("Correo"));
                list.add(us);
            }
            ps.close();
            rs.close();

        } catch (Exception ex) {
            Logger.getLogger(clsConexion.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return list;
    }
    public void mtdEliminarDatoUsuario(String pID){
        strSQL = "DELETE FROM usuarios WHERE ID = '" + pID + "'".trim();
        try{
            objStt = objcnn.createStatement();
            objStt.execute(strSQL); //INSERT ...
            System.out.println("Registro eliminado");
        }
        catch(SQLException eSQL){
            System.out.println("ERROR en la eliminacion: " + eSQL );
        }
    }
    
    //LOGIN
    public int mtdLogin(String nombre, String clave){
        String strSQL3 = "SELECT * FROM usuarios WHERE Usuario = ? AND Clave = ?";      
	PreparedStatement ps;
        int salida = -1;
	try {
		ps = objcnn.prepareStatement(strSQL3);
		ps.setString(1, nombre);
                ps.setString(2, clave);

		ResultSet rs = ps.executeQuery();
		if(rs.next()){
                    salida =1;
                }
		rs.close();
		ps.close();
	} catch(Exception e) {
		System.out.println(e.getMessage());
	}
        return salida;
    }
}

