package be.unamur.info.mdl.dto;

import java.time.LocalDate;
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

  @PastOrPresent(message = "publicationDate cannot be in the futur")
  private LocalDate publicationDate;

  @PositiveOrZero(message = "The price cannont be in the negative")
  private float price;

  @PositiveOrZero(message = "The number of Page cannot be negative")
  private int nbrePage;

  @PositiveOrZero(message = "The number of citation cannot be negative")
  private int nbreCitation;

  private UserDTO user;

  private Set<TagDTO> tags;

  public ArticleDTO(String title, LocalDate publicationDate, float price, int nbrePage,
    int nbreCitation, UserDTO user, Set<TagDTO> tags) {
    this.title = title;
    this.publicationDate = publicationDate;
    this.price = price;
    this.nbrePage = nbrePage;
    this.nbreCitation = nbreCitation;
    this.user = user;
    this.tags = tags;
  }
}
