/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasacs;
// updated version
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 *
 * @author 1412625
 */
public class JASACS extends javax.swing.JFrame {
    StripOutTags tag = new StripOutTags();
    private JTree assessmentTree;
    DefaultMutableTreeNode root;
    private String currentAssessment;
    Map<String, JSONObject> jsonAssessmentObjects = new HashMap<>();
    JMenuItem deleteassessment, loadstudent, newassessment, openAssessment;
    Map<String, String> pdfStringList = new HashMap();

    //String xPath = System.getProperty("user.dir") + "\\JASACS\\";
    String xPath = null;
    /**
     * Creates new form JASACS
     */
    public JASACS() {
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/jasacs/adstu.png")));

        
        root = new DefaultMutableTreeNode("Assessments");
        assessmentTree = new JTree(root);
        assessmentPanel.setLayout(new GridLayout(1, 1));
        assessmentPanel.add(assessmentTree);
        JPopupMenu popup = new JPopupMenu("JASACS Pop Up");
        popup.setBorderPainted(true);

        assessmentTree.add(popup);
        newassessment = new JMenuItem("Add New Assessment");
        newassessment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewAssessment();
            }
        });
        deleteassessment = new JMenuItem("Delete Current Assessment");
        deleteassessment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "You are about to delete this " + currentAssessment + " Assessment. \n Every information would be deleted forever", "You are about to delete", JOptionPane.WARNING_MESSAGE);

            }
        });
        loadstudent = new JMenuItem("Load Student's Current Assessment Version");
        loadstudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadStudentAssessment();
            }
        });
        openAssessment = new JMenuItem("Open An Assessment");
        openAssessment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadAnAssessment();
            }
        });

        popup.add(newassessment);
        popup.add(openAssessment);
        loadstudent.setEnabled(false);
        deleteassessment.setEnabled(false);

        popup.add(loadstudent);
        popup.add(deleteassessment);

        setEnableLoad(false);

        //  
        //   ClosableTabbedPane ct = new ClosableTabbedPane();
        //  studentAssessmentTab = ct;
