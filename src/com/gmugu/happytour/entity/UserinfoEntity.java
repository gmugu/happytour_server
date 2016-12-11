package com.gmugu.happytour.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by mugu on 16-4-22.
 */
@Entity
@Table(name = "userinfo", schema = "", catalog = "happytour_db")
public class UserinfoEntity {
    private int userId;
    private String portrait;
    private String portraitCheckCode;
    private String nickname;
    private String email;
    private String password;
    private String gender;
    private Integer height;
    private Float weight;
    private Date birthday;
    private String city;
    private String phone;
    private String userType;
//    private Collection<TravelTeamEntity> travelTeamById;

    @Id
    @Column(name = "user_id", nullable = false, insertable = true, updatable = true)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "portrait", nullable = true, insertable = true, updatable = true, length = 100)
    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    @Basic
    @Column(name = "portrait_check_code", nullable = true, insertable = true, updatable = true, length = 45)
    public String getPortraitCheckCode() {
        return portraitCheckCode;
    }

    public void setPortraitCheckCode(String portraitCheckCode) {
        this.portraitCheckCode = portraitCheckCode;
    }

    @Basic
    @Column(name = "nickname", nullable = true, insertable = true, updatable = true, length = 45)
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Basic
    @Column(name = "email", nullable = false, insertable = true, updatable = true, length = 45)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "password", nullable = false, insertable = true, updatable = true, length = 45)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "gender", nullable = true, insertable = true, updatable = true, length = 4)
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "height", nullable = true, insertable = true, updatable = true)
    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    @Basic
    @Column(name = "weight", nullable = true, insertable = true, updatable = true, precision = 0)
    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    @Basic
    @Column(name = "birthday", nullable = true, insertable = true, updatable = true)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Basic
    @Column(name = "city", nullable = true, insertable = true, updatable = true, length = 45)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "phone", nullable = true, insertable = true, updatable = true, length = 20)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "user_type", nullable = true, insertable = true, updatable = true, length = 15)
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserinfoEntity that = (UserinfoEntity) o;

        if (userId != that.userId) return false;
        if (portrait != null ? !portrait.equals(that.portrait) : that.portrait != null) return false;
        if (portraitCheckCode != null ? !portraitCheckCode.equals(that.portraitCheckCode) : that.portraitCheckCode != null)
            return false;
        if (nickname != null ? !nickname.equals(that.nickname) : that.nickname != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (gender != null ? !gender.equals(that.gender) : that.gender != null) return false;
        if (height != null ? !height.equals(that.height) : that.height != null) return false;
        if (weight != null ? !weight.equals(that.weight) : that.weight != null) return false;
        if (birthday != null ? !birthday.equals(that.birthday) : that.birthday != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (userType != null ? !userType.equals(that.userType) : that.userType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (portrait != null ? portrait.hashCode() : 0);
        result = 31 * result + (portraitCheckCode != null ? portraitCheckCode.hashCode() : 0);
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (height != null ? height.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (userType != null ? userType.hashCode() : 0);
        return result;
    }

//    @OneToMany(mappedBy = "guide")
//    public Collection<TravelTeamEntity> getTravelTeamById() {
//        return travelTeamById;
//    }
//
//    public void setTravelTeamById(Collection<TravelTeamEntity> travelTeamById) {
//        this.travelTeamById = travelTeamById;
//    }
}
