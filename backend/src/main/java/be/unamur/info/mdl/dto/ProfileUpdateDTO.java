package be.unamur.info.mdl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdateDTO {
  private String profilePicURL;
  private String currentUniversity;
  private String domain;
  private List<String> researchGroups;
  private List<String> interests;
  @Email(message = "Invalid email format")
  private String email;
  private String description;
}
