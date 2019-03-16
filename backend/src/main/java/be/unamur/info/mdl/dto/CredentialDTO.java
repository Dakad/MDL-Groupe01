package be.unamur.info.mdl.dto;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CredentialDTO implements Serializable {

  @NotBlank(message = "The username cannot be empty or blank")
  private String username;


  @NotBlank(message = "The password cannot be empty or blank")
  @Pattern(
      regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[#&+-_@=£$!%*\\^\\?]?).{8,30}$",
      message = "The password must be at least 8 characters long, containing 1 uppercase and number"
  )
  private String password;
}
