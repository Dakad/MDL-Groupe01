package be.unamur.info.mdl.dto;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class UserDTO implements Serializable {

    @NotBlank()
    private String username;

    @NotBlank()
    private String password;

    @NotBlank()
    private String email;


    private String lastname;


    private String firstname;

}
