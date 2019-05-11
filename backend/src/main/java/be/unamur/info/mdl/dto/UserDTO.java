package be.unamur.info.mdl.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "User", description = "Model representing an user data")
public class UserDTO extends CredentialDTO {

  @NotBlank(message = "The email is required")
  @Email(message = "The email provided is not valid")
  private String email;

  private String lastname;

  private String firstname;

  @Builder.Default
  @JsonProperty("avatar")
  private String profilePicUrl = "https://i.imgur.com/0MC7ZG4.jpg";

  @Builder.Default
  private String domain = "Unknown";

  @Builder.Default
  private String organisation = "Not defined";

}
