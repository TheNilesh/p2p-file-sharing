package com;

import java.util.HashMap;
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<String,PeerID> peers=new HashMap<String,PeerID>();
		peers.put("nil",new PeerID("nil"));
		peers.put("ni0l",new PeerID("ni0l"));
		System.out.print(peers.size());
	}

}
