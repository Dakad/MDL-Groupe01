package be.unamur.info.mdl.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO {
  private Long id;

  private String title;

  private LocalDate publicationDate;

  private float price;

  private int nbrePage;

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
