package com.gmugu.happytour.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by mugu on 16-5-12.
 */
@Entity
@Table(name = "notices", schema = "", catalog = "happytour_db")
public class NoticesEntity {
    private int id;
    private UserinfoEntity guide;
    private String title;
    private Date time;
    private String content;

    @Id
    @Column(name = "id", nullable = false, updatable = true, insertable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "guide_id", referencedColumnName = "user_id", nullable = false)
    public UserinfoEntity getGuide() {
        return guide;
    }

    public void setGuide(UserinfoEntity guide) {
        this.guide = guide;
    }

    @Basic
    @Column(name = "title", nullable = false, updatable = true, insertable = true)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "time", nullable = false, updatable = true, insertable = true)
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Basic
    @Column(name = "content", nullable = false, updatable = true, insertable = true)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NoticesEntity that = (NoticesEntity) o;

        if (id != that.id) return false;
        if (guide != null ? !guide.equals(that.guide) : that.guide != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        return !(content != null ? !content.equals(that.content) : that.content != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (guide != null ? guide.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}
