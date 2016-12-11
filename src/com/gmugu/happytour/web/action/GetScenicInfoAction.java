package com.gmugu.happytour.web.action;

import com.gmugu.happyhour.message.ScenicCommentsItemModel;
import com.gmugu.happyhour.message.ScenicModel;
import com.gmugu.happyhour.message.request.GetScenicInfoRequest;
import com.gmugu.happyhour.message.result.GetScenicInfoResult;
import com.gmugu.happytour.comment.constant.ErrorCodes;
import com.gmugu.happytour.comment.exception.ServerException;
import com.gmugu.happytour.server.IServer;

import java.util.List;

/**
 * Created by mugu on 16-5-10.
 */
public class GetScenicInfoAction extends BaseAction {

    private IServer server;

    @Override
    public String execute() throws Exception {
        GetScenicInfoRequest request = getRequest(GetScenicInfoRequest.class);
        GetScenicInfoResult result = new GetScenicInfoResult();

        try {
            ScenicModel scenicInfo = server.findScenicInfoByScenicId(request.getScenicId());
            List<ScenicCommentsItemModel> commentsItemModels = server.findScenicCommentsByScenicId(request.getScenicId());
            result.setScenicModel(scenicInfo);
            result.setScenicCommentsModels(commentsItemModels);
        } catch (ServerException e) {
            e.printStackTrace();
            result.setCode(ErrorCodes.FAILE);
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
