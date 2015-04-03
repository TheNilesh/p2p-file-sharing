package com;
import java.io.Serializable;

public class P2PResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	int reqID;	//ID of req for which this is response
	boolean status;
	String description;
	
	public P2PResponse(int reqID,boolean status){
		this.reqID=reqID;
		this.status=status;
	}
	
	public void setDescription(String desc){
		this.description=desc;
	}
	
	public String getDescription(){
		return description;
	}
	
	public boolean getStatus(){
		return status;
	}
}