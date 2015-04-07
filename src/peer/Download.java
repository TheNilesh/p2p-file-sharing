package peer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import com.Constants;
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
		

		public void run(){
			listen();
		}
		
		void listen(){
			byte buf[]=new byte[600];	//max Size for UDP packet without fragmentation
			DatagramPacket p=new DatagramPacket(buf,buf.length);
			try{ 
				while(true)
				{
					ds.receive(p);
					byte[] temp=p.getData();	//array length is = buf.length, so we need another smaller array
					byte[] packet= new byte[p.getLength()];
					System.arraycopy(temp,0,packet,0,p.getLength());
					System.out.println("Message[" + p.getLength() + "]: ");	//incoming message
					unmarshal(packet);

				 }//while
		 	}catch(Exception ex){System.out.println(ex);}
		}//listen
		
		void unmarshal(byte[] packet){
			//split header and call storeToFile
			byte[]payload = null;
			String checksum;
			int blockNumber;
			byte[] tmp=new byte[1];
			byte[] chk=new byte[16];
			ByteArrayInputStream bis=new ByteArrayInputStream(packet);
			
			try {
				bis.read(tmp); //what to do with block?
				bis.read(chk); //checksum
				checksum=Constants.bytesToHex(chk);//convert checksum back into String
				blockNumber=bis.read();	//Which location
				payload=new byte[bis.available()];
				bis.read(payload); //read data
				System.out.println("got block :" + blockNumber + " of " + f.getFile().getName());
				if(!checksum.equals(f.getChecksum())){
					System.out.println("Error :checksum does not match.");
				}else{
					if(tmp[0]==1){ 							//type of packet is block
						storeToFile(blockNumber,payload);
					}else if(tmp[0]==2){						//packet contains code
						
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		void storeToFile(int blockNumber, byte[] payload){
			//open file and store
			File file=f.getFile();
			try {
			   FileOutputStream out = new FileOutputStream(file);
			   try {
			       FileChannel ch = out.getChannel();
			       ch.position(blockNumber*Constants.BLOCK_SIZE);
			       ch.write(ByteBuffer.wrap(payload));
			   } finally {
			       out.close();
			   } 
			} catch (IOException ex) {
			   	ex.printStackTrace();
			}
		}
		
		public int getPort(){
			return ds.getLocalPort();
		}
}