package be.unamur.info.mdl.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Model representing an user's profile social information")
public class ProfileSocialInfoDTO {
  @Nullable
  private String bio;
  private int numfollow;
  private int numfollowers;
  @Nullable
  private String facebook;
  @Nullable
  private String twitter;
  @Nullable
  private String linkedin;
}
