package dto;

import entities.Player;

public class PlayerDTO {
    
    private String name;
    private int age;

    public PlayerDTO(Player player) {
        this.name = player.getName();
        this.age = player.getAge();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
}
