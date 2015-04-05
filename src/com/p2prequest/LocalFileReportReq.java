package com.p2prequest;

import java.util.HashSet;

import com.FileInfo;
import com.Req;

public class LocalFileReportReq extends P2PRequest{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HashSet<FileInfo> localFiles;
	int[] whatHappened;

	public LocalFileReportReq(HashSet<FileInfo> fLocal){
		super();
		this.localFiles=fLocal;
		this.type=Req.NEW_FILE;
		this.description="Our Local files are updated";
	}

}