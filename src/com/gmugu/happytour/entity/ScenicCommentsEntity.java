package com.gmugu.happytour.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by mugu on 16-4-22.
 */
@Entity
@Table(name = "scenic_comments", schema = "", catalog = "happytour_db")
public class ScenicCommentsEntity {
    private int id;
    private ScenicInfoEntity scenic;
    private UserinfoEntity user;
    private String comments;
    private Date time;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "comments", nullable = true, insertable = true, updatable = true, length = 65535)
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


    @ManyToOne
    @JoinColumn(name = "scenic_id", referencedColumnName = "id", nullable = false)
    public ScenicInfoEntity getScenic() {
        return scenic;
    }

    public void setScenic(ScenicInfoEntity scenic) {
        this.scenic = scenic;
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
    @Column(name = "time", nullable = true, insertable = true, updatable = true)
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScenicCommentsEntity that = (ScenicCommentsEntity) o;

        if (id != that.id) return false;
        if (scenic != null ? !scenic.equals(that.scenic) : that.scenic != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        if (comments != null ? !comments.equals(that.comments) : that.comments != null) return false;
        return !(time != null ? !time.equals(that.time) : that.time != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (scenic != null ? scenic.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
}
