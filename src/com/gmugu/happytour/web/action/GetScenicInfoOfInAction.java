package com.gmugu.happytour.web.action;

import com.gmugu.happyhour.message.ScenicModel;
import com.gmugu.happyhour.message.UserInfoModel;
import com.gmugu.happyhour.message.request.GetScenicInfoOfInRequest;
import com.gmugu.happyhour.message.result.GetScenicInfoOfInResult;
import com.gmugu.happytour.comment.constant.ErrorCodes;
import com.gmugu.happytour.comment.constant.SessionKeys;
import com.gmugu.happytour.comment.exception.ServerException;
import com.gmugu.happytour.server.IServer;

/**
 * Created by mugu on 16-4-29.
 */
public class GetScenicInfoOfInAction extends BaseAction {
    private IServer server;

    @Override
    public String execute() throws Exception {
        GetScenicInfoOfInRequest request = getRequest(GetScenicInfoOfInRequest.class);
        GetScenicInfoOfInResult result = new GetScenicInfoOfInResult();

        try {
            UserInfoModel userinfo = (UserInfoModel) getHttpSession().getAttribute(SessionKeys.USER_INFO);
            ScenicModel scenicInfoById = server.findScenicInfoByUserId(userinfo.getUserId());
            result.setScenicModel(scenicInfoById);
        } catch (ServerException e) {
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
