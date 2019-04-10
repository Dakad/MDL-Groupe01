package be.unamur.info.mdl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
  private String Domain;
  private UniversityInfoDTO University;
  @NotBlank
  private String email;
  private String profilePictureURL;
}
