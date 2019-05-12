package be.unamur.info.mdl.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.annotations.ApiModel;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Tag", description = "Model representing an Tag or Category")
public class CredentialDTO {

  @NotBlank(message = "The username is required")
  private String username;


  @NotBlank(message = "The password is required")
  @Pattern(
    regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[#&+-_@=£$!%*\\^\\?]?).{8,30}$",
    message = "The password must be at least 8 characters long, containing 1 uppercase and number"
  )
  @JsonProperty(value = "password", access = Access.WRITE_ONLY)
  private String password;
}
