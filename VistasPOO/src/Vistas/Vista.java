/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import Controladores.ControladorBoleto;
import Controladores.ControladorFuncion;
import Controladores.ControladorPelicula;
import Controladores.ControladorSala;
import Controladores.ControladorSilla;
import Controladores.ControladorUsuario;
import Modelos.Boleto;
import Modelos.Funcion;
import Modelos.Pelicula;
import Modelos.Sala;
import Modelos.Silla;
import Modelos.Usuario;
import java.awt.event.ItemEvent;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author juanc
 */
public class Vista extends javax.swing.JFrame {
    ControladorBoleto miControladorBoleto;
    ControladorFuncion miControladorFuncion;
    ControladorPelicula miControladorPelicula;
    ControladorSala miControladorSala;
    ControladorSilla miControladorSilla;
    ControladorUsuario miControladorUsuario;
    String urlServidor = "http://127.0.0.1:8080";
    LinkedList<Funcion> misFunciones;
    int indexFunciones;
    LinkedList <Silla> MisSillas;

    /**
     * Creates new form Vista
     */
    public Vista() {
        initComponents();
        boxTipoBoleto();
        
        this.miControladorSala = new ControladorSala(urlServidor, "/salas");
        this.miControladorSilla = new ControladorSilla(urlServidor, "/sillas");
        this.miControladorPelicula = new ControladorPelicula(urlServidor,"/peliculas");
        this.miControladorFuncion = new ControladorFuncion(urlServidor,"/funciones");
        this.miControladorBoleto = new ControladorBoleto(urlServidor, "/boletos");
        this.miControladorUsuario = new ControladorUsuario(urlServidor, "/usuarios");
        
        boxTipoFuncion();
        actualizarTablaPeliculas();
        actualizarTablaUsuarios();
        actualizarTablaBoletos();
    }
    public void actualizarTablaPeliculas(){
        String nombresColumnas[] = {"Nombre", "Año", "Tipo"};
        DefaultTableModel miModelo = new DefaultTableModel(null, nombresColumnas);
        this.tbPelicula.setModel(miModelo);
        LinkedList <Pelicula> peliculas = this.miControladorPelicula.listar();
        for(Pelicula actual : peliculas){
            String fila[] = new String[nombresColumnas.length];
            fila[0] = actual.getNombre();
            fila[1] = ""+ actual.getAno();
            fila[2] = actual.getTipo();
            miModelo.addRow(fila);
        }
    }
    public void actualizarTablaUsuarios(){
        String nombresColumnas[] = {"Cedula", "Nombre", "Email", "Año nacimiento"};
        DefaultTableModel miModelo = new DefaultTableModel(null, nombresColumnas);
        this.TbUsuario.setModel(miModelo);
        LinkedList <Usuario> usuarios = this.miControladorUsuario.listar();
        for(Usuario actual: usuarios){
            String fila[]= new String[nombresColumnas.length];
            fila[0] = actual.getCedula();
            fila[1] = actual.getNombre();
            fila[2] = actual.getEmail();
            fila[3] = ""+actual.getAnoNacimiento();
            miModelo.addRow(fila);
        }
    }
    public void actualizarTablaBoletos(){
        String nombresColumnas[] = {"Nombre Usuario", "Tipo", "Valor", "Funcion", "Silla"};
        DefaultTableModel miModelo = new DefaultTableModel(null, nombresColumnas);
        this.tbBoletos.setModel(miModelo);
        LinkedList <Boleto> boletos = this.miControladorBoleto.listar();
        for(Boleto actual: boletos){
            String numeroSilla = "" + actual.getMiSilla().getNumero();
            String letraSilla = actual.getMiSilla().getLetra();
            System.out.println("Id boleto" +  actual.getId());
            Funcion aux = actual.getMiFuncion();
            System.out.println("Hora funcion: " + aux.getHora());
            String salaHora = datosFuncion(aux);
            String fila[] = new String[nombresColumnas.length];
            fila[0] = actual.getMiUsuario().getNombre();
            fila[1] = actual.getTipo();
            fila[2] = "" + actual.getValor();
            fila[3] = salaHora;
            fila[4] = letraSilla +numeroSilla;
            miModelo.addRow(fila);
        }
    }
    
    public void boxTipoBoleto(){
        this.boxTipoBoleto.removeAllItems();
        this.boxTipoBoleto.addItem("Nino");
        this.boxTipoBoleto.addItem("Adulto");
    }
    
