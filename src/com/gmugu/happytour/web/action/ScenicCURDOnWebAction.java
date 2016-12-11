package com.gmugu.happytour.web.action;

import com.gmugu.happytour.comment.exception.ServerException;
import com.gmugu.happytour.entity.ScenicInfoEntity;
import com.gmugu.happytour.server.IServer;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by mugu on 16-5-28.
 */
public class ScenicCURDOnWebAction extends BaseAction {
    private IServer server;
    private int page;
    private int rows;
    private String msg;

    private int id;
    private String name;
    private String star;
    private String num;
    private String openTime;
    private File image;
    private String imageFileName;
    private String describe;
    private double longitude;
    private double latitude;

    private String scenicName;

    public String saveOrUpdate() {
        try {

            if (image != null) {
                String path = ServletActionContext.getServletContext().getRealPath("/scenicImgDir");
                String filename = System.currentTimeMillis() + ".jpg";
                File file = new File(new File(path), filename);
                System.out.println("save scenic img path:" + file.getAbsolutePath());
                FileUtils.copyFile(image, file);
                imageFileName = "scenicImgDir/" + filename;
            }
            server.updateScenicById(id, name, star, num, openTime, imageFileName, describe, longitude, latitude);

            msg = "操作成功";
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            msg = e.getMessage();
            return ERROR;
        }
    }

    public String delete() {
        try {
            server.deleteScenicById(id);
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

    public String find() {
        try {

            JSONObject object = new JSONObject();

            JSONArray rs = new JSONArray();
            object.put("rows", rs);

            List<ScenicInfoEntity> scenicInfoEntities = server.findScenicInfoByScenicName(scenicName);
            object.put("total", scenicInfoEntities.size());

            for (ScenicInfoEntity scenicInfoEntity : scenicInfoEntities) {
                JSONObject o1 = new JSONObject();
                o1.put("id", scenicInfoEntity.getId());
                o1.put("name", scenicInfoEntity.getName());
                o1.put("describe", scenicInfoEntity.getDescribe());
                o1.put("latitude", scenicInfoEntity.getLatitude());
                o1.put("longitude", scenicInfoEntity.getLongitude());
                o1.put("num", scenicInfoEntity.getNum());
                o1.put("openTime", scenicInfoEntity.getOpenTime());
                o1.put("picture", scenicInfoEntity.getPicture());
                o1.put("star", scenicInfoEntity.getStar());
                o1.put("crudOperation", scenicInfoEntity.getId());
                rs.put(o1);
            }
            PrintWriter writer = getResponse().getWriter();
            writer.write(object.toString());
            System.out.println("ScenicCURDOnWebAction.find  "+object.toString());
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            msg=e.getMessage();
        }

        return NONE;
    }

    public String findAll() {
        try {
            JSONObject object = new JSONObject();
            object.put("total", server.findScenicCount());

            JSONArray rs = new JSONArray();
            object.put("rows", rs);

            List<ScenicInfoEntity> scenicInfoEntities = server.findScenicWithPageAndRows(page, rows);
            for (ScenicInfoEntity scenicInfoEntity : scenicInfoEntities) {
                JSONObject o1 = new JSONObject();
                o1.put("id", scenicInfoEntity.getId());
                o1.put("name", scenicInfoEntity.getName());
                o1.put("describe", scenicInfoEntity.getDescribe());
                o1.put("latitude", scenicInfoEntity.getLatitude());
                o1.put("longitude", scenicInfoEntity.getLongitude());
                o1.put("num", scenicInfoEntity.getNum());
                o1.put("openTime", scenicInfoEntity.getOpenTime());
                o1.put("picture", scenicInfoEntity.getPicture());
                o1.put("star", scenicInfoEntity.getStar());
                o1.put("crudOperation", scenicInfoEntity.getId());
                rs.put(o1);
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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getScenicName() {
        return scenicName;
    }

    public void setScenicName(String scenicName) {
        this.scenicName = scenicName;
    }
}
