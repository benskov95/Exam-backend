package dto;

import entities.Coach;
import entities.SportTeam;
import java.util.ArrayList;
import java.util.List;

public class CoachDTO {
    
    private int id;
    private String name;
    private String email;
    private String phone;
    private List<SportTeamDTO> sportTeams = new ArrayList<>();

    public CoachDTO(Coach coach) {
        this.id = coach.getId();
        this.name = coach.getName();
        this.email = coach.getEmail();
        this.phone = coach.getPhone();
        this.sportTeams = getSportTeamList(coach.getSportTeams());
    }
    
    private List<SportTeamDTO> getSportTeamList(List<SportTeam> sportTeamList) {
        List<SportTeamDTO> sDTO = new ArrayList<>();
        for (SportTeam s : sportTeamList) {
            sDTO.add(new SportTeamDTO(s));
        }
        return sDTO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<SportTeamDTO> getSportTeams() {
        return sportTeams;
    }

    public void setSportTeams(List<SportTeamDTO> sportTeams) {
        this.sportTeams = sportTeams;
    }
    
}
