package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@NamedQuery(name = "SportTeam.deleteAllRows", query = "DELETE FROM SportTeam")
@Table(name = "sport_team")
public class SportTeam implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "team_name")
    private String teamName;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "price_per_year")
    private double pricePerYear;
    
    @Column(name = "min_age")
    private int minAge;
    
    @Column(name = "max_age")
    private int maxAge;
    
    @ManyToOne
    private Sport sport;
    
    @ManyToMany(mappedBy = "sportTeams", cascade = CascadeType.PERSIST)
    private List<Coach> coaches = new ArrayList<>();
    
    @OneToMany(mappedBy = "sportTeam", cascade = CascadeType.ALL)
    private List<MemberInfo> memberInfos = new ArrayList<>();

    public SportTeam(String teamName, String description, double pricePerYear, int minAge, int maxAge, Sport sport) {
        this.teamName = teamName;
        this.description = description;
        this.pricePerYear = pricePerYear;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.sport = sport;
    }

    public SportTeam() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public List<Coach> getCoaches() {
        return coaches;
    }

    public void setCoaches(List<Coach> coaches) {
        this.coaches = coaches;
    }

    public List<MemberInfo> getMemberInfos() {
        return memberInfos;
    }

    public void setMemberInfos(List<MemberInfo> memberInfos) {
        this.memberInfos = memberInfos;
    }
    
}
