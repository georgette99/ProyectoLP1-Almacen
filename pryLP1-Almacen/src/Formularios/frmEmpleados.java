
package Formularios;

import ConexionBD.clsConexion;
import java.awt.Image;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.dao.empleadosDAO;
import modelo.dao.proveedoresDAO;
import pckClases.clsEmpleados;

public class frmEmpleados extends javax.swing.JFrame {

   clsConexion objcnn = new clsConexion();
   empleadosDAO clcn = new empleadosDAO();
   private clsEmpleados empleado;
   Connection con;
   DefaultTableModel modelo;
 
    
   private String[] cabecera = {"Codigo", "Nombres", "Ap_pat", "Ap_mat", "Cargo", "Area"};
    
    public frmEmpleados() {
        initComponents();
        listar(1);
        
        setLocationRelativeTo(this);
        btnAgregar.setIcon(setIcono("/Imagenes/Agregar.png", btnAgregar));
        btnEditar.setIcon(setIcono("/Imagenes/editar.png", btnEditar));
        btnEliminar.setIcon(setIcono("/Imagenes/Eliminar.png", btnEliminar));
        btnBuscar.setIcon(setIcono("/Imagenes/buscar.png", btnBuscar));
        btnLimpiar.setIcon(setIcono("/Imagenes/limpiar.png", btnLimpiar));
    }
    
    void inicializarTabla() {
        DefaultTableModel df = new DefaultTableModel(null, cabecera);
        TablaEmpleados.setModel(df);
    }
    
    void listarTodos() throws ClassNotFoundException {
        ArrayList<clsEmpleados> clnts = empleadosDAO.obtenerTodos();
        listarTabla(clnts);
    }
    
    void listarTabla(ArrayList<clsEmpleados> lista) {
        DefaultTableModel df = (DefaultTableModel) TablaEmpleados.getModel();
        df.setRowCount(0);
        
        for (clsEmpleados c:lista) {
            df.addRow(new Object[]{
                c.getCodigo(),
                c.getNombre(),
                c.getAp_m(),
                c.getAp_p(),
                c.getDni(),
                c.getDireccion(),
                c.getTelefono(),
                c.getCorreo(),
                c.getSexo(),
                c.getCargo(),
                c.getArea_tra()
            });
        }
    }
    
    void limpiarCampos() {
        txtNombres.setText("");
        txtDNI.setText("");
        txtAp_m.setText("");
        txtAp_p.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtSexo.setText("");
        txtCargo.setText("");
        txtAr_trab.setText("");
     //   txtCodigo.setText("");
    }
    
    void limpiarEmpleado() {
        empleado = null;
    }
    
    void insertar() throws ClassNotFoundException {
        String nombre = txtNombres.getText();
        String ap_p = txtAp_p.getText();
        String ap_m =txtAp_m.getText();
        String DNI = txtDNI.getText();
        String direccion = txtDireccion.getText();
        String telefono = txtTelefono.getText();
        String correo = txtCorreo.getText();
        String sexo = txtSexo.getText();
        String cargo = txtCargo.getText();
        String area = txtAr_trab.getText();
        
        empleado = new clsEmpleados();
        
        empleado.setNombre(nombre);
        empleado.setDni(DNI);
        empleado.setAp_m(ap_m);
        empleado.setAp_p(ap_p);
        empleado.setSexo(sexo);
        empleado.setDireccion(direccion);
        empleado.setTelefono(telefono);
        empleado.setCorreo(correo);
        empleado.setCargo(cargo);
        empleado.setArea_tra(area);
        
        int res = empleadosDAO.mtdAgregarDato(empleado);
        
        if(res == 0){
            JOptionPane.showMessageDialog(this, "No se logró ingresar el empleado");
        } else {
            JOptionPane.showMessageDialog(this, "Se ingresó correctamente al empleado");
            limpiarCampos();
            listarTodos();
        }
        
        
    }
    
