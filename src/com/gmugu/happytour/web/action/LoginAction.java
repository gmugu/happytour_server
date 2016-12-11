package com.gmugu.happytour.web.action;

import com.gmugu.happyhour.message.UserInfoModel;
import com.gmugu.happyhour.message.request.LoginRequest;
import com.gmugu.happyhour.message.result.LoginResult;
import com.gmugu.happytour.comment.constant.CookieNames;
import com.gmugu.happytour.comment.constant.SessionKeys;
import com.gmugu.happytour.comment.exception.ServerException;
import com.gmugu.happytour.server.IServer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by mugu on 16-4-18.
 */
public class LoginAction extends BaseAction {

    private IServer server;

    public IServer getServer() {
        return server;
    }

    public void setServer(IServer server) {
        this.server = server;
    }

    public String execute() {

        LoginRequest request = getRequest(LoginRequest.class);
        LoginResult result = new LoginResult();

        try {
            String email = request.getEmail();
            String passwd = request.getPasswd();
            UserInfoModel userInfoModel = server.loginByEmail(email, passwd);
            getHttpSession().setAttribute(SessionKeys.USER_INFO, userInfoModel);
            result.setUserInfoModel(userInfoModel);
            HttpServletResponse response = getResponse();

            Cookie userCookie = new Cookie(CookieNames.USERNAME, email);
            userCookie.setMaxAge(2 * 7 * 24 * 60 * 60);
            Cookie passwdCookie = new Cookie(CookieNames.PASSWD, passwd);
            passwdCookie.setMaxAge(2 * 7 * 24 * 60 * 60);
            response.addCookie(userCookie);
            response.addCookie(passwdCookie);

        } catch (ServerException e) {
            System.out.println(e.getMessage());
            result.setCode(1);
            result.setMessage(e.getMessage());
        }

        sendResult(result);
        return NONE;
    }

    private String username;
    private String password;
    private String remember;
    private String msg;

    public String loginOnWeb() {
        try {
            UserInfoModel userInfoModel = server.loginByEmail(username, password);
            if (!userInfoModel.getUserType().equals(UserInfoModel.UserType.ADMIN)) {
                throw new Exception("您不是管理员!");
            }
            getHttpSession().setAttribute(SessionKeys.USER_INFO, userInfoModel);
            if ("on".equals(remember)) {
                HttpServletResponse response = getResponse();
                Cookie userCookie = new Cookie(CookieNames.USERNAME, username);
                userCookie.setMaxAge(2 * 7 * 24 * 60 * 60);
                Cookie passwdCookie = new Cookie(CookieNames.PASSWD, password);
                passwdCookie.setMaxAge(2 * 7 * 24 * 60 * 60);
                response.addCookie(userCookie);
                response.addCookie(passwdCookie);
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = e.getMessage();
            return LOGIN;
        }
        return SUCCESS;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemember() {
        return remember;
    }

    public void setRemember(String remember) {
        this.remember = remember;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
