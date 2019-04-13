package be.unamur.info.mdl.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchResultDTO {

  private List<ArticleDTO> articles;

  private List<AuthorDTO> authors;

  private List<UserDTO> users;

  @JsonProperty("sotas")
  private List<StateOfTheArtDTO> statesOfTheArt;
}
