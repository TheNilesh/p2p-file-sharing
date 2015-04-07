package peer;

import java.io.File;
import java.util.HashSet;

import com.DownloadResponse;
import com.FileInfo;
import com.P2PResponse;
import com.PeerID;
import com.PeerInfo;
import com.SearchResponse;
import com.p2prequest.*;

public class P2PMain {
	public PeerID ownID;
	private ServerConnection srv;
	private final FileManager fileManager;
	private final DownloadManager downloadMgr;
	public static final long KEEP_ALIVE_TIME=60000;
	
	P2PMain(String share,String server,int port) throws Exception{
		ownID=new PeerID();
		srv=new ServerConnection(server,port,KEEP_ALIVE_TIME,this);
		fileManager=new FileManager(share,this);
		downloadMgr=new DownloadManager(this);
		srv.open();
	}
	
	public HashSet<FileInfo> SearchFile(HashSet<String> searchTags){
		SearchResponse sr = (SearchResponse) srv.send(new SearchReq(searchTags,ownID));
		return sr.getFiles();
	}
	
	public boolean downloadFile(String checksum,String localName){
		P2PResponse pr=srv.send(new DownloadReq(checksum,ownID));
		DownloadResponse dr=(DownloadResponse) pr;
		if(dr.getStatus()==true){
			//create new Download
			FileInfo f=dr.getFile();
			File lf=new File(localName);
			
			if(!lf.exists()){
				f.setFile(lf);
				int port=downloadMgr.addDownload(f,dr.getSessionID());
				System.out.println("Listening on port :" + port);
				ReadyReq r = new ReadyReq(pr.getSessionID(),port,ownID,checksum);
				pr=srv.send(r);
			}
		}
		return pr.getStatus(); //false if file could not be located
	}
	
	public void updateFileDB(FileInfo fi){ // if file is modified update to server 
		srv.send(new LocalFileReportReq(fi,ownID));
	}
	
	public void doUploadBlock(FileInfo fi, byte[]blocks,PeerInfo p){
		fileManager.uploadFileBlock(fi.getChecksum(),blocks,p);
	}
	
public static void main(String args[]) throws Exception{
	P2PMain p=new P2PMain("D:\\Nilesh\\temp","localhost",4869);
	P2PMain p2=new P2PMain("D:\\Nilesh\\tt","localhost",4869);
	boolean t=p2.downloadFile("8199b905043eac27b18739646024a87a","D:\\Nilesh\\abcd.txt");
}
}