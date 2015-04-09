package com.p2prequest;

import java.util.HashSet;

import com.PeerID;

public class SearchReq extends P2PRequest {
	private static final long serialVersionUID = 1L;
	HashSet<String> searchTags; //search this tags
	public SearchReq(HashSet<String> searchTags,PeerID p){
		super(p);
		this.searchTags=searchTags;
	}
	
	public HashSet<String> getTags(){
		return searchTags;
	}
}
