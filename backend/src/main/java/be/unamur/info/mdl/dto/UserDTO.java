package be.unamur.info.mdl.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "User", description = "Model representing an user data")
public class UserDTO extends CredentialDTO {

  @JsonIgnore
  private Long id;

  @NotBlank(message = "The email is required")
  @Email(message = "The email provided is not valid")
  private String email;

  private String lastname;

  private String firstname;

  @JsonProperty("avatar")
  private String profilePicUrl;

  private String domain;

  private String organisation;


  public UserDTO(String username, String password, String lastname, String firstname, String email,
                 String profilePicUrl, String domain, String organisation) {
    super(username, password);
    this.email = email;
    this.lastname = lastname;
    this.firstname = firstname;
    this.profilePicUrl = profilePicUrl;
    this.domain = domain;
    this.organisation = organisation;
  }
}
