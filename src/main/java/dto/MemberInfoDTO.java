package dto;

import entities.MemberInfo;
import java.util.Date;

public class MemberInfoDTO {
    
    private boolean paid;
    private Date datePaid;
    private Date dateJoined;
    private String playerName;
    private String sportTeamName;

    public MemberInfoDTO(MemberInfo memberInfo) {
        this.paid = memberInfo.isPaid();
        this.datePaid = memberInfo.getDatePaid();
        this.dateJoined = memberInfo.getDateJoined();
        this.playerName = memberInfo.getPlayer().getName();
        this.sportTeamName = memberInfo.getSportTeam().getTeamName();
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

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getSportTeamName() {
        return sportTeamName;
    }

    public void setSportTeamName(String sportTeamName) {
        this.sportTeamName = sportTeamName;
    }
    
    
    
}
