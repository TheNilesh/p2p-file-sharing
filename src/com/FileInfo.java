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
		hasFile=new HashSet<PeerID>();
		tags.add(f.getName());
		file=f;
		len=f.length();
		fileStatus=FileStatus.NEW;
		name=f.getName();
	}
	public String toString(){
		return name;
	}
	public void calculateChecksum(){ //called by peer
			try {
				checksum=new ShowMD5(file).calculateMD5();
			} catch (Exception e) {
				e.printStackTrace();
			}
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
	
	public boolean tagMatches(HashSet<String> toSearch){
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
	public void removeSeeder(PeerID p) {
		hasFile.remove(p);
	}
	
	public HashSet<PeerID> getSeeders(){
		return hasFile;
	}
	
	public long getLen(){
		return len;
	}
	
	public byte[] getBlocks(){ //sparse matrix for blocks o file
		int fileSize=(int)len;
		int BlkCnt=fileSize/Constants.BLOCK_SIZE;
		BlkCnt=BlkCnt+ (fileSize%Constants.BLOCK_SIZE == 0?0:1);
		byte[]blk =new byte[BlkCnt];
		return blk;
	}
}

