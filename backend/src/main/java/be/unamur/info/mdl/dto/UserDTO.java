package be.unamur.info.mdl.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

  private String username;

  private String password;

  private String lastname;

  private String firstname;

  private String email;
}
