package com;
import java.io.File;
import java.util.HashSet;

public class FileInfo {
	String checksum;
	HashSet<String> tags;
	HashSet<PeerID> hasFile;
	transient File file;
	long len;
	public FileInfo(File f){
		tags=new HashSet<String>();
		tags.add(f.getName());
		file=f;
		len=f.length();
	}
	
	public void calculateChecksum(){ //called by peer
		System.out.println("Not Implemented");
		checksum="Abcd";
	}
	
	public void attachTag(String tag){ //called by server
		String tmp[]=tag.split(" ");
		for(int i=0;i<tmp.length;i++)
			tags.add(tmp[i]);
	}
	
	public boolean addSeeder(PeerID p){
		return hasFile.add(p);
	}
	
	boolean tagMatches(HashSet<String> toSearch){
		HashSet<String> sample=new HashSet<String>(tags);
		sample.retainAll(toSearch);
		return (sample.isEmpty()?false:true);
	}
}

