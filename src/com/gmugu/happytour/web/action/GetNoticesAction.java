package com.gmugu.happytour.web.action;

import com.gmugu.happyhour.message.UserInfoModel;
import com.gmugu.happyhour.message.request.GetNoticesRequest;
import com.gmugu.happyhour.message.result.GetNoticesResult;
import com.gmugu.happytour.comment.constant.ErrorCodes;
import com.gmugu.happytour.comment.constant.SessionKeys;
import com.gmugu.happytour.server.IServer;

/**
 * Created by mugu on 16-5-12.
 */
public class GetNoticesAction extends BaseAction {
    private IServer server;

    @Override
    public String execute() throws Exception {

        GetNoticesRequest request = getRequest(GetNoticesRequest.class);
        GetNoticesResult result = new GetNoticesResult();

        try {
            UserInfoModel userinfo = (UserInfoModel) getSession().get(SessionKeys.USER_INFO);
            result.setNoticeItemModels(server.findNotices(userinfo.getUserId()));
        } catch (Exception e) {
            e.printStackTrace();
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
