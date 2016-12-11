package com.gmugu.happytour.web.action;

import com.gmugu.happyhour.message.UserInfoModel;
import com.gmugu.happyhour.message.request.AddOrUpdateUserInfoRequest;
import com.gmugu.happyhour.message.result.AddOrUpdateUserInfoResult;
import com.gmugu.happytour.comment.constant.ErrorCodes;
import com.gmugu.happytour.comment.constant.SessionKeys;
import com.gmugu.happytour.comment.utils.MD5Util;
import com.gmugu.happytour.server.IServer;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by mugu on 16-4-21.
 */
public class AddOrUpdateUserInfoAction extends BaseAction {
    private IServer server;
    private File headimg;
    private String headimgFileName;
    private String headimgContentType;

    @Override
    public String execute() throws Exception {
        AddOrUpdateUserInfoRequest request = getRequest(AddOrUpdateUserInfoRequest.class);
        AddOrUpdateUserInfoResult result = new AddOrUpdateUserInfoResult();

        FileInputStream fileInputStream = null;
        try {
            UserInfoModel oldUserinfo = (UserInfoModel) getHttpSession().getAttribute(SessionKeys.USER_INFO);
            if (headimg != null) {
                String path = ServletActionContext.getServletContext().getRealPath("/headimgDir");
                String filename = "user_" + oldUserinfo.getUserId() + "_headimg";
                File file = new File(new File(path), filename);
                System.out.println("save head img path:" + file.getAbsolutePath());
                FileUtils.copyFile(headimg, file);
                fileInputStream = new FileInputStream(file);
                oldUserinfo = server.updateUserHeadImg(oldUserinfo.getUserId(), "headimgDir/" + filename, MD5Util.md5ToHexStr(fileInputStream));
            }
            if (request != null) {
                UserInfoModel newUserinfo = request.getUserInfoModel();
                if (newUserinfo == null) {
                    throw new Exception("未接收到用户数据");
                }
                newUserinfo.setUserId(oldUserinfo.getUserId());
                oldUserinfo = server.updateUserInfo(newUserinfo);
            }
            getHttpSession().setAttribute(SessionKeys.USER_INFO, oldUserinfo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result.setCode(ErrorCodes.FAILE);
            result.setMessage(e.getMessage());
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
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

    public File getHeadimg() {
        return headimg;
    }

    public void setHeadimg(File headimg) {
        this.headimg = headimg;
    }

    public String getHeadimgFileName() {
        return headimgFileName;
    }

    public void setHeadimgFileName(String headimgFileName) {
        this.headimgFileName = headimgFileName;
    }

    public String getHeadimgContentType() {
        return headimgContentType;
    }

    public void setHeadimgContentType(String headimgContentType) {
        this.headimgContentType = headimgContentType;
    }
}
