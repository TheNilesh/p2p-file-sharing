package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import com.*;
import com.p2prequest.P2PRequest;

public class PeerConnection implements Runnable{
	Socket peerConnection;
	ServerMain sm;
	PeerConnection(Socket peerConnection,ServerMain sm){
		this.peerConnection=peerConnection;
		this.sm=sm;
	}
	
	public void run(){
		try{
			ObjectOutputStream obos=new ObjectOutputStream(peerConnection.getOutputStream());
			ObjectInputStream obis=new ObjectInputStream(peerConnection.getInputStream());
			P2PRequest req=(P2PRequest)obis.readObject();
			P2PResponse resp;
			resp=sm.getResponse(req,peerConnection.getInetAddress());
			obos.writeObject(resp);
			peerConnection.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
