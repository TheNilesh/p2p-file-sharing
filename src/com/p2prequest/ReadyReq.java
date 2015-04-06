package com.p2prequest;

import com.PeerID;
import com.Req;

public class ReadyReq extends P2PRequest{
/**
	 * 
	 */
	private static final long serialVersionUID = -5166127810812001932L;
	int port;
	
	public ReadyReq(int sessionID,int port,PeerID p){
		super(p);
		this.type=Req.READY_TO_DOWNLOAD;
		this.sessionID=sessionID;
		this.port=port;
		this.description="Peer is ready to download file send that to port " + port;
	}
}
