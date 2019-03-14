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
  private String title;
  @Column(name = "publiation_date")
  private LocalDate publicationDate;
  @Column
  private float price;
  @Column(name = "nbre_page")
  private int nbrePage;
  @Column(name= "nbre_citation")
  private int nbreCitation;

  public ArticleEntity(String title, LocalDate publicationDate, float price, int nbrePage,
      int nbreCitation) {
    this.title = title;
    this.publicationDate = publicationDate;
    this.price = price;
    this.nbrePage = nbrePage;
    this.nbreCitation = nbreCitation;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getNbrePage() {
    return nbrePage;
  }


  public void setNbrePage(int nbrePage) {
    this.nbrePage = nbrePage;
  }

  public int getNbreCitation() {
    return nbreCitation;
  }

  public void setNbreCitation(int nbreCitation) {
    this.nbreCitation = nbreCitation;
  }

  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public LocalDate getPublicationDate() {
    return publicationDate;
  }

  public void setPublicationDate(LocalDate publicationDate) {
    this.publicationDate = publicationDate;
  }
}
