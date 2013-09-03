package hm;
//Declaracion de librerias
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Icon.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListModel.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.swing.JOptionPane;
import java.awt.*;

public class Pacientes extends javax.swing.JFrame {
//Declarando las claces

    Paciente objCliente;
    Arreglo_Paciente objArreglo;
    DefaultTableModel miModelo2;
    DefaultListModel Lista2;
    String[] cabecera = {"Nº", "Código", "RUC", "DNI", "Dirección", "Fecha", "Teléfono", "Celular", "Fax", "Forma P.", "Linea C.", "E-Mail", "Medico"};
    String[][] data = {};
//Variables globales
    int num = 0;

    public Pacientes() {
        initComponents();
        Deshabilitar();
        Lista2 = new DefaultListModel();
        jListClientes.setModel(Lista2);
        miModelo2 = new DefaultTableModel(data, cabecera);
        jTblClientes.setModel(miModelo2);
        objArreglo = new Arreglo_Paciente();
        cargarDatos();
        actualizarTabla();
        actualizarLista();
        txtObraS.requestFocus();
        //jLabel2.setVisible(false);
    }

    public String generateCode() {
        int cod = (int) (Math.random() * (99999 - 10000)) + 10000;
        String codig = String.valueOf(cod);
        return codig;
    }

    public void cargarDatos() {
        //Lee la data del objeto serializable
        try {
            FileInputStream fis = new FileInputStream("Clientes.bin");
            ObjectInputStream ois = new ObjectInputStream(fis);
            if (ois != null) {
                objArreglo = (Arreglo_Paciente) ois.readObject();
                ois.close();
            }
        }//Fin del try
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar el archivo binaro: " + e);
        }
    }

//METODO PARA GRABAR LA INFORMACION DEL ARREGLO DE OBJETOS AL ARCHIVO BINARIO
    public void Grabar() {
        //Guarda la data en el archivo serializado
        try {
            FileOutputStream fos = new FileOutputStream("Clientes.bin");
            ObjectOutputStream out = new ObjectOutputStream(fos);
            //Comprobando si esta vacio
            if (out != null) {
                out.writeObject(objArreglo);
                out.close();
            }//Fin del if

        }//Fin del try
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en la grabacion sobre el arreglo: " + e);
        }
    }//Fin de Grabar

//METODOS PARA MANIPULAR LA TABLA Y EL jLIST
    public void actualizarTabla() {
        //Vaciando la informacion de la tabla
        vaciarTabla();
//Capturando el tamaño del arreglo
        int n = objArreglo.numeroClientes();

        for (int i = 0; i < n; i++) {
//Extrallendo informacion de cada objeto del arreglo
            String codigo = objArreglo.getCliente(i).getCodigo();
            String RUC = objArreglo.getCliente(i).getRUC();
            String DNI = objArreglo.getCliente(i).getDNI();
            String direccion = objArreglo.getCliente(i).getDireccion();
            String razon = objArreglo.getCliente(i).getFecha();
            String telefono = objArreglo.getCliente(i).getTelefono();
            String celular = objArreglo.getCliente(i).getCelular();
            String fax = objArreglo.getCliente(i).getFax();
            String forma_p = objArreglo.getCliente(i).getForma_p();
            String credito = objArreglo.getCliente(i).getCredito();
            String mail = objArreglo.getCliente(i).getMail();
            String medico = objArreglo.getCliente(i).getMedico();

            Insertar(i + 1, codigo, RUC, DNI, direccion, razon, telefono, celular, fax, forma_p, credito, mail, medico);

        }//Fin del for
    }

    public void vaciarTabla() {
        int filas = jTblClientes.getRowCount();
        for (int i = 0; i < filas; i++) {
            miModelo2.removeRow(0); //Removiendo la fila de la tabla
        }//Fin del for
    }

    public void Insertar(int num, String codigo, String RUC, String DNI, String direccion, String razon,
            String telefono, String celular, String fax, String forma_p, String credito, String mail, String medico) {

//Llenando la fila de la tabla
        Object fila[] = {num, codigo, RUC, DNI, direccion, razon, telefono, celular, fax, forma_p, credito, mail, medico};
        //Agregando la fila a nuestro modelo de tabla
        miModelo2.addRow(fila);
    }

    public void actualizarLista() {
//Vaciamos la informacion de la lista
        Lista2.clear();
//Capturamos el tamaño del arreglo
//Iniziamos el ciclo repetitivo
        for (int i = 0; i < objArreglo.numeroClientes(); i++) {
//Extraemos informacion de cada objeto del arreglo para insertarla en la Lista
            Lista2.addElement(objArreglo.getCliente(i).getNombre().toUpperCase().trim());
            Lista2.getElementAt(i);
        }//Fin del for
    }

