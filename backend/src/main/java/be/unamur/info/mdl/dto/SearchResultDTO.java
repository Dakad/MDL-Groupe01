package be.unamur.info.mdl.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class SearchResultDTO {
  private List<ArticleDTO> articles;
  private List<UserDTO> users;
  private List<StateOfTheArtDTO> statesOfTheArt;
}
