package be.unamur.info.mdl.dto;


import java.util.LinkedList;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorDTO {

  @NotBlank
  private String name;

  private String slug;

  @Builder.Default
  private List<ArticleDTO> articles = new LinkedList<>();
}
