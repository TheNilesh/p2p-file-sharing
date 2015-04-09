package peer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
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
		System.out.println("Sending thread started> Total blocks=" + blocks.length );
		try{
			for(int i=0;i<blocks.length;i++){
				if(blocks[i]==1){
					upload(i);
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	void upload(int blockNumber) throws IOException{
		//open file, read block, send that
		System.out.println("Sent: <" + blockNumber + ">" );
		int t;
		ByteArrayOutputStream baos=new ByteArrayOutputStream(1024);
		baos.write(BLOCK);
		baos.write(checksum);
		baos.write(blockNumber);
		try{
			FileInputStream fis=new FileInputStream(f);
			fis.skip(blockNumber*Constants.BLOCK_SIZE);
			byte[] buf=new byte[Constants.BLOCK_SIZE];
			if((t=fis.read(buf,0,buf.length))!=-1)
			{
				baos.write(buf,0,t);
				new DatagramSocket().send(new DatagramPacket(baos.toByteArray(),baos.size(),p.ia,p.port));
			}
			fis.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
public static void main(String args[]) throws UnknownHostException{
	File tf=new File("E:\\TEST1\\news.mpg");
	int fileSize=(int)tf.length();
	int BlkCnt=fileSize/Constants.BLOCK_SIZE;
	BlkCnt=BlkCnt+ (fileSize%Constants.BLOCK_SIZE == 0?0:1);
	byte[]blk =new byte[BlkCnt];
	System.out.println("Size= " + tf.length() + " bytes. Divider=" + Constants.BLOCK_SIZE + " Total blocks = " + blk.length);
	for(int i=0;i<blk.length;i++){
		blk[i]=1;
	}
	
	int x=57774;
	FileInfo xx=new FileInfo(tf);
	xx.calculateChecksum();
	
	PeerInfo target=new PeerInfo("nilesh",InetAddress.getLocalHost(),x);
	Uploader u=new Uploader(tf,xx.getChecksum(),blk,target);
}
}
