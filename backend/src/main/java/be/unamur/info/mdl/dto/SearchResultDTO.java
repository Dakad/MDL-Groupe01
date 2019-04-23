package be.unamur.info.mdl.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel(value = "SearchResult", description = "Model representing a result of successful search")
public class SearchResultDTO {

  private List<ArticleDTO> articles;

  private List<AuthorDTO> authors;

  private List<UserDTO> users;

  @JsonProperty("sotas")
  private List<StateOfTheArtDTO> statesOfTheArt;
}
