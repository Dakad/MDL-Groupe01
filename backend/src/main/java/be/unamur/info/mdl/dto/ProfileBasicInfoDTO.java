package be.unamur.info.mdl.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A data transfer object containing a user's profile basic information
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ProfileBasicInfo", description = "Model representing an user's profile basic information")
public class ProfileBasicInfoDTO {

  @JsonIgnore
  public static final String DEFAULT_PROFILE_PICTURE_URL = "https://www.w3schools.com/bootstrap/img_avatar1.png";


  @NotBlank(message = "The name is required")
  private String name;

  @NotBlank(message = "The firstname is required")
  private String firstname;


  private String domain;


  private UniversityInfoDTO university;

  @NotBlank(message = "The email is required")
  private String email;

  @JsonProperty("avatar_url")
  private String profilePictureURL = DEFAULT_PROFILE_PICTURE_URL;
}