//METODOS UTILIZADOS PARA LAS CONSULTAS, ELIMINACION Y ACTUALIZACION DE DATOS
    public void Modificar() {
        String codigo = JOptionPane.showInputDialog("Ingresar: Código del paciente").toUpperCase().trim();
        String dato = verificaDatos();
        if (dato.equalsIgnoreCase("")) {
            if (codigo.equalsIgnoreCase("")) {
                mensaje("ERROR: Códico incorrecto");
                Habilitar();
                txtDNI.requestFocus();
            } else {
//Se vuelve a buscar el codigo para no repetir el mismo
                int p = objArreglo.Buscar(codigo);
//Se leen los datos de entrada de los text file
                codigo = lbllCodigo_o.getText();
                String RUC = txtObraS.getText();
                String DNI = txtDNI.getText();
                String nombre = txtNombre.getText();
                String direccion = txtDireccion.getText();
                String razon = txtFechaNac.getText();
                String telefono = txtTelefono.getText();
                String celular = txtCelular.getText();
                String fax = txtFax.getText();
                String forma_p = txtForma_p.getText();
                String credito = txtCredito.getText();
                String mail = txtMail.getText();
                String medico = jCbxMedico.getSelectedItem().toString();
                String obs = jTxaObs.getText();
                Icon foto = lblFoto_o.getIcon();

//Generando la clace para manejar un Registro academico
                objCliente = new Paciente(codigo, RUC, DNI, nombre, direccion, razon, telefono, celular, fax, forma_p, credito, mail, medico, obs, foto);
//Verificando si el codigo existe dentro del arreglo

                if (p == -1)//El codigo es nuevo
                {
                    objArreglo.Agregar(objCliente);
                } else//Codigo ya existente
                {
                    objArreglo.Reemplaza(p, objCliente);
                }

//Limpiando las entradas
                limpiarEntradas();
//Grabamos la informacion en el archivo binario
                Grabar();
//Actualizando la tabla
                actualizarTabla();
                actualizarLista();
            }//Fin de else
        } else {
            mensaje("Debe llenar los campos correspondientes del paciente que desea modificar");
        }
    }//Fin de modificar

    public void Eliminar() {
//Se llama al metodo consultar para ver los datos a borrar
        Consultar();
//Se llama al metodo busca en el arreglo que devuelve la posicion del codigo buscado
        int p = objArreglo.Buscar(lbllCodigo_o.getText().toUpperCase());
//Comprobando que el codigo existe
        if (p != -1) {
            int R = JOptionPane.showConfirmDialog(this, "Está seguro de eliminar éste registro?", "Responder", 0);
//Si la respuesta es AFIRMATIVA:
            if (R == 0) {
                //Eliminamos el objeto en la posicion capturada (Posicion "p")
                objArreglo.Elimina(p);
                limpiarEntradas();//Limpiando las entradas
                Grabar();//Grabamos la informacion en el archivo binario
                actualizarTabla();//Actualizamos la tabla
                actualizarLista();
                mensaje("Los datos se eliminaron correctamente");
            }//Fin del if
            //En caso el codigo no exista
            else {
                JOptionPane.showMessageDialog(null, "El código del alumno no existe");
            }

        }//Fin del if(Verificando que existe el codigo)


    }//Fin del metodo Eliminar

    public void Consultar() {
//Pedimos el código
        String codigo = JOptionPane.showInputDialog("Ingresar: Código").toUpperCase().trim();
//Se llama al metodo busca que devuelve la posicion del codigo buscado
        int p = objArreglo.Buscar(codigo);
//Comprobando si el codigo existe
        if (p == -1)//Cuando el codigo no exite
        {
            mensaje("Por favor verificar el código");
            limpiarEntradas();
        } else//Cuando existe el codigo
        {
            imprimirDatos(p);

        }//Fin del else

    }//Fin de Consultar

