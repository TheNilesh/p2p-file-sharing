package com.p2prequest;

import com.PeerID;
import com.Req;

public class UnjoinReq extends P2PRequest{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1912611219845899619L;

	UnjoinReq(PeerID p){
		super(p);
		this.type=Req.UNJOIN;
		this.description="I am going offline";
	}
}