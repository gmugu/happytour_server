package com.gmugu.happytour.server.impl;

import com.gmugu.happyhour.message.*;
import com.gmugu.happytour.comment.assist.Distancer;
import com.gmugu.happytour.comment.exception.ServerException;
import com.gmugu.happytour.dao.IBaseDao;
import com.gmugu.happytour.dao.IDao;
import com.gmugu.happytour.entity.*;
import com.gmugu.happytour.server.IServer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mugu on 16-4-18.
 */
public class ServerImpl implements IServer {
    private IDao dao;

    public IDao getDao() {
        return dao;
    }

    public void setDao(IDao dao) {
        this.dao = dao;
    }

    private abstract class Do<T1, T2 extends IBaseDao> {
        abstract T1 doIt(T2 dao) throws Exception;
    }

    private <T1, T2 extends IBaseDao> T1 execute(T2 dao, Do<T1, T2> mDo) throws ServerException {
        Transaction transaction = null;
        try {
            transaction = dao.beginTransaction();
            return mDo.doIt(dao);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerException(e);
        } finally {
            try {
                if (transaction != null) {
                    transaction.commit();
                }
                if (dao != null) {
                    dao.closeSession();
                }
            } catch (HibernateException e) {
                e.printStackTrace();
                throw new ServerException(e);
            }
        }
    }

    @Override
    public UserinfoEntity getUserinfoByEmain(String email) throws ServerException {

        UserinfoEntity userinfo = dao.findUserinfoByEmail(email);

        return userinfo;
    }

    @Transactional()
    @Override
    public UserInfoModel loginByEmail(String email, String passwd) throws ServerException {
        UserinfoEntity userinfo = dao.findUserinfoByEmail(email);
        if (userinfo != null) {
            if (userinfo.getPassword().equals(passwd)) {
                return userInfoModel2Entity(userinfo);
            } else {
                throw new ServerException("用户名密码错误");
            }
        } else {
            throw new ServerException("用户名不存在");
        }
    }

    @Override
    public void updatePasswdByUserId(Integer userId, String oldPasswd, String newPasswd) throws ServerException {
        execute(dao, new Do<Void, IDao>() {
            @Override
            Void doIt(IDao dao) throws Exception {
                UserinfoEntity userinfo = dao.findUserinfoByUserid(userId);
                if (userinfo == null) {
                    throw new ServerException("userId:" + userId + " 不存在");
                }
                if (oldPasswd.equals(userinfo.getPassword())) {
                    userinfo.setPassword(newPasswd);
                    dao.saveOrUpdateUserinfo(userinfo);
                } else {
                    throw new ServerException("密码不正确");
                }
                return null;
            }
        });
    }

    @Override
    public UserInfoModel registerByEmail(String email, String passwd) throws ServerException {
        return execute(dao, new Do<UserInfoModel, IDao>() {
            @Override
            UserInfoModel doIt(IDao dao) throws Exception {
                UserinfoEntity oldUser = dao.findUserinfoByEmail(email);
                if (oldUser != null) {
                    throw new ServerException("该邮箱已被注册");
                }
                UserinfoEntity userinfo = new UserinfoEntity();
                userinfo.setEmail(email);
                userinfo.setPassword(passwd);
                userinfo.setUserType("PASSENGER");
                dao.saveUserinfo(userinfo);
                UserinfoEntity userinfoByEmail = dao.findUserinfoByEmail(email);
                return userInfoModel2Entity(userinfoByEmail);
            }
        });
    }

    @Override
    public List<ScenicModel> getScenicList() throws ServerException {
        List<ScenicModel> re = new ArrayList<>();
        List<ScenicInfoEntity> scenicList = dao.findAllScenicList();
        for (ScenicInfoEntity e : scenicList) {
            ScenicModel e1 = new ScenicModel();
            e1.setScenicId(e.getId());
            e1.setDescribe(e.getDescribe());
            e1.setLatitude(e.getLatitude());
            e1.setLongitude(e.getLongitude());
            e1.setName(e.getName());
            Date openTime = e.getOpenTime();
            if (openTime != null) {
                e1.setOpenTime(openTime.getTime());
            }
            e1.setPicture(e.getPicture());
            e1.setStar(e.getStar());
            e1.setNum(e.getNum());
            re.add(e1);
        }
        return re;
    }

