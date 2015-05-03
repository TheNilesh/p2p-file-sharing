package peer;

import java.io.File;
import java.util.HashSet;

import com.Constants;
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
	public static final long KEEP_ALIVE_TIME=90000;
	
	public P2PMain(String share,String server,int port) throws Exception{
		ownID=new PeerID();
		srv=new ServerConnection(server,port,KEEP_ALIVE_TIME,this);
		fileManager=new FileManager(share,this);
		downloadMgr=new DownloadManager(this);
		srv.open();
	}
	
	public FileInfo[] searchFile(String querry){
		P2PResponse pr =srv.send(new SearchReq(querry,ownID));
		SearchResponse sr =(SearchResponse)pr;
		HashSet<FileInfo> tmp1=sr.getFiles();
		FileInfo[] tmp2=tmp1.toArray(new FileInfo[tmp1.size()]);
		return tmp2;
	}
	
	public boolean downloadFile(String checksum,String localName){
		P2PResponse pr=srv.send(new DownloadReq(checksum,ownID));
		DownloadResponse dr=(DownloadResponse) pr;
		if(dr.getStatus()==true){
			//create new Download
			FileInfo f=dr.getFile();
			File lf=new File(fileManager.getSharedDir() + "\\" +  localName);
			
			if(!lf.exists()){
				f.setFile(lf);
				fileManager.ignoreFile(lf);
				int port=downloadMgr.addDownload(f,dr.getSessionID());
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
	P2PMain p=new P2PMain("E:\\TEST1","localhost",Constants.PORT);
	//P2PMain p2=new P2PMain("D:\\Nilesh\\tt","localhost",4869);

}
}