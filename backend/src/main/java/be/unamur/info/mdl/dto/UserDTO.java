package be.unamur.info.mdl.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDTO {
    public String username;
    public String password;
    public String name;
    public String firstname;
    public String email;

    public UserDTO(String username,String password,String name,String firstname,String email){
        this.username = username;
        this.password = password;
        this.name = name;
        this.firstname = firstname;
        this.email = email;
    }

    public UserDTO(){}
}