    @Override
    public List<TravelTeamModel> findTeamListByGuideUserId(Integer guideUserId) throws ServerException {
        List<TravelTeamModel> travelTeamModels = new ArrayList<>();
        List<TravelTeamEntity> teamListByGuideUserId = dao.findTeamListByGuideUserId(guideUserId);
        for (TravelTeamEntity entity : teamListByGuideUserId) {
            travelTeamModels.add(teamEntity2Model(entity));
        }
        return travelTeamModels;
    }

    @Override
    public void deleteTeamByTeamId(Integer teamId) throws ServerException {
        execute(dao, new Do<Void, IDao>() {
            @Override
            Void doIt(IDao dao) throws Exception {
                dao.deleteTeamByTeamId(teamId);
                return null;
            }
        });
    }

    @Override
    public List<TravelTeamModel> findTeamListByPassengerUserId(Integer passengerUserId) throws ServerException {
        List<TravelTeamModel> travelTeamModels = new ArrayList<>();
        List<TravelTeamEntity> teamListByGuideUserId = dao.findTeamListByPassengerUserId(passengerUserId);
        for (TravelTeamEntity entity : teamListByGuideUserId) {
            travelTeamModels.add(teamEntity2Model(entity));
        }
        return travelTeamModels;
    }

    @Override
    public List<TravelTeamModel> findAllTeamList() throws ServerException {

        List<TravelTeamModel> travelTeamModels = new ArrayList<>();
        List<TravelTeamEntity> teamListByGuideUserId = dao.findAllTeamList();
        for (TravelTeamEntity entity : teamListByGuideUserId) {
            travelTeamModels.add(teamEntity2Model(entity));
        }
        return travelTeamModels;
    }

    @Override
    public Integer createTeam(String name, Integer guideUserId, Integer scenicId, Integer scenicRange) throws ServerException {
        return execute(dao, new Do<Integer, IDao>() {
            @Override
            Integer doIt(IDao dao) throws Exception {
                List<TravelTeamEntity> teamListByGuideUserId = dao.findTeamListByGuideUserId(guideUserId);
                if (teamListByGuideUserId != null && !teamListByGuideUserId.isEmpty()) {
                    throw new ServerException("您已经创建过旅行团了");
                }
                TravelTeamEntity entity = new TravelTeamEntity();
                entity.setName(name);
                entity.setScenicRange(scenicRange);
                entity.setScenic(dao.findScenicInfoById(scenicId));
                entity.setGuide(dao.findUserinfoByUserid(guideUserId));
                return (Integer) dao.saveTeam(entity);
            }
        });
    }

    @Override
    public ScenicModel joinTeam(Integer userId, Integer teamId) throws ServerException {
        return execute(dao, new Do<ScenicModel, IDao>() {
            @Override
            ScenicModel doIt(IDao dao) throws Exception {
                List<TravelTeamEntity> list = dao.findTeamListByPassengerUserId(userId);
                if (list != null && !list.isEmpty()) {
                    throw new ServerException("您已经加入 " + list.get(0).getName() + " 了,不能再加入其它旅行团");
                }
                TourMemberEntity entity = dao.findTourMemberByUserIdAndTeamId(userId, teamId);
                if (entity != null) {
                    throw new ServerException(("不能重复加入同一个旅行队"));
                }
                entity = new TourMemberEntity();
                UserinfoEntity userinfoByUserid = dao.findUserinfoByUserid(userId);
                if (userinfoByUserid == null) {
                    throw new ServerException("找不到user");
                }
                entity.setUser(userinfoByUserid);
                TravelTeamEntity teamById = dao.findTeamById(teamId);
                if (teamById == null) {
                    throw new ServerException("找不到team");
                }
                entity.setTeam(teamById);
                dao.saveOrUpdateTourMember(entity);
                ScenicInfoEntity scenic = entity.getTeam().getScenic();
                Date openTime1 = scenic.getOpenTime();
                Long openTime = null;
                if (openTime1 != null) {
                    openTime = openTime1.getTime();
                }
                ScenicModel model = new ScenicModel(scenic.getId(), scenic.getName(), scenic.getStar(), openTime, scenic.getPicture(), scenic.getNum(), scenic.getDescribe(), scenic.getLongitude(), scenic.getLatitude(), entity.getTeam().getScenicRange());
                return model;
            }
        });

    }

