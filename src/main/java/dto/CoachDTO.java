package dto;

import entities.Coach;

public class CoachDTO {
    
    private int id;
    private String name;
    private String email;
    private String phone;

    public CoachDTO(Coach coach) {
        this.id = coach.getId();
        this.name = coach.getName();
        this.email = coach.getEmail();
        this.phone = coach.getPhone();
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
    
}
