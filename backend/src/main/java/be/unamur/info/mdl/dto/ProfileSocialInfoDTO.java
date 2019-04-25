package be.unamur.info.mdl.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Model representing an user's profile social information")
public class ProfileSocialInfoDTO {

  private String bio = "This user hasn't added a bio.";

  @JsonAlias("num_follows")
  private int numFollows;

  @JsonAlias("num_followers")
  private int numFollowers;

  @JsonAlias("facebook_url")
  private String facebook;

  @JsonAlias("twitter_url")
  private String twitter;

  @JsonAlias("linkedin_url")
  private String linkedin;
}