    @Override
    public List<TravelTeamModel> findTeamListNotContainPassengerUserId(Integer passengerUserId) throws ServerException {
        List<TourMemberEntity> list = dao.findTourMemberListByUserId(passengerUserId);
        if (list != null && !list.isEmpty()) {
            throw new ServerException("已加入一个旅行队");
        }
        List<TravelTeamEntity> list2 = dao.findAllTeamList();
        List<TravelTeamModel> list1 = new ArrayList<>();
        for (TravelTeamEntity entity : list2) {
            list1.add(teamEntity2Model(entity));
        }
        return list1;

    }


    @Override
    public void outTeam(Integer userId, Integer teamId) throws ServerException {
        execute(dao, new Do<Void, IDao>() {
            @Override
            Void doIt(IDao dao) throws Exception {
                TourMemberEntity entity = dao.findTourMemberByUserIdAndTeamId(userId, teamId);
                if (entity == null) {
                    throw new ServerException("找不到队伍");
                }
                dao.deleteTeamMember(entity);
                return null;
            }
        });
    }

    @Override
    public void updateTourMemberLocation(Integer userId, TrackPointModel trackPointModel) throws ServerException {
        execute(dao, new Do<Void, IDao>() {
            @Override
            Void doIt(IDao dao) throws Exception {
                List<TourMemberEntity> entities = dao.findTourMemberListByUserId(userId);
                if (entities == null || entities.isEmpty()) {
                    throw new ServerException("该用户未加入任何旅行团");
                }
                dao.updateTourMemberLocation(userId, trackPointModel.getLongitude(), trackPointModel.getLatitude());
                return null;
            }
        });

    }

    @Override
    public ScenicModel startGuide(Integer guideUserId) throws ServerException {
        return execute(dao, new Do<ScenicModel, IDao>() {
            @Override
            ScenicModel doIt(IDao dao) throws Exception {
                List<TravelTeamEntity> list = dao.findTeamListByGuideUserId(guideUserId);
                if (list == null || list.isEmpty()) {
                    throw new ServerException("您还未创建旅行团");
                }
                for (TravelTeamEntity entity : list) {
                    entity.setIsStarting(1);
                    dao.saveOrUpdateTeam(entity);
                }
                ScenicInfoEntity scenic = list.get(0).getScenic();
                Date openTime1 = scenic.getOpenTime();
                Long openTime = null;
                if (openTime1 != null) {
                    openTime = openTime1.getTime();
                }

                ScenicModel model = new ScenicModel(scenic.getId(), scenic.getName(), scenic.getStar(), openTime, scenic.getPicture(), scenic.getNum(), scenic.getDescribe(), scenic.getLongitude(), scenic.getLatitude(), list.get(0).getScenicRange());
                return model;
            }
        });
    }

    @Override
    public void stopGuide(Integer guideUserId) throws ServerException {
        execute(dao, new Do<Void, IDao>() {
            @Override
            Void doIt(IDao dao) throws Exception {
                List<TravelTeamEntity> list = dao.findTeamListByGuideUserId(guideUserId);
                if (list == null || list.isEmpty()) {
                    throw new ServerException("您还未创建旅行团");
                }
                for (TravelTeamEntity entity : list) {
                    entity.setIsStarting(0);
                    dao.saveOrUpdateTeam(entity);
                }
                return null;
            }
        });
    }

