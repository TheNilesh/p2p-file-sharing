package peer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

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
		this.checksum=checksum.getBytes();
		new Thread(this).start();;
		
	}
	@Override
	public void run() {
		//select block
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
				new DatagramSocket().send(new DatagramPacket(baos.toByteArray(),baos.size(),InetAddress.getByName(p.ip),p.port));
				//	System.out.println("Sent: <" + pieceNumber + "> " + new String(buf,0,t)  );
			}
			fis.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
