package com.gmugu.happytour.web.interceptor;

import com.gmugu.happyhour.message.UserInfoModel;
import com.gmugu.happyhour.message.result.BaseResult;
import com.gmugu.happytour.comment.constant.CookieNames;
import com.gmugu.happytour.comment.constant.ErrorCodes;
import com.gmugu.happytour.comment.constant.SessionKeys;
import com.gmugu.happytour.comment.exception.ServerException;
import com.gmugu.happytour.server.IServer;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.Interceptor;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mugu on 16-4-25.
 */
public class LoginInterceptor extends BaseInterceptor {

    private IServer server;

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        super.intercept(actionInvocation);
        ActionContext invocationContext = actionInvocation.getInvocationContext();

        UserInfoModel userInfoModel = (UserInfoModel) getSession().get(SessionKeys.USER_INFO);
        if (userInfoModel == null) {//未登录
            HttpServletRequest request = (HttpServletRequest) invocationContext.get(ServletActionContext.HTTP_REQUEST);
            Cookie[] cookies = request.getCookies();
            BaseResult result = new BaseResult();
            if (cookies == null) {
                result.setCode(ErrorCodes.NOT_LOGGED_IN);
                result.setMessage("您还未登录");
            } else {
                String email = null;
                String passwd = null;
                for (Cookie c : cookies) {
                    if (CookieNames.USERNAME.equals(c.getName())) {
                        email = c.getValue();
                    }
                    if (CookieNames.PASSWD.equals(c.getName())) {
                        passwd = c.getValue();
                    }
                }
                if (email != null && passwd != null) {

                    try {
                        userInfoModel = server.loginByEmail(email, passwd);
                        getSession().put(SessionKeys.USER_INFO, userInfoModel);//登录成功
                        return actionInvocation.invoke();
                    } catch (ServerException e) {
                        System.out.println(e.getMessage());
                        result.setCode(ErrorCodes.NOT_LOGGED_IN);
                        result.setMessage("登录验证失败,请重新登录");
                    }
                } else {
                    result.setCode(ErrorCodes.NOT_LOGGED_IN);
                    result.setMessage("您还未登录");
                }
            }
            sendResult(result);
            return ActionSupport.NONE;
        } else {//已登录
            return actionInvocation.invoke();
        }

    }

    public IServer getServer() {
        return server;
    }

    public void setServer(IServer server) {
        this.server = server;
    }
}
