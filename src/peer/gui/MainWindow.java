package peer.gui;

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
import java.util.Vector;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtSearch;
	private JButton btnSearch;
	private JTable tblSearchResult;
	public DefaultTableModel dftSearch;
	private javax.swing.JScrollPane jScrollPane2;
	private P2PMain p2pMain;
	private JTextField txtPeerID;
	private JTextField txtPeerBW;
	private JTextField txtTracker;
	private JTextField txtShareDir;
	private JTable tblTasks;
	private JTable tblDownloads;
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
		dftSearch = new DefaultTableModel();
		dftSearch.addColumn("File Name");
		dftSearch.addColumn("Tags");
		dftSearch.addColumn("Size(KB)");
		dftSearch.addColumn("Seeders");
		dftSearch.addColumn("Checksum");
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 13, 636, 362);
		contentPane.add(tabbedPane);
		
		JPanel pnlSearch = new JPanel();
		tabbedPane.addTab("Search", null, pnlSearch, null);
		pnlSearch.setLayout(null);
		
		tblSearchResult = new JTable();
		jScrollPane2 = new javax.swing.JScrollPane();
		jScrollPane2.setBounds(10, 92, 611, 230);
		pnlSearch.add(jScrollPane2);
		tblSearchResult.setModel(dftSearch);
		jScrollPane2.setViewportView(tblSearchResult);
		
		btnSearch = new JButton("Search");
		btnSearch.setBounds(427, 58, 89, 23);
		pnlSearch.add(btnSearch);
		
		txtSearch = new JTextField();
		txtSearch.setBounds(196, 59, 225, 21);
		pnlSearch.add(txtSearch);
		txtSearch.setColumns(10);
		
		JLabel lblSearchFiles = new JLabel("Search Files :");
		lblSearchFiles.setBounds(31, 60, 155, 21);
		pnlSearch.add(lblSearchFiles);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(31, 11, 402, 32);
		pnlSearch.add(toolBar);
		
		JButton btnDownload = new JButton("Download");
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selRow=tblSearchResult.getSelectedRow();
				if(selRow>=0){
					String checksum=(String)tblSearchResult.getValueAt(selRow,4);
					String localName=(String)tblSearchResult.getValueAt(selRow, 0);
					System.out.println("Download? " + checksum + " " + localName);
					System.out.println(p2pMain.downloadFile(checksum, localName));
				}
			}
		});
		toolBar.add(btnDownload);
		
		JPanel pnlDownloads = new JPanel();
		tabbedPane.addTab("Downloads", null, pnlDownloads, null);
		pnlDownloads.setLayout(null);
		
		JScrollPane scrDownloads = new JScrollPane();
		scrDownloads.setBounds(29, 61, 559, 262);
		pnlDownloads.add(scrDownloads);
		
		tblDownloads = new JTable();
		scrDownloads.setViewportView(tblDownloads);
		
		JLabel lblThisIsThe = new JLabel("This is the list of background tasks being executed on this peer.");
		lblThisIsThe.setBounds(32, 11, 430, 31);
		pnlDownloads.add(lblThisIsThe);
		
		JPanel pnlTasks = new JPanel();
		tabbedPane.addTab("Tasks", null, pnlTasks, null);
		pnlTasks.setLayout(null);
		
		JScrollPane scrTasks = new JScrollPane();
		scrTasks.setBounds(45, 35, 453, 204);
		pnlTasks.add(scrTasks);
		
		tblTasks = new JTable();
		scrTasks.setViewportView(tblTasks);
		
		JPanel pnlSettings = new JPanel();
		tabbedPane.addTab("Settings", null, pnlSettings, null);
		pnlSettings.setLayout(null);
		
		txtPeerID = new JTextField();
		txtPeerID.setEditable(false);
		txtPeerID.setBounds(154, 58, 233, 20);
		pnlSettings.add(txtPeerID);
		txtPeerID.setColumns(10);
		
		JLabel lblPeerID = new JLabel("Peer ID");
		lblPeerID.setBounds(48, 58, 86, 20);
		pnlSettings.add(lblPeerID);
		
		JLabel lblPeerBandwidth = new JLabel("Peer Bandwidth");
		lblPeerBandwidth.setBounds(48, 94, 104, 20);
		pnlSettings.add(lblPeerBandwidth);
		
		txtPeerBW = new JTextField();
		txtPeerBW.setEditable(false);
		txtPeerBW.setBounds(154, 94, 75, 20);
		pnlSettings.add(txtPeerBW);
		txtPeerBW.setColumns(10);
		
		JButton btnCalculateBW = new JButton("Re-calculate");
		btnCalculateBW.setBounds(239, 93, 148, 23);
		pnlSettings.add(btnCalculateBW);
		
		JLabel lblCentralisedTracker = new JLabel("Centralised Tracker:");
		lblCentralisedTracker.setBounds(48, 128, 104, 23);
		pnlSettings.add(lblCentralisedTracker);
		
		txtTracker = new JTextField();
		txtTracker.setText("localhost:4689");
		txtTracker.setBounds(154, 129, 233, 20);
		pnlSettings.add(txtTracker);
		txtTracker.setColumns(10);
		
		JLabel lblSharedDirectory = new JLabel("Shared Directory :");
		lblSharedDirectory.setBounds(48, 170, 104, 20);
		pnlSettings.add(lblSharedDirectory);
		
		txtShareDir = new JTextField();
		txtShareDir.setText("E:\\TEST2");
		txtShareDir.setBounds(154, 170, 233, 20);
		pnlSettings.add(txtShareDir);
		txtShareDir.setColumns(10);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(298, 212, 89, 23);
		pnlSettings.add(btnSave);
		
		JPanel pnlAbout = new JPanel();
		tabbedPane.addTab("About", null, pnlAbout, null);
		pnlAbout.setLayout(null);
		
		JLabel lblConnectingToServer = new JLabel("Connecting to server...");
		lblConnectingToServer.setBounds(10, 382, 269, 15);
		contentPane.add(lblConnectingToServer);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (dftSearch.getRowCount() > 0) {
				    for (int i = dftSearch.getRowCount() - 1; i > -1; i--) {
				        dftSearch.removeRow(i);
				    }
				}
				btnSearch.setEnabled(false);
				FileInfo[] tmp1= p2pMain.searchFile(txtSearch.getText());
				for(int i=0;i<tmp1.length;i++){
					dftSearch.addRow(new Object[]{tmp1[i].name,"",tmp1[i].getLen()/1024,tmp1[i].getSeeders().size(),tmp1[i].getChecksum()});
				}
				btnSearch.setEnabled(true);
			}
		});
		
		try {
			p2pMain=new P2PMain("E:\\TEST2","localhost",Constants.PORT);
			//dftSearchFiles.addRow(new Object[]{"Hey","Guys","how"});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
