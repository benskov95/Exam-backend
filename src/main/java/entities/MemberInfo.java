package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@NamedQuery(name = "MemberInfo.deleteAllRows", query = "DELETE FROM MemberInfo")
@Table(name = "member_info")
public class MemberInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "paid")
    private boolean paid;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "date_paid")
    private Date datePaid;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "date_joined")
    private Date dateJoined;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Player player;
    
    @ManyToOne
    private SportTeam sportTeam;

    public MemberInfo(boolean paid, Player player, SportTeam sportTeam) {
        this.paid = paid;
        this.dateJoined = new Date();
        this.player = player;
        this.sportTeam = sportTeam;
    }

    public MemberInfo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Date getDatePaid() {
        return datePaid;
    }

    public void setDatePaid(Date datePaid) {
        this.datePaid = datePaid;
    }

    public Date getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public SportTeam getSportTeam() {
        return sportTeam;
    }

    public void setSportTeam(SportTeam sportTeam) {
        this.sportTeam = sportTeam;
    }
    
}
