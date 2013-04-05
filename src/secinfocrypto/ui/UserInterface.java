/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package secinfocrypto.ui;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.table.AbstractTableModel;
import secinfocrypto.signature.Keys;
import secinfocrypto.signature.SignatureAlgorithm;
import secinfocrypto.signature.SignatureChecker;
import secinfocrypto.signature.SignatureFile;
import secinfocrypto.signature.SignatureGenerator;

/**
 *
 * @author bruno
 */
public class UserInterface extends javax.swing.JPanel {

    private JFrame parent;
    private Keys keys;
    byte[] signature;
    private ArrayList<SignatureFile> files;
    private MyModel model;

    class MyModel extends AbstractTableModel {

        private HashMap<SignatureFile, Boolean> isFileSelected;
        private String[] columnName = {"Selected", "Name", "Last test", "Last check date", "Algorithm"};
        
        MyModel() {
            isFileSelected = new HashMap<SignatureFile, Boolean>();
        }
        

        @Override
        public int getRowCount() {
            return files.size();
        }

        @Override
        public void setValueAt(Object value, int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 0:
                    isFileSelected.put(files.get(rowIndex), (Boolean) value);
                    break;
                case 4:
                    files.get(rowIndex).setAlgorithm((String) value);
                    break;
                default:
                    super.setValueAt(value, rowIndex, columnIndex);
                    
            }
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            SignatureFile file = files.get(rowIndex);

            switch (columnIndex) {
                case 0:
                    return (isFileSelected.containsKey(file) ? isFileSelected.get(file) : false);
                case 1:
                    return file.getFile().getName();
                case 2:
                    return file.getLastTestResult();
                case 3:
                    return file.getLastCheckedDate();
                case 4:
                    return file.getAlgorithm();
                default:
                    return null;
            }
        }

        @Override
        public Class getColumnClass(int columnIndex) {
            if (columnIndex == 0) {
                return Boolean.class;
            }
            return String.class;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            if ((columnIndex == 0) || (columnIndex == 4)) {
                return true;
            }
            return false;
        }
        
 
        public boolean isSelected(int rowIndex) {
            return (Boolean) this.getValueAt(rowIndex, 0);
        }
        
        public ArrayList<SignatureFile> getSelectedFiles() {
            ArrayList<SignatureFile> list = new ArrayList<SignatureFile>();
            
            for (int i=0; i<files.size(); i++)
                if (this.isSelected(i))
                    list.add(files.get(i));
            
            return list;
        }
        
        public void setSelected(boolean selected, int rowIndex) {
            this.setValueAt(selected, rowIndex, 0);
        }

        @Override
        public int getColumnCount() {
            return 5;
        }
        
        @Override
        public String getColumnName(int columnIndex) {
            return columnName[columnIndex];
        }
        
