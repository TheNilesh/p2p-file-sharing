package com;

import java.net.InetAddress;

public class PeerInfo extends PeerID{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public InetAddress ia;
	public int port=0; 	//port on which UDP
	public PeerInfo(String nick,InetAddress ia,int port){
		this.ia=ia;
		this.port=port;
		this.nick=nick;
	}
}
