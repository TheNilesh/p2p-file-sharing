package peer;

import java.io.File;
import java.io.IOException;

import com.FileInfo;

public class FileManager{
	private P2PMain mainProcess;
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
	}
	
	public void startMonitor(){
		watcherThread.start();
	}
	
	public void fileChanged(File f,int fileStatus) {
		System.out.println(f.getName() + " is changed");
		int fStat[]=new int[1];
		fStat[0]=fileStatus;
		FileInfo[] fi=new FileInfo[1];
		fi[0]=new FileInfo(f);
		mainProcess.updateFileDB(fi, fStat);
	}
}
