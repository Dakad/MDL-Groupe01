package be.unamur.info.mdl.dto;


import io.swagger.annotations.ApiModel;
import java.util.LinkedList;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel(value = "Author", description = "Model representing an article author")
public class AuthorDTO {

  @NotBlank(message = "The author name is required")
  private String name;

  private String slug;

  @Builder.Default
  private List<ArticleDTO> articles = new LinkedList<>();
}
