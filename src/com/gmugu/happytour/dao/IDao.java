package com.gmugu.happytour.dao;

import com.gmugu.happyhour.message.ScenicCommentsItemModel;
import com.gmugu.happyhour.message.UserInfoModel;
import com.gmugu.happytour.entity.*;
import org.springframework.dao.DataAccessException;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mugu on 16-4-18.
 */
public interface IDao extends IBaseDao {
    /**
     * -------- user info ----------
     */
    UserinfoEntity findUserinfoByUserid(Integer userId);

    UserinfoEntity findUserinfoByEmail(String email);

    List<UserinfoEntity> findUserWithPageAndRows(int page, int rows);

    List<UserinfoEntity> findUserByNickName(String nickName);

    long findUserCount();

    void saveUserinfo(UserinfoEntity userinfoEntity);

    void saveOrUpdateUserinfo(UserinfoEntity userinfoEntity);

    void deleteUserinfo(UserinfoEntity userinfoEntity);


    /**
     * -------- scenic info ---------
     */
    List<ScenicInfoEntity> findAllScenicList();

    ScenicInfoEntity findScenicInfoById(Integer scenicId);

    void saveOrUpdateScenicInfo(ScenicInfoEntity entity);

    void updateScenicStarAndNumByScenicId(Integer scenicId, Integer num, Float star);

    /**
     * -------- travel team ---------
     */
    TravelTeamEntity findTeamById(Integer teamId);

    List<TravelTeamEntity> findTeamListByGuideUserId(Integer guideUserId);

    List<TravelTeamEntity> findTeamListByPassengerUserId(Integer passengerUserId);

    List<TravelTeamEntity> findAllTeamList();

    List<TravelTeamEntity> findTeamListNotContainPassengerUserId(Integer passengerUserId);

    Serializable saveTeam(TravelTeamEntity entity);

    void saveOrUpdateTeam(TravelTeamEntity entity);

    void deleteTeam(TravelTeamEntity entity);

    void deleteTeamByTeamId(Integer teamId) throws DataAccessException;


    /**
     * ------- tour member --------
     */
    TourMemberEntity findTourMemberByUserIdAndTeamId(Integer userId, Integer teamId);

    List<TourMemberEntity> findTourMemberListByUserId(Integer userId);

    List<TourMemberEntity> findTourMemberListByGuideId(Integer guideId);

    void saveOrUpdateTourMember(TourMemberEntity entity);

    void deleteTeamMember(TourMemberEntity entity);

    void updateTourMemberLocation(Integer userId, double longitude, double latitude);

    /**
     * -------- user tracks ---------
     */
    void saveOrUpdateUserTrack(UserTrackEntity userTrackEntity);

    List<UserTrackEntity> findUserTrackByUserId(Integer userId);



    /**
     * -------- scenic comments ---------
     */
    List<ScenicCommentsEntity> findScenicCommentsByScenicId(Integer scenicId);

    List<ScenicInfoEntity> findScenicWithPageAndRows(int page, int rows);

    List<ScenicInfoEntity> findScenicByScenicName(String scenicName);

    Long findScenicCount();

    void saveOrUpdateScenicComments(ScenicCommentsEntity entity);

    void deleteScenic(ScenicInfoEntity entity);

    /**
     * -------- notices ---------
     */
    List<NoticesEntity> findNoticesByNoticeId(Integer noticeId);

    List<NoticesEntity> findNoticesByGuideId(Integer guideId);

    void saveOrUpdateNotices(NoticesEntity entity);

    /**
     * test
     */
    void test();

}
