package server;

import com.P2PResponse;
import com.p2prequest.P2PRequest;


public class ServerMain{
public static void main(String args[]){
	new ServerMain();
}
	ConnectionManager connectionManager;
	Thread connMan;
	
	ServerMain(){
		connectionManager=new ConnectionManager(this, 4869);
		connMan=new Thread(connectionManager);
		connMan.start();
	}

	public P2PResponse getResponse(P2PRequest req) {
		System.out.println(req.getPeer() + ": " + req.description);
		return new ResponseGenerator(req).getResponse();
	}
	
}
