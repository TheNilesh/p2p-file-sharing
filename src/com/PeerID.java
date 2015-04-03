package com;
import java.io.Serializable;

public class PeerID implements Serializable{
	public byte[] id;		//Unique identifier of this peer, assigned by Server
	public String nick;	//User readable name, choosed by peer and validated by server for duplicates
}