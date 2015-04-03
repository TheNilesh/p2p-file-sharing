package peer;

import java.util.TimerTask;

import com.p2prequest.AliveReq;

class KeepAlive extends TimerTask{
	ServerConnection srv;
	KeepAlive(ServerConnection srv){
		this.srv=srv;
	}
	
	public void run(){
		srv.send(new AliveReq());
	}
}
