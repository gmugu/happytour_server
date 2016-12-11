package com.gmugu.happytour.web.action;

import com.gmugu.happyhour.message.request.GetRegisterCaptchaRequest;
import com.gmugu.happyhour.message.result.GetRegisterCaptchaResult;
import com.gmugu.happytour.comment.assist.Checker;
import com.gmugu.happytour.comment.assist.email.SimpleMailSender;
import com.gmugu.happytour.comment.constant.ErrorCodes;
import com.gmugu.happytour.comment.constant.SessionKeys;
import com.gmugu.happytour.entity.UserinfoEntity;
import com.gmugu.happytour.server.IServer;

import javax.servlet.http.HttpSession;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by mugu on 16-4-21.
 */
public class GetRegisterCaptchaAction extends BaseAction {

    private IServer server;
    private SimpleMailSender simpleMailSender;

    @Override
    public String execute() throws Exception {
        GetRegisterCaptchaRequest request = getRequest(GetRegisterCaptchaRequest.class);
        GetRegisterCaptchaResult result = new GetRegisterCaptchaResult();
        try {
            switch (request.getCaptchaType()) {
                case EMAIL:
                    String email = request.getVerifiable();
                    if (Checker.isEMail(email)) {
                        UserinfoEntity userinfo = server.getUserinfoByEmain(email);
                        if (userinfo != null) {
                            throw new Exception("该邮箱已被注册!");
                        }
                        String captcha = (1000 + (int) (8999 * Math.random())) + "";
                        simpleMailSender.send(email, "邮箱验证", "您正在进行注册,验证码为:  " + captcha + "  有效时间5分钟");
                        HttpSession session = getHttpSession();
                        session.setAttribute(SessionKeys.CAPTCHA, captcha);
                        session.setAttribute(SessionKeys.CAPTCHA_USER, email);
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                session.removeAttribute(SessionKeys.CAPTCHA);
                                System.out.println("CAPTCHA delete!");
                            }
                        }, 5 * 60 * 1000);
                    } else {
                        throw new Exception("邮箱无效");
                    }
                    break;
                case PHONE_NUMBER:
                    //todo
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result.setCode(ErrorCodes.FAILE);
            result.setMessage(e.getMessage());
        }

        sendResult(result);
        return NONE;
    }

    private void sendCaptchaToEamil() {

    }

    public SimpleMailSender getSimpleMailSender() {
        return simpleMailSender;
    }

    public void setSimpleMailSender(SimpleMailSender simpleMailSender) {
        this.simpleMailSender = simpleMailSender;
    }

    public void setServer(IServer server) {
        this.server = server;
    }
}
