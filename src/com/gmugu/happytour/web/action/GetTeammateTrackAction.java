package com.gmugu.happytour.web.action;

import com.gmugu.happyhour.message.UserInfoModel;
import com.gmugu.happyhour.message.request.GetTeammateTrackRequest;
import com.gmugu.happyhour.message.result.GetTeammateTrackResult;
import com.gmugu.happytour.comment.constant.ErrorCodes;
import com.gmugu.happytour.comment.constant.SessionKeys;
import com.gmugu.happytour.server.IServer;

import java.util.Map;

/**
 * Created by mugu on 16-5-1.
 */
public class GetTeammateTrackAction extends BaseAction {
    private IServer server;

    @Override
    public String execute() throws Exception {
        GetTeammateTrackRequest request = getRequest(GetTeammateTrackRequest.class);
        GetTeammateTrackResult result = new GetTeammateTrackResult();

        try {
            Map<String, Object> session = getSession();
            UserInfoModel userInfoModel= (UserInfoModel) session.get(SessionKeys.USER_INFO);
            result.setTrackModelMap(server.getTeammateTrack(userInfoModel));

        } catch (Exception e) {
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
