package com;
import java.io.File;

public class FileInfo {
	String name;
	String tags[];
	transient File file;
	long len;
	int pieceCount;
	byte[] checksum=null;
	public FileInfo(File f){
		name=f.getName();
		file=f;
		len=f.length();
	}
	public void calculateChecksum(){
		System.out.println("Not Implemented");
		checksum=new byte[12];
	}
}
