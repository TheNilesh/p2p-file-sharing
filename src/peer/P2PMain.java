package peer;

import java.net.UnknownHostException;

import com.FileInfo;
import com.P2PResponse;
import com.PeerID;
import com.SearchResponse;
import com.p2prequest.*;

public class P2PMain {
	public static PeerID ownID;
	private ServerConnection srv;
	private final FileManager fileManager;
	
	P2PMain(String share,String server,int port) throws Exception{
		fileManager=new FileManager(share,this);
		fileManager.startMonitor();
		ownID=new PeerID();
		srv=new ServerConnection(server,port);
		srv.open();
	}
	
	public FileInfo[] SearchFile(String[] searchTags){
		SearchResponse sr = (SearchResponse) srv.send(new SearchReq(searchTags));
		return sr.getFiles();
	}
	
	public boolean downloadFile(byte[] checksum){
		P2PResponse pr=srv.send(new DownloadReq(checksum)); 
		return pr.getStatus(); //false if file could not be located
	}
	
	public void updateFileDB(FileInfo[] localFiles,int[] whatHappened){ // if file is modified update to server 
		srv.send(new LocalFileReportReq(localFiles,whatHappened));
	}
	
public static void main(String args[]) throws Exception{
	P2PMain p=new P2PMain("D:\\Nilesh\\temp","localhost",4869);
}
}