package com.gmugu.happytour.entity;

import javax.persistence.*;

/**
 * Created by mugu on 16-4-22.
 */
@Entity
@Table(name = "tour_member", schema = "", catalog = "happytour_db")
public class TourMemberEntity {
    private int id;
    private Double curLog;
    private Double curLat;
    private TravelTeamEntity team;
    private UserinfoEntity user;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "cur_log", nullable = true, insertable = true, updatable = true, precision = 0)
    public Double getCurLog() {
        return curLog;
    }

    public void setCurLog(Double curLog) {
        this.curLog = curLog;
    }

    @Basic
    @Column(name = "cur_lat", nullable = true, insertable = true, updatable = true, precision = 0)
    public Double getCurLat() {
        return curLat;
    }

    public void setCurLat(Double curLat) {
        this.curLat = curLat;
    }

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id", nullable = false)
    public TravelTeamEntity getTeam() {
        return team;
    }

    public void setTeam(TravelTeamEntity team) {
        this.team = team;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    public UserinfoEntity getUser() {
        return user;
    }

    public void setUser(UserinfoEntity user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TourMemberEntity entity = (TourMemberEntity) o;

        if (id != entity.id) return false;
        if (curLog != null ? !curLog.equals(entity.curLog) : entity.curLog != null) return false;
        if (curLat != null ? !curLat.equals(entity.curLat) : entity.curLat != null) return false;
        if (team != null ? !team.equals(entity.team) : entity.team != null) return false;
        return !(user != null ? !user.equals(entity.user) : entity.user != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (curLog != null ? curLog.hashCode() : 0);
        result = 31 * result + (curLat != null ? curLat.hashCode() : 0);
        result = 31 * result + (team != null ? team.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}
