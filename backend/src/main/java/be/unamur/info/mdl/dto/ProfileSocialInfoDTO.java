package be.unamur.info.mdl.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileSocialInfoDTO {

  private String bio = "This user hasn't added a bio.";

  @JsonAlias("num_follows")
  private int numFollows;

  @JsonAlias("num_followers")
  private int numFollowers;

  private String facebook;

  private String twitter;

  private String linkedin;
}
