package com.gmugu.happytour.web.action;

import com.gmugu.happyhour.message.UserInfoModel;
import com.gmugu.happyhour.message.request.UpdatePasswdRequest;
import com.gmugu.happyhour.message.result.UpdatePasswdResult;
import com.gmugu.happytour.comment.constant.ErrorCodes;
import com.gmugu.happytour.comment.constant.SessionKeys;
import com.gmugu.happytour.comment.exception.ServerException;
import com.gmugu.happytour.server.IServer;

/**
 * Created by mugu on 16-4-21.
 */
public class UpdatePasswdAction extends BaseAction {

    private IServer server;

    private String token;
    private String newPasswd;
    private String msg;

    @Override
    public String execute() throws Exception {
        if (newPasswd != null && token != null) {
            Integer userId = (Integer) getServletContext().getAttribute(token);
            if (userId == null) {
                msg = "Token 不存在或已过期";
                return "fail";
            }
            try {
                server.updatePasswdByUserId(userId, newPasswd);
                getServletContext().removeAttribute(token);
            } catch (ServerException e) {
                System.out.println(e.getMessage());
                msg = e.getMessage();
                return "fail";
            }
            return SUCCESS;
        } else {
            UpdatePasswdRequest request = getRequest(UpdatePasswdRequest.class);
            UpdatePasswdResult result = new UpdatePasswdResult();
            try {
                UserInfoModel userInfo = (UserInfoModel) getHttpSession().getAttribute(SessionKeys.USER_INFO);
                if (userInfo == null) {
                    throw new Exception("您还未登录");
                }
                server.updatePasswdByUserId(userInfo.getUserId(), request.getOldPasswd(), request.getNewPasswd());
            } catch (ServerException e) {
                System.out.println(e.getMessage());
                result.setCode(ErrorCodes.FAILE);
                result.setMessage(e.getMessage());
            }
            sendResult(result);
            return NONE;
        }

    }

    public IServer getServer() {
        return server;
    }

    public void setServer(IServer server) {
        this.server = server;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNewPasswd() {
        return newPasswd;
    }

    public void setNewPasswd(String newPasswd) {
        this.newPasswd = newPasswd;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
