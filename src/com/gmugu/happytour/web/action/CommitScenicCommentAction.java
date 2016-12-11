package com.gmugu.happytour.web.action;

import com.gmugu.happyhour.message.UserInfoModel;
import com.gmugu.happyhour.message.request.CommitScenicCommentRequest;
import com.gmugu.happyhour.message.result.CommitScenicCommentResult;
import com.gmugu.happytour.comment.constant.ErrorCodes;
import com.gmugu.happytour.comment.constant.SessionKeys;
import com.gmugu.happytour.comment.exception.ServerException;
import com.gmugu.happytour.server.IServer;

/**
 * Created by mugu on 16-5-11.
 */
public class CommitScenicCommentAction extends BaseAction {
    private IServer server;

    @Override
    public String execute() throws Exception {
        CommitScenicCommentRequest request = getRequest(CommitScenicCommentRequest.class);
        CommitScenicCommentResult result = new CommitScenicCommentResult();

        try {
            UserInfoModel userinfo = (UserInfoModel) getSession().get(SessionKeys.USER_INFO);
            String comment = request.getComment();
            if (comment != null && !comment.equals("")) {
                server.addComment(userinfo.getUserId(), request.getScenicId(), comment, request.getTime());
            }
            server.updateScenicRank(request.getScenicId(), request.getRank());
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
