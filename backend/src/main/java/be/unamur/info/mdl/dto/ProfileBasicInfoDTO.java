package be.unamur.info.mdl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;

/**
 * A data transfer object containing a user's profile basic information
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileBasicInfoDTO {

  @NotBlank
  private String Name;
  @NotBlank
  private String Firstname;
  @Nullable
  private String Domain;
  @Nullable
  private UniversityInfoDTO University;
  @NotBlank @Nullable
  private String email;
  @Nullable
  private String profilePictureURL;
}