    public void boxTipoFuncion(){
        this.boxFuncionBoleto.removeAllItems();
        this.misFunciones = this.miControladorFuncion.listar();
        
        for(Funcion actual : this.misFunciones){
            String resultado = datosFuncion(actual);
            this.boxFuncionBoleto.addItem(resultado);
        }
    }
    
    public String datosFuncion(Funcion funcionActual){
        String nombreSala = funcionActual.getMiSala().getNombre();
        String nombrePelicula = funcionActual.getMiPelicula().getNombre();
        String laFecha = ""+ funcionActual.getDia()+ "-" + funcionActual.getMes() + "-" + funcionActual.getAno();
        String salaHora = nombreSala + ": "+nombrePelicula + " Hora: "+ funcionActual.getHora() + " fecha: " + laFecha;
        return salaHora;
    }
    
    public void boxTipoSilla(){
        this.BoxSillaBoleto.removeAllItems();
        
        System.out.println("Item seleccionado: " + this.boxFuncionBoleto.getSelectedItem());
        Funcion aux = this.misFunciones.get(this.indexFunciones);
        this.BoxSillaBoleto.removeAllItems();
        Sala salaAux = aux.getMiSala();
        System.out.println("Nombre sala: " + salaAux.getNombre());
        this.MisSillas = this.miControladorSilla.listarPorSala(salaAux.getId());
        for(Silla actual : MisSillas){
            this.BoxSillaBoleto.addItem(actual.getLetra() + " " + actual.getNumero());
        }
    }
    
    public void LimpiarCamposBoleto(){
        this.TxtIdBoleto.setText("");
        this.TxtValorBoleto.setText("");
        this.TxtCedulaUsuarioBoleto.setText("");
    }
    
    public void LimpiarCamposUsuario(){
        this.TxtIdUsuario.setText("");
        this.TxtNombreUsuario.setText("");
        this.TxtCedulaUsuario.setText("");
        this.TxtEmailUsuario.setText("");
        this.TxtAnoNacimientoUsuario.setText("");
    }
    
