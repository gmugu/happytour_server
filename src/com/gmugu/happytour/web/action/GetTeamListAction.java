package com.gmugu.happytour.web.action;

import com.gmugu.happyhour.message.TravelTeamModel;
import com.gmugu.happyhour.message.request.GetTeamListRequest;
import com.gmugu.happyhour.message.result.GetTeamListResult;
import com.gmugu.happytour.server.IServer;

import java.util.List;

/**
 * Created by mugu on 16-4-22.
 */
public class GetTeamListAction extends BaseAction {
    private IServer server;

    @Override
    public String execute() throws Exception {
        GetTeamListRequest request = getRequest(GetTeamListRequest.class);
        GetTeamListResult result = new GetTeamListResult();
        try {
            List<TravelTeamModel> teamList = null;
            switch (request.getListType()) {
                case DELETE_LIST:
                    teamList = server.findTeamListByGuideUserId(request.getGuideUserId());
                    break;
                case JOIN_LIST:
                    teamList = server.findTeamListNotContainPassengerUserId(request.getPassengerUserId());
                    break;
                case OUT_LIST:
                    teamList = server.findTeamListByPassengerUserId(request.getPassengerUserId());
                    break;
            }
            result.setTravelTeamModels(teamList);
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
