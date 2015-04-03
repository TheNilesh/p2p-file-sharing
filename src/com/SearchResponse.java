package com;

public class SearchResponse extends P2PResponse{
	private static final long serialVersionUID = 1L;
	FileInfo[] files;
	public FileInfo[] getFiles(){
		return files;
	}
	
	public SearchResponse(int reqID,boolean status,FileInfo[] files){
		super(reqID,status);
		this.files=files;
	}
}
