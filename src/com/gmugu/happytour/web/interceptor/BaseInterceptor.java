package com.gmugu.happytour.web.interceptor;

import com.gmugu.happyhour.message.result.BaseResult;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by mugu on 16-4-25.
 */
public class BaseInterceptor implements Interceptor {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map<String, Object> session;

    @Override
    public void destroy() {

    }

    @Override
    public void init() {

    }

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        ActionContext invocationContext = actionInvocation.getInvocationContext();
        request = (HttpServletRequest) invocationContext.get(ServletActionContext.HTTP_REQUEST);
        response = (HttpServletResponse) invocationContext.get(ServletActionContext.HTTP_RESPONSE);
        response.setCharacterEncoding("utf-8");
        session = invocationContext.getSession();
        return null;
    }

    protected <T extends BaseResult> void sendResult(T result) {

        String resultStr = new Gson().toJson(result);
        PrintWriter out = null;
        try {
            out = getResponse().getWriter();
            out.print(resultStr);
            System.out.println(getClass().getSimpleName() + ".sendResult---" + resultStr);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }

        }
    }

    public HttpServletRequest getRequest() {
        if (request == null) {
            throw new RuntimeException("You must invoke the super method of intercept()");
        }
        return request;
    }


    public HttpServletResponse getResponse() {
        if (response == null) {
            throw new RuntimeException("You must invoke the super method of intercept()");
        }
        return response;
    }

    public Map<String, Object> getSession() {
        if (session == null) {
            throw new RuntimeException("You must invoke the super method of intercept()");
        }
        return session;
    }


}
