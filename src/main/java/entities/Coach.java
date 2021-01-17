package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@NamedQuery(name = "Coach.deleteAllRows", query = "DELETE FROM Coach")
@Table(name = "coach")
public class Coach implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @JoinColumn(name = "username")
    @OneToOne(cascade = CascadeType.ALL)
    private User user;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "email", unique = true)
    private String email;
    
    @Column(name = "phone", unique = true)
    private String phone;
    
    @ManyToMany
    private List<SportTeam> sportTeams = new ArrayList<>();

    public Coach(String name, String email, String phone, User user) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.user = user;
    }
    
    public Coach() {
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public List<SportTeam> getSportTeams() {
        return sportTeams;
    }

    public void setSportTeams(List<SportTeam> sportTeams) {
        this.sportTeams = sportTeams;
    }
    
}
