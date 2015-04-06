package server;

import java.util.HashMap;

import com.P2PResponse;
import com.PeerID;
import com.p2prequest.P2PRequest;
import com.FileInfo;

public class ServerMain{
public static void main(String args[]){
	new ServerMain();
}
	ConnectionManager connectionManager;
	Thread connMan;
	HashMap<String,FileInfo> filesAvailable;
	HashMap<String,PeerID> peersAlive;
	
	ServerMain(){
		filesAvailable=new HashMap<String,FileInfo>();
		peersAlive=new HashMap<String,PeerID>();
		
		connectionManager=new ConnectionManager(this, 4869);
		connMan=new Thread(connectionManager);
		connMan.start();
	}

	public P2PResponse getResponse(P2PRequest req) {
		System.out.println(req.getPeer() + ": " + req.description);
		return new ResponseGenerator(req,this).getResponse();
	}
	
	void addPeer(PeerID p){
		peersAlive.put(p.nick, p);
	}
	
	void addFile(FileInfo f,PeerID p){
		FileInfo fServer=filesAvailable.get(f.getChecksum());
		if(fServer == null){ //file not available on server
			filesAvailable.put(f.getChecksum(), f);
		}else{ //file already available
			fServer.addSeeder(p);
		}
		System.out.println(f.name + " added");
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
}
