package peer;


import com.FileInfo;

import java.io.File;
import java.net.SocketException;
import java.util.HashMap;

public class DownloadManager {
	HashMap<String,Download> list;
	P2PMain proc;
	DownloadManager(P2PMain proc){
		this.proc=proc;
		list=new HashMap<String,Download>();
	}
	
	int addDownload(FileInfo f,int sessionID){
		try{
				Download d=new Download(f,sessionID,this);
				list.put(f.getChecksum(), d);
				d.startDownload();
				return d.getPort();
			}catch(SocketException e){
				e.printStackTrace();
			return 0;
		}
	}

	public void setComplete(Download d, boolean b) {
		System.out.println("Download Complete :" + d.f.name + " Success?:" + b);
		if(b==true){
			proc.downloadComplete(d.f);
		}
	}
	
	
}
