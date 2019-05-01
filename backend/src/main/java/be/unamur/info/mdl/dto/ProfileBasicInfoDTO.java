package be.unamur.info.mdl.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

/**
 * A data transfer object containing a user's profile basic information
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ProfileBasicInfo", description = "Model representing an user's profile basic information")
public class ProfileBasicInfoDTO {

  @NotBlank(message = "The name is required")
  private String name;

  @NotBlank(message = "The firstname is required")
  private String firstname;


  private String domain;


  private UniversityInfoDTO university;

  @NotBlank(message = "The email is required")
  private String email;

  @JsonProperty("avatar")
  private String profilePictureURL;
}
