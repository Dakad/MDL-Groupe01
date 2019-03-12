package be.unamur.info.mdl.dto;

import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class UserDTO implements Serializable {

    @NotBlank()
    private String username;

    @NotBlank()
    private String password;

    @NotBlank()
    @Email()
    private String email;


    private String lastname;


    private String firstname;


    public UserDTO(String username,String password,String name,String firstname,String email){
        this.username = username;
        this.password = password;
        this.lastname = name;
        this.firstname = firstname;
        this.email = email;
    }

    public UserDTO(){}

}
