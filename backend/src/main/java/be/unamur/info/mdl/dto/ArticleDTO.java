package be.unamur.info.mdl.dto;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO {

  private Long id;

  @NotBlank(message = "The title is required")
  private String title;

  @NotBlank(message = "The content(abstract) is required")
  private String content;

  @PastOrPresent(message = "publicationDate cannot be in the futur")
  private LocalDate publicationDate;

  @PositiveOrZero(message = "The price cannont be in the negative")
  private float price;

  @PositiveOrZero(message = "The number of Page cannot be negative")
  private int nbrePage;

  @PositiveOrZero(message = "The number of citation cannot be negative")
  private int nbreCitation;

  private UserDTO user;

  private Set<TagDTO> tags = Collections.emptySet();

}
