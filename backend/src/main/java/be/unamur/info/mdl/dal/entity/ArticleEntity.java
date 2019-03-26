package be.unamur.info.mdl.dal.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.PastOrPresent;

import be.unamur.info.mdl.dto.ArticleDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "article")
public class ArticleEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(unique = true, nullable = false)
  private String title;

  @Column(name = "publication_date", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @PastOrPresent
  private LocalDate publicationDate;

  @Column
  @Min(0)
  private float price;

  @Column(name = "nbre_page")
  @Min(1)
  private int nbrePage;

  @Column(name = "nbre_citation")
  @Min(0)
  private int nbreCitation;


  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", unique = true, nullable = false)
  private UserEntity user;

  public ArticleDTO toDTO(){
    return new ArticleDTO(id,title,publicationDate,price,nbrePage,nbreCitation,user.toDTO());
  }

}