    void listar(int opcion){
        modelo = new DefaultTableModel(null, cabecera);
        TablaEmpleados.setModel(modelo);
        try {
            objcnn.mtdAbrirBD();
            switch (opcion){
                case 1:
                    ArrayList<clsEmpleados> usu = clcn.obtenerTodos();
                    for(clsEmpleados u:usu){
                        modelo.addRow(new Object[]{
                            u.getCodigo(), u.getNombre(), u.getAp_p(), u.getAp_m(), u.getArea_tra(), u.getCargo(),
                        });
                    }
                    break;
           /*     case 2:
                    String Codigo = txtBuscar.getText();
                    clsClientes usr1 = objcnn.mtdBuscarRegistroCliente(Codigo);
                    modelo.addRow(new Object[]{
                        usr1.getCodigo(), usr1.getRUC(), usr1.getNombre(), usr1.getDireccion(), usr1.getTelefono(), usr1.getCorreo(),});
                    break;*/

                default:
                    System.out.println("Opción inválida");
                    break;
            }

            
            objcnn.mtdCerrarBD();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(frmEmpleados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void cargarEmpleados() {
        if(empleado != null) {
            
            txtNombres.setText(empleado.getNombre());
            txtDNI.setText(empleado.getDni());
            txtDireccion.setText(empleado.getDireccion());
            txtCorreo.setText(empleado.getCorreo());
            txtTelefono.setText(empleado.getTelefono());
            txtAp_m.setText(empleado.getAp_m());
            txtAp_p.setText(empleado.getAp_p());
            txtSexo.setText(empleado.getSexo());
            txtCargo.setText(empleado.getCargo());
            txtAr_trab.setText(empleado.getArea_tra());
        }
    }
    
    void seleccionar() throws ClassNotFoundException {
        int fila = TablaEmpleados.getSelectedRow();
        
        if(fila >= 0) {
            
            int codigo = (int) TablaEmpleados.getModel().getValueAt(fila, 0);
            
            empleado = empleadosDAO.obtenerUno(codigo);
            
            cargarEmpleados();
         //   habilitarBotones(false);
        }
    }
    
    
    
    
    void modificar() throws ClassNotFoundException {
        
        if(empleado!= null) {
            
            String nombre = txtNombres.getText();
            String ap_pat = txtAp_p.getText();
            String ap_mat = txtAp_m.getText();
            String dni = txtDNI.getText();
            String direccion = txtDireccion.getText();
            String correo = txtCorreo.getText();
            String telefono = txtTelefono.getText();
            String sexo = txtSexo.getText();
            String cargo = txtCargo.getText();
            String area = txtAr_trab.getText();

            empleado.setNombre(nombre);
            empleado.setAp_p(ap_pat);
            empleado.setAp_m(ap_mat);
            empleado.setDni(dni);
            empleado.setDireccion(direccion);
            empleado.setTelefono(telefono);
            empleado.setCorreo(correo);
            empleado.setSexo(sexo);
            empleado.setCargo(cargo);
            empleado.setArea_tra(area);
            
            
            
            int res = empleadosDAO.mtdModificarDatoEmpleado(empleado);
            
            if(res == 0){
                JOptionPane.showMessageDialog(this, "No se logró modificar el empleado");
            } else {
                JOptionPane.showMessageDialog(this, "Se modificó correctamente al empleado");
                limpiarCampos();
                listarTodos();
                limpiarEmpleado();
            }
        }
        
    }
    
    void eliminar() throws ClassNotFoundException {
        if(empleado != null) {
            
            int opt = JOptionPane.showConfirmDialog(this, "¿Desea eliminar al empleado"+ empleado.getNombre() +"?");
            
            if(opt == JOptionPane.OK_OPTION) {
                int res = empleadosDAO.eliminar(empleado.getCodigo());
            
                if(res == 0){
                    JOptionPane.showMessageDialog(this, "No se logró eliminar el empleado");
                } else {
                    JOptionPane.showMessageDialog(this, "Se eliminó correctamente al empleado");
                    limpiarCampos();
                    listarTodos();
                    limpiarEmpleado();
                }
            }
        }
    }
    
    /*void listar(int opcion){
        modelo = new DefaultTableModel(null, cabecera);
        TablaEmpleados.setModel(modelo);
        try {
            objcnn.mtdAbrirBD();
            switch (opcion){
                case 1:
                    ArrayList<clsEmpleados> usu = objcnn.obtenerTodos();
                    for(clsEmpleados u:usu){
                        modelo.addRow(new Object[]{
                            u.getCodigo(), u.getNombre(), u.getAp_p(), u.getAp_m(), u.getArea_tra(), u.getCargo(),
                        });
                    }
                    break;
                case 2:
                    String nombre = txtBuscar.getText();
                    clsEmpleados usr1 = objcnn.mtdBuscarRegistro(nombre);
                    modelo.addRow(new Object[]{
                        usr1.getCodigo(), usr1.getNombre(), usr1.getAp_p(), usr1.getAp_m(), usr1.getCargo(), usr1.getArea_tra(),});
                    break;
                case 3:
                    String nombre2 = txtNuevo.getText();
                    clsUsuario usr2 = objAccesoBD.mtdBuscarRegistro(nombre2);
                    modelo.addRow(new Object[]{
                        usr2.getId(), usr2.getNombre(), usr2.getClave()
                    });
                    break;
                default:
                    System.out.println("Opción inválida");
                    break;
            }

            
            objcnn.mtdCerrarBD();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(frmEmpleados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
   
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        labelEmpleado = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        TablaEmpleados = new javax.swing.JTable();
        btnLimpiar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtDNI = new javax.swing.JTextField();
        txtNombres = new javax.swing.JTextField();
        txtAp_p = new javax.swing.JTextField();
        txtAp_m = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtSexo = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtCargo = new javax.swing.JTextField();
        txtAr_trab = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        btnAgregar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jButton6.setText("jButton6");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 51, 51));

        labelEmpleado.setFont(new java.awt.Font("Yellow Rabbit - Personal Use", 0, 72)); // NOI18N
        labelEmpleado.setForeground(new java.awt.Color(255, 255, 255));
        labelEmpleado.setText("Empleados");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(350, 350, 350)
                .addComponent(labelEmpleado)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(0, 153, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Consulta", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel3.setForeground(new java.awt.Color(0, 153, 102));

        btnBuscar.setBackground(new java.awt.Color(0, 51, 51));
        btnBuscar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        TablaEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        TablaEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaEmpleadosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TablaEmpleados);

        btnLimpiar.setBackground(new java.awt.Color(0, 51, 51));
        btnLimpiar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnLimpiar.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpiar.setText("Limpiar Registro");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnBuscar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLimpiar)
                .addGap(148, 148, 148))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(btnLimpiar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(0, 153, 102));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Registro", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel4.setForeground(new java.awt.Color(0, 153, 102));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Código:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Nombres:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Apellido Paterno:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Apellido Materno:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("DNI:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Correo:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Direccion:");

        txtCodigo.setEnabled(false);
        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });

        txtAp_p.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAp_pActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Teléfono:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Sexo:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Cargo:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setText("Área de trabajo:");

        txtAr_trab.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSexo)
                    .addComponent(txtCodigo)
                    .addComponent(txtDNI)
                    .addComponent(txtNombres)
                    .addComponent(txtAp_p)
                    .addComponent(txtAp_m)
                    .addComponent(txtDireccion)
                    .addComponent(txtCorreo)
                    .addComponent(txtTelefono)
                    .addComponent(txtCargo)
                    .addComponent(txtAr_trab, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDNI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAp_p, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAp_m, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtAr_trab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(0, 153, 102));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Opciones", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel5.setForeground(new java.awt.Color(0, 153, 102));

        btnAgregar.setBackground(new java.awt.Color(0, 51, 51));
        btnAgregar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnEditar.setBackground(new java.awt.Color(0, 51, 51));
        btnEditar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnEditar.setForeground(new java.awt.Color(255, 255, 255));
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(0, 51, 51));
        btnEliminar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAgregar, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar)
                    .addComponent(btnEditar)
                    .addComponent(btnEliminar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenu1.setText("Opciones");

        jMenuItem1.setText("Regresar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtAp_pActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAp_pActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAp_pActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
       try {
           insertar();
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(frmClientes.class.getName()).log(Level.SEVERE, null, ex);
       }
        limpiar();
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        try {
            objcnn.mtdAbrirBD();
            
            String  CampoDeBusqueda;
            CampoDeBusqueda = txtBuscar.getText();
            if(objcnn.mtdBuscarRegistro(CampoDeBusqueda)== null){
                System.out.println("No se ha encontrado el registro");
            }
            else{
                System.out.println("Registro encontrado");
                listar(2);
            }
            
            objcnn.mtdCerrarBD();
        } catch (Exception e) {
            System.out.println("Error" + e );
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        this.dispose();
        frmPrincipal principal = new frmPrincipal();
        principal.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
          try {
           eliminar();
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(frmEmpleados.class.getName()).log(Level.SEVERE, null, ex);
       }  
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        txtCodigo.setText("");
        txtDNI.setText("");
        txtNombres.setText("");
        txtAp_p.setText("");
        txtAp_m.setText("");
        txtDireccion.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtSexo.setText("");
        txtCargo.setText("");
        txtAr_trab.setText("");
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void TablaEmpleadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaEmpleadosMouseClicked
       try {
           seleccionar();
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(frmEmpleados.class.getName()).log(Level.SEVERE, null, ex);
       }
        
        
    }//GEN-LAST:event_TablaEmpleadosMouseClicked

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        try {
           modificar();
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(frmEmpleados.class.getName()).log(Level.SEVERE, null, ex);
       }
    }//GEN-LAST:event_btnEditarActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmEmpleados().setVisible(true);
            }
        });
    }
    
    public Icon setIcono(String url, JButton boton){
         ImageIcon icon = new ImageIcon(getClass().getResource(url));
         ImageIcon icono = new ImageIcon(icon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
         boton.setIcon(icono);
         return icono;
    }
    
    void limpiar(){
        txtCodigo.setText("");
        txtAp_p.setText("");
        txtAp_m.setText("");
        txtNombres.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtCargo.setText("");
        txtSexo.setText("");
        txtDNI.setText("");
        txtTelefono.setText("");
    }
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TablaEmpleados;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel labelEmpleado;
    private javax.swing.JTextField txtAp_m;
    private javax.swing.JTextField txtAp_p;
    private javax.swing.JTextField txtAr_trab;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCargo;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDNI;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtNombres;
    private javax.swing.JTextField txtSexo;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
