package com.p2prequest;

import com.PeerID;
import com.Req;

public class AliveReq extends P2PRequest{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5421976118128376200L;

	public AliveReq(PeerID p){
		super(p);
		this.type=Req.ALIVE;
		this.description="I am online. You can send back me work";
	}
}