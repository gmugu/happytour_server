package com.gmugu.happytour.web.interceptor;

import com.gmugu.happyhour.message.UserInfoModel;
import com.gmugu.happyhour.message.result.BaseResult;
import com.gmugu.happytour.comment.constant.ErrorCodes;
import com.gmugu.happytour.comment.constant.SessionKeys;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Created by mugu on 16-4-25.
 */
public class PermissionInterceptor extends BaseInterceptor {
    private String role;

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        super.intercept(actionInvocation);
        UserInfoModel userInfoModel = (UserInfoModel) getSession().get(SessionKeys.USER_INFO);

        if (userInfoModel != null && role != null && UserInfoModel.UserType.valueOf(role).equals(userInfoModel.getUserType())) {
            return actionInvocation.invoke();
        } else {
            BaseResult result = new BaseResult();
            result.setCode(ErrorCodes.NO_PERMISSIONS);
            result.setMessage("您没有权限执行本操作");
            sendResult(result);
            return ActionSupport.NONE;
        }

    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
