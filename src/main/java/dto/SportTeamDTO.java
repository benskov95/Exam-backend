package dto;

import entities.Coach;
import entities.MemberInfo;
import entities.Player;
import entities.SportTeam;
import java.util.ArrayList;
import java.util.List;

public class SportTeamDTO {
    
    private String teamName;
    private double pricePerYear;
    private int minAge;
    private int maxAge;
    private String sport;
    private String description;
    private List<PlayerDTO> players = new ArrayList<>();
    private List<CoachDTO> coaches = new ArrayList<>();

    public SportTeamDTO(SportTeam sportTeam) {
        this.teamName = sportTeam.getTeamName();
        this.pricePerYear = sportTeam.getPricePerYear();
        this.minAge = sportTeam.getMinAge();
        this.maxAge = sportTeam.getMaxAge();
        this.sport = sportTeam.getSport().getName();
        this.description = sportTeam.getDescription();
    }
    
    public List<PlayerDTO> getPlayerList(List<MemberInfo> memberInfos) {
        List<PlayerDTO> playerDTOs = new ArrayList<>();
        for (MemberInfo m : memberInfos) {
            playerDTOs.add(new PlayerDTO(m.getPlayer()));
        }
        return playerDTOs;
    }
    
    public List<CoachDTO> getCoachList(List<Coach> coaches) {
        List<CoachDTO> coachDTOs = new ArrayList<>();
        for (Coach coach : coaches) {
            coachDTOs.add(new CoachDTO(coach));
        }
        return coachDTOs;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public double getPricePerYear() {
        return pricePerYear;
    }

    public void setPricePerYear(double pricePerYear) {
        this.pricePerYear = pricePerYear;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PlayerDTO> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerDTO> players) {
        this.players = players;
    }

    public List<CoachDTO> getCoaches() {
        return coaches;
    }

    public void setCoaches(List<CoachDTO> coaches) {
        this.coaches = coaches;
    }
    
    
    
}


