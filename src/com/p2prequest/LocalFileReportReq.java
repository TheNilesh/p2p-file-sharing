package com.p2prequest;

import com.FileInfo;
import com.Req;

public class LocalFileReportReq extends P2PRequest{
/**
	 * 
	 */
	private static final long serialVersionUID = -3740451455198013360L;
	FileInfo[] localFiles;
	int[] whatHappened;

	public LocalFileReportReq(FileInfo[] fLocal,int[] whatHappened){
		super();
		this.localFiles=fLocal;
		this.type=Req.NEW_FILE;
		this.whatHappened=whatHappened; //File updated=1, file deleted=2, file created=3
		this.description="Our Local files are updated";
	}
}