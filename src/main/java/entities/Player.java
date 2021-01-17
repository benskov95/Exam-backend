package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "player")
public class Player implements Serializable {

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
    
    @Column(name = "age")
    private int age;
    
    @OneToMany(mappedBy = "player")
    private List<MemberInfo> memberInfos;

    public Player(String name, String email, String phone, int age, User user) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.user = user;
    }

    public Player() {
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<MemberInfo> getMemberInfos() {
        return memberInfos;
    }

    public void setMemberInfos(List<MemberInfo> memberInfos) {
        this.memberInfos = memberInfos;
    }
    
}
