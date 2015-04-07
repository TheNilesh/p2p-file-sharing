package com.p2prequest;

import com.PeerID;
import com.Req;

public class DownloadReq extends P2PRequest{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8962283416152524839L;
	String fileID;
	
	public DownloadReq(String checksum,PeerID p){
		super(p);
		this.type=Req.DOWNLOAD_REQ;
		this.fileID=checksum;
		this.description="request to send file, i.e, ask other peers to send me";
	}
	
	public String getChecksum(){
		return fileID;
	}
}