package com.gmugu.happytour.web.action;

import com.gmugu.happyhour.message.request.DeleteTeamRequest;
import com.gmugu.happyhour.message.result.DeleteTeamResult;
import com.gmugu.happytour.server.IServer;

/**
 * Created by mugu on 16-4-22.
 */
public class DeleteTeamAction extends BaseAction {
    private IServer server;
    @Override
    public String execute() throws Exception {
        DeleteTeamRequest request = getRequest(DeleteTeamRequest.class);
        DeleteTeamResult result = new DeleteTeamResult();

        try {
            Integer teamId = request.getTeamId();
            server.deleteTeamByTeamId(teamId);
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
