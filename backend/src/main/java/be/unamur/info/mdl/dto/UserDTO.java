package be.unamur.info.mdl.dto;

import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
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

}
