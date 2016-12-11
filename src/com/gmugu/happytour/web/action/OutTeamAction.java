package com.gmugu.happytour.web.action;

import com.gmugu.happyhour.message.request.OutTeamRequest;
import com.gmugu.happyhour.message.result.OutTeamResult;
import com.gmugu.happytour.comment.exception.ServerException;
import com.gmugu.happytour.server.IServer;

/**
 * Created by mugu on 16-4-23.
 */
public class OutTeamAction extends BaseAction {
    private IServer server;

    @Override
    public String execute() throws Exception {
        OutTeamRequest request = getRequest(OutTeamRequest.class);
        OutTeamResult result = new OutTeamResult();
        try {
            server.outTeam(request.getUserId(),request.getTeamId());
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(1);
            result.setMessage(e.getMessage());
        }
        sendResult(result);

        return NONE;
    }

    public IServer getServer() {
        return server;
    }

    public void setServer(IServer server) {
        this.server = server;
    }
}
