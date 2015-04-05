package peer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import com.FileInfo;
import com.PeerInfo;

public class FileManager{
	private P2PMain mainProcess;
	private HashMap<String,FileInfo> localFiles;
	private File sharedDirectory;
	private WatchDir watcher;
	private Thread watcherThread;
	
	FileManager(String shareDir,P2PMain mainProcess) throws IOException {
		this.mainProcess=mainProcess;
		sharedDirectory=new File(shareDir);
		if(!sharedDirectory.isDirectory()){
			System.out.println("Invalid directory");
			throw new IOException();
		}
		
		watcher=new WatchDir(sharedDirectory.toPath(),false,this);
		Thread watcherThread=new Thread(watcher);
		watcherThread.start();
	}

	public void fileChanged(File f,int fileStatus) {
		System.out.println(f.getName() + " is changed");
		HashSet<FileInfo> tmp=new HashSet<FileInfo>();
		FileInfo fi=new FileInfo(f);
		fi.setStatus(fileStatus);
		tmp.add(fi);
		mainProcess.updateFileDB(tmp);
	}
	
	public void uploadFileBlock(String checksum, byte[]blocks,PeerInfo p){
		File f=localFiles.get(checksum).getFile();
		Uploader u=new Uploader(f,checksum,blocks,p);
	}
}
