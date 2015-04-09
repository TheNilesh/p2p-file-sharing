package peer.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JTable;

import peer.P2PMain;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JProgressBar;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JButton btnSearch;
	private JTable table;
	public DefaultTableModel dft;
	private javax.swing.JScrollPane jScrollPane2;
	private P2PMain p2pMain;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
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
	public MainWindow() {
		setTitle("Peer to Peer File Sharing");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 433, 317);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSearchFiles = new JLabel("Search Files :");
		lblSearchFiles.setBounds(10, 23, 82, 21);
		contentPane.add(lblSearchFiles);
		
		textField = new JTextField();
		textField.setBounds(102, 23, 200, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		btnSearch.setBounds(311, 22, 89, 23);
		contentPane.add(btnSearch);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 54, 390, 9);
		contentPane.add(separator);
		
		table = new JTable();
		jScrollPane2 = new javax.swing.JScrollPane();
		dft = new DefaultTableModel();
		table.setModel(dft);
		jScrollPane2.setViewportView(table);
		dft.addColumn("File Name");
		dft.addColumn("Tags");
		dft.addColumn("Size(KB)");
		dft.addColumn("Seeders");
		dft.addColumn("Checksum");
		
		jScrollPane2.setBounds(10, 60, 390, 170);
		contentPane.add(jScrollPane2);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(10, 254, 390, 14);
		contentPane.add(progressBar);
		
		JLabel lblIdle = new JLabel("Idle");
		lblIdle.setBounds(10, 241, 390, 14);
		contentPane.add(lblIdle);
		dft.addRow(new Object[] { "cnme", "12","6566", "file" });
		dft.addRow(new Object[] { "cnme", "12","6566", "file" });
		
		try {
		//	p2pMain=new P2PMain("E:\\MOVIES","localhost",4689);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
