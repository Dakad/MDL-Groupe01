package be.unamur.info.mdl.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "PasswordChange", description = "Model representing an PasswordChange")
public class PasswordChangeDTO implements Serializable {

  @NotBlank(message = "The old password is required")
  private String oldPassword;

  @NotBlank(message = "The new password is required")
  @Pattern(
    regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[#&+-_@=£$!%*\\^\\?]?).{8,30}$",
    message = "The new password must be at least 8 characters long, containing 1 uppercase and number"
  )
  private String newPassword;

}
