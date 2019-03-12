package be.unamur.info.mdl.dal.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ArticleEntity {

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private long id;
  @Column(unique = true, nullable = false)
  public String title;
  @Column(name = "publiation_date")
  private LocalDate publicationDate;
  @Column
  private float price;
  @Column(name = "nbre_page")
  private int nbrePage;
  @Column(name= "nbre_citation")
  private int nbreCitation;


}
