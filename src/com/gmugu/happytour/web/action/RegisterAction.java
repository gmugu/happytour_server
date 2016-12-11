package com.gmugu.happytour.web.action;

import com.gmugu.happyhour.message.UserInfoModel;
import com.gmugu.happyhour.message.request.RegisterRequest;
import com.gmugu.happyhour.message.result.RegisterResult;
import com.gmugu.happytour.comment.assist.Checker;
import com.gmugu.happytour.comment.constant.CookieNames;
import com.gmugu.happytour.comment.constant.ErrorCodes;
import com.gmugu.happytour.comment.constant.SessionKeys;
import com.gmugu.happytour.server.IServer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by mugu on 16-4-18.
 */
public class RegisterAction extends BaseAction {

    private IServer server;

    public String execute() {
        RegisterRequest request = getRequest(RegisterRequest.class);
        RegisterResult result = new RegisterResult();
        try {
            String email = request.getEmail();
            if (!Checker.isEMail(email)) {
                throw new Exception("无效的邮箱地址");
            }
            String passwd = request.getPasswd();
            if (!Checker.isEffectivePasswd(passwd)) {
                throw new Exception("无效的密码");
            }
            String token = request.getToken();
            String stoken = (String) getHttpSession().getAttribute(SessionKeys.CAPTCHA);
            String semail = (String) getHttpSession().getAttribute(SessionKeys.CAPTCHA_USER);
            if (token != null && token.equals(stoken) && email.equals(semail)) {
                UserInfoModel userInfoModel = server.registerByEmail(email, passwd);
                result.setUserId(userInfoModel.getUserId());
                result.setUserInfoModel(userInfoModel);
                getHttpSession().setAttribute(SessionKeys.USER_INFO, userInfoModel);
                HttpServletResponse response = getResponse();
                Cookie userCookie = new Cookie(CookieNames.USERNAME, email);
                userCookie.setMaxAge(2*7*24*60*60);
                Cookie passwdCookie = new Cookie(CookieNames.PASSWD, passwd);
                passwdCookie.setMaxAge(2*7*24*60*60);
                response.addCookie(userCookie);
                response.addCookie(passwdCookie);

            } else {
                throw new Exception("验证码不正确");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            result.setCode(ErrorCodes.FAILE);
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
