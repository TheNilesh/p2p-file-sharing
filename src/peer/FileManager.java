package peer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import com.FileInfo;
import com.FileStatus;
import com.PeerInfo;

public class FileManager{
	private P2PMain mainProcess;
	private HashMap<String,FileInfo> localFiles;
	private HashMap<String,String>	nameToChk; 
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
		localFiles=new  HashMap<String,FileInfo>();
		nameToChk=new HashMap<String,String>();
		
		watcher=new WatchDir(sharedDirectory.toPath(),false,this);
		Thread watcherThread=new Thread(watcher);
		watcherThread.start();
	}

	public void fileChanged(File f,int fileStatus) {
		System.out.println(f.getName() + " is changed");
		
		FileInfo fi=null;
		
		if(fileStatus==FileStatus.NEW){
			fi=new FileInfo(f);
			fi.calculateChecksum();
			fi.addSeeder(mainProcess.ownID);
			localFiles.put(fi.getChecksum(), fi);
			nameToChk.put(f.getName(), fi.getChecksum());
		}	
		else if (fileStatus==FileStatus.DELETE){
			fi=localFiles.get( nameToChk.get(f.getName())); //search into name 2 chk then into localFiles  
			localFiles.remove(fi.getChecksum());
			nameToChk.remove(f.getName());
			fi.setStatus(FileStatus.DELETE);
			System.out.println(fi.getStatus());
		}
		mainProcess.updateFileDB(fi);
	}
	
	public void uploadFileBlock(String checksum, byte[]blocks,PeerInfo p){
		File f=localFiles.get(checksum).getFile();
		Uploader u=new Uploader(f,checksum,blocks,p);
	}
}
