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
  private long id;

  private String title;

  private LocalDate publicationDate;

  private float price;

  private int nbrePage;

  private int nbreCitation;

  private UserDTO user;

  private Set<TagDTO> tags;
}