    public void LimpiarCamposPelicula(){
        this.TxtIdPelicula.setText("");
        this.TxtAnoPelicula.setText("");
        this.TxtNombrePelicula.setText("");
        this.TxtTipoPelicula.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        BtnCrearBoleto = new javax.swing.JButton();
        BtnBuscarBoleto = new javax.swing.JButton();
        BtnEliminarBoleto = new javax.swing.JButton();
        BtnEditarBoleto = new javax.swing.JButton();
        TxtIdBoleto = new javax.swing.JTextField();
        TxtValorBoleto = new javax.swing.JTextField();
        TxtCedulaUsuarioBoleto = new javax.swing.JTextField();
        boxFuncionBoleto = new javax.swing.JComboBox<>();
        boxTipoBoleto = new javax.swing.JComboBox<>();
        BoxSillaBoleto = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbBoletos = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        BtnCrearPelicula = new javax.swing.JButton();
        BtnBuscarPelicula = new javax.swing.JButton();
        BtnEditarPelicula = new javax.swing.JButton();
        BtnEliminarPelicula = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        TxtIdPelicula = new javax.swing.JTextField();
        TxtAnoPelicula = new javax.swing.JTextField();
        TxtNombrePelicula = new javax.swing.JTextField();
        TxtTipoPelicula = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbPelicula = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        TxtAnoNacimientoUsuario = new javax.swing.JTextField();
        TxtCedulaUsuario = new javax.swing.JTextField();
        TxtNombreUsuario = new javax.swing.JTextField();
        TxtEmailUsuario = new javax.swing.JTextField();
        TxtIdUsuario = new javax.swing.JTextField();
        BtnCrearUsuario = new javax.swing.JButton();
        BtnBuscarUsuario = new javax.swing.JButton();
        BtnEditarUsuario = new javax.swing.JButton();
        BtnEliminarUsuario = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        TbUsuario = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        BtnCrearBoleto.setText("Crear");
        BtnCrearBoleto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BtnCrearBoletoMouseClicked(evt);
            }
        });
        BtnCrearBoleto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCrearBoletoActionPerformed(evt);
            }
        });

        BtnBuscarBoleto.setText("Buscar");
        BtnBuscarBoleto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBuscarBoletoActionPerformed(evt);
            }
        });

        BtnEliminarBoleto.setText("Eliminar");
        BtnEliminarBoleto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEliminarBoletoActionPerformed(evt);
            }
        });

        BtnEditarBoleto.setText("Editar");
        BtnEditarBoleto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditarBoletoActionPerformed(evt);
            }
        });

        TxtValorBoleto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtValorBoletoActionPerformed(evt);
            }
        });

        TxtCedulaUsuarioBoleto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtCedulaUsuarioBoletoActionPerformed(evt);
            }
        });

        boxFuncionBoleto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        boxFuncionBoleto.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                boxFuncionBoletoItemStateChanged(evt);
            }
        });
        boxFuncionBoleto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxFuncionBoletoActionPerformed(evt);
            }
        });

        boxTipoBoleto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        boxTipoBoleto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxTipoBoletoActionPerformed(evt);
            }
        });

        BoxSillaBoleto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setText("Id");

        jLabel3.setText("Valor");

        jLabel4.setText("Función");

        jLabel5.setText("Tipo");

        jLabel6.setText("Cedula Usuario");

        jLabel7.setText("Silla");

        tbBoletos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tbBoletos);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(boxFuncionBoleto, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(TxtIdBoleto, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(TxtValorBoleto, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 37, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BoxSillaBoleto, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtCedulaUsuarioBoleto, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boxTipoBoleto, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(111, 111, 111))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(BtnCrearBoleto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtnBuscarBoleto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtnEditarBoleto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtnEliminarBoleto)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TxtIdBoleto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boxTipoBoleto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TxtValorBoleto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtCedulaUsuarioBoleto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(BoxSillaBoleto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(boxFuncionBoleto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnCrearBoleto)
                    .addComponent(BtnBuscarBoleto)
                    .addComponent(BtnEditarBoleto)
                    .addComponent(BtnEliminarBoleto))
                .addGap(21, 21, 21)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Boletos", jPanel2);

        BtnCrearPelicula.setText("Crear");
        BtnCrearPelicula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCrearPeliculaActionPerformed(evt);
            }
        });

        BtnBuscarPelicula.setText("Buscar");
        BtnBuscarPelicula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBuscarPeliculaActionPerformed(evt);
            }
        });

        BtnEditarPelicula.setText("Editar");
        BtnEditarPelicula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditarPeliculaActionPerformed(evt);
            }
        });

        BtnEliminarPelicula.setText("Eliminar");
        BtnEliminarPelicula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEliminarPeliculaActionPerformed(evt);
            }
        });

        jLabel8.setText("Id");

        jLabel9.setText("Nombre");

        jLabel10.setText("Año");

        jLabel11.setText("Tipo");

        TxtIdPelicula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtIdPeliculaActionPerformed(evt);
            }
        });

        TxtAnoPelicula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtAnoPeliculaActionPerformed(evt);
            }
        });

        TxtNombrePelicula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtNombrePeliculaActionPerformed(evt);
            }
        });

        TxtTipoPelicula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtTipoPeliculaActionPerformed(evt);
            }
        });

        tbPelicula.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tbPelicula);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel11))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TxtTipoPelicula, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TxtIdPelicula, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TxtAnoPelicula, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TxtNombrePelicula, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(BtnCrearPelicula)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BtnBuscarPelicula)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BtnEditarPelicula)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BtnEliminarPelicula)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(TxtIdPelicula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(TxtNombrePelicula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(TxtAnoPelicula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(TxtTipoPelicula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnCrearPelicula)
                    .addComponent(BtnBuscarPelicula)
                    .addComponent(BtnEditarPelicula)
                    .addComponent(BtnEliminarPelicula))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Peliculas", jPanel3);

        jLabel12.setText("Id");

        jLabel13.setText("Cédula");

        jLabel14.setText("Nombre");

        jLabel15.setText("Email");

        jLabel16.setText("Año Nacimiento");

        BtnCrearUsuario.setText("Crear");
        BtnCrearUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCrearUsuarioActionPerformed(evt);
            }
        });

        BtnBuscarUsuario.setText("Buscar");
        BtnBuscarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBuscarUsuarioActionPerformed(evt);
            }
        });

        BtnEditarUsuario.setText("Editar");
        BtnEditarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditarUsuarioActionPerformed(evt);
            }
        });

        BtnEliminarUsuario.setText("Eliminar");
        BtnEliminarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEliminarUsuarioActionPerformed(evt);
            }
        });

        TbUsuario.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(TbUsuario);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel15))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TxtEmailUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TxtNombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TxtCedulaUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TxtIdUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TxtAnoNacimientoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(BtnCrearUsuario)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BtnBuscarUsuario)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BtnEditarUsuario)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BtnEliminarUsuario)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(TxtIdUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(TxtCedulaUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(TxtNombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(TxtEmailUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(TxtAnoNacimientoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnCrearUsuario)
                    .addComponent(BtnEditarUsuario)
                    .addComponent(BtnEliminarUsuario)
                    .addComponent(BtnBuscarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Usuarios", jPanel4);

        jLabel1.setText("CINE LATINO");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(328, 328, 328))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnCrearBoletoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCrearBoletoActionPerformed
        // TODO add your handling code here:
        String stringValor = TxtValorBoleto.getText();
        String tipo = "" + this.boxTipoBoleto.getSelectedItem();
        Usuario usuarioAux = this.miControladorUsuario.buscarPorCedula(TxtCedulaUsuarioBoleto.getText());
        double valor = Double.parseDouble(stringValor);
        if(usuarioAux == null){
            JOptionPane.showMessageDialog(this, "No se encuentra el usuario");
        }else{
            System.out.println("Se encontro con exito");
            Silla miSilla = this.MisSillas.get(this.BoxSillaBoleto.getSelectedIndex());
            
            Boleto nuevoBoleto = new Boleto(valor, tipo);
            Funcion funcionAux = this.misFunciones.get(this.indexFunciones);
            
            nuevoBoleto.setMiSilla(miSilla);
            nuevoBoleto.setMiFuncion(funcionAux);
            nuevoBoleto.setMiUsuario(usuarioAux);
            
            nuevoBoleto = this.miControladorBoleto.crear(nuevoBoleto);
            nuevoBoleto = this.miControladorBoleto.actualizarRelaciones(nuevoBoleto, miSilla.getId(), "silla");
            nuevoBoleto = this.miControladorBoleto.actualizarRelaciones(nuevoBoleto, funcionAux.getId(), "funcion");
            nuevoBoleto = this.miControladorBoleto.actualizarRelaciones(nuevoBoleto, usuarioAux.getId(), "usuario");
            this.TxtIdBoleto.setText(nuevoBoleto.getId());
            actualizarTablaBoletos();
            JOptionPane.showMessageDialog(null, "Boleto encontrado con el sigueinte id: " +nuevoBoleto.getId());
        }
    }//GEN-LAST:event_BtnCrearBoletoActionPerformed

    private void BtnCrearBoletoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnCrearBoletoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCrearBoletoMouseClicked

    private void BtnBuscarBoletoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBuscarBoletoActionPerformed
        // TODO add your handling code here:
        String cedulaUsuario = this.TxtCedulaUsuarioBoleto.getText();
        LinkedList<Boleto> boletos = this.miControladorBoleto.listar();
        Boleto encontrado = new Boleto();
        for (Boleto boletoActual : boletos) {
            Usuario actual = boletoActual.getMiUsuario();
            if (cedulaUsuario.equals(actual.getCedula())) {
                encontrado = boletoActual;
                System.out.println("boletoencontrado **********" + encontrado);
            } else {
                System.out.println("boleto no encontrado");
            }

        }
        if (encontrado != null) {
            this.TxtIdBoleto.setText(encontrado.getId());
            this.TxtValorBoleto.setText("" + encontrado.getValor());
            this.TxtCedulaUsuarioBoleto.setText(encontrado.getMiUsuario().getCedula());
            this.boxTipoBoleto.removeAllItems();
            this.boxTipoBoleto.addItem(encontrado.getTipo());
            this.boxFuncionBoleto.removeAllItems();
            String funcionEncontrado = datosFuncion(encontrado.getMiFuncion());
            this.boxFuncionBoleto.addItem(funcionEncontrado);
            this.BoxSillaBoleto.removeAllItems();
            String numeroSilla = "" + encontrado.getMiSilla().getNumero();
            String sillaEncontrado = encontrado.getMiSilla().getLetra() + numeroSilla;
            this.BoxSillaBoleto.addItem(sillaEncontrado);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el boleto");
        }
    }//GEN-LAST:event_BtnBuscarBoletoActionPerformed

    private void BtnEliminarBoletoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarBoletoActionPerformed
        // TODO add your handling code here:
        try {
            String id = this.TxtIdBoleto.getText();
            this.miControladorBoleto.eliminar(id);

            JOptionPane.showMessageDialog(null, "Eliminación exitosa");
            actualizarTablaBoletos();
            LimpiarCamposBoleto();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eliminación sin éxito " + e);
        }
    }//GEN-LAST:event_BtnEliminarBoletoActionPerformed

    private void TxtValorBoletoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtValorBoletoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtValorBoletoActionPerformed

    private void TxtCedulaUsuarioBoletoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtCedulaUsuarioBoletoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtCedulaUsuarioBoletoActionPerformed

    private void TxtIdPeliculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtIdPeliculaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtIdPeliculaActionPerformed

    private void TxtAnoPeliculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtAnoPeliculaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtAnoPeliculaActionPerformed

    private void TxtNombrePeliculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtNombrePeliculaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtNombrePeliculaActionPerformed

    private void TxtTipoPeliculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtTipoPeliculaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtTipoPeliculaActionPerformed

    private void BtnCrearPeliculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCrearPeliculaActionPerformed
        // TODO add your handling code here:
        String nombre = this.TxtNombrePelicula.getText();
        int ano = Integer.parseInt(this.TxtAnoPelicula.getText());
        String tipo = this.TxtTipoPelicula.getText();

        Pelicula nuevaPelicula = new Pelicula(nombre, ano, tipo);
        nuevaPelicula = this.miControladorPelicula.crear(nuevaPelicula);

        if (nuevaPelicula == null) {
            JOptionPane.showMessageDialog(null, "No se pudo crear la pelicula");
        } else {
            JOptionPane.showMessageDialog(null, "Pelicula creada exitosamente con id " + nuevaPelicula.getId());
            this.TxtIdPelicula.setText(nuevaPelicula.getId());
            actualizarTablaPeliculas();
        }
    }//GEN-LAST:event_BtnCrearPeliculaActionPerformed

    private void BtnBuscarPeliculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBuscarPeliculaActionPerformed
        // TODO add your handling code here:
        String nombre = this.TxtNombrePelicula.getText();
        Pelicula encontrado = this.miControladorPelicula.buscarPorNombre(nombre);
        if (encontrado != null) {
            this.TxtIdPelicula.setText(encontrado.getId());
            this.TxtNombrePelicula.setText(encontrado.getNombre());
            this.TxtAnoPelicula.setText(""+encontrado.getAno());
            this.TxtTipoPelicula.setText(encontrado.getTipo());
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo encontrar la pelicula");
        }
    }//GEN-LAST:event_BtnBuscarPeliculaActionPerformed

    private void BtnEditarPeliculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditarPeliculaActionPerformed
        // TODO add your handling code here:
        try {
            String id = this.TxtIdPelicula.getText();
            String nombre = this.TxtNombrePelicula.getText();
            int ano = Integer.parseInt(this.TxtAnoPelicula.getText());
            String tipo = this.TxtTipoPelicula.getText();

            Pelicula peliculaActualizada = new Pelicula(nombre, ano, tipo);
            peliculaActualizada.setId(id);

            Pelicula actualizado = this.miControladorPelicula.actualizar(peliculaActualizada);

            this.TxtIdPelicula.setText(actualizado.getId());
            this.TxtNombrePelicula.setText(actualizado.getNombre());
            this.TxtAnoPelicula.setText("" + actualizado.getAno());
            this.TxtTipoPelicula.setText(actualizado.getTipo());
            JOptionPane.showMessageDialog(this, "Pelicula actualizada con éxito");
            actualizarTablaPeliculas();
            LimpiarCamposPelicula();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar la pelicula " + e);
        }
    }//GEN-LAST:event_BtnEditarPeliculaActionPerformed

    private void BtnEliminarPeliculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarPeliculaActionPerformed
        // TODO add your handling code here:
        try {
            String id = this.TxtIdPelicula.getText();
            this.miControladorPelicula.eliminar(id);

            JOptionPane.showMessageDialog(null, "Eliminación exitosa");
            actualizarTablaPeliculas();
            LimpiarCamposPelicula();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eliminación sin éxito " + e);
        }
    }//GEN-LAST:event_BtnEliminarPeliculaActionPerformed

    private void BtnCrearUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCrearUsuarioActionPerformed
        // TODO add your handling code here:
        String cedula = this.TxtCedulaUsuario.getText();
        String nombre = this.TxtNombreUsuario.getText();
        String email = this.TxtEmailUsuario.getText();
        int anoNacimiento = Integer.parseInt(this.TxtAnoNacimientoUsuario.getText());

        Usuario nuevoUsuario = new Usuario(cedula, nombre, email, anoNacimiento);
        nuevoUsuario = this.miControladorUsuario.crear(nuevoUsuario);

        if (nuevoUsuario == null) {
            JOptionPane.showMessageDialog(null, "Problemas al crear el usuario");
        } else {
            JOptionPane.showMessageDialog(null, "Usuario creado exitosamente con id " + nuevoUsuario.getId());
            this.TxtIdUsuario.setText(nuevoUsuario.getId());
            actualizarTablaUsuarios();
        }
    }//GEN-LAST:event_BtnCrearUsuarioActionPerformed

    private void BtnBuscarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBuscarUsuarioActionPerformed
        // TODO add your handling code here:
        String cedula = this.TxtCedulaUsuario.getText();
        Usuario encontrado = this.miControladorUsuario.buscarPorCedula(cedula);
        if (encontrado != null) {
            this.TxtIdUsuario.setText(encontrado.getId());
            this.TxtCedulaUsuario.setText(encontrado.getCedula());
            this.TxtNombreUsuario.setText(encontrado.getNombre());
            this.TxtEmailUsuario.setText(encontrado.getEmail());
            this.TxtAnoNacimientoUsuario.setText("" + encontrado.getAnoNacimiento());

        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el usuario");
        }
    }//GEN-LAST:event_BtnBuscarUsuarioActionPerformed

    private void BtnEditarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditarUsuarioActionPerformed
        // TODO add your handling code here:
        try {
            String id = this.TxtIdUsuario.getText();
            String cedula = this.TxtCedulaUsuario.getText();
            String nombre = this.TxtNombreUsuario.getText();
            String email = this.TxtEmailUsuario.getText();
            int anoNacimiento = Integer.parseInt(this.TxtAnoNacimientoUsuario.getText());

            Usuario usuarioActualizado = new Usuario(cedula, nombre, email, anoNacimiento);
            usuarioActualizado.setId(id);

            Usuario actualizado = this.miControladorUsuario.actualizar(usuarioActualizado);

            this.TxtIdUsuario.setText(actualizado.getId());
            this.TxtCedulaUsuario.setText(actualizado.getCedula());
            this.TxtNombreUsuario.setText(actualizado.getNombre());
            this.TxtEmailUsuario.setText(actualizado.getEmail());
            this.TxtAnoNacimientoUsuario.setText("" + actualizado.getAnoNacimiento());
            JOptionPane.showMessageDialog(this, "Usuario actualizado correctamente");
            actualizarTablaUsuarios();
            LimpiarCamposUsuario();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el usuario " + e);
        }
    }//GEN-LAST:event_BtnEditarUsuarioActionPerformed

    private void BtnEliminarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarUsuarioActionPerformed
        // TODO add your handling code here:
        try {
            String id = this.TxtIdUsuario.getText();
            this.miControladorUsuario.eliminar(id);

            JOptionPane.showMessageDialog(null, "Eliminación exitosa");
            actualizarTablaUsuarios();
            LimpiarCamposUsuario();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eliminación sin éxito " + e);
        }
    }//GEN-LAST:event_BtnEliminarUsuarioActionPerformed

    private void boxFuncionBoletoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxFuncionBoletoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_boxFuncionBoletoActionPerformed

    private void boxFuncionBoletoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_boxFuncionBoletoItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            if (!this.boxFuncionBoleto.getSelectedItem().equals("elige uno...")) {
                this.indexFunciones = this.boxFuncionBoleto.getSelectedIndex();
                boxTipoSilla();

            }
        }
    }//GEN-LAST:event_boxFuncionBoletoItemStateChanged

    private void BtnEditarBoletoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditarBoletoActionPerformed
        // TODO add your handling code here:
        try {
            String id = this.TxtIdBoleto.getText();
            double valor = Double.parseDouble(this.TxtValorBoleto.getText());
            String tipo = "" + this.boxTipoBoleto.getSelectedItem();
            Usuario usuarioaux = this.miControladorUsuario.buscarPorCedula(TxtCedulaUsuarioBoleto.getText());
//            Silla sillaaux=""+this.boxSillaBoleto.getSelectedItem();
            if (usuarioaux == null) {
                JOptionPane.showMessageDialog(this, "Usuario no encotrado");
            } else {
                System.out.println("encontrado con exito");
                Silla miSilla = this.MisSillas.get(this.BoxSillaBoleto.getSelectedIndex());

                Boleto boletoActualizado = new Boleto(valor, tipo);
                Funcion funcionAux = this.misFunciones.get(this.indexFunciones);

                boletoActualizado.setId(id);

                boletoActualizado.setMiSilla(miSilla);
                boletoActualizado.setMiFuncion(funcionAux);
                boletoActualizado.setMiUsuario(usuarioaux);
                //            miSilla.setMiBoleto(NuevoBoleto);
                //            usuarioaux.getMisBoletos().add(NuevoBoleto);
                Boleto BoletoActualizado = this.miControladorBoleto.actualizar(boletoActualizado);
                boletoActualizado = this.miControladorBoleto.actualizarRelaciones(boletoActualizado, miSilla.getId(), "silla");
                System.out.println("000000,,,,,--------antes de " + boletoActualizado.getValor());
                boletoActualizado = this.miControladorBoleto.actualizarRelaciones(boletoActualizado, funcionAux.getId(), "funcion");
                boletoActualizado = this.miControladorBoleto.actualizarRelaciones(boletoActualizado, usuarioaux.getId(), "usuario");
                System.out.println("000000,,,,,--------" + boletoActualizado.getValor());

                System.out.println("hasta aqui nice");
                this.TxtIdBoleto.setText(BoletoActualizado.getId());
                this.TxtValorBoleto.setText("" + BoletoActualizado.getValor());
                this.TxtCedulaUsuario.setText(BoletoActualizado.getMiUsuario().getCedula());
                this.boxTipoBoleto.removeAllItems();
                this.boxTipoBoleto.addItem(BoletoActualizado.getTipo());
                this.boxFuncionBoleto.removeAllItems();
                String funcionEncontrado = datosFuncion(BoletoActualizado.getMiFuncion());
                this.boxFuncionBoleto.addItem(funcionEncontrado);
                this.BoxSillaBoleto.removeAllItems();
                String numeroSilla = "" + BoletoActualizado.getMiSilla().getNumero();
                String sillaEncontrado = BoletoActualizado.getMiSilla().getLetra() + numeroSilla;
                this.BoxSillaBoleto.addItem(sillaEncontrado);
                JOptionPane.showMessageDialog(this, "Boleto actualizado");
                actualizarTablaBoletos();
                LimpiarCamposBoleto();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el boleto " + e);
        }
    }//GEN-LAST:event_BtnEditarBoletoActionPerformed

    private void boxTipoBoletoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxTipoBoletoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_boxTipoBoletoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Vista().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> BoxSillaBoleto;
    private javax.swing.JButton BtnBuscarBoleto;
    private javax.swing.JButton BtnBuscarPelicula;
    private javax.swing.JButton BtnBuscarUsuario;
    private javax.swing.JButton BtnCrearBoleto;
    private javax.swing.JButton BtnCrearPelicula;
    private javax.swing.JButton BtnCrearUsuario;
    private javax.swing.JButton BtnEditarBoleto;
    private javax.swing.JButton BtnEditarPelicula;
    private javax.swing.JButton BtnEditarUsuario;
    private javax.swing.JButton BtnEliminarBoleto;
    private javax.swing.JButton BtnEliminarPelicula;
    private javax.swing.JButton BtnEliminarUsuario;
    private javax.swing.JTable TbUsuario;
    private javax.swing.JTextField TxtAnoNacimientoUsuario;
    private javax.swing.JTextField TxtAnoPelicula;
    private javax.swing.JTextField TxtCedulaUsuario;
    private javax.swing.JTextField TxtCedulaUsuarioBoleto;
    private javax.swing.JTextField TxtEmailUsuario;
    private javax.swing.JTextField TxtIdBoleto;
    private javax.swing.JTextField TxtIdPelicula;
    private javax.swing.JTextField TxtIdUsuario;
    private javax.swing.JTextField TxtNombrePelicula;
    private javax.swing.JTextField TxtNombreUsuario;
    private javax.swing.JTextField TxtTipoPelicula;
    private javax.swing.JTextField TxtValorBoleto;
    private javax.swing.JComboBox<String> boxFuncionBoleto;
    private javax.swing.JComboBox<String> boxTipoBoleto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tbBoletos;
    private javax.swing.JTable tbPelicula;
    // End of variables declaration//GEN-END:variables
}
