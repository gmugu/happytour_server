package com.gmugu.happytour.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Collection;

/**
 * Created by mugu on 16-4-22.
 */
@Entity
@Table(name = "scenic_info", schema = "", catalog = "happytour_db")
public class ScenicInfoEntity {
    private int id;
    private String name;
    private Float star;
    private Integer num;
    private Date openTime;
    private String picture;
    private String describe;
    private Double longitude;
    private Double latitude;
//    private Collection<TravelTeamEntity> travelTeamById;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "star", nullable = true, insertable = true, updatable = true, precision = 0)
    public Float getStar() {
        return star;
    }

    public void setStar(Float star) {
        this.star = star;
    }

    @Basic
    @Column(name = "num", nullable = true, insertable = true, updatable = true)
    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Basic
    @Column(name = "open_time", nullable = true, insertable = true, updatable = true)
    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    @Basic
    @Column(name = "picture", nullable = true, insertable = true, updatable = true, length = 60)
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Basic
    @Column(name = "describe1", nullable = true, insertable = true, updatable = true, length = 65535)
    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Basic
    @Column(name = "longitude", nullable = true, insertable = true, updatable = true, precision = 0)
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "latitude", nullable = true, insertable = true, updatable = true, precision = 0)
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

//    @OneToMany(mappedBy = "scenic")
//    public Collection<TravelTeamEntity> getTravelTeamById() {
//        return travelTeamById;
//    }
//
//    public void setTravelTeamById(Collection<TravelTeamEntity> travelTeamById) {
//        this.travelTeamById = travelTeamById;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        ScenicInfoEntity that = (ScenicInfoEntity) o;
//
//        if (id != that.id) return false;
//        if (name != null ? !name.equals(that.name) : that.name != null) return false;
//        if (star != null ? !star.equals(that.star) : that.star != null) return false;
//        if (num != null ? !num.equals(that.num) : that.num != null) return false;
//        if (openTime != null ? !openTime.equals(that.openTime) : that.openTime != null) return false;
//        if (picture != null ? !picture.equals(that.picture) : that.picture != null) return false;
//        if (describe != null ? !describe.equals(that.describe) : that.describe != null) return false;
//        if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null) return false;
//        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null) return false;
//        return !(travelTeamById != null ? !travelTeamById.equals(that.travelTeamById) : that.travelTeamById != null);
//
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScenicInfoEntity that = (ScenicInfoEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (star != null ? !star.equals(that.star) : that.star != null) return false;
        if (num != null ? !num.equals(that.num) : that.num != null) return false;
        if (openTime != null ? !openTime.equals(that.openTime) : that.openTime != null) return false;
        if (picture != null ? !picture.equals(that.picture) : that.picture != null) return false;
        if (describe != null ? !describe.equals(that.describe) : that.describe != null) return false;
        if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null) return false;
        return !(latitude != null ? !latitude.equals(that.latitude) : that.latitude != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (star != null ? star.hashCode() : 0);
        result = 31 * result + (num != null ? num.hashCode() : 0);
        result = 31 * result + (openTime != null ? openTime.hashCode() : 0);
        result = 31 * result + (picture != null ? picture.hashCode() : 0);
        result = 31 * result + (describe != null ? describe.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
//        result = 31 * result + (travelTeamById != null ? travelTeamById.hashCode() : 0);
        return result;
    }
}
