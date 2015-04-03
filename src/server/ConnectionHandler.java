package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import com.*;
import com.p2prequest.P2PRequest;

public class ConnectionHandler implements Runnable{
	Socket peerConnection;
	ConnectionHandler(Socket peerConnection){
		this.peerConnection=peerConnection;		
		new Thread(this).start();
	}
	
	public void run(){
		try{
			ObjectOutputStream obos=new ObjectOutputStream(peerConnection.getOutputStream());
			ObjectInputStream obis=new ObjectInputStream(peerConnection.getInputStream());
			P2PRequest req=(P2PRequest)obis.readObject();
			P2PResponse resp;
			resp=new ResponseGenerator(req).getResponse();
			obos.writeObject(resp);
			peerConnection.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