//        studentAssessmentTab.addPropertyChangeListener(TabbedPaneFactory.PROP_CLOSE, new PropertyChangeListener() {
//            public void propertyChange(PropertyChangeEvent evt) {
//                JTabbedPane pane = (JTabbedPane)evt.getSource();
//                int sel = pane.getSelectedIndex();
//                pane.removeTabAt(sel);
//                
//            }
//        });
        //assessmentTree.setRootVisible(false);
        //assessmentTree.setShowsRootHandles(true); 
        assessmentTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    System.out.println("event triggered by: " + e.getComponent().getName());
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {

                    System.out.println("event triggered by: " + e.getSource());
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                if (assessmentTree.getSelectionPath() == null) {
                    return;
                }
                TreePath tp = assessmentTree.getSelectionPath();

                int s = tp.getPathCount();
                //  System.out.println("Path Count: "+s);
                //  System.out.println("Path Component: "+tp.getPathComponent(s-1));
                switch (s) {
                    case 1:
                        setEnableLoad(false);
                        pathInfo.setText("");
                        break;
                    case 3:
                        //  System.out.println("Parent");
                        DefaultMutableTreeNode d = (DefaultMutableTreeNode) tp.getPathComponent(s - 1);
                        currentAssessment = d.getParent().toString();
                        getChildAssessment(tp.getPathComponent(s - 1));

                        pathInfo.setText(currentAssessment);
                        System.out.println("Current Assessment: " + currentAssessment);
                        setEnableLoad(true);
                        break;
                    default:
                        String ca = tp.getPathComponent(s - 1).toString();
                        currentAssessment = ca;
                        pathInfo.setText(ca);
                        setEnableLoad(true);
                        break;
                }

            }

        });

        assessmentTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                TreePath tp = e.getPath();
                int s = tp.getPathCount();
                //  System.out.println("Path Count: "+s);
                //  System.out.println("Path Component: "+tp.getPathComponent(s-1));
                switch (s) {
                    case 1:
                        setEnableLoad(false);
                        break;
                    case 3:
                        //  System.out.println("Parent");
                        DefaultMutableTreeNode d = (DefaultMutableTreeNode) tp.getPathComponent(s - 1);
                        currentAssessment = d.getParent().toString();
                        pathInfo.setText(currentAssessment);
                        System.out.println("the currenter: "+tp.getPathComponent(s - 1));
                        if(tp.getPathComponent(s - 1).equals("TestModel")){
                            
                        }else{
                            getChildAssessment(tp.getPathComponent(s - 1));
                        }
                        //   System.out.println("Current Assessment: "+currentAssessment);
                        setEnableLoad(true);
                        break;
                    default:
                        String ca = tp.getPathComponent(s - 1).toString();
                        currentAssessment = ca;
                        pathInfo.setText(ca);
                        setEnableLoad(true);
                        break;
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        statusBar = new javax.swing.JLabel();
        studentAssessmentTab = new ClosableTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        assessmentPanel = new javax.swing.JPanel();
        pathInfo = new javax.swing.JLabel();
        generatePDF = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        assessToolBar = new javax.swing.JToolBar();
        newAssessmentBtn = new javax.swing.JButton();
        loadAssessmentBtn = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        addStud = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        newAssessmentMenu = new javax.swing.JMenuItem();
        loadAnAssessment = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        loadAssessmentMenu = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        genPdfAs = new javax.swing.JMenuItem();
        genPdf = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        jMenuItem1.setText("jMenuItem1");

        jMenuItem4.setText("jMenuItem4");

        jMenuItem5.setText("jMenuItem5");

        jMenuItem3.setText("jMenuItem3");

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JASACS: Java Automated Structural Assessment Coding System");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("  Assessment");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        statusBar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        studentAssessmentTab.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        assessmentPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout assessmentPanelLayout = new javax.swing.GroupLayout(assessmentPanel);
        assessmentPanel.setLayout(assessmentPanelLayout);
        assessmentPanelLayout.setHorizontalGroup(
            assessmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        assessmentPanelLayout.setVerticalGroup(
            assessmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 483, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(assessmentPanel);

        pathInfo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        pathInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pathInfo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        generatePDF.setText("Generate PDF");
        generatePDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generatePDFActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        assessToolBar.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0))));
        assessToolBar.setRollover(true);

        newAssessmentBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jasacs/new-hi.png"))); // NOI18N
        newAssessmentBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newAssessmentBtnActionPerformed(evt);
            }
        });
        assessToolBar.add(newAssessmentBtn);

        loadAssessmentBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jasacs/load-hi.png"))); // NOI18N
        loadAssessmentBtn.setFocusable(false);
        loadAssessmentBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        loadAssessmentBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        loadAssessmentBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadAssessmentBtnActionPerformed(evt);
            }
        });
        assessToolBar.add(loadAssessmentBtn);

        jToolBar1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0))));
        jToolBar1.setRollover(true);

        addStud.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jasacs/adstu.png"))); // NOI18N
        addStud.setEnabled(false);
        addStud.setFocusable(false);
        addStud.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addStud.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        addStud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStudActionPerformed(evt);
            }
        });
        jToolBar1.add(addStud);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(assessToolBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(assessToolBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jMenu1.setText("File");
        jMenu1.setHideActionText(true);

        newAssessmentMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newAssessmentMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jasacs/ass.png"))); // NOI18N
        newAssessmentMenu.setText("New Assessment");
        newAssessmentMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newAssessmentMenuActionPerformed(evt);
            }
        });
        jMenu1.add(newAssessmentMenu);

        loadAnAssessment.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        loadAnAssessment.setText("Load An Assessment");
        loadAnAssessment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadAnAssessmentActionPerformed(evt);
            }
        });
        jMenu1.add(loadAnAssessment);
        jMenu1.add(jSeparator2);

        loadAssessmentMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        loadAssessmentMenu.setMnemonic('L');
        loadAssessmentMenu.setText("Load Student Assessmernt");
        loadAssessmentMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadAssessmentMenuActionPerformed(evt);
            }
        });
        jMenu1.add(loadAssessmentMenu);
        jMenu1.add(jSeparator3);

        genPdfAs.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        genPdfAs.setText("Generate PDF As");
        genPdfAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genPdfAsActionPerformed(evt);
            }
        });
        jMenu1.add(genPdfAs);

        genPdf.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        genPdf.setText("Generate PDF");
        genPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genPdfActionPerformed(evt);
            }
        });
        jMenu1.add(genPdf);
        jMenu1.add(jSeparator1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Exit");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(statusBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(generatePDF, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane1)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pathInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(studentAssessmentTab, javax.swing.GroupLayout.DEFAULT_SIZE, 954, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pathInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1))
                    .addComponent(studentAssessmentTab))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(statusBar, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(generatePDF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void newAssessmentMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newAssessmentMenuActionPerformed
        createNewAssessment();
    }//GEN-LAST:event_newAssessmentMenuActionPerformed

    private void newAssessmentBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newAssessmentBtnActionPerformed
        createNewAssessment();
    }//GEN-LAST:event_newAssessmentBtnActionPerformed

    private void loadAssessmentMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadAssessmentMenuActionPerformed
        loadStudentAssessment();
    }//GEN-LAST:event_loadAssessmentMenuActionPerformed

    private void loadAssessmentBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadAssessmentBtnActionPerformed
        loadAnAssessment();
    }//GEN-LAST:event_loadAssessmentBtnActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void loadAnAssessmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadAnAssessmentActionPerformed
        loadAnAssessment();      
    }//GEN-LAST:event_loadAnAssessmentActionPerformed
    
    public boolean chooseFolder(){
        boolean flag = false;
        JFileChooser jc = new JFileChooser();
        jc.setDialogTitle("Select directory to save assessment");
        jc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = jc.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            xPath = jc.getSelectedFile().getAbsolutePath();
            statusBar.setText(xPath);
            flag = true;
        }
        return flag;
    }
    

    private void generate(){
            if(studentAssessmentTab.getSelectedComponent()!= null)  {
                if(xPath == null){
                if(chooseFolder()){
                generate();
                }               
                }else{
            try {
                Component c =  studentAssessmentTab.getSelectedComponent();
                JScrollPane jcp = (JScrollPane) c;
                JViewport jvp = jcp.getViewport();
                String tt =  ((JEditorPane) jvp.getView()).getName();
                String cv =  ((JEditorPane) jvp.getView()).getSelectedText();
                PDF pdf = new PDF();
                
                String xPaths = xPath + "\\htmll\\"+cv+".html";
                System.out.println(xPaths);
                File m = new File(xPath, "html");
                if(!m.exists()){
                m.mkdirs();
                }
                File cr = new File(m, cv+".html");
                try (FileWriter wr = new FileWriter(cr)) {
                    BufferedWriter bf = new BufferedWriter(wr);
                    StripOutTags cf = new StripOutTags();
                    bf.write(cf.filterOutTag(pdfStringList.get(tt)));
                    bf.close();
                    wr.close(); 
                }         
                pdf.createPDF(tt,cr.getAbsolutePath(), xPath);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
            
            }
           }
        }else{
            JOptionPane.showMessageDialog(this, "No Asessment is selected");
        }
    }
    
  
    
    
    private void generatePDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generatePDFActionPerformed
        generate();
    }//GEN-LAST:event_generatePDFActionPerformed

    private void addStudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStudActionPerformed
        loadStudentAssessment();
    }//GEN-LAST:event_addStudActionPerformed

    private void genPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_genPdfActionPerformed
        generate();
    }//GEN-LAST:event_genPdfActionPerformed

    private void genPdfAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_genPdfAsActionPerformed
       chooseFolder();
    }//GEN-LAST:event_genPdfAsActionPerformed
    
    private void loadAnAssessment(){
       AllAssessmentDialog aad = new AllAssessmentDialog(this, rootPaneCheckingEnabled);
       aad.setVisible(true);
       String assToLoad = aad.getSelectedAssessment();
        System.out.println("Ass to load: "+assToLoad);
        if(assToLoad != null){
            loadExistingAssessment(assToLoad);
        }
    }
    public void createNewAssessment() {
        AssessObject ao = null;
        AssessmentBoard af = new AssessmentBoard(this, rootPaneCheckingEnabled);
        af.setVisible(true);
        ClassLocationObject clo = af.clo;
        if (clo != null) {
            try {
                String prepare = System.getProperty("user.dir") + "\\JASACS\\" + clo.getClassName();
                String preparePath = prepare + "\\TestModel";
//                if(new File(preparePath).exists()){
//                JOptionPane.showMessageDialog(this, "This assessment exist!");
//                    return;
//                
//                }
                String check_for_space = clo.getFullClassLocation();
                if (check_for_space.contains(" ")) {
                    JOptionPane.showMessageDialog(null, "Please make sure no folder has space character i.e \" \".\n You can delete the space or use underscore (_ or anything) inplace of the space.\n" + check_for_space, "Space in one of the directory's name", JOptionPane.ERROR_MESSAGE);

                } else {
                    GetFullClassPath gfp = new GetFullClassPath(new File(clo.getFullClassLocation()));
                    File workdir = new File(preparePath, gfp.getPackagePath());
                    if (!workdir.exists()) {
                        workdir.mkdirs();
                        System.out.println("directory created");
                    }
                    //else {
//                    JOptionPane.showMessageDialog(this, "This assessment exist!");
//                    return;
//                }
                    //     System.out.println("class name:>>> "+clo.getClassName());
                    File f = new File(workdir, clo.getClassName() + ".class");
                    if (!f.exists()) {

                        System.out.println("directory created");
                    } else {
                        JOptionPane.showMessageDialog(this, "This assessment exist!");
                        return;
                    }
                    try (FileOutputStream fos = new FileOutputStream(prepare + "\\buildpath.jasacs")) {
                        String drep = System.getProperty("user.dir") + "\\JASACS\\testtemp";
                        copyFolder(new File(drep),new File(preparePath));
                       // Files.copy(source, target, REPLACE_EXISTING);
                        ao = new AssessObject(f.getAbsolutePath());

                        ObjectOutputStream out = new ObjectOutputStream(fos);
                        out.writeObject(ao);
                        out.close();
                         deletetempFolder(new File(drep));
                    }
                   
                    addAssessmentToTree(clo);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(JASACS.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(JASACS.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            JOptionPane.showMessageDialog(this, "No File chossen");
        }

    }

    private void loadStudentAssessment() {
        StudentsAssessmentVersionBoard af = new StudentsAssessmentVersionBoard(this, rootPaneCheckingEnabled, currentAssessment);
        af.setVisible(true);
        ClassLocationObject clo = af.clo;
        String sPath = af.studAsPath;
        String matric = af.studMatric;
        String preparePath = System.getProperty("user.dir") + "\\JASACS\\" + currentAssessment + "\\" + matric;      
        if(new File(preparePath).isDirectory()){
            JOptionPane.showMessageDialog(this, "This student exists");  
            deletetempFolder(new File(System.getProperty("user.dir") + "\\JASACS\\temp"));
            return;
        }
        
        
        
        System.out.println("Student stuff: " + sPath);

        if (clo != null) {
            FileOutputStream fos = null;
            // write all dependencies 
           // CheckForStudentID cfsID = new CheckForStudentID();
           // String stud_ID = cfsID.studentID(sPath, currentAssessment);

            try {

                System.out.println("spath --------------------------------------> "+sPath);
                sPath = sPath.replace(System.getProperty("user.dir") + "\\JASACS\\temp\\" + currentAssessment + "\\", "");
               System.out.println("before concat spath --------------------------------------> "+sPath);
                
                
                System.out.println("written spath --------------------------------------> "+sPath);
                AssessObject ao = new AssessObject(sPath);


                GetFullClassPath gfp = new GetFullClassPath(new File(clo.getFullClassLocation()));
                File workdir = new File(preparePath, gfp.getPackagePath());

                //     System.out.println("class name:>>> "+clo.getClassName());
                File f = new File(workdir, clo.getClassName() + ".class");
               

                String source = System.getProperty("user.dir") + "\\JASACS\\temp\\" + currentAssessment;
               

                copyFolder(new File(source), new File(preparePath));
                FileOutputStream foa = new FileOutputStream(preparePath + "\\loadpath.jasacs");
                ObjectOutputStream oos = new ObjectOutputStream(foa);
                oos.writeObject(ao);

                deletetempFolder(new File(System.getProperty("user.dir") + "\\JASACS\\temp"));
                addStudentToNode(matric);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(JASACS.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(JASACS.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No File chosen");
        }

    }

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
            java.util.logging.Logger.getLogger(JASACS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JASACS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JASACS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JASACS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JASACS().setVisible(true);
            }
        });
    }

    private boolean writeDependncy() {

        return true;
    }

    public void setEnableLoad(boolean flag) {

        loadAssessmentMenu.setEnabled(flag);
        deleteassessment.setEnabled(flag);
        loadstudent.setEnabled(flag);
        addStud.setEnabled(flag);

    }
    public void setEnableGenPDF(boolean flag) {
        genPdf.setEnabled(flag);
        generatePDF.setEnabled(flag);

    }

    private void addAssessmentToTree(ClassLocationObject clo) {
        String[] arr = clo.getClassLocation().split("\\.");
        String assessmentName = arr[arr.length - 2];
        currentAssessment = assessmentName;
        DefaultMutableTreeNode dmt = new DefaultMutableTreeNode(assessmentName); 
        DefaultMutableTreeNode tm = new DefaultMutableTreeNode("Test Model");
        dmt.add(tm);
        addAssessmentJsonObject();    
        DefaultTreeModel model = (DefaultTreeModel) assessmentTree.getModel();
        DefaultMutableTreeNode rooted = (DefaultMutableTreeNode) assessmentTree.getModel().getRoot();
        model.insertNodeInto(dmt, rooted, rooted.getChildCount());
        pathInfo.setText(currentAssessment);
        setEnableLoad(true);
    }

    private void addAssessmentJsonObject() {
        LoadTestModelStructure ltms = new LoadTestModelStructure(currentAssessment);

        JSONObject jo = ltms.startOperation();
        jsonAssessmentObjects.put(currentAssessment, jo);
        System.out.println("json obj:");
        System.out.println(jsonAssessmentObjects);
    }

    private void getChildAssessment(Object o) {
        int cCount = -1;
        //   System.out.println("obj name: "+o.toString());
        DefaultMutableTreeNode t = (DefaultMutableTreeNode) o;
        //     System.out.println("parent name: "+t.getParent().toString());
        //   studentAssessmentTab.add(" "+o.toString()+" ",new JEditorPane());
        String tooltext = o.toString() + "'s Version for Assessment :" + t.getParent().toString();
        JEditorPane jed = null;
        Component[] comp = studentAssessmentTab.getComponents();
        System.out.println("comp count: " + comp.length);
        for (Component c : comp) {
            cCount = cCount + 1;
            JScrollPane jcp = (JScrollPane) c;
            JViewport jvp = jcp.getViewport();
            if (((JEditorPane) jvp.getView()).getName().equals(tooltext)) {

                jed = (JEditorPane) jvp.getView();
                break;
            }
        }

        if (jed == null) {
            HTMLEditorKit kit = new HTMLEditorKit();
            
            StyleSheet styleSheet = kit.getStyleSheet();
            styleSheet.addRule("body {color:red; margin: 4px; font-family: 'Verdana', serif;}");
            styleSheet.addRule("h1 {color: blue;}");
            styleSheet.addRule(".error {color: red;}");
          //  styleSheet.addRule("p {color: red; }");
            styleSheet.addRule("h2 {color: #ff0000;}");       
            styleSheet.addRule("h3 {color: black; font-weight : 600;}");       
            styleSheet.addRule("  td{  border-style:solid; border-width:thin; border-spacing: 0px;}");
            styleSheet.addRule("table {  border-spacing: 0px; width : 100%;}");
            styleSheet.addRule("table {  border:2px solid black; border-width:thin; border-spacing: 0px; }");

  
            jed = new JEditorPane() {   
                
                String cName = tooltext;
                String level = o.toString();

                @Override
                public String getName() {
                    return cName;
                }

                @Override
                public String getSelectedText() {
                    return level;
                }
            };
            jed.setEditorKit(kit);
            JScrollPane jsc = new JScrollPane(jed);
            studentAssessmentTab.addTab("  " + o.toString() + "  ", null, jsc, tooltext);
            jed.setToolTipText(tooltext);

            //add the file difference here---------------------------------------------------------------------------------> difference
            String text = "<html><h1><u><center>" + tooltext + "</center></u></h1>";
            JSONObject jo = null;
            if (jed.getSelectedText().equals("Test Model")) {

                jo = jsonAssessmentObjects.get(currentAssessment);
                System.out.println("current assessment: " + currentAssessment);
                JSONArray ja = (JSONArray) jo.get("class");
                //  class array only has one element
                JSONObject jag = (JSONObject) ja.get(0);

//                text += "<p><br/><u>Class Description</u><br/>Class Name: " + jag.get("class name");
//                text += "<br/>Class Type: " + jag.get("type name");
//                text += "<br/>Package Name: " + jag.get("package name");
//                text += "<br/>Modifier: " + jag.get("modifier");
//                text += "<br/>Superclass: " + jag.get("superclass");
              
                if(!(jag.get("package name").equals("") || jag.get("package name")==null))
                text += "<br/>package " + jag.get("package name")+"<br/>";
               
                if(!(jag.get("modifier").equals("") || jag.get("modifier")==null))
                text +=  jag.get("modifier")+" ";
               
                    text +=  jag.get("Final")+" ";
                  text += jag.get("abstract")+" ";
                 
                
                
                    text += jag.get("type name")+" ";
                    text += jag.get("class name");
                    
                if(!(jag.get("superclass").equals("java.lang.Object")) && (jag.get("superclass")!=null) && !(jag.get("superclass").equals(""))){
                    String p = jag.get("superclass").toString();
                    p = p.replace("abstract", "");
                   p = p.replace("final", "");
                   p = p.replace("interface", "");
                   p = p.replace("public", "");
                   p = p.replace(jag.get("package name")+".", "");
                text +=  " extends "+p;
                }
                
               JSONArray jup = (JSONArray) jag.get("Implemented Interface");
                if(!jup.isEmpty()){
                text += " implements ";
                int po = 0;
                for (Object x : jup) {
                   String p = x.toString();
                   p = p.replace("abstract", "");
                   p = p.replace("final", "");
                   p = p.replace("interface", "");
                   p = p.replace("public", "");
                   p = p.replace(jag.get("package name")+".", "");

                    text += p;
                    
                    if(po != jup.size() - 1){
                        text +=", ";
                    }
                    po++;
                }
            }
                
                ///----------------------------------------------------------
               
                text += "</p>";

                text += "<p><u>CONSTRUCTORS</u><br/>";
                JSONArray jc = (JSONArray) jo.get("constructors");
               
                for (Object x : jc) {
                    JSONObject ob = (JSONObject) x;

                    text += ob.get("modifier") + " " +ob.get("name")+"( ";
                    JSONArray mp = (JSONArray) ob.get("parameters");
                   
                        JSONArray[] jan = new JSONArray[mp.size()];
                        for (int ox = 0; ox < jan.length; ox++) {
                            text += mp.get(ox);
                            if(ox != jan.length-1)
                            {
                           text += ", ";
                            }
                                
                        }
                        
                        text += " )<br/>";
                }
                text += "<br/>";
                text += "</p>";

                jc = null;
                text += "<p><u>ATTRIBUTES</u><br/>";
                jc = (JSONArray) jo.get("fields");
                
                for (Object x : jc) {
                    JSONObject ob = (JSONObject) x;

                    text += ob.get("modifier") + " " +ob.get("generic type")+ " "+ ob.get("name") + " <br/>";
                }
                text += "<br/>";
                text += "</p>";

                jc = null;
                text += "<p><u>METHODS</u><br/>";
                jc = (JSONArray) jo.get("methods");
                for (Object x : jc) {
                    JSONObject ob = (JSONObject) x;

                    text += ob.get("modifier") + "  "+ ob.get("return type")+" "+ ob.get("name") + " ( ";
                    JSONArray mp = (JSONArray) ob.get("parameters");
               
                             JSONArray[] jan = new JSONArray[mp.size()];
                        for (int ox = 0; ox < jan.length; ox++) {
                            text += mp.get(ox);
                            if(ox != jan.length-1)
                            {
                           text += ", ";
                            }
                                
                        }
                        
                        text += " )<br/>";

                }
                text += "<br/>";
                text += "</p>";

            } else {
                LoadStudentModelVersion ltms = new LoadStudentModelVersion(currentAssessment, jed.getSelectedText());
                jo = ltms.startOperation();
                //---------------------- 
                TestJsonDiff tj = new TestJsonDiff(jsonAssessmentObjects.get(currentAssessment), jo);

                text += "<p>"+tooltext+"</p><br/><br/><h3> <u>Class Description</u></h3>" + tj.testClassDescriptions();
                text += "<br/><br/><h3> <u>Constructors</u></h3>" + tj.testConstructors();
                text += "<br/><br/><h3> <u>Attributes</u></h3>" + tj.testFields();
                text += "<br/><br/><h3> <u>Methods</u></h3>" + tj.testMethods() ;
                text += "<br/><br/><h3><u>Annotations</u></h3>" + tj.testAnnotations();

            }
            text += "</html>";
            if(!pdfStringList.containsKey(tooltext))
                pdfStringList.put(tooltext, text);
            jed.setText(tag.filterOutTag(text));
            jed.setEditable(false);
            studentAssessmentTab.setSelectedIndex(studentAssessmentTab.getComponentCount() - 1);
        } else {
            System.out.println("This is displayed and has name : " + jed.getName());
            System.out.println("This is level and has name : " + jed.getSelectedText());
            studentAssessmentTab.requestFocusInWindow();
            studentAssessmentTab.getComponent(cCount).requestFocusInWindow();
            studentAssessmentTab.setSelectedIndex(cCount);

        }

        // studentAssessmentTab.getToolTipText();
        //      JEditorPane jt = (JEditorPane) studentAssessmentTab.getComponentAt(0);  
        //       System.out.println("tool kit :=> "+jt.getName());
    }
    //   String preparePath =  System.getProperty("user.dir")+ "\\JASACS\\temp";

    private void addStudentToNode(String sID) {
        Enumeration e = root.depthFirstEnumeration();
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();

            if (node.getLevel() != 1) {
                continue;
            }

            if (node.toString().equals(currentAssessment)) {
                //  System.out.println("The target assessment: "+node.toString());
                DefaultMutableTreeNode child = new DefaultMutableTreeNode(sID);
                DefaultTreeModel model = (DefaultTreeModel) assessmentTree.getModel();
                DefaultMutableTreeNode rooted = (DefaultMutableTreeNode) assessmentTree.getModel().getRoot();
                int xs = rooted.getIndex(node);
                DefaultMutableTreeNode assess = (DefaultMutableTreeNode) rooted.getChildAt(xs);
                model.insertNodeInto(child, assess, assess.getChildCount() - 1);
                break;
            }

        }

    }

    private void copyFolder(File sourceFolder, File destinationFolder) throws IOException {

        if (sourceFolder.isDirectory()) {
            if (!destinationFolder.exists()) {
                destinationFolder.mkdir();
                System.out.println("Directory created :: " + destinationFolder);
            }
            String files[] = sourceFolder.list();

            for (String file : files) {
                File srcFile = new File(sourceFolder, file);
                File destFile = new File(destinationFolder, file);
                copyFolder(srcFile, destFile);
            }
        } else {
            Files.copy(sourceFolder.toPath(), destinationFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied :: " + destinationFolder);
        }
    }

    private void deletetempFolder(File f) {
        if (f.isDirectory()) {
            for (File fx : f.listFiles()) {
                deletetempFolder(fx);
            }
        }

        f.delete();
        // Path p = Paths.get(dir);            
    }
    
    
    private void loadExistingAssessment(String ea){
        boolean flag = false;
        System.out.println("starting Flag: "+flag);
        Enumeration enu = root.children();
           if (enu != null) {
            while (enu.hasMoreElements()) {
                System.out.println("found: " + ea);
               
                 if(enu.nextElement().toString().equalsIgnoreCase(ea)){
                    flag = true;
                     System.out.println(flag);
                    
                    break;
                 }
        }
    }
        
         
        
        if(!flag){
        currentAssessment = ea;
        DefaultMutableTreeNode dmt = new DefaultMutableTreeNode(ea);
        String directory = System.getProperty("user.dir")+ "\\JASACS\\"+ea;
        File dir = new File(directory);
        File[] aDItem = dir.listFiles();
        for(File item : aDItem){
            if(item.isDirectory()){
                DefaultMutableTreeNode child = null;
                if(item.getName().equals("TestModel")){
                    child = new DefaultMutableTreeNode("Test Model");
                    addAssessmentJsonObject();
                }else{
               child = new DefaultMutableTreeNode(item.getName());
                }
                dmt.add(child);
            }
        }
        DefaultTreeModel model = (DefaultTreeModel) assessmentTree.getModel();
        DefaultMutableTreeNode rooted = (DefaultMutableTreeNode) assessmentTree.getModel().getRoot();
        model.insertNodeInto(dmt, rooted, rooted.getChildCount());
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addStud;
    private javax.swing.JToolBar assessToolBar;
    private javax.swing.JPanel assessmentPanel;
    private javax.swing.JMenuItem genPdf;
    private javax.swing.JMenuItem genPdfAs;
    private javax.swing.JButton generatePDF;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem loadAnAssessment;
    private javax.swing.JButton loadAssessmentBtn;
    private javax.swing.JMenuItem loadAssessmentMenu;
    private javax.swing.JButton newAssessmentBtn;
    private javax.swing.JMenuItem newAssessmentMenu;
    private javax.swing.JLabel pathInfo;
    private javax.swing.JLabel statusBar;
    private javax.swing.JTabbedPane studentAssessmentTab;
    // End of variables declaration//GEN-END:variables
}

//
// MouseListener ml = new MouseAdapter() {
//     public void mousePressed(MouseEvent e) {
//         int selRow = tree.getRowForLocation(e.getX(), e.getY());
//         TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
//         if(selRow != -1) {
//             if(e.getClickCount() == 1) {
//                 mySingleClick(selRow, selPath);
//             }
//             else if(e.getClickCount() == 2) {
//                 myDoubleClick(selRow, selPath);
//             }
//         }
//     }
// };
// tree.addMouseListener(ml);
