package com.gmugu.happytour.server;

import com.gmugu.happyhour.message.*;
import com.gmugu.happytour.comment.exception.ServerException;
import com.gmugu.happytour.entity.ScenicInfoEntity;
import com.gmugu.happytour.entity.UserinfoEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by mugu on 16-4-18.
 */
public interface IServer {

    UserinfoEntity getUserinfoByEmain(String email) throws ServerException;

    UserInfoModel loginByEmail(String email, String passwd) throws ServerException;

    void updatePasswdByUserId(Integer userId, String oldPasswd, String newPasswd) throws ServerException;

    UserInfoModel registerByEmail(String email, String passwd) throws ServerException;

    List<ScenicModel> getScenicList() throws ServerException;

    List<TravelTeamModel> findTeamListByGuideUserId(Integer guideUserId) throws ServerException;

    void deleteTeamByTeamId(Integer teamId) throws ServerException;

    List<TravelTeamModel> findTeamListByPassengerUserId(Integer passengerUserId) throws ServerException;

    List<TravelTeamModel> findAllTeamList() throws ServerException;

    Integer createTeam(String name, Integer guideUserId, Integer scenicId, Integer scenicRange) throws ServerException;

    ScenicModel joinTeam(Integer userId, Integer teamId) throws ServerException;

    List<TravelTeamModel> findTeamListNotContainPassengerUserId(Integer passengerUserId) throws ServerException;

    void outTeam(Integer userId, Integer teamId) throws ServerException;

    void updateTourMemberLocation(Integer userId, TrackPointModel trackPointModel) throws ServerException;

    ScenicModel startGuide(Integer guideUserId) throws ServerException;

    void stopGuide(Integer guideUserId) throws ServerException;

    boolean isStartGuide(Integer userId) throws ServerException;

    UserInfoModel updateUserInfo(UserInfoModel userInfoModel) throws ServerException;

    UserInfoModel updateUserHeadImg(Integer userId, String path, String checkCode) throws ServerException;

    void updatePasswdByUserId(Integer userId, String newPasswd) throws ServerException;

    Map<Integer, UserLocationModel> getAllTeamLocation(Integer guideUserId) throws ServerException;

    ScenicModel findScenicInfoByUserId(Integer userId) throws ServerException;

    void saveUserTrack(TrackModel trackModel) throws ServerException;

    Map<Integer, List<TrackModel>> getTeammateTrack(UserInfoModel userInfoModel) throws ServerException;

    ScenicModel findScenicInfoByScenicId(Integer scenicId) throws ServerException;

    List<ScenicCommentsItemModel> findScenicCommentsByScenicId(Integer scenicId) throws ServerException;

    void addComment(Integer userId, Integer scenicId, String comment, Long time) throws ServerException;

    void updateScenicRank(Integer scenicId, Float rank) throws ServerException;

    List<NoticeItemModel> findNotices(Integer userId) throws ServerException;

    void publicNotice(Integer userId, NoticeItemModel noticeItemModel) throws ServerException;

    List<UserinfoEntity> findUserWithPageAndRows(int page, int rows) throws ServerException;

    long findUserCount() throws ServerException;


    void deleteUserByUserId(int id) throws ServerException;

    void updateScenicById(int id, UserInfoModel.UserType userType) throws ServerException;

    List<ScenicInfoEntity> findScenicWithPageAndRows(int page, int rows) throws ServerException;

    long findScenicCount() throws ServerException;

    void deleteScenicById(int id) throws ServerException;

    void updateScenicById(int id, String name, String star, String num, String openTime, String pictureFileName, String describe, double longitude, double latitude) throws ServerException;

    List<ScenicInfoEntity> findScenicInfoByScenicName(String scenicName) throws ServerException;

    List<UserinfoEntity> findUserByNickName(String nickName) throws ServerException;

    void test() throws ServerException;

}
