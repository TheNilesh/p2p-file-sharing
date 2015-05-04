package com;
import java.io.Serializable;
import java.util.Random;

public class PeerID implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public byte[] id;		//Unique identifier of this peer, assigned by Server
	public String nick;	//User readable name, choosed by peer and validated by server for duplicates
	public String toString(){
		return nick;
	}
	public PeerID(){
		Random r=new Random();
		String s="Peer"  + r.nextInt(50);
		nick=s;
	}
	public PeerID(String name){
		this.nick=name;
	}
}