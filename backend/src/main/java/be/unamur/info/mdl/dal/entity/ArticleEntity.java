package be.unamur.info.mdl.dal.entity;

import be.unamur.info.mdl.dto.ArticleDTO;
import be.unamur.info.mdl.dto.TagDTO;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
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
  private Long id;

  @Column(unique = true, nullable = false)
  private String title;
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

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", unique = true, nullable = false)
  private UserEntity user;

  @ManyToMany(cascade = {
    CascadeType.PERSIST,
    CascadeType.MERGE})
  @JoinTable(name = "tag_article",
    joinColumns = {@JoinColumn(name = "article_id")},
    inverseJoinColumns = {@JoinColumn(name = "tag_id")})
  private Set<TagEntity> tags;


  @Column(name = "abstract", unique = true, nullable = false)
  private String content;


  @Column(name = "nbre_vues")
  @PositiveOrZero
  private int nbreVues = 0;

  @Column(name = "created_at")
  private LocalDate createdAt = LocalDate.now();


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


  public static ArticleEntity of(ArticleDTO articleData) {
    UserEntity user = UserEntity.of(articleData.getUser());
    Set<TagEntity> tags = articleData.getTags().stream().map(t -> TagEntity.of(t))
      .collect(Collectors.toSet());

    return new ArticleEntity(null, articleData.getTitle(), articleData.getPublicationDate(),
      articleData.getPrice(),
      articleData.getNbrePage(), articleData.getNbreCitation(),user,
    tags, null, 0, null, null
      , null, null);
  }

  public ArticleDTO toDTO() {
    Set<TagDTO> listOfTags = tags.stream().map(t -> t.toDTO()).collect(Collectors.toSet());
    return new ArticleDTO(id, title, publicationDate, price, nbrePages, nbreCitations, user.toDTO(),
      listOfTags);
  }

}
