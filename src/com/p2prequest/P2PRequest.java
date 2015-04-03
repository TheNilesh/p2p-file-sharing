package com.p2prequest;
import java.io.Serializable;
import java.util.Random;

import com.PeerID;

public class P2PRequest implements Serializable {
	private static final long serialVersionUID = -926441096397026064L;
	int type; //to be set by sub classes
	PeerID peerID; //to  be set by this
	int sessionID;
	private final int reqID;
	public String description;
	
	public PeerID getPeer(){
		return peerID;
	}
	
	public int getReqID(){
		return reqID;
	}
	
	public int getType(){
		return type;
	}
	
	public int getSessionID(){
		return sessionID;
	}
	P2PRequest(){
		reqID=(new Random()).nextInt();
		peerID=peer.P2PMain.ownID;
	}
}







