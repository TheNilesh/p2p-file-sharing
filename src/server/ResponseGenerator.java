package server;
import com.FileInfo;
import com.FileStatus;
import com.P2PResponse;
import com.Req;
import com.p2prequest.LocalFileReportReq;
import com.p2prequest.P2PRequest;

public class ResponseGenerator {
	P2PRequest request;
	ServerMain sm;
	ResponseGenerator(P2PRequest request,ServerMain sm){
		this.request=request;
		this.sm=sm;
	}
	public P2PResponse getResponse(){
		P2PResponse presp;
		switch(request.getType()){
		case Req.ALIVE:
			sm.addPeer(request.getPeer());
			presp=new P2PResponse(request.getReqID(),true);
			presp.setDescription("OK_You_are_Alive");
			break;
		case Req.DOWNLOAD_REQ:
			System.out.println("Local files:" + sm.filesAvailable.size());
			presp=new P2PResponse(request.getReqID(),true);
			presp.setDescription("Download_Req_will_be handled");
			break;
		case Req.JOIN:
			presp=new P2PResponse(request.getReqID(),true);
			presp.setDescription("OK");
			break;
		case Req.FILE_CHANGED:
			LocalFileReportReq r=(LocalFileReportReq)request;
			FileInfo fi=r.getFirstFile();
			if(fi.getStatus()== FileStatus.NEW){
				sm.addFile(r.getFirstFile(),r.getPeer());
			}else if(fi.getStatus()== FileStatus.DELETE){
				sm.removeSeeder(fi,r.getPeer()); //I am no longer Seeder
			}
			presp=new P2PResponse(request.getReqID(),true);
			presp.setDescription("OK");
			break;
		case Req.READY_TO_DOWNLOAD:
			presp=new P2PResponse(request.getReqID(),true);
			presp.setDescription("OK");
			break;
		case Req.SEARCH:
			presp=new P2PResponse(request.getReqID(),true);
			presp.setDescription("OK");
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

}
