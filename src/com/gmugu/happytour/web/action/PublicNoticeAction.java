package com.gmugu.happytour.web.action;

import com.gmugu.happyhour.message.NoticeItemModel;
import com.gmugu.happyhour.message.UserInfoModel;
import com.gmugu.happyhour.message.request.PublicNoticeRequest;
import com.gmugu.happyhour.message.result.PublicNoticeResult;
import com.gmugu.happytour.comment.constant.ErrorCodes;
import com.gmugu.happytour.comment.constant.SessionKeys;
import com.gmugu.happytour.server.IServer;

/**
 * Created by mugu on 16-5-12.
 */
public class PublicNoticeAction extends BaseAction {
    private IServer server;

    @Override
    public String execute() throws Exception {
        PublicNoticeRequest request = getRequest(PublicNoticeRequest.class);
        PublicNoticeResult result = new PublicNoticeResult();

        try {
            UserInfoModel model= (UserInfoModel) getSession().get(SessionKeys.USER_INFO);
            NoticeItemModel noticeItemModel = request.getNoticeItemModel();
            if (noticeItemModel == null) {
                throw new Exception("公告内容为空");
            }
            server.publicNotice(model.getUserId(), noticeItemModel);
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
