package com.p2prequest;

import com.PeerID;
import com.Req;

public class SearchReq extends P2PRequest {
	private static final long serialVersionUID = 1L;
	private String searchText; //search this tags
	public SearchReq(String searchTags,PeerID p){
		super(p);
		this.type=Req.SEARCH;
		this.searchText=searchTags;
		this.description="SEARCH : " + searchText;
	}
	
	public String getSearchText(){
		return searchText;
	}
}
