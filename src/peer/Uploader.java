package peer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.Constants;
import com.FileInfo;
import com.PeerInfo;

public class Uploader implements Runnable {
	private File f;
	private byte blocks[];
	private PeerInfo p;
	private byte[] checksum;
	public static final byte BLOCK=1;
	public static final byte CODE=2;

	Uploader(File f,String checksum, byte[] blocks,PeerInfo p){
		this.f=f;
		this.blocks=blocks;
		this.p=p;
		this.checksum=Constants.hexToBytes(checksum);
		new Thread(this).start();
	}
	@Override
	public void run() {
		//select block
		System.out.println("Sending Blocks: of " + f.getName() + " to " + p + " on " + p.ia.getHostAddress() + ":" + p.port );
		try{
			for(int i=0;i<blocks.length;i++){
				if(blocks[i]==BLOCK){
					uploadData(i);
				}else if(blocks[i]==CODE){
					uploadCode(i);
				}
				
				//wait sometime
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	void uploadCode(int blockNumber) throws IOException{
		//open file, read block, send that
		//System.out.println("Sending code: <" + blockNumber + ">" );
		ByteArrayOutputStream baos=new ByteArrayOutputStream(1024);
		baos.write(CODE);
		baos.write(checksum);
		baos.write(blockNumber);
			byte[] b1=readBlock(blockNumber);
			byte[] b2=readBlock(blockNumber+1); //if b2.length< BLOCK_SIZE then??
			//System.out.println("exoring"+ blockNumber + " and "+ (blockNumber+1));
			byte[] exored=exorBlocks(b1,b2);
		baos.write(exored,0,exored.length);
		sendPacket(baos.toByteArray());
	}
	
	private void sendPacket(byte[] packet){
		DatagramSocket datagramSocket;
		try {
			datagramSocket = new DatagramSocket();
			datagramSocket.send(new DatagramPacket(packet,packet.length,p.ia,p.port));
			datagramSocket.close();
		} catch ( IOException e) {
			e.printStackTrace();
		}
	}
	
	private void uploadData(int blockNumber) throws IOException{
		System.out.print("<" + blockNumber + ">" );
		int t;
		ByteArrayOutputStream baos=new ByteArrayOutputStream(1024);
		baos.write(BLOCK);
		baos.write(checksum);
		baos.write(blockNumber);
		byte[] blk=readBlock(blockNumber);
		baos.write(blk,0,blk.length);
		
		sendPacket(baos.toByteArray());
	}
	
	private byte[] readBlock(int blockNumber) {
		File file=f;
		byte []tmp=null;
		
		try {
		  FileInputStream fis=new FileInputStream(file);
		  tmp=new byte[Constants.BLOCK_SIZE];
		   try {
			   fis.skip(blockNumber*Constants.BLOCK_SIZE);
		       int arrSize=fis.read(tmp);
		       
		       if(arrSize<Constants.BLOCK_SIZE){
		    	   if(arrSize==-1){ //read failed
		    		   fis.close();
		    		   return null;
		    	   }
		    	   byte[] tmp2=new byte[arrSize];
		    	   System.arraycopy(tmp, 0, tmp2, 0, tmp2.length);
		    	   tmp=tmp2; //shrink array
		       }
		       
		   } finally {
		      fis.close();
		   }
		} catch (IOException ex) {
		   	ex.printStackTrace();
		}
		return tmp;
	}
	
	private byte[] exorBlocks(byte[] b1, byte[] b2) {
		byte[] exored=new byte[b1.length];
		
		for(int i=0;i<b1.length;i++){ //exoring
			exored[i]=(byte) ((int) b1[i]^(int)b2[i]);
		}
		
		return exored;
	}
	
public static void main(String args[]) throws UnknownHostException{
	File tf=new File("E:\\TEST1\\a.pdf");
	int fileSize=(int)tf.length();
	int BlkCnt=fileSize/Constants.BLOCK_SIZE;
	BlkCnt=BlkCnt+ (fileSize%Constants.BLOCK_SIZE == 0?0:1);
	byte[]blk =new byte[BlkCnt];
	System.out.println("Size= " + tf.length() + " bytes. Divider=" + Constants.BLOCK_SIZE + " Total blocks = " + blk.length);
	//for(int i=0;i<blk.length;i++){
	//	blk[i]=1;
	//}
	blk[0]=0;
	blk[1]=1;
	blk[2]=0;
	
	int x=65198;
	FileInfo xx=new FileInfo(tf);
	xx.calculateChecksum();
	
	PeerInfo target=new PeerInfo("nilesh",InetAddress.getLocalHost(),x);
	Uploader u=new Uploader(tf,xx.getChecksum(),blk,target);
}
}
