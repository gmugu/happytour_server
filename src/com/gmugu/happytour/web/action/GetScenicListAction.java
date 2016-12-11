package com.gmugu.happytour.web.action;

import com.gmugu.happyhour.message.ScenicModel;
import com.gmugu.happyhour.message.request.GetScenicListRequest;
import com.gmugu.happyhour.message.result.GetScenicListResult;
import com.gmugu.happytour.comment.exception.ServerException;
import com.gmugu.happytour.server.IServer;

import java.util.List;

/**
 * Created by mugu on 16-4-21.
 */
public class GetScenicListAction extends BaseAction {
    private IServer server;

    @Override
    public String execute() throws Exception {
        GetScenicListRequest request = getRequest(GetScenicListRequest.class);
        GetScenicListResult result = new GetScenicListResult();

        try {
            List<ScenicModel> scenicList = server.getScenicList();
            result.setScenicModels(scenicList);
        } catch (ServerException e) {
            e.printStackTrace();
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
