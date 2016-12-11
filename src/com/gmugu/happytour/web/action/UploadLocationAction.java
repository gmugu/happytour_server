package com.gmugu.happytour.web.action;

import com.gmugu.happyhour.message.TrackModel;
import com.gmugu.happyhour.message.UserInfoModel;
import com.gmugu.happyhour.message.request.UploadLocationRequest;
import com.gmugu.happyhour.message.result.UploadLocationResult;
import com.gmugu.happytour.comment.constant.SessionKeys;
import com.gmugu.happytour.server.IServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mugu on 16-4-23.
 */
public class UploadLocationAction extends BaseAction {
    private IServer server;

    @Override
    public String execute() throws Exception {

        UploadLocationRequest request = getRequest(UploadLocationRequest.class);
        UploadLocationResult result = new UploadLocationResult();

        try {

            Map<String, Object> session=getSession();
            UserInfoModel userInfo = (UserInfoModel) getSession().get(SessionKeys.USER_INFO);
            server.updateTourMemberLocation(request.getUserId(), request.getTrackPointModel());
            if (server.isStartGuide(request.getUserId())) {
                System.out.println("isStartGuide");
                //将轨迹点累加到session
                if (!session.containsKey(SessionKeys.TRACK_POINT)) {//轨迹
                    TrackModel trackModel = new TrackModel();
                    trackModel.setTrackList(new ArrayList<>());
                    if (userInfo == null) {
                        throw new Exception("未登录");
                    }
                    trackModel.setUserId(userInfo.getUserId());
                    session.put(SessionKeys.TRACK_POINT, trackModel);
                }

                TrackModel track = (TrackModel) session.get(SessionKeys.TRACK_POINT);
                track.getTrackList().add(request.getTrackPointModel());
            } else {
                if (session.containsKey(SessionKeys.TRACK_POINT)) {//session里有轨迹信息则保存到数据库
                    server.saveUserTrack((TrackModel) session.get(SessionKeys.TRACK_POINT));
                    session.remove(SessionKeys.TRACK_POINT);
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
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
