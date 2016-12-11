package com.gmugu.happytour.entity;

import javax.persistence.*;

/**
 * Created by mugu on 16-4-22.
 */
@Entity
@Table(name = "travel_team", schema = "", catalog = "happytour_db")
public class TravelTeamEntity {
    private int id;
    private String name;
    private int scenicRange;
    private UserinfoEntity guide;
    private ScenicInfoEntity scenic;
    private int isStarting;

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
    @Column(name = "scenic_range", nullable = false, insertable = true, updatable = true)
    public int getScenicRange() {
        return scenicRange;
    }

    public void setScenicRange(int scenicRange) {
        this.scenicRange = scenicRange;
    }


    @ManyToOne
    @JoinColumn(name = "guide_user_id", referencedColumnName = "user_id", nullable = false)
    public UserinfoEntity getGuide() {
        return guide;
    }

    public void setGuide(UserinfoEntity guide) {
        this.guide = guide;
    }

    @ManyToOne
    @JoinColumn(name = "scenic_id", referencedColumnName = "id", nullable = false)
    public ScenicInfoEntity getScenic() {
        return scenic;
    }

    public void setScenic(ScenicInfoEntity scenic) {
        this.scenic = scenic;
    }

    @Basic
    @Column(name = "is_starting", nullable = true, insertable = true, updatable = true)
    public int getIsStarting() {
        return isStarting;
    }

    public void setIsStarting(int isStarting) {
        this.isStarting = isStarting;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TravelTeamEntity that = (TravelTeamEntity) o;

        if (id != that.id) return false;
        if (scenicRange != that.scenicRange) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (isStarting != that.isStarting) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + scenicRange;
        result = 31 * result + isStarting;
        return result;
    }

}
