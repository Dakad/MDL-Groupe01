package be.unamur.info.mdl.dal.entity;

import be.unamur.info.mdl.dto.ArticleDTO;
import be.unamur.info.mdl.dto.TagDTO;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.Set;

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

  @Column(name="abstract", unique = true, nullable = false)
  private String content;

  @Column(name = "publication_date", nullable = false)
  @PastOrPresent
  private LocalDate publicationDate;

  @Column
  @PositiveOrZero
  private float price;

  @Column(name = "nbre_pages")
  @PositiveOrZero
  private int nbrePages;

  @Column(name = "nbre_citations")
  @PositiveOrZero
  private int nbreCitations;

  @Column(name = "nbre_vues")
  @PositiveOrZero
  private int nbreVues = 0;

  @Column(name = "created_at")
  private LocalDate createdAt = LocalDate.now();


  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", unique = true, nullable = false)
  private UserEntity user;


  @ManyToMany(mappedBy = "articles")
  private Set<BookmarkEntity> bookmark;


  @ManyToMany(cascade = {
    CascadeType.PERSIST,
    CascadeType.MERGE})
  @JoinTable(name = "article_references",
    joinColumns = {@JoinColumn(name = "article_id")},
    inverseJoinColumns = {@JoinColumn(name = "reference_id")})
  private Set<ArticleEntity> references;


  @ManyToMany(mappedBy = "articles")
  private Set<StateOfTheArtEntity> state_of_the_art;


  @ManyToMany(cascade = {
    CascadeType.PERSIST,
    CascadeType.MERGE})
  @JoinTable(name = "tag_article",
    joinColumns = {@JoinColumn(name = "article_id")},
    inverseJoinColumns = {@JoinColumn(name = "tag_id")})
  private Set<TagEntity> tags;



  public ArticleDTO toDTO() {
    Set<TagDTO> listOfTags = tags.stream().map(t -> t.toDTO()).collect(Collectors.toSet());
    return new ArticleDTO(id, title, publicationDate, price, nbrePages, nbreCitations, user.toDTO(),
      listOfTags);
  }

}
