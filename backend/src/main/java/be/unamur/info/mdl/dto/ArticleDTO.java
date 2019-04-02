package be.unamur.info.mdl.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ArticleDTO {
  private long id;
  private String title;
  private LocalDate publicationDate;
  private float price;
  private int nbrePage;
  private int nbreCitation;
  private UserDTO user;

  public ArticleDTO(long id, String title, LocalDate publicationDate, float price, int nbrePage, int nbreCitation, UserDTO user){
    this.id = id;
    this.title = title;
    this.publicationDate = publicationDate;
    this.price = price;
    this.nbrePage = nbrePage;
    this.nbreCitation = nbreCitation;
    this.user = user;
  }
}
