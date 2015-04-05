package com.p2prequest;

import java.util.HashSet;

public class SearchReq extends P2PRequest {
	private static final long serialVersionUID = 1L;
	HashSet<String> searchTags; //search this tags
	public SearchReq(HashSet<String> searchTags){
		super();
		this.searchTags=searchTags;
	}
}
