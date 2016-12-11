package com.gmugu.happytour.dao.impl;

import com.gmugu.happytour.dao.IDao;
import com.gmugu.happytour.entity.*;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.dao.DataAccessException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mugu on 16-4-18.
 */
public class DaoImpl extends BaseDao implements IDao {


    @Override
    public UserinfoEntity findUserinfoByUserid(Integer userId) {
        Query query = getSession().createQuery("from UserinfoEntity where userId=?");
        query.setParameter(0, userId);
        List<UserinfoEntity> list = query.list();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public UserinfoEntity findUserinfoByEmail(String email) {
        Query query = getSession().createQuery("from UserinfoEntity where email=?");
        query.setParameter(0, email);
        List<UserinfoEntity> list = query.list();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<UserinfoEntity> findUserWithPageAndRows(int page, int rows) {
        Query query = getSession().createQuery("from UserinfoEntity ");
        query.setFirstResult((page - 1) * rows);
        query.setMaxResults(rows);
        return query.list();

    }

    @Override
    public List<UserinfoEntity> findUserByNickName(String nickName) {
        Query query = getSession().createQuery("from UserinfoEntity where nickname like ?");
        query.setParameter(0, "%" + nickName + "%");
        return query.list();
    }

    @Override
    public long findUserCount() {
        Query query = getSession().createQuery("select count(*) from UserinfoEntity");
        return (long) query.uniqueResult();
    }

    @Override
    public void saveUserinfo(UserinfoEntity userinfoEntity) {
        getSession().save(userinfoEntity);
    }

    @Override
    public void saveOrUpdateUserinfo(UserinfoEntity userinfoEntity) {
        getSession().saveOrUpdate(userinfoEntity);
    }

    @Override
    public void deleteUserinfo(UserinfoEntity userinfoEntity) {
        getSession().delete(userinfoEntity);
    }

    @Override
    public List<ScenicInfoEntity> findAllScenicList() {
        Query query = getSession().createQuery("from ScenicInfoEntity");
        return query.list();
    }

    @Override
    public ScenicInfoEntity findScenicInfoById(Integer scenicId) {
        Query query = getSession().createQuery("from ScenicInfoEntity where id=?");
        query.setParameter(0, scenicId);
        List<ScenicInfoEntity> list = query.list();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public void saveOrUpdateScenicInfo(ScenicInfoEntity entity) {
        getSession().save(entity);

    }

    @Override
    public void updateScenicStarAndNumByScenicId(Integer scenicId, Integer num, Float star) {
        Query query = getSession().createQuery("update ScenicInfoEntity set num=?, star=? where id=?");
        query.setParameter(0, num);
        query.setParameter(1, star);
        query.setParameter(2, scenicId);
        query.executeUpdate();
    }

    @Override
    public TravelTeamEntity findTeamById(Integer teamId) {
        Query query = getSession().createQuery("from TravelTeamEntity where id=?");
        query.setParameter(0, teamId);
        List<TravelTeamEntity> list = query.list();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<TravelTeamEntity> findTeamListByGuideUserId(Integer guideUserId) {
        Query query = getSession().createQuery("from TravelTeamEntity where guide.userId=?");
        query.setParameter(0, guideUserId);
        return query.list();
    }

    @Override
    public List<TravelTeamEntity> findTeamListByPassengerUserId(Integer passengerUserId) {
        Query query = getSession().createQuery("from TourMemberEntity where user.id=?");
        query.setParameter(0, passengerUserId);
        List<TourMemberEntity> list = query.list();
        List<TravelTeamEntity> list1 = new ArrayList<>();
        for (TourMemberEntity e : list) {
            list1.add(e.getTeam());
        }
        return list1;
    }

    @Override
    public List<TravelTeamEntity> findAllTeamList() {
        Query query = getSession().createQuery("from TravelTeamEntity ");
        return query.list();
    }

    @Override
    public List<TravelTeamEntity> findTeamListNotContainPassengerUserId(Integer passengerUserId) {
        //todo
        return null;
    }

    @Override
    public Serializable saveTeam(TravelTeamEntity entity) {
        return getSession().save(entity);
    }

    @Override
    public void saveOrUpdateTeam(TravelTeamEntity entity) {
        getSession().saveOrUpdate(entity);
    }


    @Override
    public void deleteTeam(TravelTeamEntity entity) {
        getSession().delete(entity);
    }

    @Override
    public void deleteTeamByTeamId(Integer teamId) throws DataAccessException {
        Query query = getSession().createQuery("delete TravelTeamEntity where id=?");
        query.setParameter(0, teamId);
        query.executeUpdate();
    }

    @Override
    public TourMemberEntity findTourMemberByUserIdAndTeamId(Integer userId, Integer teamId) {
        Query query = getSession().createQuery("from TourMemberEntity where user.userId=? and team.id=?");
        query.setParameter(0, userId);
        query.setParameter(1, teamId);
        List<TourMemberEntity> list = query.list();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<TourMemberEntity> findTourMemberListByUserId(Integer userId) {
        Query query = getSession().createQuery("from TourMemberEntity where user.userId=?");
        query.setParameter(0, userId);
        return query.list();
    }

    @Override
    public List<TourMemberEntity> findTourMemberListByGuideId(Integer guideId) {
        Query query = getSession().createQuery("from TourMemberEntity where team.guide.userId=?");
        query.setParameter(0, guideId);
        return query.list();
    }

    @Override
    public void saveOrUpdateTourMember(TourMemberEntity entity) {
        getSession().saveOrUpdate(entity);
    }

    @Override
    public void deleteTeamMember(TourMemberEntity entity) {
        getSession().delete(entity);
    }

    @Override
    public void updateTourMemberLocation(Integer userId, double longitude, double latitude) {
        Query query = getSession().createQuery("update TourMemberEntity set curLog=?,curLat=? where user.userId=?");
        query.setParameter(0, longitude);
        query.setParameter(1, latitude);
        query.setParameter(2, userId);
        query.executeUpdate();
    }

    @Override
    public void saveOrUpdateUserTrack(UserTrackEntity userTrackEntity) {
        getSession().saveOrUpdate(userTrackEntity);
    }

    @Override
    public List<UserTrackEntity> findUserTrackByUserId(Integer userId) {
        Query query = getSession().createQuery("from UserTrackEntity where user.userId=?");
        query.setParameter(0, userId);
        return query.list();
    }

    @Override
    public List<ScenicCommentsEntity> findScenicCommentsByScenicId(Integer scenicId) {
        Query query = getSession().createQuery("from ScenicCommentsEntity where scenic.id=? order by time desc");
        query.setParameter(0, scenicId);
        return query.list();
    }

    @Override
    public List<ScenicInfoEntity> findScenicWithPageAndRows(int page, int rows) {
        Query query = getSession().createQuery("from ScenicInfoEntity");
        query.setFirstResult((page - 1) * rows);
        query.setMaxResults(rows);
        return query.list();
    }

    @Override
    public List<ScenicInfoEntity> findScenicByScenicName(String scenicName) {
        Query query = getSession().createQuery("from ScenicInfoEntity where name like ?");
        query.setParameter(0, "%" + scenicName + "%");
        return query.list();
    }

    @Override
    public Long findScenicCount() {
        Query query = getSession().createQuery("select count(*) from ScenicInfoEntity");
        return (Long) query.uniqueResult();
    }

    @Override
    public void saveOrUpdateScenicComments(ScenicCommentsEntity entity) {
        getSession().saveOrUpdate(entity);
    }

    @Override
    public void deleteScenic(ScenicInfoEntity entity) {
        getSession().delete(entity);
    }

    @Override
    public List<NoticesEntity> findNoticesByNoticeId(Integer noticeId) {
        Query query = getSession().createQuery("from NoticesEntity where id=?");
        query.setParameter(0, noticeId);
        return query.list();
    }

    @Override
    public List<NoticesEntity> findNoticesByGuideId(Integer guideId) {
        Query query = getSession().createQuery("from NoticesEntity where guide.userId=?");
        query.setParameter(0, guideId);
        return query.list();
    }

    @Override
    public void saveOrUpdateNotices(NoticesEntity entity) {
        getSession().saveOrUpdate(entity);
    }


    @Override
    public void test() {
        UserinfoEntity entity=new UserinfoEntity();
        entity.setEmail("sf");
        entity.setPassword("Sfsf");
        entity.setUserType("ADMIN");
        sessionFactory.getCurrentSession().save(entity);

    }


}