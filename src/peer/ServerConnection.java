package peer;

import java.io.*;
import java.net.*;
import java.util.Timer;

import com.P2PResponse;
import com.TaskResponse;
import com.p2prequest.P2PRequest;

public class ServerConnection{
private InetAddress srv=null;
private int port;
P2PMain peerProcess;
private Timer timer;
private KeepAlive keepAlive;
long keepAliveInterval;

	ServerConnection(InetAddress ia,int port,long keepAliveInterval,P2PMain p){
		this.srv=ia;
		this.port=port;
		this.peerProcess=p;
		this.keepAliveInterval=keepAliveInterval;
		timer=new Timer();
		keepAlive=new KeepAlive(this); //Object which querries Server @ Regular Interval
	}
	
	ServerConnection(String servIP,int port,long keepAliveInterval,P2PMain p) throws UnknownHostException{
		this(InetAddress.getByName(servIP),port,keepAliveInterval,p);
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
			ex.printStackTrace();
		}
		return resp;
	}
	public void open(){
		timer.schedule(keepAlive,1000,keepAliveInterval);
	}
	public void close(){
		timer.cancel();
	}
	
	void doTask(TaskResponse tr){
		peerProcess.doUploadBlock(tr.getFile(),tr.getBlocks(), tr.getPeer());
	}
}