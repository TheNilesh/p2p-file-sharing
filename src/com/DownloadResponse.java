package com;

public class DownloadResponse extends P2PResponse {
	private static final long serialVersionUID = 1L;
	private FileInfo fileI;
	
	public DownloadResponse(int reqID, boolean status,FileInfo f) {
		super(reqID, status);
		this.fileI=f;
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 */
	public FileInfo getFile(){
		return fileI;
	}

}
