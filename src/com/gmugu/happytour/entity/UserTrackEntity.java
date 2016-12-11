package com.gmugu.happytour.entity;


import javax.persistence.*;
import java.util.Date;

/**
 * Created by mugu on 16-4-29.
 */
@Entity
@Table(name = "user_track", catalog = "", schema = "happytour_db")
public class UserTrackEntity {
    private int id;
    private UserinfoEntity user;
    private Date beginTime;
    private Date endTime;
    private String runningRecode;
    private float distance;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    public UserinfoEntity getUser() {
        return user;
    }

    public void setUser(UserinfoEntity user) {
        this.user = user;
    }

    @Basic
    @Column(name = "time_begin", nullable = false, insertable = true, updatable = true)
    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    @Basic
    @Column(name = "time_end", nullable = false, insertable = true, updatable = true)
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "running_record", nullable = false, insertable = true, updatable = true)
    public String getRunningRecode() {
        return runningRecode;
    }

    public void setRunningRecode(String runningRecode) {
        this.runningRecode = runningRecode;
    }

    @Basic
    @Column(name = "distance", nullable = false, insertable = true, updatable = true)
    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }
}
