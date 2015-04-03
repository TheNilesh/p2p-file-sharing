package com.p2prequest;

import com.Req;

public class DownloadReq extends P2PRequest{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8962283416152524839L;
	byte[] fileID;
	
	public DownloadReq(byte[] fileID){
		super();
		this.type=Req.DOWNLOAD_REQ;
		this.fileID=fileID;
		this.description="request to send file, i.e, ask other peers to send me";
	}
}