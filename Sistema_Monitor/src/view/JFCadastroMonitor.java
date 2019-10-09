/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DadosDefaultDAO;
import dao.MaquinaDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author renato.soares
 */
public class JFCadastroMonitor extends javax.swing.JFrame {
    DadosDefaultDAO df = new DadosDefaultDAO();
            
    /**
     * Creates new form JFCadastroMonitor
     */
    public JFCadastroMonitor() {
        try {
            initComponents();
            MaquinaDAO maq = new MaquinaDAO();
            List<String> lista = new ArrayList();
            lista = maq.listarMaquinas();
            for (int i=0;i<lista.size();i++){
                jcbListaMaquinas.addItem(lista.get(i));
            }
            jlSerial.setText(JFPrincipal.getIdentificador());
        } catch (SQLException ex) {
            Logger.getLogger(JFCadastroMonitor.class.getName()).log(Level.SEVERE, null, ex);
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

        jLabel1 = new javax.swing.JLabel();
        jlSerial = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jcbListaMaquinas = new javax.swing.JComboBox<>();
        jbRegistrarMonitor = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Cadastro de Maquina");
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(204, 255, 255));
        setLocationByPlatform(true);
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setText("Serial:");

        jlSerial.setText("jlSerial");

        jLabel2.setText("Maquina:");

        jbRegistrarMonitor.setText("Registrar");
        jbRegistrarMonitor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRegistrarMonitorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jbRegistrarMonitor, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jlSerial, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcbListaMaquinas, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jlSerial))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jcbListaMaquinas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbRegistrarMonitor)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        String codMaquina = df.buscaCodigoMaquina(JFPrincipal.getIdentificador());
        if(codMaquina==null){
            JOptionPane.showMessageDialog(rootPane, "Por favor cadastre a maquina "
                    + "antes de continuar!!","Aguardando cadastro",JOptionPane.ERROR_MESSAGE);
        }else{
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
    }//GEN-LAST:event_formWindowClosing

    private void jbRegistrarMonitorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRegistrarMonitorActionPerformed
        // TODO add your handling code here:
        if(df.cadastrarMaquinaMonitor(jlSerial.getText(),jcbListaMaquinas.getSelectedItem().toString())){
            JOptionPane.showMessageDialog(rootPane,"Cadastro realizado com sucesso!!","Cadastro",JOptionPane.INFORMATION_MESSAGE);            
        }
        
    }//GEN-LAST:event_jbRegistrarMonitorActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton jbRegistrarMonitor;
    private javax.swing.JComboBox<String> jcbListaMaquinas;
    private javax.swing.JLabel jlSerial;
    // End of variables declaration//GEN-END:variables
}