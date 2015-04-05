package com;
import java.io.File;
import java.io.Serializable;
import java.util.HashSet;

public class FileInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String checksum;
	public String name;
	private HashSet<String> tags;
	private HashSet<PeerID> hasFile;
	private transient File file;
	private long len;
	private int fileStatus;
	public FileInfo(File f){
		tags=new HashSet<String>();
		tags.add(f.getName());
		file=f;
		len=f.length();
		fileStatus=0;
		name=f.getName();
	}
	public String toString(){
		return name;
	}
	public void calculateChecksum(){ //called by peer
		System.out.println("Not Implemented");
		checksum="Abcd";
	}
	
	public File getFile(){
			return file;
	}
	
	public void attachTag(String tag){ //called by server
		String tmp[]=tag.split(" ");
		for(int i=0;i<tmp.length;i++)
			tags.add(tmp[i]);
	}
	
	public void setStatus(int status){
		fileStatus=status;
	}
	
	public boolean addSeeder(PeerID p){
		return hasFile.add(p);
	}
	
	boolean tagMatches(HashSet<String> toSearch){
		HashSet<String> sample=new HashSet<String>(tags);
		sample.retainAll(toSearch);
		return (sample.isEmpty()?false:true);
	}
	
	public int getStatus(){
		return fileStatus;
	}
	
	public String getChecksum(){
		return checksum;
	}
	public void setFile(File lf) {
		// TODO Auto-generated method stub
		this.file=lf;
	}
}

