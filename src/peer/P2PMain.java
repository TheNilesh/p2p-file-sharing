package peer;

import java.net.UnknownHostException;

import com.P2PResponse;
import com.PeerID;
import com.p2prequest.*;

public class P2PMain {
	public static PeerID ownID;
	private ServerConnection srv;
	P2PMain(){
		ownID=new PeerID();
		try
		{
			srv=new ServerConnection("localhost",4869);	
		}catch(UnknownHostException ex){
			System.err.println("Provided IP is invalid");
		}
	}

	
	public FileInfo[] SearchFile(byte[] checksum){
		srv.send(new SearchReq())
	}
	
	
	
	
public static void main(String args[]){
	P2PMain p=new P2PMain();
	P2PResponse re=p.srv.send(new AliveReq());
	if(re!=null){
		System.out.println(re.getStatus());
	}
}
}