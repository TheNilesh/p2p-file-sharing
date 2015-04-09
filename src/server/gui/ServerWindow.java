package server.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JButton;

import com.Constants;

import server.ServerMain;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ServerWindow extends JFrame {

	private ServerMain serverMain;
	private JPanel contentPane;
	private DefaultListModel<String> listModel;
	private JList list;
	private JList listFiles;
	private DefaultListModel lstFileModel;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerWindow frame = new ServerWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ServerWindow() {
		setTitle("Peer to Peer Super Node");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		listModel = new DefaultListModel();
		list = new JList(listModel);
		list.setBounds(30, 44, 130, 153);
		contentPane.add(list);
		
		lstFileModel = new DefaultListModel();
		listFiles = new JList(lstFileModel);
		listFiles.setBounds(194, 44, 230, 153);
		contentPane.add(listFiles);
		
		serverMain=new ServerMain(this,Constants.PORT);
	}
	
	public void addPeer(String pName){
		listModel.addElement(pName);
	}
	
	public void removePeer(String pName){
		listModel.removeElement(pName);
	}
	
	public void addFile(String fName){
		lstFileModel.addElement(fName);
	}
	
	public void removeFile(String fName){
		lstFileModel.removeElement(fName);
	}
}
