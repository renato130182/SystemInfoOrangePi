/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.pi4j.system.SystemInfo;
import controller.ControllerProducao;
import controller.Login;
import dao.DadosDefaultDAO;
import dao.MaquinaDAO;
import dao.PesagemDAO;
import dao.ProducaoDAO;
import dao.ProdutoMaquinaDAO;
import dao.ProgramacaoMaquinaDAO;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.DadosConexao;
import model.Maquina;
import model.Pesagem;
import model.Producao;
import model.ProdutoCarretel;
import model.ProdutoMaquina;
import model.ProgramacaoMaquina;
import model.Usuario;
import Serial.*;
import controller.ControllerConfigSerialPort;
import controller.ControllerMicrometro;
import controller.ControllerParadasMaquina;
import dao.ProdutoCarretelDAO;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import model.Micrometro;

/**
 *
 * @author renato.soares
 */
public class JFPrincipal extends javax.swing.JFrame implements ActionListener {
    private static final String SERIAL_RFID = "rfidserial";
    private static final String SERIAL_MICROMETRO = "micrometroserial";
    private  static String identificador;
    private static String codMaquina;
    private long tempoSistema = System.currentTimeMillis(); 
    private double[] mediaVel = {0,0,0,0,0,0,0,0,0,0};  
    private boolean maqParada = false;
    private boolean evtRegistrado = true;
    private boolean iniciaLeituras = true;
    private int resumoRelatorio,linhas=14;
    private int eventosTimer,qtdEvt=10;
    private List<Long> metrosAlerta = new ArrayList<>();
    Login login = new Login();
    Maquina maquina = new Maquina();
    MaquinaDAO maqDao =  new MaquinaDAO();
    ProdutoMaquina prodmaq = new ProdutoMaquina();
    ProgramacaoMaquina prog = new ProgramacaoMaquina();
    ProdutoCarretel prodCar = new ProdutoCarretel();
    Producao prod = new Producao();
    SerialTxRx comRFID ;
    SerialTxRx comMicrometro;
    Micrometro leituraAtual = new Micrometro();
    Micrometro leituraAnterior = new Micrometro();
    ControllerMicrometro mic = new ControllerMicrometro();
    ControllerParadasMaquina paradas;
    
    /**
     * Creates new form JFPrincipal
     */
    
    Timer timerVelocimetro = new Timer();
    
