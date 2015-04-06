package peer;

import java.util.TimerTask;

import com.P2PResponse;
import com.TaskResponse;
import com.p2prequest.AliveReq;

class KeepAlive extends TimerTask{
	ServerConnection srv;
	KeepAlive(ServerConnection srv){
		this.srv=srv;
	}
	
	public void run(){
		P2PResponse pr=srv.send(new AliveReq(srv.peerProcess.ownID));
		if(pr.getStatus()==false){
			//Server sent job
			TaskResponse tr=(TaskResponse)pr;
			srv.doTask(tr); //we done it
		}
	}
}
