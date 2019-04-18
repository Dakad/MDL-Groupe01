package be.unamur.info.mdl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
