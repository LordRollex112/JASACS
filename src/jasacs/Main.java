/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.   888deleting and adding using popup menu
 */
package jasacs;

/**
 *
 * @author 1412625
 */
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.awt.*;
import java.net.*;
import java.awt.event.*;

public class Main extends JFrame{
    public Main() {
    DefaultMutableTreeNode root = createNodes();
    JTree tree = new JTree(root);
    final TreePopup treePopup = new TreePopup(tree);
    tree.addMouseListener(new MouseAdapter() {
    public void mouseReleased (MouseEvent e) {
        if (e.isPopupTrigger()) {
        treePopup.show(e.getComponent(), e.getX(), e.getY());
        }
        }
    });
        getContentPane().add(new JScrollPane(tree));

    addWindowListener(new WindowAdapter() {
    public void windowClosing(WindowEvent we) {
    System.exit(0);
        }
    });
    }

    public static DefaultMutableTreeNode createNodes() {
    DefaultMutableTreeNode root = new DefaultMutableTreeNode("Java");

        DefaultMutableTreeNode j2se = new DefaultMutableTreeNode("J2SE");
        DefaultMutableTreeNode j2ee = new DefaultMutableTreeNode("J2EE");
        DefaultMutableTreeNode j2me = new DefaultMutableTreeNode("J2ME");

        j2se.add(new DefaultMutableTreeNode("http://java.sun.com/j2se/"));
        j2ee.add(new DefaultMutableTreeNode("http://java.sun.com/j2ee/"));
        j2me.add(new DefaultMutableTreeNode("http://java.sun.com/j2me/"));
        root.add(j2se);
        root.add(j2ee);
        root.add(j2me);

    return root;
    }
    public static void main(String []args) {
    Main main = new Main();
    main.setSize(400, 400);
    main.setVisible(true);
    }
}

class TreePopup extends JPopupMenu {
    public TreePopup(JTree tree) {
        JMenuItem itemDelete = new JMenuItem("Delete");
        JMenuItem itemAdd = new JMenuItem("Add");
        itemDelete.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent ae) {
        System.out.println("Delete child");
        }
        });
        itemAdd.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent ae) {
        System.out.println("Add child");
        }
    });
    add(itemDelete);
    add(new JSeparator());
    add(itemAdd);
 }
}