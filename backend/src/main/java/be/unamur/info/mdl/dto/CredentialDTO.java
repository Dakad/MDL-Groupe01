package be.unamur.info.mdl.dto;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CredentialDTO implements Serializable {

  @NotBlank(message = "The username cannot be empty")
  private String username;

  @NotBlank(message = "The password cannot be empty")
  private String password;
}
