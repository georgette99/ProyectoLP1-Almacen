
package Formularios;

import ConexionBD.clsConexion;
import java.awt.Image;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import pckClases.clsClientes;
import modelo.dao.clienteDAO;

public final class frmClientes extends javax.swing.JFrame {
    
   clsConexion objcnn = new clsConexion();
   clienteDAO clcn = new clienteDAO();
   private clsClientes cliente;
   Connection con;
   DefaultTableModel modelo;
    
   private String[] cabecera = {"Codigo", "RUC", "Nombre", "Direccion", "Telefono", "Correo"};
    
    public frmClientes() {
        initComponents();
        listar(1);
        setLocationRelativeTo(this);
        btnAgregar.setIcon(setIcono("/Imagenes/Agregar.png", btnAgregar));
        btnEditar.setIcon(setIcono("/Imagenes/editar.png", btnEditar));
        btnEliminar.setIcon(setIcono("/Imagenes/Eliminar.png", btnEliminar));
        btnBuscar.setIcon(setIcono("/Imagenes/buscar.png", btnBuscar));
   
    }
    
    void inicializarTabla() {
        DefaultTableModel df = new DefaultTableModel(null, cabecera);
        TablaClientes.setModel(df);
    }
    
    void listarTodos() throws ClassNotFoundException {
        ArrayList<clsClientes> clnts = clienteDAO.obtenerTodosClientes();
        listarTabla(clnts);
    }
    
    void listarTabla(ArrayList<clsClientes> lista) {
        DefaultTableModel df = (DefaultTableModel) TablaClientes.getModel();
        df.setRowCount(0);
        
        for (clsClientes c:lista) {
            df.addRow(new Object[]{
                c.getCodigo(),
                c.getRUC(),
                c.getNombre(),
                c.getDireccion(),
                c.getTelefono(),
                c.getCorreo()
            });
        }
    }
    
    void limpiarCampos() {
        txtNombre.setText("");
        txtRUC.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
     //   txtCodigo.setText("");
    }
    
    void limpiarCliente() {
        cliente = null;
    }
    
    void insertar() throws ClassNotFoundException {
        String nombre = txtNombre.getText();
        String RUC = txtRUC.getText();
        String direccion = txtDireccion.getText();
        String telefono = txtTelefono.getText();
        String correo = txtCorreo.getText();
        
        cliente = new clsClientes();
        
        cliente.setNombre(nombre);
        cliente.setRUC(RUC);
        cliente.setDireccion(direccion);
        cliente.setTelefono(telefono);
        cliente.setCorreo(correo);
        
        int res = clienteDAO.mtdAgregarDatoCliente(cliente);
        
        if(res == 0){
            JOptionPane.showMessageDialog(this, "No se logró ingresar el cliente");
        } else {
            JOptionPane.showMessageDialog(this, "Se ingresó correctamente al cliente");
            limpiarCampos();
            listarTodos();
        }
        
        
    }
    
