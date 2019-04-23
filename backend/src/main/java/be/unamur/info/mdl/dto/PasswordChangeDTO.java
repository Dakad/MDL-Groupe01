package be.unamur.info.mdl.dto;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordChangeDTO implements Serializable {

  @NotBlank
  private String oldPassword;

  @Size(min = 8, max = 20)
  private String newPassword;

}
