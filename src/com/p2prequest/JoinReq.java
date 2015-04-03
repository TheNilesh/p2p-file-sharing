package com.p2prequest;

import com.Req;

public class JoinReq extends P2PRequest{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4911626473678961533L;

	public JoinReq(){
		super();
		this.type=Req.JOIN;
	}
}