    @Override
    public boolean isStartGuide(Integer userId) throws ServerException {
        for (TravelTeamEntity entity : dao.findTeamListByPassengerUserId(userId)) {
            if (entity.getIsStarting() != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public UserInfoModel updateUserInfo(UserInfoModel userInfoModel) throws ServerException {
        return execute(dao, new Do<UserInfoModel, IDao>() {
            @Override
            UserInfoModel doIt(IDao dao) throws Exception {
                UserinfoEntity userinfo = dao.findUserinfoByUserid(userInfoModel.getUserId());
                userinfo.setPhone(userInfoModel.getPhone());
                Long birthday = userInfoModel.getBirthday();
                if (birthday != null) {
                    userinfo.setBirthday(new java.sql.Date(birthday));
                }
                userinfo.setCity(userInfoModel.getCity());
                userinfo.setGender(userInfoModel.getGender());
                userinfo.setHeight(userInfoModel.getHeight());
                userinfo.setNickname(userInfoModel.getNickname());
                userinfo.setWeight(userInfoModel.getWeight());
                dao.saveOrUpdateUserinfo(userinfo);
                return userInfoModel;
            }
        });
    }

    @Override
    public UserInfoModel updateUserHeadImg(Integer userId, String path, String checkCode) throws ServerException {
        return execute(dao, new Do<UserInfoModel, IDao>() {
            @Override
            UserInfoModel doIt(IDao dao) throws Exception {
                UserinfoEntity userinfoEntity = dao.findUserinfoByUserid(userId);
                userinfoEntity.setPortrait(path);
                userinfoEntity.setPortraitCheckCode(checkCode);
                dao.saveOrUpdateUserinfo(userinfoEntity);
                return userinfoEntity2Model(userinfoEntity);
            }

        });
    }

    @Override
    public void updatePasswdByUserId(Integer userId, String newPasswd) throws ServerException {
        execute(dao, new Do<Void, IDao>() {
            @Override
            Void doIt(IDao dao) throws Exception {
                UserinfoEntity userinfoByEmail = dao.findUserinfoByUserid(userId);
                userinfoByEmail.setPassword(newPasswd);
                dao.saveOrUpdateUserinfo(userinfoByEmail);
                return null;
            }
        });
    }

    @Override
    public Map<Integer, UserLocationModel> getAllTeamLocation(Integer guideUserId) throws ServerException {
        return execute(dao, new Do<Map<Integer, UserLocationModel>, IDao>() {
            @Override
            Map<Integer, UserLocationModel> doIt(IDao dao) throws Exception {
                Map<Integer, UserLocationModel> re = new HashMap<>();
                List<TourMemberEntity> list = dao.findTourMemberListByGuideId(guideUserId);
                for (TourMemberEntity entity : list) {
                    UserinfoEntity user = entity.getUser();
                    if (user.getUserId() != guideUserId) {
                        re.put(user.getUserId(), new UserLocationModel(user.getUserId(), user.getNickname(), user.getPhone(), entity.getCurLog(), entity.getCurLat()));
                    }
                }
                return re;
            }

        });

    }

    @Override
    public ScenicModel findScenicInfoByUserId(Integer userId) throws ServerException {
        return execute(dao, new Do<ScenicModel, IDao>() {
            @Override
            ScenicModel doIt(IDao dao) throws Exception {
                List<TourMemberEntity> list = dao.findTourMemberListByUserId(userId);
                if (list == null || list.isEmpty()) {
                    throw new ServerException("未加入旅行团");
                }
                ScenicInfoEntity scenic = list.get(0).getTeam().getScenic();
                Date openTime1 = scenic.getOpenTime();
                Long openTime = null;
                if (openTime1 != null) {
                    openTime = openTime1.getTime();
                }

                ScenicModel model = new ScenicModel(scenic.getId(), scenic.getName(), scenic.getStar(), openTime, scenic.getPicture(), scenic.getNum(), scenic.getDescribe(), scenic.getLongitude(), scenic.getLatitude(), list.get(0).getTeam().getScenicRange());
                return model;
            }
        });
    }

    @Override
    public void saveUserTrack(TrackModel trackModel) throws ServerException {
        execute(dao, new Do<Void, IDao>() {
            @Override
            Void doIt(IDao dao) throws Exception {
                UserTrackEntity userTrackEntity = new UserTrackEntity();
                List<TrackPointModel> trackList = trackModel.getTrackList();
                userTrackEntity.setUser(dao.findUserinfoByUserid(trackModel.getUserId()));
                userTrackEntity.setBeginTime(new Date(trackList.get(0).getCurrentTime()));
                userTrackEntity.setEndTime(new Date(trackList.get(trackList.size() - 1).getCurrentTime()));
                userTrackEntity.setRunningRecode(new Gson().toJson(trackList));
                Distancer distancer = new Distancer();
                for (TrackPointModel model : trackList) {
                    distancer.addPoint(model.getLongitude(), model.getLatitude());
                }
                userTrackEntity.setDistance((float) distancer.stop());
                dao.saveOrUpdateUserTrack(userTrackEntity);
                return null;
            }
        });
    }

    @Override
    public Map<Integer, List<TrackModel>> getTeammateTrack(UserInfoModel userInfoModel) throws ServerException {
        return execute(dao, new Do<Map<Integer, List<TrackModel>>, IDao>() {
            @Override
            Map<Integer, List<TrackModel>> doIt(IDao dao) throws Exception {
                Integer userId = userInfoModel.getUserId();
                Map<Integer, List<TrackModel>> re = new HashMap<>();
                switch (userInfoModel.getUserType()) {
                    case GUIDE:
                        List<TourMemberEntity> teamList = dao.findTourMemberListByGuideId(userId);
                        if (teamList != null && !teamList.isEmpty()) {
                            for (TourMemberEntity entity : teamList) {
                                UserinfoEntity user = entity.getUser();
                                List<UserTrackEntity> entityList = dao.findUserTrackByUserId(user.getUserId());
                                if (entityList != null && !entityList.isEmpty()) {
                                    List<TrackModel> modelList = new ArrayList<TrackModel>();
                                    for (UserTrackEntity entity1 : entityList) {
                                        modelList.add(userTrackAndUserinfoEntity2TrackModel(entity1));
                                    }
                                    re.put(user.getUserId(), modelList);
                                }
                            }
                        }
                        break;
                    case PASSENGER:
                        List<UserTrackEntity> entityList = dao.findUserTrackByUserId(userId);
                        if (entityList != null && !entityList.isEmpty()) {
                            List<TrackModel> modelList = new ArrayList<>();
                            for (UserTrackEntity entity1 : entityList) {
                                modelList.add(userTrackAndUserinfoEntity2TrackModel(entity1));
                            }
                            re.put(userId, modelList);
                        }

                        break;
                }
                return re;
            }
        });
    }

    @Override
    public ScenicModel findScenicInfoByScenicId(Integer scenicId) throws ServerException {
        return execute(dao, new Do<ScenicModel, IDao>() {
            @Override
            ScenicModel doIt(IDao dao) throws Exception {
                return scenicEntity2Model(dao.findScenicInfoById(scenicId));

            }
        });
    }

    @Override
    public List<ScenicCommentsItemModel> findScenicCommentsByScenicId(Integer scenicId) throws ServerException {
        return execute(dao, new Do<List<ScenicCommentsItemModel>, IDao>() {
            @Override
            List<ScenicCommentsItemModel> doIt(IDao dao) throws Exception {

                ArrayList<ScenicCommentsItemModel> models = new ArrayList<>();
                List<ScenicCommentsEntity> entities = dao.findScenicCommentsByScenicId(scenicId);
                for (ScenicCommentsEntity entity : entities) {
                    ScenicCommentsItemModel model = new ScenicCommentsItemModel();
                    model.setScenicId(entity.getScenic().getId());
                    model.setComment(entity.getComments());
                    model.setUserNickname(entity.getUser().getNickname());
                    Date time = entity.getTime();
                    if (time != null) {
                        model.setTime(time.getTime());
                    }
                    models.add(model);
                }
                return models;
            }
        });
    }

    @Override
    public void addComment(Integer userId, Integer scenicId, String comment, Long time) throws ServerException {
        execute(dao, new Do<Void, IDao>() {
            @Override
            Void doIt(IDao dao) throws Exception {
                ScenicCommentsEntity entity = new ScenicCommentsEntity();
                UserinfoEntity user = dao.findUserinfoByUserid(userId);
                if (user == null) {
                    throw new ServerException("用户不存在");
                }
                entity.setUser(user);
                ScenicInfoEntity scenic = dao.findScenicInfoById(scenicId);
                if (scenic == null) {
                    throw new ServerException("景区不存在");
                }
                entity.setScenic(scenic);
                entity.setComments(comment);
                entity.setTime(new Date(time));
                dao.saveOrUpdateScenicComments(entity);
                return null;
            }
        });
    }

    @Override
    public void updateScenicRank(Integer scenicId, Float rank) throws ServerException {
        execute(dao, new Do<Void, IDao>() {
            @Override
            Void doIt(IDao dao) throws Exception {
                ScenicInfoEntity entity = dao.findScenicInfoById(scenicId);
                Integer num = entity.getNum();
                Float star = entity.getStar();
                star = (star * num + rank) / (num + 1);
                dao.updateScenicStarAndNumByScenicId(scenicId, num + 1, star);
                return null;
            }
        });
    }

    @Override
    public List<NoticeItemModel> findNotices(Integer userId) throws ServerException {
        return execute(dao, new Do<List<NoticeItemModel>, IDao>() {
            @Override
            List<NoticeItemModel> doIt(IDao dao) throws Exception {
                List<TourMemberEntity> tourMemberEntities = dao.findTourMemberListByUserId(userId);
                TourMemberEntity tourMemberEntity = tourMemberEntities.get(0);
                int guideId = tourMemberEntity.getTeam().getGuide().getUserId();
                List<NoticesEntity> noticesByGuideId = dao.findNoticesByGuideId(guideId);
                List<NoticeItemModel> re = new ArrayList<>();
                for (NoticesEntity entity : noticesByGuideId) {
                    NoticeItemModel model = new NoticeItemModel();
                    model.setTitle(entity.getTitle());
                    model.setContent(entity.getContent());
                    model.setTime(entity.getTime().getTime());
                    re.add(model);
                }
                return re;
            }
        });
    }

    @Override
    public void publicNotice(Integer userId, NoticeItemModel noticeItemModel) throws ServerException {
        execute(dao, new Do<Void, IDao>() {
            @Override
            Void doIt(IDao dao) throws Exception {
                NoticesEntity entity = new NoticesEntity();
                entity.setTime(new Date(System.currentTimeMillis()));
                String title = noticeItemModel.getTitle();
                if (title == null || title.equals("")) {
                    throw new ServerException("标题不能为空");
                }
                entity.setTitle(title);
                String content = noticeItemModel.getContent();
                if (content == null || content.equals("")) {
                    throw new ServerException("内容不能为空");
                }
                entity.setContent(content);
                entity.setGuide(dao.findUserinfoByUserid(userId));
                dao.saveOrUpdateNotices(entity);
                return null;
            }
        });
    }

    @Override
    public List<UserinfoEntity> findUserWithPageAndRows(int page, int rows) throws ServerException {
        return execute(dao, new Do<List<UserinfoEntity>, IDao>() {
            @Override
            List<UserinfoEntity> doIt(IDao dao) throws Exception {
                return dao.findUserWithPageAndRows(page, rows);
            }
        });
    }

    @Override
    public long findUserCount() throws ServerException {
        return execute(dao, new Do<Long, IDao>() {
            @Override
            Long doIt(IDao dao) throws Exception {
                return dao.findUserCount();
            }
        });
    }


    @Override
    public void test() {
        dao.test();
    }

    @Override
    public void deleteUserByUserId(int id) throws ServerException {
        execute(dao, new Do<Void, IDao>() {
            @Override
            Void doIt(IDao dao) throws Exception {
                dao.deleteUserinfo(dao.findUserinfoByUserid(id));
                return null;
            }
        });
    }

    @Override
    public void updateScenicById(int id, UserInfoModel.UserType userType) throws ServerException {
        execute(dao, new Do<Void, IDao>() {
            @Override
            Void doIt(IDao dao) throws Exception {
                UserinfoEntity entity = dao.findUserinfoByUserid(id);
                entity.setUserType(userType.toString());
                dao.saveOrUpdateUserinfo(entity);
                return null;
            }
        });
    }

    @Override
    public List<ScenicInfoEntity> findScenicWithPageAndRows(int page, int rows) throws ServerException {
        return execute(dao, new Do<List<ScenicInfoEntity>, IDao>() {
            @Override
            List<ScenicInfoEntity> doIt(IDao dao) throws Exception {
                return dao.findScenicWithPageAndRows(page, rows);
            }
        });
    }

    @Override
    public long findScenicCount() throws ServerException {
        return execute(dao, new Do<Long, IDao>() {
            @Override
            Long doIt(IDao dao) throws Exception {
                return dao.findScenicCount();
            }
        });
    }

    @Override
    public void deleteScenicById(int id) throws ServerException {
        execute(dao, new Do<Void, IDao>() {
            @Override
            Void doIt(IDao dao) throws Exception {
                dao.deleteScenic(dao.findScenicInfoById(id));
                return null;
            }
        });
    }

    @Override
    public void updateScenicById(int id, String name, String star, String num, String openTime, String pictureFileName, String describe, double longitude, double latitude) throws ServerException {
        execute(dao, new Do<Void, IDao>() {
            @Override
            Void doIt(IDao dao) throws Exception {
                ScenicInfoEntity entity = dao.findScenicInfoById(id);
                if (entity == null) {
                    entity=new ScenicInfoEntity();
                }
                entity.setName(name);
                entity.setStar(Float.parseFloat(star));
                entity.setNum(Integer.parseInt(num));
                entity.setPicture(pictureFileName);
                entity.setDescribe(describe);
                entity.setLatitude(latitude);
                entity.setLongitude(longitude);
                Date date = null;
                try {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    date = format.parse(openTime);
                } catch (Exception e) {
                }
                if (date != null) {
                    entity.setOpenTime(date);
                }
                dao.saveOrUpdateScenicInfo(entity);
                return null;
            }
        });
    }

    @Override
    public List<ScenicInfoEntity> findScenicInfoByScenicName(String scenicName) throws ServerException {
        return execute(dao, new Do<List<ScenicInfoEntity>, IDao>() {
            @Override
            List<ScenicInfoEntity> doIt(IDao dao) throws Exception {
                return dao.findScenicByScenicName(scenicName);
            }
        });
    }

    @Override
    public List<UserinfoEntity> findUserByNickName(String nickName) throws ServerException {
        return execute(dao, new Do<List<UserinfoEntity>, IDao>() {
            @Override
            List<UserinfoEntity> doIt(IDao dao) throws Exception {
                return dao.findUserByNickName(nickName);
            }
        });
    }

    private UserInfoModel userInfoModel2Entity(UserinfoEntity entity) {
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setUserId(entity.getUserId());
        Date birthday = entity.getBirthday();
        if (birthday != null) {
            userInfoModel.setBirthday(birthday.getTime());
        }
        userInfoModel.setNickname(entity.getNickname());
        userInfoModel.setCity(entity.getCity());
        userInfoModel.setGender(entity.getGender());
        userInfoModel.setHeight(entity.getHeight());
        userInfoModel.setPortrait(entity.getPortrait());
        userInfoModel.setPortraitCheckCode(entity.getPortraitCheckCode());
        userInfoModel.setWeight(entity.getWeight());
        userInfoModel.setPhone(entity.getPhone());
        String userType = entity.getUserType();
        if (userType != null) {
            userInfoModel.setUserType(UserInfoModel.UserType.valueOf(userType));
        }
        return userInfoModel;
    }

    private TravelTeamModel teamEntity2Model(TravelTeamEntity entity) {
        TravelTeamModel model = new TravelTeamModel();
        model.setName(entity.getName());
        model.setScenicName(entity.getScenic().getName());
        model.setGuideName(entity.getGuide().getNickname());
        model.setTeamId(entity.getId());
        return model;
    }

    private UserInfoModel userinfoEntity2Model(UserinfoEntity entity) {
        Date birthday1 = entity.getBirthday();
        Long birthday = null;
        if (birthday1 != null) {
            birthday = birthday1.getTime();
        }
        return new UserInfoModel(entity.getUserId(), entity.getPortrait(), entity.getPortraitCheckCode(), entity.getNickname(), entity.getGender(), entity.getHeight(), entity.getWeight(), birthday, entity.getCity());
    }

    private ScenicModel scenicEntity2Model(ScenicInfoEntity entity) {
        ScenicModel model = new ScenicModel();
        model.setDescribe(entity.getDescribe());
        model.setLatitude(entity.getLatitude());
        model.setLongitude(entity.getLongitude());
        model.setName(entity.getName());
        model.setNum(entity.getNum());
        Date openTime = entity.getOpenTime();
        if (openTime != null) {
            model.setOpenTime(openTime.getTime());
        }
        model.setPicture(entity.getPicture());
        model.setScenicId(entity.getId());
        model.setStar(entity.getStar());
        return model;
    }

    private TrackModel userTrackAndUserinfoEntity2TrackModel(UserTrackEntity entity) {
        try {
            UserinfoEntity entity2 = entity.getUser();
            TrackModel trackModel = new TrackModel();
            trackModel.setUserId(entity2.getUserId());
            trackModel.setTrackList(new Gson().fromJson(entity.getRunningRecode(), new TypeToken<List<TrackPointModel>>() {
            }.getType()));
            TrackSnapshotsModel model = new TrackSnapshotsModel();
            model.setUserId(entity2.getUserId());
            model.setNickName(entity2.getNickname());
            model.setStartTime(entity.getBeginTime().getTime());
            model.setStopTime(entity.getEndTime().getTime());
            model.setDistance(entity.getDistance());
            trackModel.setTrackSnapshotsModel(model);
            return trackModel;
        } catch (Exception e) {
            return null;
        }
    }


}
