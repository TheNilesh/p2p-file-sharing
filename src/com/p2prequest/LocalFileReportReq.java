package com.p2prequest;

import java.util.HashSet;

import com.FileInfo;
import com.PeerID;
import com.Req;

public class LocalFileReportReq extends P2PRequest{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	FileInfo fi;

	public LocalFileReportReq(FileInfo fLocal,PeerID p){
		super(p);
		this.fi=fLocal;
		this.type=Req.FILE_CHANGED;
		this.description="Our Local files are updated";
	}
	
	public FileInfo getFirstFile(){
		return fi;
	}

}