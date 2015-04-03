package peer;

import java.io.*;
import java.net.*;

import com.P2PResponse;
import com.p2prequest.P2PRequest;

public class ServerConnection {
private InetAddress srv=null;
private int port;

	ServerConnection(InetAddress ia,int port){
		//Initiate connection with server
		this.srv=ia;
		this.port=port;
	}
	
	ServerConnection(String servIP,int port) throws UnknownHostException{
		this.srv=InetAddress.getByName(servIP);
		this.port=port;
	}
	
	public P2PResponse send(P2PRequest req){
		P2PResponse resp=null;
		try{
				Socket s=new Socket(srv,port);
				ObjectOutputStream obos=new ObjectOutputStream(s.getOutputStream());
				ObjectInputStream obis=new ObjectInputStream(s.getInputStream());
				obos.writeObject(req);
				resp=( P2PResponse )obis.readObject();
				s.close();
		}catch(Exception ex){
			System.err.println("Ex ServerConnection.send : " + ex);
		}
		return resp;
	}
}