        public void remove(SignatureFile file) {
            this.isFileSelected.remove(file);
            files.remove(file);
        }
    };

    /**
     * Creates new form UserInterface
     */
    public UserInterface(JFrame parent) {
        this.parent = parent;

        this.files = new ArrayList<SignatureFile>();
        for (int i = 0; i < 4; i++) {
            this.files.add(new SignatureFile(new File("/Users/bruno/Desktop/Programmation_Android.pdf")));
        }

        this.model = new MyModel();

        initComponents();

        this.jTable.getTableHeader().setReorderingAllowed(false);
        this.jTable.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(new JComboBox(SignatureAlgorithm.ALGORITHMS)));

        this.keys = new Keys();
        try {
            keys.generation();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
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

        jTabbedPane = new javax.swing.JTabbedPane();
        jPanelTabFiles = new javax.swing.JPanel();
        jPanelFilesActions = new javax.swing.JPanel();
        jCheckBoxSelectAll = new javax.swing.JCheckBox();
        jButtonCheck = new javax.swing.JButton();
        jButtonSign = new javax.swing.JButton();
        jButtonFiles = new javax.swing.JButton();
        jButtonRemove = new javax.swing.JButton();
        jButtonLogout = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        jPanelTabQueue = new javax.swing.JPanel();
        jPanelQueueActions = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanelQueue = new javax.swing.JPanel();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        jPanelTabFiles.setLayout(new javax.swing.BoxLayout(jPanelTabFiles, javax.swing.BoxLayout.PAGE_AXIS));

        jCheckBoxSelectAll.setText("Select all");
        jCheckBoxSelectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxSelectAllActionPerformed(evt);
            }
        });
        jPanelFilesActions.add(jCheckBoxSelectAll);

        jButtonCheck.setText("Check");
        jButtonCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCheckActionPerformed(evt);
            }
        });
        jPanelFilesActions.add(jButtonCheck);

        jButtonSign.setText("Sign");
        jButtonSign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSignActionPerformed(evt);
            }
        });
        jPanelFilesActions.add(jButtonSign);

        jButtonFiles.setText("Add File(s)");
        jButtonFiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFilesActionPerformed(evt);
            }
        });
        jPanelFilesActions.add(jButtonFiles);

        jButtonRemove.setText("Remove selected file(s)");
        jButtonRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoveActionPerformed(evt);
            }
        });
        jPanelFilesActions.add(jButtonRemove);

        jButtonLogout.setText("Logout");
        jButtonLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogoutActionPerformed(evt);
            }
        });
        jPanelFilesActions.add(jButtonLogout);

        jPanelTabFiles.add(jPanelFilesActions);

        jTable.setModel(this.model);
        jScrollPane2.setViewportView(jTable);

        jScrollPane1.setViewportView(jScrollPane2);

        jPanelTabFiles.add(jScrollPane1);

        jTabbedPane.addTab("FIles", jPanelTabFiles);

        jPanelTabQueue.setLayout(new javax.swing.BoxLayout(jPanelTabQueue, javax.swing.BoxLayout.PAGE_AXIS));

        jButton1.setText("Clean Queue");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanelQueueActions.add(jButton1);

        jPanelTabQueue.add(jPanelQueueActions);

        jPanelQueue.setLayout(new javax.swing.BoxLayout(jPanelQueue, javax.swing.BoxLayout.PAGE_AXIS));
        jScrollPane3.setViewportView(jPanelQueue);

        jPanelTabQueue.add(jScrollPane3);

        jTabbedPane.addTab("Queue", jPanelTabQueue);

        add(jTabbedPane);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonFilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFilesActionPerformed
        Logger.getLogger(UserInterface.class.getName()).log(Level.FINE, "FileChooser");
        JFileChooser f = new JFileChooser();
        f.setMultiSelectionEnabled(true);
        
        if (f.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

            File[] fs = f.getSelectedFiles();

            for (File fi : fs)
                this.files.add(new SignatureFile(fi)); 
            this.model.fireTableDataChanged();
        }
    }//GEN-LAST:event_jButtonFilesActionPerformed

    private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutActionPerformed
        Logger.getLogger(UserInterface.class.getName()).log(Level.FINE, "Logout");
        this.parent.setContentPane(new Login(this.parent));
        this.parent.pack();
    }//GEN-LAST:event_jButtonLogoutActionPerformed

    private void jButtonSignActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSignActionPerformed

        this.jTabbedPane.setSelectedComponent(this.jPanelTabQueue);
        
        for (int i=0; i<this.model.getRowCount(); i++) {
            if (this.model.isSelected(i)) {
                SignatureFileView sfv = new SignatureFileView(this.files.get(i));
                this.jPanelQueue.add(sfv);
                SignatureGenerator generator = new SignatureGenerator();
                generator.setSignatureListener(sfv.new GeneratorListener(sfv));
                generator.execute(this.files.get(i), keys.getPrivateKey());
            }
        }
    }//GEN-LAST:event_jButtonSignActionPerformed

    private void jButtonCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCheckActionPerformed
 
        this.jTabbedPane.setSelectedComponent(this.jPanelTabQueue);
        
        for (int i=0; i<this.model.getRowCount(); i++) {
            if (this.model.isSelected(i)) {
                SignatureFileView sfv = new SignatureFileView(this.files.get(i));
                this.jPanelQueue.add(sfv);
                SignatureChecker checker = new SignatureChecker();
                checker.setSignatureListener(sfv.new CheckerListener(sfv));
                checker.execute(this.files.get(i), keys.getPublicKey());
            }
        } 
    }//GEN-LAST:event_jButtonCheckActionPerformed

    private void jCheckBoxSelectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxSelectAllActionPerformed
        for (int i=0; i<this.model.getRowCount(); i++)
            this.model.setSelected(this.jCheckBoxSelectAll.isSelected(),i);
        this.model.fireTableDataChanged();
    }//GEN-LAST:event_jCheckBoxSelectAllActionPerformed

    private void jButtonRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoveActionPerformed
        Iterator<SignatureFile> it = this.model.getSelectedFiles().iterator();
        
        while (it.hasNext())
                this.model.remove(it.next());
        this.model.fireTableDataChanged();
        
    }//GEN-LAST:event_jButtonRemoveActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.jPanelQueue.removeAll();
        this.jPanelQueue.updateUI();
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonCheck;
    private javax.swing.JButton jButtonFiles;
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JButton jButtonRemove;
    private javax.swing.JButton jButtonSign;
    private javax.swing.JCheckBox jCheckBoxSelectAll;
    private javax.swing.JPanel jPanelFilesActions;
    private javax.swing.JPanel jPanelQueue;
    private javax.swing.JPanel jPanelQueueActions;
    private javax.swing.JPanel jPanelTabFiles;
    private javax.swing.JPanel jPanelTabQueue;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTable jTable;
    // End of variables declaration//GEN-END:variables
}
