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
	P2PMain(){
		ownID=new PeerID();
		try
		{
			srv=new ServerConnection("localhost",4869);	
		}catch(UnknownHostException ex){
			System.err.println("Provided IP is invalid");
		}
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
	
public static void main(String args[]){
	P2PMain p=new P2PMain();
	P2PResponse re=p.srv.send(new AliveReq());
	if(re!=null){
		System.out.println(re.getStatus());
	}
}
}