package com.gmugu.happytour.web.action;

import com.gmugu.happyhour.message.UserLocationModel;
import com.gmugu.happyhour.message.request.GetAllTeamLocationRequest;
import com.gmugu.happyhour.message.result.GetAllTeamLocationResult;
import com.gmugu.happytour.comment.constant.ErrorCodes;
import com.gmugu.happytour.comment.exception.ServerException;
import com.gmugu.happytour.server.IServer;

import java.util.Map;

/**
 * Created by mugu on 16-4-27.
 */
public class GetAllTeamLocationAction extends BaseAction {

    private IServer server;

    @Override
    public String execute() throws Exception {

        GetAllTeamLocationRequest request = getRequest(GetAllTeamLocationRequest.class);
        GetAllTeamLocationResult result = new GetAllTeamLocationResult();
        try {
            Map<Integer, UserLocationModel> locations = server.getAllTeamLocation(request.getGuideUserId());
            result.setUsersLoactionInfo(locations);
        } catch (ServerException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            result.setCode(ErrorCodes.FAILE);
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
