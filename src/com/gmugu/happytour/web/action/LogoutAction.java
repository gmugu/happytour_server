package com.gmugu.happytour.web.action;

import com.gmugu.happyhour.message.result.LoginResult;
import com.gmugu.happytour.comment.constant.ErrorCodes;
import com.gmugu.happytour.comment.constant.SessionKeys;
import com.gmugu.happytour.server.IServer;

/**
 * Created by mugu on 16-4-21.
 */
public class LogoutAction extends BaseAction {
    @Override
    public String execute() throws Exception {
        LoginResult result = new LoginResult();
        try {
            getHttpSession().removeAttribute(SessionKeys.USER_INFO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result.setCode(ErrorCodes.FAILE);
            result.setMessage(e.getMessage());
        }
        sendResult(result);
        return NONE;
    }

    private IServer server;

    public IServer getServer() {
        return server;
    }

    public void setServer(IServer server) {
        this.server = server;
    }
}
