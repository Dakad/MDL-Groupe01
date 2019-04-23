package be.unamur.info.mdl.dto;

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
  private String Name;

  @NotBlank(message = "The firstname is required")
  private String Firstname;

  @Nullable
  private String Domain;

  @Nullable
  private UniversityInfoDTO University;

  @NotBlank(message = "The email is required")
  private String email;

  @Nullable
  private String profilePictureURL;
}
