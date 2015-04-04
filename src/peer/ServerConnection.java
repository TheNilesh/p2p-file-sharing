package peer;

import java.io.*;
import java.net.*;
import java.util.Timer;

import com.P2PResponse;
import com.p2prequest.P2PRequest;

public class ServerConnection{
private InetAddress srv=null;
private int port;
private Timer timer;
private KeepAlive keepAlive;

	ServerConnection(InetAddress ia,int port){
		this.srv=ia;
		this.port=port;
		timer=new Timer();
		keepAlive=new KeepAlive(this); //Object which querries Server @ Regular Interval
	}
	
	ServerConnection(String servIP,int port) throws UnknownHostException{
		this(InetAddress.getByName(servIP),port);
	}
	
	public synchronized P2PResponse send(P2PRequest req){
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
	public void open(){
		timer.schedule(keepAlive, 10000, 10000);
	}
	public void close(){
		timer.cancel();
	}
}