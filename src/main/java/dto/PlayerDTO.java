package dto;

import entities.MemberInfo;
import entities.Player;
import java.util.ArrayList;
import java.util.List;

public class PlayerDTO {
    
    private int id;
    private String name;
    private String email;
    private String phone;
    private int age;
    private List<MemberInfoDTO> memberInfoDTOs = new ArrayList<>();

    public PlayerDTO(Player player) {
        this.id = player.getId();
        this.name = player.getName();
        this.email = player.getEmail();
        this.phone = player.getPhone();
        this.age = player.getAge();
        this.memberInfoDTOs = getInfoList(player.getMemberInfos());
    }
    
    private List<MemberInfoDTO> getInfoList(List<MemberInfo> memberInfos) {
        List<MemberInfoDTO> infoDTOs = new ArrayList<>();
        for (MemberInfo m : memberInfos) {
            infoDTOs.add(new MemberInfoDTO(m));
        }
        return infoDTOs;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<MemberInfoDTO> getMemberInfoDTOs() {
        return memberInfoDTOs;
    }

    public void setMemberInfoDTOs(List<MemberInfoDTO> memberInfoDTOs) {
        this.memberInfoDTOs = memberInfoDTOs;
    }
    
}
