package be.unamur.info.mdl.dto;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CredentialDTO implements Serializable {

  @NotBlank
  private String username;

  @NotBlank
  private String password;
}