    void listar(int opcion){
        modelo = new DefaultTableModel(null, cabecera);
        TablaClientes.setModel(modelo);
        try {
            objcnn.mtdAbrirBD();
            switch (opcion){
                case 1:
                    ArrayList<clsClientes> usu = clcn.obtenerTodosClientes();
                    for(clsClientes u:usu){
                        modelo.addRow(new Object[]{
                            u.getCodigo(), u.getRUC(), u.getNombre(),u.getDireccion(), u.getTelefono(), u.getCorreo(),
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
    
    void cargarCliente() {
        if(cliente != null) {
            
            txtNombre.setText(cliente.getNombre());
            txtRUC.setText(cliente.getRUC());
            txtDireccion.setText(cliente.getDireccion());
            txtCorreo.setText(cliente.getCorreo());
            txtTelefono.setText(cliente.getTelefono());
        }
    }
    
    void seleccionar() throws ClassNotFoundException {
        int fila = TablaClientes.getSelectedRow();
        
        if(fila >= 0) {
            
            int codigo = (int) TablaClientes.getModel().getValueAt(fila, 0);
            
            cliente = clienteDAO.obtenerUno(codigo);
            
            cargarCliente();
         //   habilitarBotones(false);
        }
    }
    
    
    
    
    void modificar() throws ClassNotFoundException {
        
        if(cliente != null) {
            
            String nombre = txtNombre.getText();
            String ruc = txtRUC.getText();
            String direccion = txtDireccion.getText();
            String telefono = txtTelefono.getText();
            String correo = txtCorreo.getText();

            cliente.setNombre(nombre);
            cliente.setDireccion(direccion);
            cliente.setTelefono(telefono);
            cliente.setCorreo(correo);
            cliente.setRUC(ruc);
            
            int res = clienteDAO.mtdModificarDatoCliente(cliente);
            
            if(res == 0){
                JOptionPane.showMessageDialog(this, "No se logró modificar el cliente");
            } else {
                JOptionPane.showMessageDialog(this, "Se modificó correctamente al cliente");
                limpiarCampos();
                listarTodos();
                limpiarCliente();
            }
        }
        
    }
    
    void eliminar() throws ClassNotFoundException {
        if(cliente != null) {
            
            int opt = JOptionPane.showConfirmDialog(this, "¿Desea eliminar al cliente "+ cliente.getNombre() +"?");
            
            if(opt == JOptionPane.OK_OPTION) {
                int res = clienteDAO.eliminar(cliente.getCodigo());
            
                if(res == 0){
                    JOptionPane.showMessageDialog(this, "No se logró eliminar el cliente");
                } else {
                    JOptionPane.showMessageDialog(this, "Se eliminó correctamente al cliente");
                    limpiarCampos();
                    listarTodos();
                    limpiarCliente();
                }
            }
        }
    }
    /*void listar(int opcion){
        modelo = new DefaultTableModel(null, cabecera);
        TablaClientes.setModel(modelo);
        try {
            objcnn.mtdAbrirBD();
            switch (opcion){
                case 1:
                    ArrayList<clsClientes> usu = objcnn.obtenerTodosClientes();
                    for(clsClientes u:usu){
                        modelo.addRow(new Object[]{
                            u.getCodigo(), u.getRUC(), u.getNombre(),u.getDireccion(), u.getTelefono(), u.getCorreo(),
                        });
                    }
                    break;
                case 2:
                    String Codigo = txtBuscar.getText();
                    clsClientes usr1 = objcnn.mtdBuscarRegistroCliente(Codigo);
                    modelo.addRow(new Object[]{
                        usr1.getCodigo(), usr1.getRUC(), usr1.getNombre(), usr1.getDireccion(), usr1.getTelefono(), usr1.getCorreo(),});
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

        jPanel1 = new javax.swing.JPanel();
        labelClientes = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtNombre = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtRUC = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        btnAgregar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaClientes = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 153, 0));

        labelClientes.setFont(new java.awt.Font("Yellow Rabbit - Personal Use", 0, 72)); // NOI18N
        labelClientes.setForeground(new java.awt.Color(255, 255, 255));
        labelClientes.setText("Clientes");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelClientes)
                .addGap(401, 401, 401))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(255, 204, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Registro", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        jLabel2.setText("R.U.C.");

        jLabel3.setText("Dirección:");

        jLabel4.setText("Teléfono:");

        jLabel5.setText("Correo:");

        jLabel6.setText("Código:");

        txtCodigo.setEnabled(false);
        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });

        jLabel1.setText("Nombre:");

        txtRUC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRUCActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 204, 102));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Opciones", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        btnAgregar.setBackground(new java.awt.Color(204, 153, 0));
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnEditar.setBackground(new java.awt.Color(204, 153, 0));
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(204, 153, 0));
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar)
                    .addComponent(btnEditar)
                    .addComponent(btnEliminar))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNombre)
                            .addComponent(txtDireccion)
                            .addComponent(txtTelefono)
                            .addComponent(txtCorreo)
                            .addComponent(txtRUC, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(84, 84, 84))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtRUC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 204, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Consulta", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        btnBuscar.setBackground(new java.awt.Color(204, 153, 0));
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        TablaClientes.setModel(new javax.swing.table.DefaultTableModel(
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
        TablaClientes.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                TablaClientesAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        TablaClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TablaClientes);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btnBuscar))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscar)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenu1.setText("Opciones");
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        jMenuItem1.setText("Regresar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed
        this.dispose();
        frmPrincipal principal = new frmPrincipal();
        principal.setVisible(true);
    }//GEN-LAST:event_jMenu1ActionPerformed

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
            if(objcnn.mtdBuscarRegistroCliente(CampoDeBusqueda)== null){
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
    }
          
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        this.dispose();
        frmPrincipal principal = new frmPrincipal();
        principal.setVisible(true);
    }                                          

    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmEmpleados().setVisible(true);
            }
        });
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void TablaClientesAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_TablaClientesAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_TablaClientesAncestorAdded

    private void TablaClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaClientesMouseClicked
       try {
           seleccionar();
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(frmClientes.class.getName()).log(Level.SEVERE, null, ex);
       }
    }//GEN-LAST:event_TablaClientesMouseClicked

    private void txtRUCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRUCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRUCActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        try {
           modificar();
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(frmClientes.class.getName()).log(Level.SEVERE, null, ex);
       }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

       try {
           eliminar();
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(frmClientes.class.getName()).log(Level.SEVERE, null, ex);
       }  
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        this.dispose();
        frmPrincipal principal = new frmPrincipal();
        principal.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed
    
    public Icon setIcono(String url, JButton boton){
         ImageIcon icon = new ImageIcon(getClass().getResource(url));
         ImageIcon icono = new ImageIcon(icon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
         boton.setIcon(icono);
         return icono;
    }
    
    void limpiar(){
        txtCodigo.setText("");
        txtRUC.setText("");
        txtNombre.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TablaClientes;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelClientes;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtRUC;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
