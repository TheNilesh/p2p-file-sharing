package peer;

import java.io.File;

public class FileManager {
	public void fileChanged(File f,int fileStatus) {
		System.out.println(f.getName() + " is " + fileStatus);
	}
}