//METODOS SECUNDARIOS PARA EL DISEÑO DEL FORMULARIO
    public void Deshabilitar() {
        lbllCodigo_o.setEnabled(false);
        txtObraS.setEnabled(false);
        txtDNI.setEnabled(false);
        txtNombre.setEnabled(false);
        txtDireccion.setEnabled(false);
        txtFechaNac.setEnabled(false);
        txtTelefono.setEnabled(false);
        txtCelular.setEnabled(false);
        txtFax.setEnabled(false);
        txtForma_p.setEnabled(false);
        txtCredito.setEnabled(false);
        txtMail.setEnabled(false);
        jCbxMedico.setEnabled(false);
        btnFoto.setEnabled(false);
        jTxaObs.setEnabled(false);
    }

    public void Habilitar() {
        lbllCodigo_o.setEnabled(true);
        txtObraS.setEnabled(true);
        txtDNI.setEnabled(true);
        txtNombre.setEnabled(true);
        txtDireccion.setEnabled(true);
        txtFechaNac.setEnabled(true);
        txtTelefono.setEnabled(true);
        txtCelular.setEnabled(true);
        txtFax.setEnabled(true);
        txtForma_p.setEnabled(true);
        txtCredito.setEnabled(true);
        txtMail.setEnabled(true);
        jCbxMedico.setEnabled(true);
        btnFoto.setEnabled(true);
        jTxaObs.setEnabled(true);
        txtObraS.requestFocus();
    }

