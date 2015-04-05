package peer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.FileInfo;
import com.PeerInfo;

public class Uploader implements Runnable {
	private File f;
	private byte blocks[];
	private PeerInfo p;
	private String checksum;
	
	public static final int BLOCK_SIZE=65500;
	Uploader(File f,String checksum, byte[] blocks,PeerInfo p){
		this.f=f;
		this.blocks=blocks;
		this.p=p;
		new Thread(this).start();;
		
	}
	@Override
	public void run() {
		//open file get block
		for(int i=0;i<blocks.length;i++){
			if(blocks[i]==1){
				upload(i);
			}
		}
		
	}
	
	void upload(int blockNumber){
		//open file, read block, send that
		int t;
		ByteArrayOutputStream baos=new ByteArrayOutputStream(1024);
	//	baos.write(0x1B);
	//	baos.write(fi.checksum);
	//	baos.write(pieceNumber);
		try{
			FileInputStream fis=new FileInputStream(f);
			fis.skip(blockNumber*BLOCK_SIZE);
			byte[] buf=new byte[(int)BLOCK_SIZE*2];
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
