package com.gmugu.happytour.web.action;

import com.gmugu.happyhour.message.ScenicModel;
import com.gmugu.happyhour.message.request.JoinTeamRequest;
import com.gmugu.happyhour.message.result.JoinTeamResult;
import com.gmugu.happytour.server.IServer;

/**
 * Created by mugu on 16-4-23.
 */
public class JoinTeamAction extends BaseAction {
    private IServer server;

    @Override
    public String execute() throws Exception {
        JoinTeamRequest request = getRequest(JoinTeamRequest.class);
        JoinTeamResult result = new JoinTeamResult();

        try {
            Integer userId = request.getUserId();
            Integer teamId = request.getTeamId();
            ScenicModel model = server.joinTeam(userId, teamId);
            result.setScenicModel(model);
        } catch (Exception e) {
            System.err.println(e.getMessage());
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
