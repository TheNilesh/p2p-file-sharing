package com.p2prequest;

public class SearchReq extends P2PRequest {
	private static final long serialVersionUID = 1L;
	String searchTags[]; //search this tags
	public SearchReq(String searchTags[]){
		super();
		this.searchTags=searchTags;
	}
}
