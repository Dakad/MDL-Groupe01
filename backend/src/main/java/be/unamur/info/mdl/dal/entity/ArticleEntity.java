package be.unamur.info.mdl.dal.entity;

import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ArticleEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(unique = true, nullable = false)
  private String title;

  @Column(name = "publiation_date", nullable = false)
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


  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", unique = true)
  private UserEntity user;

  

}
