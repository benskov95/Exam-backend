package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

@Entity
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
    
    @ManyToOne
    private Player player;
    
    @ManyToOne
    private SportTeam sportTeam;

    public MemberInfo(boolean paid, Date datePaid, Player player) {
        this.paid = paid;
        this.datePaid = datePaid;
        this.player = player;
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
    
}
