package com.gmugu.happytour.web.action;

import com.gmugu.happyhour.message.UserInfoModel;
import com.gmugu.happytour.comment.exception.ServerException;
import com.gmugu.happytour.entity.UserinfoEntity;
import com.gmugu.happytour.server.IServer;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.util.List;

/**
 * Created by mugu on 16-5-27.
 */
public class UserCURDOnWebAction extends BaseAction {

    private IServer server;
    private String page;
    private String rows;
    private String msg;
    private int id;
    private String userType;
    private String nickName;


    public String updateUserType() {
        try {
            server.updateScenicById(id, UserInfoModel.UserType.valueOf(userType));
            msg = "修改成功";
            return SUCCESS;
        } catch (ServerException e) {
            e.printStackTrace();
            msg = e.getMessage();
            return ERROR;
        }
    }

    public String delete() {
        try {
            server.deleteUserByUserId(id);
            msg = "删除成功";
        } catch (ServerException e) {
            e.printStackTrace();
            msg = e.getMessage();
        }
        try {
            PrintWriter writer = getResponse().getWriter();
            JSONObject object = new JSONObject();
            object.put("msg", msg);
            writer.write(object.toString());
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return NONE;
    }

    public String find(){
        try {
            JSONObject object = new JSONObject();

            JSONArray rs = new JSONArray();
            object.put("rows", rs);

            List<UserinfoEntity> userinfoEntities = server.findUserByNickName(nickName);
            object.put("total", userinfoEntities.size());
            for (UserinfoEntity userinfoEntity : userinfoEntities) {
                if (UserInfoModel.UserType.valueOf(userinfoEntity.getUserType()).equals(UserInfoModel.UserType.ADMIN)){
                    continue;
                }
                String o = new Gson().toJson(userinfoEntity);
                if (o != null) {
                    JSONObject o1 = new JSONObject(o);
                    o1.put("columnsrudOperation", userinfoEntity.getUserId()+"|"+userinfoEntity.getNickname()+"|"+userinfoEntity.getEmail());
                    rs.put(o1);
                }
            }

            PrintWriter writer = getResponse().getWriter();
            writer.write(object.toString());
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return NONE;
    }

    public String findAll() {

        try {
            JSONObject object = new JSONObject();
            object.put("total", server.findUserCount());

            JSONArray rs = new JSONArray();
            object.put("rows", rs);

            List<UserinfoEntity> userinfoEntities = server.findUserWithPageAndRows(Integer.parseInt(page), Integer.parseInt(rows));
            for (UserinfoEntity userinfoEntity : userinfoEntities) {
                if (UserInfoModel.UserType.valueOf(userinfoEntity.getUserType()).equals(UserInfoModel.UserType.ADMIN)){
                    continue;
                }
                String o = new Gson().toJson(userinfoEntity);
                if (o != null) {
                    JSONObject o1 = new JSONObject(o);
                    o1.put("columnsrudOperation", userinfoEntity.getUserId()+"|"+userinfoEntity.getNickname()+"|"+userinfoEntity.getEmail());
                    rs.put(o1);
                }
            }

            PrintWriter writer = getResponse().getWriter();
            writer.write(object.toString());
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return NONE;
    }

    public IServer getServer() {
        return server;
    }

    public void setServer(IServer server) {
        this.server = server;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
