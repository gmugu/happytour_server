package com.gmugu.happytour.web.action;

import com.gmugu.happytour.server.IServer;

/**
 * Created by mugu on 16-4-21.
 */
public class UploadTrackAction extends BaseAction {
    @Override
    public String execute() throws Exception {
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