    private void tarefaVelocidade(){
        int delay = 0;   // delay de 1 seg.
        int interval = 1100;  // intervalo de 1 seg.        

        timerVelocimetro.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("Enveto Timer");
                    
                    if(eventosTimer >= qtdEvt){
                        radialLcdVelocidade.setValueAnimated(mediaVelocidade(0));
                        System.out.println("Parada pelo Timer!!!!");
                        if(!maqParada){
                            abrirTelaParadas();                            
                        }
                        if(!evtRegistrado)evtRegistrado=paradas.registraInicioParadamaquina((long) 
                            displaySingleMetragemCarretel.getValue() , codMaquina);
                    }else{
                        eventosTimer++;
                    }
                    if(displaySingleEvtCarEntrada.getValue() <= maquina.getAlertaMetrosParaArrebentamento()){
                        lightBulbAlertaEvento.setOn(true);
                       
                        jLabelAlerta.setSize(250,125);
                        jLabelAlerta.setVisible(true);
                    }else{
                        lightBulbAlertaEvento.setOn(false);
                        jLabelAlerta.setVisible(false);
                    }
                    
                }                
            }, delay, interval);
    }
    
    public JFPrincipal() {
        try {
            if(System.getProperty("os.name").equals("Linux")){
                identificador = SystemInfo.getSerial();
            }else{
                identificador = "ADMINISTRADOR";
            }
            
            System.out.println("OS. name: " + System.getProperty("os.name"));
            System.out.println("Serial: " + identificador);
            initComponents();
            Canvas logo = new Canvas();
            this.jPLogo.add(logo);
            bloquearMenu();            
            DadosDefaultDAO dados = new DadosDefaultDAO();
            codMaquina = dados.buscaCodigoMaquina(identificador);
            if(codMaquina!=null){                
                maquina = maqDao.buscarDadosMaquina(codMaquina);
            }
            abrirTelaLogin();
            
        } catch (UnsupportedOperationException | IOException | InterruptedException ex) {
            Logger.getLogger(JFPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jpRoot = new javax.swing.JPanel();
        jpLogin = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jtfUser = new javax.swing.JTextField();
        jpfPassword = new javax.swing.JPasswordField();
        jbLogin = new javax.swing.JButton();
        jPLogo = new javax.swing.JPanel();
        jpProgramacao = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableProgramacao = new javax.swing.JTable();
        jpParadas = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel38 = new javax.swing.JLabel();
        jComboBoxParadasMaquina = new javax.swing.JComboBox<>();
        jLabel39 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextAreaObsParada = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableMotivosParada = new javax.swing.JTable();
        jButtonIncluirMotivoParada = new javax.swing.JButton();
        jButtonRemoverMotivoParada = new javax.swing.JButton();
        jButtonRegistrarParada = new javax.swing.JButton();
        jpConfigDefault = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jtfUserDafaut = new javax.swing.JTextField();
        jpfSenhaDefault = new javax.swing.JPasswordField();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jtfDriverProducao = new javax.swing.JTextField();
        jtfServidorProducao = new javax.swing.JTextField();
        jtfUrlProducao = new javax.swing.JTextField();
        jtfBDProducao = new javax.swing.JTextField();
        jtfUsuarioProducao = new javax.swing.JTextField();
        jtfDriverTeste = new javax.swing.JTextField();
        jtfServidorTeste = new javax.swing.JTextField();
        jtfUrlTeste = new javax.swing.JTextField();
        jtfBDTeste = new javax.swing.JTextField();
        jtfUsuarioTeste = new javax.swing.JTextField();
        jbCriarArquivosDefault = new javax.swing.JButton();
        jpfSenhaProducao = new javax.swing.JPasswordField();
        jpfSenhaTeste = new javax.swing.JPasswordField();
        jpProducao = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabelProducaoCodItem = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabelProducaoDescricaoItem = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabelProducaoQtdProg = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabelProducaoQtdProd = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabelProducaoOF = new javax.swing.JLabel();
        jLabelProducaoMetTotalProg = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabelProducaoMetTotalProd = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabelProducaoMetCarretel = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabelProducaoDMinimo = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabelProducaoDnominal = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabelProducaoDMaximo = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabelProducaoVelIdeal = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableProducaoArrebentamentos = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableProducaoParadas = new javax.swing.JTable();
        jSeparator7 = new javax.swing.JSeparator();
        radialLcdVelocidade = new eu.hansolo.steelseries.gauges.Radial4Lcd();
        linearCarretelSaida = new eu.hansolo.steelseries.gauges.Linear();
        displaySingleMetragemCarretel = new eu.hansolo.steelseries.gauges.DisplaySingle();
        linearProgramacao = new eu.hansolo.steelseries.gauges.Linear();
        displaySingleMetragemProgramado = new eu.hansolo.steelseries.gauges.DisplaySingle();
        jPanelEventoEntrada = new javax.swing.JPanel();
        displaySingleEvtCarEntrada = new eu.hansolo.steelseries.gauges.DisplaySingle();
        jLabel36 = new javax.swing.JLabel();
        lightBulbAlertaEvento = new eu.hansolo.lightbulb.LightBulb();
        jLabelAlerta = new javax.swing.JLabel();
        radialLcdDiametro = new eu.hansolo.steelseries.gauges.Radial4Lcd();
        menuPrincipal = new javax.swing.JMenuBar();
        menuLogin = new javax.swing.JMenu();
        jMenuItemLogin = new javax.swing.JMenuItem();
        jMenuItemSair = new javax.swing.JMenuItem();
        jMenuItemDesligar = new javax.swing.JMenuItem();
        jMenuItemReiniciar = new javax.swing.JMenuItem();
        menuProgramacao = new javax.swing.JMenu();
        jMenuItemProgramacao = new javax.swing.JMenuItem();
        menuParadas = new javax.swing.JMenu();
        jMenuItemParadas = new javax.swing.JMenuItem();
        menuProducao = new javax.swing.JMenu();
        jMenuItemProducao = new javax.swing.JMenuItem();
        menuConfiguracoes = new javax.swing.JMenu();
        jMenuConfigDefault = new javax.swing.JMenuItem();
        jMenuTrocarMaquina = new javax.swing.JMenuItem();
        jMenuConfigSerialRFID = new javax.swing.JMenuItem();

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Sistema Condumig");
        setExtendedState(JFPrincipal.MAXIMIZED_BOTH);
        setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        setName("framePrincipal"); // NOI18N
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowDeactivated(java.awt.event.WindowEvent evt) {
                formWindowDeactivated(evt);
            }
            public void windowIconified(java.awt.event.WindowEvent evt) {
                formWindowIconified(evt);
            }
        });

        jpRoot.setLayout(new java.awt.CardLayout());

        jpLogin.setBackground(new java.awt.Color(204, 255, 255));

        jLabel1.setText("Usuario: ");

        jLabel2.setText("Senha:");

        jtfUser.setText("renatoinf");

        jpfPassword.setText("vitor");

        jbLogin.setText("Login");
        jbLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbLoginActionPerformed(evt);
            }
        });

        jPLogo.setBackground(new java.awt.Color(204, 255, 255));
        jPLogo.setName("jPLogo"); // NOI18N
        jPLogo.setLayout(new java.awt.BorderLayout(100, 100));

        javax.swing.GroupLayout jpLoginLayout = new javax.swing.GroupLayout(jpLogin);
        jpLogin.setLayout(jpLoginLayout);
        jpLoginLayout.setHorizontalGroup(
            jpLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpLoginLayout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addGroup(jpLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jbLogin)
                    .addGroup(jpLoginLayout.createSequentialGroup()
                        .addGroup(jpLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jtfUser)
                            .addComponent(jpfPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE))))
                .addContainerGap(644, Short.MAX_VALUE))
            .addGroup(jpLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPLogo, javax.swing.GroupLayout.DEFAULT_SIZE, 903, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpLoginLayout.setVerticalGroup(
            jpLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpLoginLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jpLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtfUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jpfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbLogin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(399, Short.MAX_VALUE))
        );

        jpRoot.add(jpLogin, "jpLogin");

        jpProgramacao.setBackground(new java.awt.Color(204, 255, 255));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setText("Acompanhamento de Programação");

        jLabel22.setText("Itens programados:");

        jTableProgramacao.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTableProgramacao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Codigo Item", "Descrição", "Lote", "Quantidade", "Metragem prog.", "Data "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableProgramacao.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jScrollPane1.setViewportView(jTableProgramacao);
        if (jTableProgramacao.getColumnModel().getColumnCount() > 0) {
            jTableProgramacao.getColumnModel().getColumn(4).setHeaderValue("Metragem prog.");
            jTableProgramacao.getColumnModel().getColumn(5).setHeaderValue("Data ");
        }

        javax.swing.GroupLayout jpProgramacaoLayout = new javax.swing.GroupLayout(jpProgramacao);
        jpProgramacao.setLayout(jpProgramacaoLayout);
        jpProgramacaoLayout.setHorizontalGroup(
            jpProgramacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpProgramacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpProgramacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpProgramacaoLayout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(0, 669, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpProgramacaoLayout.createSequentialGroup()
                        .addGroup(jpProgramacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 923, Short.MAX_VALUE)
        );
        jpProgramacaoLayout.setVerticalGroup(
            jpProgramacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpProgramacaoLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22)
                .addGap(43, 43, 43)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 671, Short.MAX_VALUE)
                .addContainerGap())
        );

        jpRoot.add(jpProgramacao, "jpProgramacao");

        jpParadas.setBackground(new java.awt.Color(208, 93, 79));

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel37.setText("Apontamento de paradas.");

        jLabel38.setText("Motivo da Parada:");

        jComboBoxParadasMaquina.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel39.setText("Observações:");

        jTextAreaObsParada.setColumns(20);
        jTextAreaObsParada.setRows(5);
        jScrollPane4.setViewportView(jTextAreaObsParada);

        jTableMotivosParada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Cod. Parada", "Abreviação", "Descriçao","observação"
            }
        ));
        jScrollPane5.setViewportView(jTableMotivosParada);

        jButtonIncluirMotivoParada.setText("Incluir Motivo");
        jButtonIncluirMotivoParada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIncluirMotivoParadaActionPerformed(evt);
            }
        });

        jButtonRemoverMotivoParada.setText("Remover Motivo");
        jButtonRemoverMotivoParada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoverMotivoParadaActionPerformed(evt);
            }
        });

        jButtonRegistrarParada.setText("Registrar Parada");
        jButtonRegistrarParada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegistrarParadaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpParadasLayout = new javax.swing.GroupLayout(jpParadas);
        jpParadas.setLayout(jpParadasLayout);
        jpParadasLayout.setHorizontalGroup(
            jpParadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpParadasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpParadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator8)
                    .addComponent(jScrollPane4)
                    .addComponent(jComboBoxParadasMaquina, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 903, Short.MAX_VALUE)
                    .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jpParadasLayout.createSequentialGroup()
                        .addComponent(jButtonIncluirMotivoParada, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonRemoverMotivoParada, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpParadasLayout.createSequentialGroup()
                        .addComponent(jButtonRegistrarParada, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jpParadasLayout.setVerticalGroup(
            jpParadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpParadasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxParadasMaquina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(jpParadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonIncluirMotivoParada, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonRemoverMotivoParada, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonRegistrarParada, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jpRoot.add(jpParadas, "jpParadas");

        jpConfigDefault.setBackground(new java.awt.Color(204, 255, 255));
        jpConfigDefault.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel3.setText("Configurações Default");

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel4.setText("Configurações usuário default");

        jLabel5.setText("Usuário:");

        jLabel6.setText("Senha:");

        jtfUserDafaut.setText("jtfUserDafaut");

        jpfSenhaDefault.setText("jPasswordField1");

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel7.setText("Configurações de Banco de dados base produção:");

        jLabel8.setText("Nome do driver:");

        jLabel9.setText("Servidor:");

        jLabel10.setText("Url:");

        jLabel11.setText("Banco de dados:");

        jLabel12.setText("Usuario:");

        jLabel13.setText("Senha:");

        jLabel14.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel14.setText("Configurações de Banco de dados base teste:");

        jLabel15.setText("Nome do driver:");

        jLabel16.setText("Servidor:");

        jLabel17.setText("Url:");

        jLabel18.setText("Banco de dados:");

        jLabel19.setText("Usuário:");

        jLabel20.setText("Senha:");

        jtfDriverProducao.setText("jtfDriverProducao");

        jtfServidorProducao.setText("jtfServidorProducao");

        jtfUrlProducao.setText("jtfUrlProducao");

        jtfBDProducao.setText("jtfBDProducao");

        jtfUsuarioProducao.setText("jtfUsuarioProducao");

        jtfDriverTeste.setText("jtfDriverTeste");

        jtfServidorTeste.setText("jtfServidorTeste");

        jtfUrlTeste.setText("jtfUrlTeste");

        jtfBDTeste.setText("jtfBDTeste");

        jtfUsuarioTeste.setText("jtfUsuarioTeste");

        jbCriarArquivosDefault.setText("Guardar");
        jbCriarArquivosDefault.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCriarArquivosDefaultActionPerformed(evt);
            }
        });

        jpfSenhaProducao.setText("000000000000");

        jpfSenhaTeste.setText("jPasswordField1");

        javax.swing.GroupLayout jpConfigDefaultLayout = new javax.swing.GroupLayout(jpConfigDefault);
        jpConfigDefault.setLayout(jpConfigDefaultLayout);
        jpConfigDefaultLayout.setHorizontalGroup(
            jpConfigDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpConfigDefaultLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpConfigDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpConfigDefaultLayout.createSequentialGroup()
                        .addGroup(jpConfigDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator1)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jpConfigDefaultLayout.createSequentialGroup()
                                .addGroup(jpConfigDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jpConfigDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jpfSenhaDefault)
                                    .addComponent(jtfUserDafaut)))
                            .addGroup(jpConfigDefaultLayout.createSequentialGroup()
                                .addGroup(jpConfigDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jpConfigDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtfServidorTeste)
                                    .addComponent(jtfUrlTeste)
                                    .addComponent(jtfBDTeste)
                                    .addComponent(jtfUsuarioTeste)
                                    .addComponent(jtfDriverTeste)))
                            .addGroup(jpConfigDefaultLayout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jpConfigDefaultLayout.createSequentialGroup()
                                .addGroup(jpConfigDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jpConfigDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jpConfigDefaultLayout.createSequentialGroup()
                                        .addComponent(jtfDriverProducao, javax.swing.GroupLayout.PREFERRED_SIZE, 705, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 96, Short.MAX_VALUE))
                                    .addComponent(jtfServidorProducao)
                                    .addComponent(jtfUrlProducao)
                                    .addComponent(jtfBDProducao)
                                    .addComponent(jtfUsuarioProducao)
                                    .addComponent(jpfSenhaProducao))))
                        .addContainerGap())
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator3)
                    .addComponent(jSeparator2)
                    .addGroup(jpConfigDefaultLayout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpfSenhaTeste)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbCriarArquivosDefault, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jpConfigDefaultLayout.setVerticalGroup(
            jpConfigDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpConfigDefaultLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpConfigDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jtfUserDafaut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jpConfigDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jpfSenhaDefault, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpConfigDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfDriverProducao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpConfigDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jtfServidorProducao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpConfigDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jtfUrlProducao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpConfigDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jtfBDProducao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpConfigDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jtfUsuarioProducao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpConfigDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jpfSenhaProducao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpConfigDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jtfDriverTeste, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpConfigDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jtfServidorTeste, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpConfigDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jtfUrlTeste, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpConfigDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jtfBDTeste, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpConfigDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jtfUsuarioTeste, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpConfigDefaultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jpfSenhaTeste, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbCriarArquivosDefault))
                .addGap(197, 197, 197))
        );

        jpRoot.add(jpConfigDefault, "jpConfigDefault");
        jpConfigDefault.getAccessibleContext().setAccessibleName("");

        jpProducao.setBackground(new java.awt.Color(204, 255, 255));
        jpProducao.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setText("Acompanhamento de produção:");

        jLabel24.setText("Informações da ordem de fabricação:");

        jLabel25.setText("Codigo do Item:");

        jLabelProducaoCodItem.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelProducaoCodItem.setText("400500000000");

        jLabel26.setText("Desccrição: ");

        jLabelProducaoDescricaoItem.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelProducaoDescricaoItem.setText("jLabelProducaoDescriçãoItem");

        jLabel27.setText("Quantidade programada:");

        jLabelProducaoQtdProg.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelProducaoQtdProg.setText("00");

        jLabel28.setText("Quantidade produzida:");

        jLabelProducaoQtdProd.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelProducaoQtdProd.setText("00");

        jLabel29.setText("Metragem total programada :");

        jLabelProducaoOF.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelProducaoOF.setText("000000");

        jLabelProducaoMetTotalProg.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelProducaoMetTotalProg.setText("0000000");
        jLabelProducaoMetTotalProg.setToolTipText("");

        jLabel30.setText("Metragem total apontada:");
        jLabel30.setToolTipText("");

        jLabelProducaoMetTotalProd.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelProducaoMetTotalProd.setText("0000000");

        jLabel31.setText("Metragem por carretel:");

        jLabelProducaoMetCarretel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelProducaoMetCarretel.setText("000000");
        jLabelProducaoMetCarretel.setToolTipText("");

        jLabel32.setText("Diametro minimo:");

        jLabelProducaoDMinimo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelProducaoDMinimo.setText("0.00");

        jLabel33.setText("Diametro nominal:");

        jLabelProducaoDnominal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelProducaoDnominal.setText("0.00");

        jLabel34.setText("Diametro máximo:");

        jLabelProducaoDMaximo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelProducaoDMaximo.setText("0.00");

        jLabel35.setText("Velocidade de produção ideal:");

        jLabelProducaoVelIdeal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelProducaoVelIdeal.setText("000");

        jScrollPane2.setBackground(new java.awt.Color(204, 255, 255));
        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("Arrebentamentos carretel de entrada:"));

        jTableProducaoArrebentamentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "Numero", "Metragem"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableProducaoArrebentamentos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        jTableProducaoArrebentamentos.setCellSelectionEnabled(true);
        jScrollPane2.setViewportView(jTableProducaoArrebentamentos);
        if (jTableProducaoArrebentamentos.getColumnModel().getColumnCount() > 0) {
            jTableProducaoArrebentamentos.getColumnModel().getColumn(0).setResizable(false);
            jTableProducaoArrebentamentos.getColumnModel().getColumn(1).setResizable(false);
        }

        jScrollPane3.setBackground(new java.awt.Color(204, 255, 255));
        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder("Paradas durante a produção:"));

        jTableProducaoParadas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Numero", "Cod. Parada", "Tempo", "Descricao"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTableProducaoParadas);
        if (jTableProducaoParadas.getColumnModel().getColumnCount() > 0) {
            jTableProducaoParadas.getColumnModel().getColumn(0).setResizable(false);
            jTableProducaoParadas.getColumnModel().getColumn(0).setPreferredWidth(1);
            jTableProducaoParadas.getColumnModel().getColumn(1).setResizable(false);
            jTableProducaoParadas.getColumnModel().getColumn(1).setPreferredWidth(1);
            jTableProducaoParadas.getColumnModel().getColumn(2).setResizable(false);
            jTableProducaoParadas.getColumnModel().getColumn(2).setPreferredWidth(1);
            jTableProducaoParadas.getColumnModel().getColumn(3).setResizable(false);
        }

        radialLcdVelocidade.setLedColor(eu.hansolo.steelseries.tools.LedColor.GREEN_LED);
        radialLcdVelocidade.setLedVisible(false);
        radialLcdVelocidade.setMaxMeasuredValueVisible(true);
        radialLcdVelocidade.setMaxValue(500.0);
        radialLcdVelocidade.setName(""); // NOI18N
        radialLcdVelocidade.setThreshold(500.0);
        radialLcdVelocidade.setThresholdVisible(true);
        radialLcdVelocidade.setTickLabelPeriod(50);
        radialLcdVelocidade.setTrackRange(100.0);
        radialLcdVelocidade.setTrackSection(50.0);
        radialLcdVelocidade.setTrackSectionColor(new java.awt.Color(0, 255, 0));
        radialLcdVelocidade.setTrackStart(10.0);
        radialLcdVelocidade.setTrackStartColor(new java.awt.Color(255, 0, 0));
        radialLcdVelocidade.setTrackVisible(true);

        linearCarretelSaida.setThreshold(80.0);
        linearCarretelSaida.setThresholdVisible(true);
        linearCarretelSaida.setTitle("Carrele de Saida (%)");
        linearCarretelSaida.setTrackRange(100.0);
        linearCarretelSaida.setTrackSection(50.0);
        linearCarretelSaida.setTrackSectionColor(new java.awt.Color(0, 255, 0));
        linearCarretelSaida.setTrackStartColor(new java.awt.Color(255, 255, 0));
        linearCarretelSaida.setTrackStopColor(new java.awt.Color(255, 255, 0));
        linearCarretelSaida.setUnitString("Metros");
        linearCarretelSaida.setUnitStringVisible(false);

        displaySingleMetragemCarretel.setLcdDecimals(0);
        displaySingleMetragemCarretel.setUnitString("m");
        linearCarretelSaida.add(displaySingleMetragemCarretel);
        displaySingleMetragemCarretel.setBounds(30, 40, 100, 30);

        linearProgramacao.setTitle("Programação para o lote (%)");
        linearProgramacao.setTrackRange(10.0);
        linearProgramacao.setUnitStringVisible(false);

        displaySingleMetragemProgramado.setLcdDecimals(0);
        displaySingleMetragemProgramado.setUnitString("m");
        linearProgramacao.add(displaySingleMetragemProgramado);
        displaySingleMetragemProgramado.setBounds(30, 40, 100, 30);

        jPanelEventoEntrada.setBackground(new java.awt.Color(204, 204, 204));
        jPanelEventoEntrada.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelEventoEntrada.setToolTipText("Proximo evento anotado no carretel de entrada.");
        jPanelEventoEntrada.setName("PainelEventosEntrada"); // NOI18N

        displaySingleEvtCarEntrada.setLcdDecimals(0);
        displaySingleEvtCarEntrada.setUnitString("m");

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel36.setText("Proximo evento Carretel de entrada");

        lightBulbAlertaEvento.setGlowColor(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanelEventoEntradaLayout = new javax.swing.GroupLayout(jPanelEventoEntrada);
        jPanelEventoEntrada.setLayout(jPanelEventoEntradaLayout);
        jPanelEventoEntradaLayout.setHorizontalGroup(
            jPanelEventoEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEventoEntradaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelEventoEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(displaySingleEvtCarEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lightBulbAlertaEvento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanelEventoEntradaLayout.setVerticalGroup(
            jPanelEventoEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEventoEntradaLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(displaySingleEvtCarEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
            .addGroup(jPanelEventoEntradaLayout.createSequentialGroup()
                .addComponent(lightBulbAlertaEvento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabelAlerta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ezgif.com-resize.gif"))); // NOI18N
        jLabelAlerta.setText("jLabel37");

        radialLcdDiametro.setLedColor(eu.hansolo.steelseries.tools.LedColor.GREEN_LED);
        radialLcdDiametro.setLedVisible(false);
        radialLcdDiametro.setMaxMeasuredValueVisible(true);
        radialLcdDiametro.setMaxValue(500.0);
        radialLcdDiametro.setName(""); // NOI18N
        radialLcdDiametro.setThreshold(500.0);
        radialLcdDiametro.setThresholdVisible(true);
        radialLcdDiametro.setTickLabelPeriod(50);
        radialLcdDiametro.setTrackRange(100.0);
        radialLcdDiametro.setTrackSection(50.0);
        radialLcdDiametro.setTrackSectionColor(new java.awt.Color(0, 255, 0));
        radialLcdDiametro.setTrackStart(10.0);
        radialLcdDiametro.setTrackStartColor(new java.awt.Color(255, 0, 0));
        radialLcdDiametro.setTrackVisible(true);

        javax.swing.GroupLayout jpProducaoLayout = new javax.swing.GroupLayout(jpProducao);
        jpProducao.setLayout(jpProducaoLayout);
        jpProducaoLayout.setHorizontalGroup(
            jpProducaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpProducaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpProducaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator5)
                    .addComponent(jSeparator7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator6)
                    .addGroup(jpProducaoLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3))
                    .addGroup(jpProducaoLayout.createSequentialGroup()
                        .addGroup(jpProducaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(radialLcdVelocidade, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                            .addComponent(radialLcdDiametro, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpProducaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpProducaoLayout.createSequentialGroup()
                                .addComponent(jPanelEventoEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(linearCarretelSaida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(linearProgramacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jpProducaoLayout.createSequentialGroup()
                        .addGroup(jpProducaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpProducaoLayout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabelProducaoQtdProg)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel28)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabelProducaoQtdProd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel29)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabelProducaoMetTotalProg)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel30)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabelProducaoMetTotalProd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel31)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabelProducaoMetCarretel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpProducaoLayout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabelProducaoCodItem)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabelProducaoDescricaoItem))
                            .addGroup(jpProducaoLayout.createSequentialGroup()
                                .addComponent(jLabel32)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabelProducaoDMinimo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel33)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabelProducaoDnominal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel34)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabelProducaoDMaximo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel35)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabelProducaoVelIdeal))
                            .addComponent(jLabel23))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jpProducaoLayout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelProducaoOF)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelAlerta, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(118, 118, 118)))
                .addContainerGap())
        );
        jpProducaoLayout.setVerticalGroup(
            jpProducaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpProducaoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpProducaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpProducaoLayout.createSequentialGroup()
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpProducaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelProducaoOF))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jpProducaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(jLabelProducaoQtdProg)
                            .addComponent(jLabel28)
                            .addComponent(jLabelProducaoQtdProd)
                            .addComponent(jLabel29)
                            .addComponent(jLabelProducaoMetTotalProg)
                            .addComponent(jLabel30)
                            .addComponent(jLabelProducaoMetTotalProd)
                            .addComponent(jLabel31)
                            .addComponent(jLabelProducaoMetCarretel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jpProducaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(jLabelProducaoCodItem)
                            .addComponent(jLabel26)
                            .addComponent(jLabelProducaoDescricaoItem))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jpProducaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel32)
                            .addComponent(jLabelProducaoDMinimo)
                            .addComponent(jLabel33)
                            .addComponent(jLabelProducaoDnominal)
                            .addComponent(jLabel34)
                            .addComponent(jLabelProducaoDMaximo)
                            .addComponent(jLabel35)
                            .addComponent(jLabelProducaoVelIdeal))
                        .addGap(11, 11, 11)
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpProducaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpProducaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpProducaoLayout.createSequentialGroup()
                                .addComponent(linearCarretelSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(linearProgramacao, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanelEventoEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpProducaoLayout.createSequentialGroup()
                                .addComponent(radialLcdVelocidade, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(radialLcdDiametro, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabelAlerta, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpRoot.add(jpProducao, "jpProducao");

        getContentPane().add(jpRoot, java.awt.BorderLayout.CENTER);

        menuLogin.setText("Incial");

        jMenuItemLogin.setText("Login");
        jMenuItemLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemLoginActionPerformed(evt);
            }
        });
        menuLogin.add(jMenuItemLogin);

        jMenuItemSair.setText("Sair");
        jMenuItemSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSairActionPerformed(evt);
            }
        });
        menuLogin.add(jMenuItemSair);

        jMenuItemDesligar.setText("Desligar");
        jMenuItemDesligar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDesligarActionPerformed(evt);
            }
        });
        menuLogin.add(jMenuItemDesligar);

        jMenuItemReiniciar.setText("Reiniciar");
        jMenuItemReiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemReiniciarActionPerformed(evt);
            }
        });
        menuLogin.add(jMenuItemReiniciar);

        menuPrincipal.add(menuLogin);

        menuProgramacao.setText("Programação");

        jMenuItemProgramacao.setText("Acompanhamento de programação");
        jMenuItemProgramacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemProgramacaoActionPerformed(evt);
            }
        });
        menuProgramacao.add(jMenuItemProgramacao);

        menuPrincipal.add(menuProgramacao);

        menuParadas.setText("Paradas");

        jMenuItemParadas.setText("Paradas");
        jMenuItemParadas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemParadasActionPerformed(evt);
            }
        });
        menuParadas.add(jMenuItemParadas);

        menuPrincipal.add(menuParadas);

        menuProducao.setText("Produção");

        jMenuItemProducao.setText("Produção");
        jMenuItemProducao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemProducaoActionPerformed(evt);
            }
        });
        menuProducao.add(jMenuItemProducao);

        menuPrincipal.add(menuProducao);

        menuConfiguracoes.setText("Configurações");

        jMenuConfigDefault.setText("Default");
        jMenuConfigDefault.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuConfigDefaultActionPerformed(evt);
            }
        });
        menuConfiguracoes.add(jMenuConfigDefault);

        jMenuTrocarMaquina.setText("Trocar maquina");
        jMenuTrocarMaquina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuTrocarMaquinaActionPerformed(evt);
            }
        });
        menuConfiguracoes.add(jMenuTrocarMaquina);

        jMenuConfigSerialRFID.setText("Serial RFID");
        jMenuConfigSerialRFID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuConfigSerialRFIDActionPerformed(evt);
            }
        });
        menuConfiguracoes.add(jMenuConfigSerialRFID);

        menuPrincipal.add(menuConfiguracoes);

        setJMenuBar(menuPrincipal);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemProgramacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemProgramacaoActionPerformed
        // TODO add your handling code here:    
        abrirTelaProgramacao();
        
    }//GEN-LAST:event_jMenuItemProgramacaoActionPerformed

    private void jMenuItemLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemLoginActionPerformed
        // TODO add your handling code here:
        abrirTelaLogin();
    }//GEN-LAST:event_jMenuItemLoginActionPerformed

    private void jMenuItemParadasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemParadasActionPerformed
        // TODO add your handling code here:
        abrirTelaParadas();        
    }//GEN-LAST:event_jMenuItemParadasActionPerformed

    private void jMenuItemProducaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemProducaoActionPerformed
        // TODO add your handling code here:
        abrirTelaProducao();        
    }//GEN-LAST:event_jMenuItemProducaoActionPerformed

    private void jbLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLoginActionPerformed
        // TODO add your handling code here:
        
        login.setUsuario(jtfUser.getText());
        //System.out.println("usuario: " + login.getUsuario());
        login.setSenha(jpfPassword.getText());
        //System.out.println("Psw: " + login.getSenha());
        if(login.logar(login)){
            usuariologado();
        }else{
            JOptionPane.showMessageDialog(null, "Usuario ou Senha invalidos","Login",JOptionPane.ERROR_MESSAGE);            
        }
    }//GEN-LAST:event_jbLoginActionPerformed

    private void jMenuConfigDefaultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuConfigDefaultActionPerformed
        // TODO add your handling code here:
        limparCamposConfigDefault();
        DadosConexao dp =  new DadosConexao();
        DadosDefaultDAO dao = new DadosDefaultDAO();
        dp = dao.buscaDadosConexaoDefault(true);
        if(dp!=null){
            jtfBDProducao.setText(dp.getMyDatabase());
            jtfDriverProducao.setText(dp.getDriverName());
            jtfServidorProducao.setText(dp.getServerName());
            jtfUrlProducao.setText(dp.getUrl());
        }
        DadosConexao dt =  new DadosConexao();
        dt = dao.buscaDadosConexaoDefault(false);
        if(dt!=null){
            jtfBDTeste.setText(dt.getMyDatabase());
            jtfDriverTeste.setText(dt.getDriverName());
            jtfServidorTeste.setText(dt.getServerName());
            jtfUrlTeste.setText(dt.getUrl());
        }
        CardLayout card = (CardLayout) jpRoot.getLayout();
        card.show(jpRoot,"jpConfigDefault");
    }//GEN-LAST:event_jMenuConfigDefaultActionPerformed

    private void jbCriarArquivosDefaultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCriarArquivosDefaultActionPerformed
        // TODO add your handling code here:
        boolean add = true;
        Usuario us = new Usuario();
        us.setUsuario(jtfUserDafaut.getText());
        us.setSenha(jpfSenhaDefault.getText());
        DadosDefaultDAO dao = new DadosDefaultDAO();
        if (!dao.ArmazenarUserDefault(us)) {
            JOptionPane.showMessageDialog(null, "Falha ao registrar dados usuario default","Usuário Default",JOptionPane.ERROR_MESSAGE);
            add = false;
        }
        DadosConexao d = new DadosConexao();
        d.setDriverName(jtfDriverProducao.getText());
        d.setMyDatabase(jtfBDProducao.getText());
        d.setPassword(jpfSenhaProducao.getText());
        d.setServerName(jtfServidorProducao.getText());
        d.setUrl(jtfUrlProducao.getText());
        d.setUserName(jtfUsuarioProducao.getText());
        if(!dao.armazenaDadosConexao(d,true)) {
            JOptionPane.showMessageDialog(null, "Falha ao registrar dados de conexão","Banco de dados Produção",JOptionPane.ERROR_MESSAGE);
            add = false;
        }
        d.setDriverName(jtfDriverTeste.getText());
        d.setMyDatabase(jtfBDTeste.getText());
        d.setPassword(jpfSenhaTeste.getText());
        d.setServerName(jtfServidorTeste.getText());
        d.setUrl(jtfUrlTeste.getText());
        d.setUserName(jtfUsuarioTeste.getText());
        if(!dao.armazenaDadosConexao(d,false)) {
            JOptionPane.showMessageDialog(null, "Falha ao registrar dados de conexão","Banco de dados Teste",JOptionPane.ERROR_MESSAGE);
            add = false;
        }
        if(add) JOptionPane.showMessageDialog(null,"Dados Registrados com sucesso");
    }//GEN-LAST:event_jbCriarArquivosDefaultActionPerformed

    private void jMenuItemSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSairActionPerformed
        // TODO add your handling code here:       
        abrirTelaLogin();                
    }//GEN-LAST:event_jMenuItemSairActionPerformed

    private void jMenuItemDesligarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemDesligarActionPerformed
        try {
            // TODO add your handling code here:
             if(System.getProperty("os.name").equals("Linux"))Runtime.getRuntime().exec("shutdown -h now");
        } catch (IOException ex) {
            Logger.getLogger(JFPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItemDesligarActionPerformed

    private void jMenuTrocarMaquinaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuTrocarMaquinaActionPerformed
        // TODO add your handling code here:
        JFCadastroMonitor cad = new JFCadastroMonitor();
        cad.setEdit(true);
        cad.setVisible(true);
    }//GEN-LAST:event_jMenuTrocarMaquinaActionPerformed

    private void jMenuConfigSerialRFIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuConfigSerialRFIDActionPerformed
        // TODO add your handling code here:
        JFConfigSerialRFID cfg = new JFConfigSerialRFID();
        cfg.setConfigName(SERIAL_RFID);
        cfg.setVisible(true);
    }//GEN-LAST:event_jMenuConfigSerialRFIDActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        //System.out.println("Fechando");
        if(login.getNivel().equals("0")) {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }else{
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_formWindowClosing

    private void formWindowIconified(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowIconified
        // TODO add your handling code here:           
        if(!login.getNivel().equals("0"))  setExtendedState(MAXIMIZED_BOTH);
    }//GEN-LAST:event_formWindowIconified

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
        // TODO add your handling code here:
        //System.out.println("formWindowStateChanged");
        if(!login.getNivel().equals("0"))
            setExtendedState(MAXIMIZED_BOTH);        
    }//GEN-LAST:event_formWindowStateChanged

    private void formWindowDeactivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowDeactivated
        // TODO add your handling code here:
        //System.out.println("formWindowDeactivated");
        if(!login.getNivel().equals("0"))
            setExtendedState(MAXIMIZED_BOTH);          
    }//GEN-LAST:event_formWindowDeactivated

    private void jMenuItemReiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemReiniciarActionPerformed
        try {
            // TODO add your handling code here:
             if(System.getProperty("os.name").equals("Linux"))Runtime.getRuntime().exec("reboot");
        } catch (IOException ex) {
            Logger.getLogger(JFPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItemReiniciarActionPerformed

    private void jButtonRemoverMotivoParadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoverMotivoParadaActionPerformed
        // TODO add your handling code here:
        DefaultTableModel modelo = (DefaultTableModel)jTableMotivosParada.getModel();
        modelo.removeRow(jTableMotivosParada.getSelectedRow());
    }//GEN-LAST:event_jButtonRemoverMotivoParadaActionPerformed

    private void jButtonIncluirMotivoParadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIncluirMotivoParadaActionPerformed
        // TODO add your handling code here:
        DefaultTableModel modelo = (DefaultTableModel)jTableMotivosParada.getModel();
        int motivoEscolhido = jComboBoxParadasMaquina.getSelectedIndex();
        List<String> infoParadas = paradas.buscaInfoParadaPorCodigo(motivoEscolhido);
        modelo.addRow(new Object[]{infoParadas.get(0),infoParadas.get(1),infoParadas.get(2),
            jTextAreaObsParada.getText().trim()});
    }//GEN-LAST:event_jButtonIncluirMotivoParadaActionPerformed

    private void jButtonRegistrarParadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegistrarParadaActionPerformed
        // TODO add your handling code here:
        if(!maqParada)
            abrirTelaProducao();
        
    }//GEN-LAST:event_jButtonRegistrarParadaActionPerformed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new JFPrincipal().setVisible(true);     
            
            if(codMaquina==null){
                JOptionPane.showMessageDialog(null, "Não foi encontrada uma maquina cadastrada para este sistema",
                        "Registro necessário",JOptionPane.ERROR_MESSAGE);
                new JFCadastroMonitor().setVisible(true);
            }
        });        
        /*
        String dados = "";
        String msg = "admin;admin";                 
        byte[] msgCrito = CriptoCode.encrypt(msg);
        for ( int i=0; i<msgCrito.length;i++){
            //System.out.print(new Integer(msgCrito[i])+" ");                    
            dados = dados + Byte.toString(msgCrito[i])+" ";
        }
        ManipuladorArquivo man = new ManipuladorArquivo();
        man.setArquivo(DadosDefaultDAO.getUSERDEFAULT());
        man.setDados(dados);
        man.escreverArquivo();
        
        
        this.driverName = dadosConexao[0];
                this.myDatabase = dadosConexao[1];
                this.password = dadosConexao[2];
                this.serverName = dadosConexao[3];                
                this.url = dadosConexao[4] + this.serverName + "/" + this.myDatabase ;
                this.userName = dadosConexao[5];
        //Teste de criptografia ....
        String msg = "192.168.1.74;condumigproducao;producao;Prod#153";                 
        //System.out.println(msg);         
        byte[] msgCrito = CriptoCode.encrypt(msg);    
        String dados = "";
        for ( int i=0; i<msgCrito.length;i++){
            //System.out.print(new Integer(msgCrito[i])+" ");                    
            dados = dados + Byte.toString(msgCrito[i])+" ";
        }
        
        man.setDados(dados);
        
        man.escreverArquivo();
        man.setDados("");
        */      
      
        
          
    }

    public static void setCodMaquina(String codMaquina) {
        JFPrincipal.codMaquina = codMaquina;
    }

    public static String getIdentificador() {
        return identificador;
    }
    
    private void bloquearMenu(){
        menuParadas.setEnabled(false);
        menuProducao.setEnabled(false);
        menuProgramacao.setEnabled(false );
        menuConfiguracoes.setEnabled(false);
        jMenuItemSair.setEnabled(false);
        jMenuItemLogin.setEnabled(true);
    }
    private void habilitarMenu() {
        
        menuParadas.setEnabled(true);
        menuProducao.setEnabled(true);
        menuProgramacao.setEnabled(true);
        jMenuItemSair.setEnabled(true);
        jMenuItemLogin.setEnabled(false);
        if(login.getNivel().equals("0")){
            menuConfiguracoes.setEnabled(true);
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private eu.hansolo.steelseries.gauges.DisplaySingle displaySingleEvtCarEntrada;
    private eu.hansolo.steelseries.gauges.DisplaySingle displaySingleMetragemCarretel;
    private eu.hansolo.steelseries.gauges.DisplaySingle displaySingleMetragemProgramado;
    private javax.swing.JButton jButtonIncluirMotivoParada;
    private javax.swing.JButton jButtonRegistrarParada;
    private javax.swing.JButton jButtonRemoverMotivoParada;
    private javax.swing.JComboBox<String> jComboBoxParadasMaquina;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelAlerta;
    private javax.swing.JLabel jLabelProducaoCodItem;
    private javax.swing.JLabel jLabelProducaoDMaximo;
    private javax.swing.JLabel jLabelProducaoDMinimo;
    private javax.swing.JLabel jLabelProducaoDescricaoItem;
    private javax.swing.JLabel jLabelProducaoDnominal;
    private javax.swing.JLabel jLabelProducaoMetCarretel;
    private javax.swing.JLabel jLabelProducaoMetTotalProd;
    private javax.swing.JLabel jLabelProducaoMetTotalProg;
    private javax.swing.JLabel jLabelProducaoOF;
    private javax.swing.JLabel jLabelProducaoQtdProd;
    private javax.swing.JLabel jLabelProducaoQtdProg;
    private javax.swing.JLabel jLabelProducaoVelIdeal;
    private javax.swing.JMenuItem jMenuConfigDefault;
    private javax.swing.JMenuItem jMenuConfigSerialRFID;
    private javax.swing.JMenuItem jMenuItemDesligar;
    private javax.swing.JMenuItem jMenuItemLogin;
    private javax.swing.JMenuItem jMenuItemParadas;
    private javax.swing.JMenuItem jMenuItemProducao;
    private javax.swing.JMenuItem jMenuItemProgramacao;
    private javax.swing.JMenuItem jMenuItemReiniciar;
    private javax.swing.JMenuItem jMenuItemSair;
    private javax.swing.JMenuItem jMenuTrocarMaquina;
    private javax.swing.JPanel jPLogo;
    private javax.swing.JPanel jPanelEventoEntrada;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JTable jTableMotivosParada;
    private javax.swing.JTable jTableProducaoArrebentamentos;
    private javax.swing.JTable jTableProducaoParadas;
    private javax.swing.JTable jTableProgramacao;
    private javax.swing.JTextArea jTextAreaObsParada;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton jbCriarArquivosDefault;
    private javax.swing.JButton jbLogin;
    private javax.swing.JPanel jpConfigDefault;
    private javax.swing.JPanel jpLogin;
    private javax.swing.JPanel jpParadas;
    private javax.swing.JPanel jpProducao;
    private javax.swing.JPanel jpProgramacao;
    private javax.swing.JPanel jpRoot;
    private javax.swing.JPasswordField jpfPassword;
    private javax.swing.JPasswordField jpfSenhaDefault;
    private javax.swing.JPasswordField jpfSenhaProducao;
    private javax.swing.JPasswordField jpfSenhaTeste;
    private javax.swing.JTextField jtfBDProducao;
    private javax.swing.JTextField jtfBDTeste;
    private javax.swing.JTextField jtfDriverProducao;
    private javax.swing.JTextField jtfDriverTeste;
    private javax.swing.JTextField jtfServidorProducao;
    private javax.swing.JTextField jtfServidorTeste;
    private javax.swing.JTextField jtfUrlProducao;
    private javax.swing.JTextField jtfUrlTeste;
    private javax.swing.JTextField jtfUser;
    private javax.swing.JTextField jtfUserDafaut;
    private javax.swing.JTextField jtfUsuarioProducao;
    private javax.swing.JTextField jtfUsuarioTeste;
    private eu.hansolo.lightbulb.LightBulb lightBulbAlertaEvento;
    private eu.hansolo.steelseries.gauges.Linear linearCarretelSaida;
    private eu.hansolo.steelseries.gauges.Linear linearProgramacao;
    private javax.swing.JMenu menuConfiguracoes;
    private javax.swing.JMenu menuLogin;
    private javax.swing.JMenu menuParadas;
    private javax.swing.JMenuBar menuPrincipal;
    private javax.swing.JMenu menuProducao;
    private javax.swing.JMenu menuProgramacao;
    private eu.hansolo.steelseries.gauges.Radial4Lcd radialLcdDiametro;
    private eu.hansolo.steelseries.gauges.Radial4Lcd radialLcdVelocidade;
    // End of variables declaration//GEN-END:variables

    private void limparCamposConfigDefault() {
        jtfUserDafaut.setText("");
        jpfSenhaDefault.setText("");
        jtfUsuarioProducao.setText("");
        jpfSenhaProducao.setText("");
        jtfUsuarioTeste.setText("");
        jpfSenhaTeste.setText("");
    }
    
    private void limparTelaLogin(){
        jtfUser.setText("");
        jpfPassword.setText("");
    }
   
    private void limparJTable(JTable tabela ){
        DefaultTableModel tblRemove = (DefaultTableModel)tabela.getModel();
        while(tblRemove.getRowCount()>0){                        
            tblRemove.removeRow(0);                 
        }
    }
        
    private void buscarProgramacaoMaquina() {   
        DefaultTableModel modelo = (DefaultTableModel)jTableProgramacao.getModel();
        ProgramacaoMaquinaDAO programacao =  new ProgramacaoMaquinaDAO();
        List<ProgramacaoMaquina> lista =  new ArrayList<>();
        lista = programacao.buscaProgramacaoMaquina(codMaquina);
        for(int i=0;i<lista.size();i++){
            modelo.addRow(new Object[]{lista.get(i).getProduto().getCodigo(),
                lista.get(i).getProduto().getDescricao(),lista.get(i).getLoteproducao(),
                lista.get(i).getQuantidadeProgramada(),lista.get(i).getMetragemProgramada(),lista.get(i).getDataProgramada()});
        }
    }
    
    private void buscarInformaçoesProducao(){
        
        ProducaoDAO daoProd = new ProducaoDAO();
        prod = daoProd.buscaItemProducao(codMaquina);
        if(prod != null){
            
            ProgramacaoMaquinaDAO daoProg = new ProgramacaoMaquinaDAO();
            ProdutoCarretelDAO daoProdCar =  new ProdutoCarretelDAO();
            prog = daoProg.buscaProgramacaoLoteItem(prod.getLoteProducao(),prod.getItemProducao());
            if(prog != null){
                jLabelProducaoCodItem.setText(prod.getItemProducao());
                jLabelProducaoOF.setText(prod.getLoteProducao());
                jLabelProducaoDescricaoItem.setText(prog.getProduto().getDescricao());
                jLabelProducaoQtdProg.setText(Integer.toString(prog.getQuantidadeProgramada()));
                jLabelProducaoQtdProd.setText(Integer.toString(prog.getQuantidadeProduzida()));
                jLabelProducaoMetTotalProg.setText(String.valueOf(prog.getMetragemTotalProgramada()));
                jLabelProducaoMetCarretel.setText(String.valueOf(prog.getMetragemProgramada()));
                jLabelProducaoDMinimo.setText(String.valueOf(prog.getProduto().getDiametroMinimo()));
                jLabelProducaoDnominal.setText(String.valueOf(prog.getProduto().getDiametroNominal()));
                jLabelProducaoDMaximo.setText(String.valueOf(prog.getProduto().getDiametroMaximo()));
                jLabelProducaoMetTotalProd.setText(String.valueOf(daoProd.BuscaMetragemProduzida(prod.getLoteProducao(),prod.getItemProducao())));
                ProdutoMaquinaDAO daoProdMaq = new ProdutoMaquinaDAO();
                prodmaq = daoProdMaq.buscaVelocidadeProdutoMaquina(prod.getItemProducao(), codMaquina);
                prodCar = daoProdCar.buscaDadosProdutoCarretel(prod.getItemProducao(),"0131250",codMaquina);
                jLabelProducaoVelIdeal.setText(String.valueOf(prodmaq.getVelocidade())+ " " + prodmaq.getUnidade());
                buscarRegistrosObservacaoPesagem();
            }else{
                JOptionPane.showMessageDialog(rootPane,"Falha ao busrcar dados da programação do item em produção, "
                        + "Por favor informe ao setor de Produção","Falha ao buscar dados",JOptionPane.ERROR_MESSAGE);
                abrirTelaProgramacao();
            }
        }else{
            JOptionPane.showMessageDialog(rootPane,"Maquina sem producao, por favor realise a montagem",
                    "Maquina sem producao",JOptionPane.OK_OPTION);
            abrirTelaProgramacao();
        }
    }
    private void abrirTelaParadas() {
        try {
            if(paradas==null) paradas = new ControllerParadasMaquina(codMaquina);            
            long metragem = (long) displaySingleMetragemCarretel.getValue();
            evtRegistrado=paradas.registraInicioParadamaquina(metragem, codMaquina);
            List<String> listaParadas = new ArrayList<>();
            listaParadas = paradas.buscaListaParadasDescricao();
            jComboBoxParadasMaquina.removeAllItems();
            for(int i=0;i<listaParadas.size();i++){
                jComboBoxParadasMaquina.addItem(listaParadas.get(i));
            } 
            maqParada = true;
            bloquearMenu();
            CardLayout card = (CardLayout) jpRoot.getLayout();
            card.show(jpRoot,"jpParadas");
            
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }
    private void abrirTelaProgramacao() {
        limparJTable(jTableProgramacao);
        buscarProgramacaoMaquina();
        CardLayout card = (CardLayout) jpRoot.getLayout();
        card.show(jpRoot,"jpProgramacao");
    }

    private void abrirTelaProducao() {
        limparJTable(jTableProducaoArrebentamentos);
        limparJTable(jTableProducaoParadas);
        jLabelAlerta.setVisible(false);
        if(comMicrometro==null) comMicrometro = new SerialTxRx();
        if(parametrizarSerial(SERIAL_MICROMETRO)){
            if(comMicrometro.iniciaSerial()){
                System.out.println("serial Micrometro iniciada");
                comMicrometro.addActionListener(this);
            }
        }
        buscarInformaçoesProducao();
        ajustarMostradorVelocidade();
        ajustarMostradoresMetragem();
        this.tarefaVelocidade();
        CardLayout card = (CardLayout) jpRoot.getLayout();
        card.show(jpRoot,"jpProducao");
    }

    private void abrirTelaLogin() {
        if(comRFID==null) comRFID = new SerialTxRx();
        login.setUsuario("");
        login.setNome("");
        login.setNivel("");
        login.setSenha("");
        login.setCode("");
        if(false) limparTelaLogin(); //apenas para teste, passar para true em producao
        bloquearMenu();
        if(parametrizarSerial(SERIAL_RFID)){
            if(comRFID.iniciaSerial()){
                System.out.println("serial RFID iniciada");
                comRFID.addActionListener(this);
            }            
        }
        CardLayout card = (CardLayout) jpRoot.getLayout();
        card.show(jpRoot,"jpLogin");
    }
    private boolean parametrizarSerial(String configName ){
        ControllerConfigSerialPort cfg = new ControllerConfigSerialPort();
        if(configName.equals(SERIAL_RFID)){
            if(comRFID!=null){               
               comRFID = cfg.configurarPortaSerial(configName,identificador);
               if(comRFID!=null){                   
                   System.out.println("Serial comfigurada com sucesso!! " + comRFID.getSerialPortName());
                   return true;
               }else{                   
                   System.out.println("Falha ao configurar porta serial");
                   return false;
               }                    
           }
        }
        if(configName.equals(SERIAL_MICROMETRO)){
            if(comMicrometro!=null){               
               comMicrometro = cfg.configurarPortaSerial(configName,identificador);
               if(comMicrometro!=null){                   
                   System.out.println("Serial do micrometro comfigurada com sucesso!! " + comMicrometro.getSerialPortName());
                   return true;
               }else{                   
                   System.out.println("Falha ao configurar porta serial para comunicação com micrometro");
                   return false;
               }                    
           }
        }
        return false;
    }
    private void buscarRegistrosObservacaoPesagem() {
        List<Pesagem> lista = new ArrayList<>();
        PesagemDAO daoPes = new PesagemDAO();
        ControllerProducao ctr = new ControllerProducao();
        lista = daoPes.buscapesagensMontagem(codMaquina);
        if(lista != null){
            DefaultTableModel modelo = (DefaultTableModel)jTableProducaoArrebentamentos.getModel();
            for(int i=0;i<lista.size();i++){
                ctr.AddicionarMetragensObservacao(lista.get(i).getObservacao(),lista.get(i).getMetragemOperador());
            }
            
            metrosAlerta = ctr.getListaMetragemObservacao();
            for (int i=0;i<metrosAlerta.size();i++){
                modelo.addRow(new Object[]{String.valueOf(i+1),metrosAlerta.get(i).toString()});
            }
        }        
    }

    @Override
    public void actionPerformed(ActionEvent e) {            
        if(e.getActionCommand().trim().equals("")) return;
        System.out.println(e.getActionCommand());
        if(e.getActionCommand().substring(0,3).equalsIgnoreCase("TAG")){
            tagEvent(e.getActionCommand());
        }else{
            if(e.getActionCommand().equals("**********  Troca de bobina  - Novo relatorio  **********")){
                Object[] options = { "Sim", "Não" }; 
                int i = JOptionPane.showOptionDialog(null, "Deseja realizar uma troca de carretel de saída?", 
                        "Troca de carretel", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
                        null, options, options[0]); 
                if (i == JOptionPane.YES_OPTION) {
                    System.out.println("Apontar pesagem e trocar carretel de saida"); 
                }else{
                    leituraAnterior.setMetragem(0);
                    System.out.println("Metragem leitura anterior: " + leituraAnterior.getMetragem());
                    return;
                }
            }
            dadosSerialMicrometro(e.getActionCommand());
        }                        
    }
    
    private void dadosSerialMicrometro(String dados){
        double tempoCalculadoSistema;
        double metrosProduzidos;
        double velocidade;
        try {                    
            
            if(!iniciaLeituras){
                double metAnterior = (double) leituraAnterior.getMetragem();
                leituraAtual = mic.setarDadosMicrometro(dados);               
                tempoCalculadoSistema = (double)(System.currentTimeMillis() - tempoSistema);                
                double metAtual = (double) leituraAtual.getMetragem();
                
                if(metAnterior < metAtual) {
                    metrosProduzidos = metAtual - metAnterior;
                    atualizarMostradoresMetragem(metrosProduzidos);
                    //System.out.println("Tempo calculado pelo sistema: " + (tempoCalculadoSistema / 1000.0));
                    velocidade = (metrosProduzidos/(tempoCalculadoSistema/1000.0));
                    tempoSistema = System.currentTimeMillis();
                    //System.out.println("Metros Produzidos: " + metrosProduzidos);
                    //System.out.println("tempo sistema: " + tempoSistema);    
                    //System.out.println("Velocidade: " + velocidade + "m/s");
                    //System.out.println("Velocidade pelo tempo micrimetro: " + velocidadePeloTempoMicrometro + "m/s");
                    //System.out.println("Velocidade: " + (velocidade * 60) + "m/min");
                    //System.out.println("Velocidade pelo tempo micrometro: " + (velocidadePeloTempoMicrometro * 60) + "m/min");  
                    //System.out.println("Media velocidade " + mediaVelocidade(velocidade)*60);                 
                    ProducaoDAO daoProd = new ProducaoDAO();                    
                    if(daoProd.atualizaMetragemProduzida(codMaquina, String.valueOf(metrosProduzidos))) 
                        leituraAnterior = mic.setarDadosMicrometro(dados);  
                    double velMediana = mediaVelocidade(velocidade*60);                    
                    //System.out.println("Mediana: " + velMediana);
                    resumoRelatorio = 0;
                    radialLcdVelocidade.setValueAnimated(velMediana);                             
                    if(maqParada)if(velMediana>=(radialLcdVelocidade.getTrackStart() + 10))registrarRetornoEvento();
                    eventosTimer = 0;
                    if(velMediana < radialLcdVelocidade.getTrackStart() - 10){
                        System.out.println("Parada por velocidade abaixo da minima");
                        if(!maqParada){
                            abrirTelaParadas();
                        }
                    }
                }else{
                    if(resumoRelatorio >= linhas){
                        if(radialLcdVelocidade.getValue()>0)radialLcdVelocidade.setValueAnimated(mediaVelocidade(0));
                        if (radialLcdVelocidade.getValue()==0){
                            System.out.println("Parada!!!");
                            if(!maqParada)abrirTelaParadas();
                        } 
                    }else{
                        resumoRelatorio ++;
                        System.out.println("Resumo relatoio: " + resumoRelatorio);
                    }
                }
            }else{            
                iniciarLeiturasSerial(dados);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void iniciarLeiturasSerial(String dados){
        try {
            leituraAnterior = mic.setarDadosMicrometro(dados);
            leituraAtual = mic.setarDadosMicrometro(dados);
            tempoSistema = System.currentTimeMillis();
            iniciaLeituras = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private double mediaVelocidade(double ultimaVel){
        double media=0;
        int interacaoes=0;
        try {                    
            for (int i=8;i>=0;i--){
                mediaVel[i+1]=mediaVel[i];    
            } 
            mediaVel[0]=ultimaVel;
            for (int i=0;i<=9;i++){
                //if(mediaVel[i]>=1){
                    media = media + mediaVel[i];
                    interacaoes = interacaoes + 1;
                //}
            }
            media = media / interacaoes;            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return media;
    }
    private void tagEvent(String tag){        
        String code=tag.substring(3,tag.length());
        if(!login.getCode().equals(code)){
            //System.out.println("Evento: " + code);        
            if(!login.getCode().trim().equals("")){
                code = login.getCode();
            }
            login.setCode(code);              
            if(login.logarCode(login)){
                usuariologado();            
            }else{
                login.setCode(code);
                JOptionPane.showMessageDialog(null, "Codigo de Identificação não vinculado","Codigo invalido",JOptionPane.ERROR_MESSAGE);
            }        
        }
    }
    
    private void usuariologado() {
        if(!login.getNome().trim().equals("")) bloquearMenu();
            habilitarMenu();
            System.out.println("Logado com " + login.getNome()
                + " e nivel de permissão: " + login.getNivel());
            if(maqParada){
                abrirTelaParadas();
            }else{
                abrirTelaProducao();           
            }
        }

    private void ajustarMostradorVelocidade() {
        try {
            double maxVel = (double) prodmaq.getVelocidade() * 2;
            double alvoVel = (double) prodmaq.getVelocidade();
            double startTrack = (double) (alvoVel * maquina.getAlertaPercentualVelocidade());
            double rangeVel = (double) (maxVel - (2 * startTrack));
            System.out.println("Unidade: " + prodmaq.getUnidade());
            radialLcdVelocidade.setUnitString(prodmaq.getUnidade());
            radialLcdVelocidade.setTitle("Velocidade");
            radialLcdVelocidade.setMaxMeasuredValueVisible(true);
            radialLcdVelocidade.setMaxValue(maxVel);
            radialLcdVelocidade.setTrackStart(startTrack);
            radialLcdVelocidade.setTrackRange(rangeVel); 
            radialLcdVelocidade.setTrackSection(alvoVel);
            radialLcdVelocidade.setThreshold(alvoVel);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ajustarMostradoresMetragem() {
        try {
            double metProgramada = (double) prog.getMetragemProgramada();
            double metMaxima = (double) prodCar.getMetragemMaxima();
            double metProduzida = (double) prod.getMetragemProduzida();
            double metTotalProduzida = Double.valueOf(jLabelProducaoMetTotalProd.getText());
            metTotalProduzida = metTotalProduzida + metProduzida;
            double metTotalProgramada = Double.valueOf(jLabelProducaoMetTotalProg.getText());
            double metAlvo = ((metProgramada / metMaxima) * 100);
            //System.out.println("Met Alvo: " + metAlvo);            
            linearCarretelSaida.setThreshold(metAlvo);
            linearCarretelSaida.setThresholdVisible(true);
            linearCarretelSaida.setValueAnimated((metProduzida/metMaxima)*100);
            displaySingleMetragemCarretel.setValue(metProduzida);
            displaySingleMetragemProgramado.setValue(metTotalProduzida);
            double percProduzido = (metTotalProduzida/metTotalProgramada) * 100;
            linearProgramacao.setValueAnimated(percProduzido);
            atualiMostradorParadaEntrada(metProduzida);
            
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
    
    private void atualizarMostradoresMetragem( double metrosProduzidos){
        try {       
            if(metrosProduzidos>0){
                displaySingleMetragemCarretel.setValue(displaySingleMetragemCarretel.getValue() + metrosProduzidos);
                atualiMostradorParadaEntrada(displaySingleMetragemCarretel.getValue());
                displaySingleMetragemProgramado.setValue(displaySingleMetragemProgramado.getValue() + metrosProduzidos);
                linearProgramacao.setValue((displaySingleMetragemProgramado.getValue() / prog.getMetragemTotalProgramada()) * 100);            
                linearCarretelSaida.setValue((displaySingleMetragemCarretel.getValue() / prodCar.getMetragemMaxima())*100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void atualiMostradorParadaEntrada(double metrosProduzidos) {
        double metrosParaAlerta=0;
        try {
            for (int i=0;i<metrosAlerta.size();i++){
                //modelo.addRow(new Object[]{String.valueOf(i+1),metrosAlerta.get(i).toString()});                
                metrosParaAlerta = (double) metrosAlerta.get(i);
                if(metrosProduzidos < metrosParaAlerta){
                    metrosParaAlerta = metrosParaAlerta - metrosProduzidos;
                    displaySingleEvtCarEntrada.setValue(metrosParaAlerta);
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registrarRetornoEvento() {
        if(paradas.registraRetornoParadamaquina((long) displaySingleMetragemCarretel.getValue(), codMaquina)){
            maqParada = false;
            eventosTimer = 0;
            habilitarMenu();
        }        
    }
}                                 
