package com.gmugu.happytour.web.action;

import com.gmugu.happyhour.message.request.CreateTeamRequest;
import com.gmugu.happyhour.message.result.CreateTeamResult;
import com.gmugu.happytour.server.IServer;

/**
 * Created by mugu on 16-4-21.
 */
public class CreateTeamAction extends BaseAction {
    private IServer server;

    @Override
    public String execute() throws Exception {
        CreateTeamRequest request = getRequest(CreateTeamRequest.class);
        CreateTeamResult result = new CreateTeamResult();

        try {
            Integer teamId = server.createTeam(request.getName(), request.getGuideUserId(), request.getScenicId(), request.getScenicRange());
            server.joinTeam(request.getGuideUserId(), teamId);
            result.setTeamId(teamId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            result.setCode(1);
            result.setMessage(e.getMessage());
        }

        sendResult(result);
        return NONE;
    }

    public void setServer(IServer server) {
        this.server = server;
    }

    public IServer getServer() {
        return server;
    }
}
