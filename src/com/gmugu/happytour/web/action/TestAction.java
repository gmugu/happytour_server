package com.gmugu.happytour.web.action;

import com.gmugu.happytour.server.IServer;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Created by mugu on 16-6-7.
 */
public class TestAction extends ActionSupport {

    private IServer server;
    @Override
    public String execute() throws Exception {
        server.test();
        return NONE;
    }

    public IServer getServer() {
        return server;
    }

    public void setServer(IServer server) {
        this.server = server;
    }
}
