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
	private HashSet<String> ignored;
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
		ignored=new HashSet<String>();
		
		watcher=new WatchDir(sharedDirectory.toPath(),false,this);
		Thread watcherThread=new Thread(watcher);
		System.out.println("Start Local Scan..");
		readCurrentFiles();
		System.out.println("Local Scan Complete\nStarted Listening new file modification events");
		
		watcherThread.start();
	}

	public void fileChanged(File f,int fileStatus) {
		FileInfo fi=null;
		
		if(ignored.contains(f.getName())) //This file is created to download
			return;
		
		if(fileStatus==FileStatus.NEW){
			fi=new FileInfo(f);
			fi.calculateChecksum();
			fi.addSeeder(mainProcess.ownID);
			localFiles.put(fi.getChecksum(), fi);
			nameToChk.put(f.getName(), fi.getChecksum());
			System.out.println("New file: " + f.getName() + "-" + fi.getChecksum());
		}	
		else if (fileStatus==FileStatus.DELETE){
			fi=localFiles.get( nameToChk.get(f.getName())); //search into name 2 chk then into localFiles  
			localFiles.remove(fi.getChecksum());
			nameToChk.remove(f.getName());
			fi.setStatus(FileStatus.DELETE);
			System.out.println("Deleted file: " + f.getName() + "-" + fi.getChecksum());
		}else if (fileStatus==FileStatus.MODIFY){
			fileChanged(f,FileStatus.DELETE);
			fileChanged(f,FileStatus.NEW);
		}
		mainProcess.updateFileDB(fi);
	}
	
	public void uploadFileBlock(String checksum, byte[]blocks,PeerInfo p){
		File f=localFiles.get(checksum).getFile();
		System.out.println("Upload : " + f.getName() + " to " + p.nick);
		Uploader u=new Uploader(f,checksum,blocks,p);
	}
	
	void readCurrentFiles(){
		File[] f=sharedDirectory.listFiles();
		for(int i=0;i<f.length;i++){
			if(!f[i].isDirectory()){
				fileChanged(f[i],FileStatus.NEW);
			}
		}
	}
	
	public void ignoreFile(File f){
		ignored.add(f.getName());
	}
	
	public void cancelIgnoredFile(File f){
		ignored.remove(f.getName());
		//also call fileChanged, since its new member
		fileChanged(f,FileStatus.NEW);
	}
	
	public String getSharedDir(){
		return sharedDirectory.getPath();
	}
}
