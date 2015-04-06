package com.p2prequest;

import com.PeerID;
import com.Req;

public class JoinReq extends P2PRequest{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4911626473678961533L;

	public JoinReq(PeerID p){
		super(p);
		this.type=Req.JOIN;
	}
}
