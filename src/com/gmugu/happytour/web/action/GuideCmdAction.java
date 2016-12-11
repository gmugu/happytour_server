package com.gmugu.happytour.web.action;

import com.gmugu.happyhour.message.ScenicModel;
import com.gmugu.happyhour.message.request.GuideCmdRequest;
import com.gmugu.happyhour.message.result.GuideCmdResult;
import com.gmugu.happytour.server.IServer;

/**
 * Created by mugu on 16-4-23.
 */
public class GuideCmdAction extends BaseAction {

    private IServer server;

    @Override
    public String execute() throws Exception {
        GuideCmdRequest request = getRequest(GuideCmdRequest.class);
        GuideCmdResult result = new GuideCmdResult();

        try {
            switch (request.getCmdType()) {
                case START_TOUR:
                    ScenicModel model = server.startGuide(request.getGuideUserId());
                    result.setScenicModel(model);
                    break;
                case STOP_TOUR:
                    server.stopGuide(request.getGuideUserId());
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result.setCode(1);
            result.setMessage(e.getMessage());
        }
        sendResult(result);

        return NONE;
    }

    public IServer getServer() {
        return server;
    }

    public void setServer(IServer server) {
        this.server = server;
    }
}
