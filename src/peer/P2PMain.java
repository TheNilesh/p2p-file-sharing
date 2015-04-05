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
	public static PeerID ownID;
	private ServerConnection srv;
	private final FileManager fileManager;
	private final DownloadManager downloadMgr;
	public static final long KEEP_ALIVE_TIME=60000;
	
	P2PMain(String share,String server,int port) throws Exception{
		fileManager=new FileManager(share,this);
		downloadMgr=new DownloadManager(this);
		ownID=new PeerID();
		srv=new ServerConnection(server,port,KEEP_ALIVE_TIME,this);
		srv.open();
	}
	
	public HashSet<FileInfo> SearchFile(HashSet<String> searchTags){
		SearchResponse sr = (SearchResponse) srv.send(new SearchReq(searchTags));
		return sr.getFiles();
	}
	
	public boolean downloadFile(String checksum,String localName){
		P2PResponse pr=srv.send(new DownloadReq(checksum));
		DownloadResponse dr=(DownloadResponse) pr;
		if(dr.getStatus()==true){
			//create new Download
			FileInfo f=dr.getFile();
			File lf=new File(localName);
			
			if(!lf.exists()){
				f.setFile(lf);
				int port=downloadMgr.addDownload(f,dr.getSessionID());
				ReadyReq r = new ReadyReq(pr.getSessionID(),port);
				pr=srv.send(r);
			}
		}
		return pr.getStatus(); //false if file could not be located
	}
	
	public void updateFileDB(HashSet<FileInfo> localFiles){ // if file is modified update to server 
		srv.send(new LocalFileReportReq(localFiles));
	}
	
	public void doUploadBlock(FileInfo fi, byte[]blocks,PeerInfo p){
		fileManager.uploadFileBlock(fi.getChecksum(),blocks,p);
	}
	
public static void main(String args[]) throws Exception{
	P2PMain p=new P2PMain("D:\\Nilesh\\temp","localhost",4869);
	boolean t=p.downloadFile("checkSUM","D:\\Nilesh\\abc.txt");
	System.out.println(t);
}
}