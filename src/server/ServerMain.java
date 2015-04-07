package server;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Logger;

import com.Constants;
import com.P2PResponse;
import com.PeerID;
import com.PeerInfo;
import com.TaskResponse;
import com.p2prequest.P2PRequest;
import com.FileInfo;

public class ServerMain{
public static void main(String args[]){
	new ServerMain();
}
	private static final Logger log = Logger.getLogger( ServerMain.class.getName() );
	ConnectionManager connectionManager;
	Thread connMan;
	ResponseGenerator rg;
	HashMap<String,FileInfo> filesAvailable;
	HashMap<String,PeerID> peersAlive;
	
	ServerMain(){
		filesAvailable=new HashMap<String,FileInfo>();
		peersAlive=new HashMap<String,PeerID>();
		
		connectionManager=new ConnectionManager(this, 4869);
		connMan=new Thread(connectionManager);
		connMan.start();
		rg=new ResponseGenerator(this);
	}

	public P2PResponse getResponse(P2PRequest req,InetAddress ia) {
		System.out.println(req.getPeer() + ": " + req.description);
		return rg.getResponse(req, ia);
	}
	
	void addPeer(PeerID p){
		peersAlive.put(p.nick, p);
	}
	
	void addFile(FileInfo f,PeerID p){
		FileInfo fServer=filesAvailable.get(f.getChecksum());
		if(fServer == null){ //file not available on server
			filesAvailable.put(f.getChecksum(), f);
			System.out.println("File added ");
		}else{ //file already available
			fServer.addSeeder(p);
			System.out.println("File already available. Seeder added.");
		}
	}
	
	void removeSeeder(FileInfo f,PeerID p){
		FileInfo fServer=filesAvailable.get(f.getChecksum());
		if(fServer == null){
			//file not available on server
		}else{ //file available
			fServer.removeSeeder(p);
		}
		System.out.println(f.name + " seeder deleted");
	}
	
	FileInfo requestFileDownload(String checksum){
		FileInfo t=filesAvailable.get(checksum);
		return t;
	}

	void sendTaskToPeers(PeerID peer,InetAddress ia, int port, String checksum) {
		System.out.println("Informing others:");
		FileInfo fi=requestFileDownload(checksum);
		PeerInfo pInfo=new PeerInfo(peer.nick,ia,port);
		
		if(fi!=null){
			HashSet<PeerID> peers=fi.getSeeders();
			int peerCnt=peers.size();
			
			System.out.println("Size: " + fi.getLen() + "\nPeers count:" + peerCnt);
			
			int totalBlocks=(int)fi.getLen() / Constants.BLOCK_SIZE;
			int equalTask= totalBlocks/peerCnt; //That means each peer should upload this much blocks
			Iterator<PeerID >itPeers=peers.iterator();
			int j=0;
			while(itPeers.hasNext()){
				byte[] blocks=new byte[totalBlocks]; //blocks.size=TotalBlocks
				PeerID p=itPeers.next();			//fetch peer having file

				for(;j<equalTask;j++)		//create blocks array for p
					blocks[j]=1;
				
				TaskResponse tr=new TaskResponse(fi,pInfo,blocks,123,false);
				System.out.println(p.nick);
				rg.addTaskToSend(p.nick, tr);
			}
			
		}
	}
}
