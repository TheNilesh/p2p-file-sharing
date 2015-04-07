package com;
import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class ShowMD5{
	File f;
	public static void main(String args[]) throws Exception{
		ShowMD5 a=new ShowMD5(new File("C:\\IRST.log"));
		System.out.println("Hash = " + a.calculateMD5());
	}
	
	public ShowMD5(File f){
		this.f=f;
	}
	
	public String calculateMD5() throws Exception{
		System.out.println("Started digest");
		String strMD5="";
		FileInputStream fis=new FileInputStream(f);
		byte data[]=new byte[1024];
			//org.apache.commons.codec.digest.DigestUtils.md5(fis);
		MessageDigest complete=MessageDigest.getInstance("MD5");
		int numRead;
		do{
			numRead=fis.read(data);
			if(numRead>0)
				complete.update(data,0,numRead);
		}while(numRead!=-1);
		fis.close();
		
		byte[] md5=complete.digest();
		for(int i=0;i<md5.length;i++){
			String str=Integer.toString((md5[i] & 0xff) + 0x100,16).substring(1);
			//System.out.print(str);
			strMD5=strMD5.concat(str);
		}
		System.out.println("Digest completed.");
		return strMD5;
	}
}
