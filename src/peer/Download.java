package peer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import com.FileInfo;

public class Download implements Runnable {
	DatagramSocket ds;
	int sessionID;
	int progress;
	Thread thread;
	private DownloadManager dm;
	FileInfo f;
	
		Download(FileInfo f,int sessionID, DownloadManager dm) throws SocketException{
			this.dm=dm;
			this.f=f;
			this.sessionID=sessionID;
			ds=new DatagramSocket();
		}
		public void startDownload(){
			thread=new Thread(this);
			thread.start();
		}
		
		public void pauseDownload(){
			
		}
		
		public void run(){
			listen();
		}
		void listen(){
			byte buf[]=new byte[65500];	//max Size for UDP packet without segmentation
			DatagramPacket p=new DatagramPacket(buf,buf.length);
			try{ 
				while(true)
				{
					ds.receive(p);
					byte[] temp=p.getData();	//array length is = buf.length, so we need another smaller array
					byte[] packet= new byte[p.getLength()];
					System.arraycopy(temp,0,packet,0,p.getLength());
					System.out.print("Message[" + p.getLength() + "]: ");	//incoming message
					unmarshal(packet);

				 }//while
		 	}catch(Exception ex){System.out.println(ex);}
		}//listen
		
		void unmarshal(byte[] packet){
			//split header and call storeToFile
			long startLoc = 0,blockLen = 0;
			byte[]payload = null;
			
			storeToFile(startLoc,blockLen,payload);
		}
		
		void storeToFile(long startLoc,long blockLen, byte[] payload){
			//open file and store
			//f.getFile()
			
		}
		
		public int getPort(){
			return ds.getLocalPort();
		}
}