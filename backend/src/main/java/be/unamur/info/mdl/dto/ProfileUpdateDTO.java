package be.unamur.info.mdl.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdateDTO {

  @JsonProperty("avatar_url")
  private String profilePictureURL;

  @JsonProperty("university")
  private String currentUniversity;

  private String domain;

  @JsonProperty("research_groups")
  private List<String> researchGroups;

  private List<String> interests;

  @Email(message = "Invalid email format")
  private String email;

  @JsonProperty("bio")
  private String description;
}
