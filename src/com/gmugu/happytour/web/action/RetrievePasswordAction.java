package com.gmugu.happytour.web.action;

import com.gmugu.happyhour.message.request.RetrievePasswordRequest;
import com.gmugu.happyhour.message.result.RetrievePasswordResult;
import com.gmugu.happytour.comment.assist.email.SimpleMailSender;
import com.gmugu.happytour.comment.constant.ErrorCodes;
import com.gmugu.happytour.comment.utils.MD5Util;
import com.gmugu.happytour.entity.UserinfoEntity;
import com.gmugu.happytour.server.IServer;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import javax.servlet.ServletContext;
import javax.servlet.jsp.PageContext;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by mugu on 16-4-21.
 */
public class RetrievePasswordAction extends BaseAction {

    private IServer server;
    private SimpleMailSender simpleMailSender;

    private String token;

    @Override
    public String execute() throws Exception {
        RetrievePasswordRequest request = getRequest(RetrievePasswordRequest.class);
        RetrievePasswordResult result = new RetrievePasswordResult();

        try {
            UserinfoEntity entity = server.getUserinfoByEmain(request.getEmail());
            if (entity == null) {
                throw new Exception("该用户不存在");
            }
            ServletContext servletContext = getServletContext();
            do {
                token = MD5Util.md5ToHexStr(Math.random() + "");
            } while (servletContext.getAttribute(token) != null);

            simpleMailSender.send(request.getEmail(),
                    "找回密码",
                    "点击链接找回密码(有效期5分钟)  <br/>" +
                            "http://localhost:8080/happytour/updatePasswd.jsp?token=" + token);
            servletContext.setAttribute(token, entity.getUserId());
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    servletContext.removeAttribute(token);
                }
            }, 5 * 60 * 1000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result.setCode(ErrorCodes.FAILE);
            result.setMessage(e.getMessage());
        }

        sendResult(result);
        return NONE;
    }

    public void setSimpleMailSender(SimpleMailSender simpleMailSender) {
        this.simpleMailSender = simpleMailSender;
    }

    public SimpleMailSender getSimpleMailSender() {
        return simpleMailSender;
    }

    public IServer getServer() {
        return server;
    }

    public void setServer(IServer server) {
        this.server = server;
    }
}
