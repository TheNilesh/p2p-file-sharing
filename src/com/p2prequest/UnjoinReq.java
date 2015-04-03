package com.p2prequest;

import com.Req;

public class UnjoinReq extends P2PRequest{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1912611219845899619L;

	UnjoinReq(){
		super();
		this.type=Req.UNJOIN;
		this.description="I am going offline";
	}
}