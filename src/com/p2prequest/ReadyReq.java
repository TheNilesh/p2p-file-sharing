package com.p2prequest;

import com.PeerID;
import com.Req;

public class ReadyReq extends P2PRequest{
/**
	 * 
	 */
	private static final long serialVersionUID = -5166127810812001932L;
	int port;
	private String checksum;
	
	public ReadyReq(int sessionID,int port,PeerID p,String checksum){
		super(p);
		this.type=Req.READY_TO_DOWNLOAD;
		this.sessionID=sessionID;
		this.port=port;
		this.checksum=checksum;
		this.description="READY_DOWNLOAD:" + port;
	}
	public int getPort(){
		return port;
	}
	public String getChecksum(){
		return checksum;
	}
}
