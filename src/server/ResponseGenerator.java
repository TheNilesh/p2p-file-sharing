package server;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.HashSet;

import com.DownloadResponse;
import com.FileInfo;
import com.FileStatus;
import com.P2PResponse;
import com.Req;
import com.SearchResponse;
import com.p2prequest.DownloadReq;
import com.p2prequest.LocalFileReportReq;
import com.p2prequest.P2PRequest;
import com.p2prequest.ReadyReq;
import com.p2prequest.SearchReq;
import com.TaskResponse;

public class ResponseGenerator {
	ServerMain sm;
	HashMap<String,TaskResponse> tasks;
	
	ResponseGenerator(ServerMain sm){
		this.sm=sm;
		tasks=new HashMap<String,TaskResponse>();
	}
	
	public P2PResponse getResponse(P2PRequest request,InetAddress ia){
		P2PResponse presp;
		FileInfo fi=null;
		switch(request.getType()){
		case Req.ALIVE:
			sm.addPeer(request.getPeer());
			String peerName=request.getPeer().nick;
			TaskResponse tr=tasks.remove(peerName);
			if(tr==null){
				presp=new P2PResponse(request.getReqID(),true);
				presp.setDescription("OK_No_Task");
			}else{
				System.out.println("sent task to " + peerName);
				presp=tr; //send task
			}
			break;
		case Req.DOWNLOAD_REQ:
			DownloadReq dr=(DownloadReq)request;
			fi=sm.requestFileDownload(dr.getChecksum());
			if(fi!=null){
				System.out.println(fi.name + " requested hold by " + fi.getSeeders().size());
			}
			presp=new DownloadResponse(request.getReqID(),fi!=null,fi);
			presp.setDescription("Download_Req_will_be handled : ");
			break;
		case Req.JOIN:
			presp=new P2PResponse(request.getReqID(),true);
			presp.setDescription("OK");
			break;
		case Req.FILE_CHANGED:
			LocalFileReportReq r=(LocalFileReportReq)request;
			fi=r.getFirstFile();
			if(fi.getStatus()== FileStatus.NEW){
				sm.addFile(r.getFirstFile(),r.getPeer());
			}else if(fi.getStatus()== FileStatus.DELETE){
				sm.removeSeeder(fi,r.getPeer()); //I am no longer Seeder
			}
			presp=new P2PResponse(request.getReqID(),true);
			presp.setDescription("OK");
			break;
		case Req.READY_TO_DOWNLOAD:
			ReadyReq rr=(ReadyReq)request;
			sm.sendTaskToPeers(rr.getPeer(),ia,rr.getPort(),rr.getChecksum());
			presp=new P2PResponse(request.getReqID(),true);
			presp.setDescription("OK");
			break;
		case Req.SEARCH:
			SearchReq sr=(SearchReq)request;
			System.out.println("Search request:" + sr.getSearchText());
			HashSet<FileInfo> tmp1=sm.searchFiles(sr.getSearchText());
			presp=new SearchResponse(request.getReqID(),!tmp1.isEmpty(),tmp1);
			presp.setDescription("Text Searched and result returned");
			break;
		case Req.UNJOIN:
			presp=new P2PResponse(request.getReqID(),true);
			presp.setDescription("OK");
			break;
		default:
			presp=new P2PResponse(request.getReqID(),false);
			presp.setDescription("Unknown Type of Request");
			break;
		}
		
		return presp;
	}

	void addTaskToSend(String peerName, TaskResponse tr){
		tasks.put(peerName, tr);
	}
}
