package server;
import com.P2PResponse;
import com.Req;
import com.p2prequest.P2PRequest;

public class ResponseGenerator {
	P2PRequest request;
	ResponseGenerator(P2PRequest request){
		this.request=request;
	}
	public P2PResponse getResponse(){
		P2PResponse presp;
		switch(request.getType()){
		case Req.ALIVE:
			presp=new P2PResponse(request.getReqID(),true);
			presp.setDescription("OK_You_are_Alive");
			break;
		case Req.DOWNLOAD_REQ:
			presp=new P2PResponse(request.getReqID(),true);
			presp.setDescription("Download_Req_will_be handled");
			break;
		case Req.JOIN:
			presp=new P2PResponse(request.getReqID(),true);
			presp.setDescription("OK");
			break;
		case Req.NEW_FILE:
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
