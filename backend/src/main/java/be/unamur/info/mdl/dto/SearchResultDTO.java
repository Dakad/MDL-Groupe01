package be.unamur.info.mdl.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import java.util.EnumMap;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@ApiModel(value = "SearchResult", description = "Model representing a result of successful search")
public class SearchResultDTO {

  private SearchResultMetaDTO metas;

  private List<ArticleDTO> articles;

  private List<AuthorDTO> authors;

  private List<UserDTO> users;

  @JsonProperty("sotas")
  private List<StateOfTheArtDTO> statesOfTheArt;



  @ApiModel(description = "Model representing meta data about the successful search results")
  @NoArgsConstructor
  @Getter @Setter
  public static class SearchResultMetaDTO {

    @JsonProperty("articles")
    EnumMap<MetaField, Object> articlesMeta;

    @JsonProperty("sotas")
    EnumMap<MetaField, Object> sotasMeta;

    @JsonProperty("authors")
    EnumMap<MetaField, Object> authorsMeta;

    @JsonProperty("users")
    EnumMap<MetaField, Object> usersMeta;
  }

  @JsonFormat(shape = Shape.STRING)
  public enum MetaField {

    @JsonProperty("total_pages")
    TOTAL_PAGES,

    @JsonProperty("current_page")
    CURRENT_PAGE,

    @JsonProperty("total_size")
    TOTAL_PAGE_SIZE,

    @JsonProperty("size")
    PAGE_SIZE,

    @JsonProperty("ordered_by")
    ORDER_BY,

    @JsonProperty("sorted_by")
    SORT_BY,

    ;

  }
}
