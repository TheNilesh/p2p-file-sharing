package com;

import java.util.HashSet;

public class SearchResponse extends P2PResponse{
	private static final long serialVersionUID = 1L;
	HashSet<FileInfo> files;
	public HashSet<FileInfo> getFiles(){
		return files;
	}
	
	public SearchResponse(int reqID,boolean status,HashSet<FileInfo> files){
		super(reqID,status);
		this.files=files;
	}
}
