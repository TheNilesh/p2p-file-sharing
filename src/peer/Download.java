package peer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import com.Constants;
import com.FileInfo;

public class Download implements Runnable {
	DatagramSocket ds;
	int sessionID;
	int progress;
	Thread thread;
	private DownloadManager dm;
	FileInfo f;
	private boolean downloadComplete;
	private byte[] blocks;
	private static final int RECEIVED=11;
	
		Download(FileInfo f,int sessionID, DownloadManager dm) throws SocketException{
			this.dm=dm;
			downloadComplete=false;
			this.f=f;
			this.sessionID=sessionID;
			this.blocks=f.getBlocks();
			ds=new DatagramSocket();
		}
		public void startDownload(){
			thread=new Thread(this);
			thread.start();
		}
		

		public void run(){
			try{
				createNullFile();
				listen();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
		void listen(){
			byte buf[]=new byte[Constants.BLOCK_SIZE + 20];	//18 bytes header
			DatagramPacket p=new DatagramPacket(buf,buf.length);
			try{ 
				while(!downloadComplete)
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
				System.out.println("Received block [" + blockNumber + "] of " + f.getFile().getName());
				if(!checksum.equals(f.getChecksum())){
					System.out.println("Error :checksum does not match.");
				}else{
					if(tmp[0]==1){ 							//type of packet is block
						storeToFile(blockNumber,payload);
						checkIfComplete();
					}else if(tmp[0]==2){						//packet contains code
						
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		void storeToFile(int blockNumber, byte[] payload){
			File file=f.getFile();
			try {
			  RandomAccessFile out=new RandomAccessFile(file,"rw");
			   try {
			       out.seek(blockNumber*Constants.BLOCK_SIZE);
			       out.write(payload);
			       blocks[blockNumber]=RECEIVED;	//mark block received
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
		
		public File createNullFile() throws IOException,FileNotFoundException{
			File file=new File(f.name);
			if(file.exists())
				return file;	//file already available no create
			byte b=0x00; //null
			FileOutputStream fos=new FileOutputStream(file);
			long fileSize=f.getLen();
			for(int i=0;i<fileSize;i++)
				fos.write(b);//write null
			fos.close();
			return file;
		}
		
		public boolean checkIfComplete(){
			for(int i=0;i<blocks.length;i++){
				if(blocks[i]!=RECEIVED){
					System.out.println("!" + i + "!");
					return false;
				}
			}
			downloadComplete=true;
			return true;
		}
public static void main(String args[]) throws SocketException{
	FileInfo tmp=new FileInfo(new File("E:\\TEST1\\aaa.java"));
	tmp.calculateChecksum();
	System.out.println("OLD:" +tmp.getChecksum());
	tmp.setFile(new File("E:\\TEST1\\aaa2.java"));
	Download d =new Download(tmp,123,null);
	System.out.println(d.getPort());
	d.startDownload();
}
}