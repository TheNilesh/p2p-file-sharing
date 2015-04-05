package com;

import java.util.HashSet;


public class TaskResponse extends P2PResponse {
//Using this response server can allocate job to Peer, such as file Upload, fileEncode, calculate bandwidth
	/**
	 * 
	 */
	public static final int UPLOAD_BLOCK=1;
	public static final int UPLOAD_ENCODE=2;
	private static final long serialVersionUID = 1L;
	private FileInfo fi;
	private PeerInfo peer;
	private byte blocks[]; //block[i] = 1 send this block, =0, dont send this block
	
	TaskResponse(FileInfo fi,PeerInfo p,byte[] blocks, int reqID, boolean status){
		super(reqID,status);
		this.fi=fi;
		this.peer=p;
		this.blocks=blocks;
	}
	
	public byte[] getBlocks(){
		return this.blocks;
	}

	public FileInfo getFile() {
		// TODO Auto-generated method stub
		return fi;
	}

	public PeerInfo getPeer() {
		// TODO Auto-generated method stub
		return peer;
	}
}