//METODO PARA MOSTRAR LOS MENSAJES DEL APLICATIVO
    public void mensaje(String texto) {
        JOptionPane.showMessageDialog(this, texto);
    }//Fin del mensaje

    public void limpiarEntradas() {
        lbllCodigo_o.setText("");
        txtObraS.setText("");
        txtDNI.setText("");
        txtNombre.setText("");
        txtDireccion.setText("");
        txtFechaNac.setText("");
        txtTelefono.setText("");
        txtCelular.setText("");
        txtFax.setText("");
        txtForma_p.setText("");
        txtCredito.setText("");
        txtMail.setText("");
        jCbxMedico.setSelectedIndex(0);
        lblFoto_o.setIcon(null);
        jTxaObs.setText("");
        txtObraS.requestFocus();
    }

    public void imprimirDatos(int pos) {
//Se extrae todo el objeto con toda la informacion
        objCliente = objArreglo.getCliente(pos);
//Se extrae la ifnromacion de los campos del objeto
        String codigo = objCliente.getCodigo();
        String RUC = objCliente.getRUC();
        String DNI = objCliente.getDNI();
        String nombre = objCliente.getNombre();
        String direccion = objCliente.getDireccion();
        String fecha = objCliente.getFecha();
        String telefono = objCliente.getTelefono();
        String celular = objCliente.getCelular();
        String fax = objCliente.getFax();
        String forma_p = objCliente.getForma_p();
        String credito = objCliente.getCredito();
        String mail = objCliente.getMail();
        String medico = objCliente.getMedico();
        String obs = objCliente.getObs();
        Icon foto = objCliente.getFoto();

//Colocando la informacion en los objetos
        lbllCodigo_o.setText(codigo);
        txtObraS.setText(RUC);
        txtDNI.setText(DNI);
        txtNombre.setText(nombre);
        txtDireccion.setText(direccion);
        txtFechaNac.setText(fecha);
        txtTelefono.setText(telefono);
        txtCelular.setText(celular);
        txtFax.setText(fax);
        txtForma_p.setText(forma_p);
        txtCredito.setText(credito);
        txtMail.setText(mail);
        jCbxMedico.setSelectedItem(medico);

        jTxaObs.setText(obs);
        lblFoto_o.setIcon(foto);
        Habilitar();
    }

    public String verificaDatos() {
        String dato = "";

        if (txtObraS.getText().equals("")) {
            dato = "Verifique los datos en el apartado: Num. Obra Social ";
            txtObraS.requestFocus();
            return dato;
        }

        if (txtDNI.getText().equals("")) {
            dato = "Verifique los datos en el apartado: DNI ";
            txtDNI.requestFocus();
            return dato;
        }

        if (txtNombre.getText().equals("")) {
            dato = "Verifique los datos en el apartado: Nombre ";
            txtNombre.requestFocus();
            return dato;
        }

        if (txtDireccion.getText().equals("")) {
            dato = "Verifique los datos en el apartado: Direccion ";
            txtDireccion.requestFocus();
            return dato;
        }

        if (txtFechaNac.getText().equals("")) {
            dato = "Verifique los datos en el apartado: Fecha de Nacimiento ";
            txtFechaNac.requestFocus();
            return dato;
        }

        if (txtTelefono.getText().equals("")) {
            dato = "Verifique los datos en el apartado: Nº Telefono";
            txtTelefono.requestFocus();
            return dato;
        }

        if (txtCelular.getText().equals("")) {
            dato = "Verifique los datos en el apartado: Nº Celular ";
            txtCelular.requestFocus();
            return dato;
        }

        if (txtFax.getText().equals("")) {
            dato = "Verifique los datos en el apartado: Nº Fax ";
            txtFax.requestFocus();
            return dato;
        }

        if (txtForma_p.getText().equals("")) {
            dato = "Verifique los datos en el apartado: Forma de pago ";
            txtForma_p.requestFocus();
            return dato;
        }

        if (txtCredito.getText().equals("")) {
            dato = "Verifique los datos en el apartado: Linea de credito ";
            txtCredito.requestFocus();
            return dato;
        }

        if (txtMail.getText().equals("")) {
            dato = "Verifique los datos en el apartado: E - Mail ";
            txtMail.requestFocus();
            return dato;
        }


        return "";
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblCodigo = new javax.swing.JLabel();
        lblObraS = new javax.swing.JLabel();
        txtObraS = new javax.swing.JTextField();
        lblDNI = new javax.swing.JLabel();
        txtDNI = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblDireccion = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        lblNacimiento = new javax.swing.JLabel();
        txtFechaNac = new javax.swing.JTextField();
        lblTel = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        lblCel = new javax.swing.JLabel();
        txtCelular = new javax.swing.JTextField();
        lblFax = new javax.swing.JLabel();
        txtFax = new javax.swing.JTextField();
        lblForma_p = new javax.swing.JLabel();
        txtForma_p = new javax.swing.JTextField();
        lblCredito = new javax.swing.JLabel();
        txtCredito = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        txtMail = new javax.swing.JTextField();
        lblMedico = new javax.swing.JLabel();
        lblOtros = new javax.swing.JLabel();
        btnFoto = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTxaObs = new javax.swing.JTextArea();
        btnIngresar = new javax.swing.JButton();
        jCbxMedico = new javax.swing.JComboBox();
        lbllCodigo_o = new javax.swing.JLabel();
        lblFoto_o = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListClientes = new javax.swing.JList();
        jPanel2 = new javax.swing.JPanel();
        btnGrabar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTblClientes = new javax.swing.JTable();
        btnLimpiar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("PACIENTES");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "INGRESAR LOS DATOS DEL CLIENTE: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        lblCodigo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblCodigo.setText("Codigo:");

        lblObraS.setText("Num. Obra Social:");

        lblDNI.setText("DNI:");

        lblNombre.setText("Nombre Completo:");

        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        lblDireccion.setText("Dirección:");

        lblNacimiento.setText("Fecha de Nacimiento:");

        txtFechaNac.setText("     DD/MM/AAAA");
        txtFechaNac.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtFechaNacMouseClicked(evt);
            }
        });
        txtFechaNac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaNacActionPerformed(evt);
            }
        });

        lblTel.setText("Nº Telefono:");

        lblCel.setText("Nº Celular:");

        lblFax.setText("Nº Fax:");

        lblForma_p.setText("Forma de pago:");

        lblCredito.setText("Línea Crédito:");

        lblEmail.setText("E - Mail:");

        lblMedico.setText("Medico:");

        lblOtros.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblOtros.setText("Otros Datos:");

        btnFoto.setBackground(new java.awt.Color(255, 255, 255));
        btnFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos graficos/user-icon.jpg"))); // NOI18N
        btnFoto.setText("FOTO DEL CLIENTE");
        btnFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFotoActionPerformed(evt);
            }
        });

        jTxaObs.setColumns(20);
        jTxaObs.setRows(5);
        jScrollPane1.setViewportView(jTxaObs);

        btnIngresar.setBackground(new java.awt.Color(255, 255, 255));
        btnIngresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos graficos/plus-icon.png"))); // NOI18N
        btnIngresar.setText("INGRESAR DATOS DEL CLIENTE");
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });

        jCbxMedico.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "------Seleccione----", "Endrizzi Silvana", "Sanchez Laura" }));

        lbllCodigo_o.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lbllCodigo_o.setForeground(new java.awt.Color(255, 0, 0));

        lblFoto_o.setBackground(new java.awt.Color(255, 255, 204));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, 628, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 200, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDireccion)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCodigo)
                                    .addComponent(lblObraS)
                                    .addComponent(lblDNI)
                                    .addComponent(lblNombre))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lbllCodigo_o, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtObraS, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                                            .addComponent(txtDNI, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtDireccion, javax.swing.GroupLayout.Alignment.TRAILING)))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCel)
                                    .addComponent(lblTel)
                                    .addComponent(lblNacimiento))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtCelular)
                                    .addComponent(txtTelefono)
                                    .addComponent(txtFechaNac, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblForma_p)
                            .addComponent(lblFax)
                            .addComponent(lblCredito)
                            .addComponent(lblEmail)
                            .addComponent(lblMedico))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtMail, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCredito, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtForma_p, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFax, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCbxMedico, javax.swing.GroupLayout.Alignment.LEADING, 0, 111, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblOtros)
                            .addComponent(btnFoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblFoto_o, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(lblOtros)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnFoto)
                                .addGap(23, 23, 23))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblFax)
                                    .addComponent(txtFax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblForma_p)
                                    .addComponent(txtForma_p, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblCredito)
                                    .addComponent(txtCredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblEmail)
                                    .addComponent(txtMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblMedico)
                                    .addComponent(jCbxMedico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lbllCodigo_o, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblCodigo))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblObraS)
                                    .addComponent(txtObraS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblDNI)
                                    .addComponent(txtDNI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblNombre)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblDireccion)
                                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblNacimiento)
                                    .addComponent(txtFechaNac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblTel)
                                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblCel)
                                    .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(lblFoto_o, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(btnIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jListClientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jListClientesKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(jListClientes);

        btnGrabar.setBackground(new java.awt.Color(255, 255, 255));
        btnGrabar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos graficos/save.png"))); // NOI18N
        btnGrabar.setText("Grabar");
        btnGrabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGrabarActionPerformed(evt);
            }
        });

        btnModificar.setBackground(new java.awt.Color(255, 255, 255));
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos graficos/icon_edit_large.gif"))); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(255, 255, 255));
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos graficos/RTEmagicC_EliminarPag_10_png.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos graficos/eqsl_exit.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnBuscar.setBackground(new java.awt.Color(255, 255, 255));
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos graficos/ico_search.jpg"))); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnGrabar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 8, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGrabar)
                .addGap(18, 18, 18)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnModificar)
                .addGap(18, 18, 18)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Título 5", "Título 6", "Título 7", "Título 8", "Título 9", "Título 10", "Título 11", "Título 12", "Título 13", "Título 14", "Título 15", "Título 16"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTblClientes);

        btnLimpiar.setBackground(new java.awt.Color(255, 255, 255));
        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos graficos/tango-clear_1.png"))); // NOI18N
        btnLimpiar.setText("LIMPIAR ENTRADAS");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel1)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 637, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(9, 9, 9)))
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.getAccessibleContext().setAccessibleName("INGRESAR LOS DATOS DEL PACIENTE: ");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFotoActionPerformed
        JFileChooser dlg = new JFileChooser();
        //Abre la ventana de dialogo
        int option = dlg.showOpenDialog(this);
        //Si hace click en el boton abrir del dialogo
        if (option == JFileChooser.APPROVE_OPTION) {
            //Obtiene nombre del archivo seleccionado
            String file = dlg.getSelectedFile().getPath();
            lblFoto_o.setIcon(new ImageIcon(file));
            //Modificando la imagen
            ImageIcon icon = new ImageIcon(file);
            //Se extrae la imagen del icono
            Image img = icon.getImage();
            //Se modifica su tamaño
            Image newimg = img.getScaledInstance(140, 170, java.awt.Image.SCALE_SMOOTH);
            //SE GENERA EL IMAGE ICON CON LA NUEVA IMAGEN
            ImageIcon newIcon = new ImageIcon(newimg);
            //Se coloca el nuevo icono modificado
            lblFoto_o.setIcon(newIcon);
            //Se cambia el tamaño de la etiqueta
            lblFoto_o.setSize(470, 290);
        }

}//GEN-LAST:event_btnFotoActionPerformed

    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarActionPerformed
        Habilitar();
        limpiarEntradas();
    }//GEN-LAST:event_btnIngresarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        Consultar();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGrabarActionPerformed
        String dato = verificaDatos();
        if (dato.equalsIgnoreCase("")) {
            //Se leen los datos de entrada de los text file
            String codigo = lbllCodigo_o.getText();
            String RUC = txtObraS.getText();
            String DNI = txtDNI.getText();
            String nombre = txtNombre.getText();
            String direccion = txtDireccion.getText();
            String fecha = txtFechaNac.getText();
            String telefono = txtTelefono.getText();
            String celular = txtCelular.getText();
            String fax = txtFax.getText();
            String forma_p = txtForma_p.getText();
            String credito = txtCredito.getText();
            String mail = txtMail.getText();
            String medico = jCbxMedico.getSelectedItem().toString();
            String obs = jTxaObs.getText();
            Icon foto = lblFoto_o.getIcon();


            //Generando la clase para manejar un Registro academico
            objCliente = new Paciente(codigo, RUC, DNI, nombre, direccion, fecha, telefono, celular, fax, forma_p, credito, mail, medico, obs, foto);
            //Verificando si el codigo existe dentro del arreglo
            if (objArreglo.Buscar(objCliente.getCodigo()) != -1) {
                mensaje("Codigo Repetido");
            } else {
                //Instanciamos una clace con diferente codigo para el caso sea un nuevo registro
                String cod = generateCode();
                objCliente = new Paciente(cod, RUC, DNI, nombre, direccion, fecha, telefono, celular, fax, forma_p, credito, mail, medico, obs, foto);
                //Se agrega el objeto al arreglo
                objArreglo.Agregar(objCliente);
                //Insertando la ifnromacion en la tabla
                Insertar(0, codigo, RUC, DNI, direccion, fecha, telefono, celular, fax, forma_p, credito, mail, medico);
                //Limpiando las entradas
                limpiarEntradas();
                //Grabando la ifnromacion en el archivo binario
                Grabar();
                //Actualizando la tabla de registros
                actualizarTabla();
                actualizarLista();

            }//Fin del else
        } else {
            mensaje(dato);
            Habilitar();
            txtDNI.requestFocus();
        }
    }//GEN-LAST:event_btnGrabarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        Modificar();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarEntradas();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        Eliminar();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void jListClientesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jListClientesKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            if (jListClientes.getSelectedIndex() == -1) {
                mensaje("No hay un nombre seleccionado");
            } else {
                Deshabilitar();
                String nombre = jListClientes.getSelectedValue().toString();
                int p = objArreglo.BuscarPorNombre(nombre);
                //Certificando la existencia de los datos
                if (p != -1) {
                    imprimirDatos(p);
                    jListClientes.requestFocus();
                }
            }

        }//Fin de cndicion de key pressed

    }//GEN-LAST:event_jListClientesKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        jCbxMedico.setSelectedItem("nombres");
    }//GEN-LAST:event_formWindowOpened

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        setVisible(false);
}//GEN-LAST:event_btnSalirActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtFechaNacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaNacActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaNacActionPerformed

    private void txtFechaNacMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtFechaNacMouseClicked

    }//GEN-LAST:event_txtFechaNacMouseClicked

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pacientes().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnFoto;
    private javax.swing.JButton btnGrabar;
    private javax.swing.JButton btnIngresar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox jCbxMedico;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList jListClientes;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTblClientes;
    private javax.swing.JTextArea jTxaObs;
    private javax.swing.JLabel lblCel;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblCredito;
    private javax.swing.JLabel lblDNI;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblFax;
    private javax.swing.JLabel lblForma_p;
    private javax.swing.JLabel lblFoto_o;
    private javax.swing.JLabel lblMedico;
    private javax.swing.JLabel lblNacimiento;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblObraS;
    private javax.swing.JLabel lblOtros;
    private javax.swing.JLabel lblTel;
    private javax.swing.JLabel lbllCodigo_o;
    private javax.swing.JTextField txtCelular;
    private javax.swing.JTextField txtCredito;
    private javax.swing.JTextField txtDNI;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtFax;
    private javax.swing.JTextField txtFechaNac;
    private javax.swing.JTextField txtForma_p;
    private javax.swing.JTextField txtMail;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtObraS;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
