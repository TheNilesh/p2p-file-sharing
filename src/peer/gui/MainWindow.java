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

import com.Constants;
import com.FileInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

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
		setBounds(100, 100, 674, 446);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		dft = new DefaultTableModel();
		dft.addColumn("File Name");
		dft.addColumn("Tags");
		dft.addColumn("Size(KB)");
		dft.addColumn("Seeders");
		dft.addColumn("Checksum");
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 13, 636, 384);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Local", null, panel, null);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Search", null, panel_1, null);
		panel_1.setLayout(null);
		
		table = new JTable();
		jScrollPane2 = new javax.swing.JScrollPane();
		jScrollPane2.setBounds(10, 92, 611, 230);
		panel_1.add(jScrollPane2);
		table.setModel(dft);
		jScrollPane2.setViewportView(table);
		
		btnSearch = new JButton("Search");
		btnSearch.setBounds(427, 58, 89, 23);
		panel_1.add(btnSearch);
		
		textField = new JTextField();
		textField.setBounds(196, 59, 225, 21);
		panel_1.add(textField);
		textField.setColumns(10);
		
		JLabel lblSearchFiles = new JLabel("Search Files :");
		lblSearchFiles.setBounds(31, 60, 155, 21);
		panel_1.add(lblSearchFiles);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(31, 11, 402, 32);
		panel_1.add(toolBar);
		
		JButton btnDownload = new JButton("Download");
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selRow=table.getSelectedRow();
				if(selRow>=0){
					String checksum=(String)table.getValueAt(selRow,4);
					String localName=(String)table.getValueAt(selRow, 0);
					System.out.println("Download? " + checksum + " " + localName);
					System.out.println(p2pMain.downloadFile(checksum, localName));
				}
			}
		});
		toolBar.add(btnDownload);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (dft.getRowCount() > 0) {
				    for (int i = dft.getRowCount() - 1; i > -1; i--) {
				        dft.removeRow(i);
				    }
				}
				btnSearch.setEnabled(false);
				FileInfo[] tmp1= p2pMain.searchFile(textField.getText());
				for(int i=0;i<tmp1.length;i++){
					dft.addRow(new Object[]{tmp1[i].name,"",tmp1[i].getLen()/1024,tmp1[i].getSeeders().size(),tmp1[i].getChecksum()});
				}
				btnSearch.setEnabled(true);
			}
		});
		
		try {
			p2pMain=new P2PMain("E:\\TEST1","localhost",Constants.PORT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
