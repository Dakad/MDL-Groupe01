package be.unamur.info.mdl.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Model representing an bookmark on an article or sota")
public class BookmarkDTO {

  private String note;

  @JsonProperty(value = "article", access = Access.READ_ONLY)
  private ArticleDTO article;

  @JsonProperty(value = "sota", access = Access.READ_ONLY)
  private StateOfTheArtDTO sota;
}
