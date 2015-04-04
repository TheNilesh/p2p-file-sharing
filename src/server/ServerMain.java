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
		return new ResponseGenerator(req).getResponse();
	}
	
}

/*
import java.io.IOException;
import java.net.*;

public class ServerMain{
	public static void main(String args[]){
		ServerMain sm=new ServerMain(true);
		sm.startServer(4869);
		
	}
	private ServerSocket srv;
	private boolean status; //if false server will stop
	
	ServerMain(boolean status){
		this.status=status;
	}
	void startServer(int port){
		try {
			srv = new ServerSocket(port);
				while(status == true){
					try{
						Socket sck=srv.accept();
						System.out.println("Got connection");
						new ConnectionHandler(sck);
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			srv.close();
			System.err.println("Server stopped");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}//startServer
	
	void stopServer(){
		status=false; //server will quit after serving 1 request
	}
	
}
*/