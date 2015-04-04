package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionManager implements Runnable{
	ServerSocket srv;
	int port;
	ServerMain sm;
	
	ConnectionManager(ServerMain sm,int port){
		this.sm=sm;
		this.port=port;
	}
	
	public void run(){
		startServer();
	}
	
	void startServer(){
		try {
			srv = new ServerSocket(port);
				while(true){
					try{
						Socket sck=srv.accept();
						//System.out.println("Got connection");
						new Thread(new PeerConnection(sck,sm)).start();
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
