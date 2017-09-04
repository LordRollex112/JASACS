/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasacs;

/**
 *
 * @author 1412625
 */
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.text.*;

public class AddNodes{
	public static DefaultTreeModel model;
	public static TreePath path;
	public static JTree tree;
	public static DefaultMutableTreeNode nNode;
	public static MutableTreeNode node;
	String nodeName;
	public static void main(String[] args) {
		JFrame frame = new JFrame("Adding a Node to a JTree Component!");
		JPanel panel = new JPanel();
		DefaultMutableTreeNode myComputer = new DefaultMutableTreeNode("My Computer");
		DefaultMutableTreeNode c = new DefaultMutableTreeNode("Local Disk(C:)");
		DefaultMutableTreeNode vinod = new DefaultMutableTreeNode("Vinod");
		DefaultMutableTreeNode swing = new DefaultMutableTreeNode("Swing");
		DefaultMutableTreeNode tr = new DefaultMutableTreeNode("Tree");
		DefaultMutableTreeNode a = new DefaultMutableTreeNode("3½ Floppy(A:)");
		DefaultMutableTreeNode e = new DefaultMutableTreeNode("New Volume(E:)");
		c.add(vinod);
		vinod.add(swing);
		swing.add(tr);
		myComputer.add(c);
		myComputer.add(a);
		myComputer.add(e);
		tree = new JTree(myComputer);
		JButton button = new JButton("Add Tree");
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				model = (DefaultTreeModel)tree.getModel();
				String nodeName = JOptionPane.showInputDialog(null, "Enter the node name:");
				if(nodeName.equals("")){
					JOptionPane.showMessageDialog(null, "Node is not added in the tree!");
				}
				else{
			//		create a new node
					nNode = new DefaultMutableTreeNode(nodeName);
					path = tree.getNextMatch("M", 0, Position.Bias.Forward);
                                        
					node = (MutableTreeNode)path.getLastPathComponent();
					model.insertNodeInto(nNode, node, node.getChildCount());
					JOptionPane.showMessageDialog(null, "Node are added in the tree!");
				}
			}
		});
		panel.add(tree);
		panel.add(button);
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		frame.setSize(200,300);
		frame.setVisible(true);
	}
}