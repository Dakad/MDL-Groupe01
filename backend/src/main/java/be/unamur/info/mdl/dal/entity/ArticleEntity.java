package be.unamur.info.mdl.dal.entity;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
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
  private long id;

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

  @Column(name = "nbre_vues")
  @PositiveOrZero
  private int nbreVues = 0;

  @Column(name = "created_at")
  private LocalDate createdAt = LocalDate.now();



  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", unique = true, nullable = false)
  private UserEntity user;

    //Relation entre Articles et Bookmark
  @ManyToMany(mappedBy = "articles")
  private Set<BookmarkEntity>bookmark;

  // Auto referece des articles
  @ManyToMany(cascade = {
    CascadeType.PERSIST,
    CascadeType.MERGE})
  @JoinTable(name = "article_references",
    joinColumns = {@JoinColumn(name = "article_id")},
    inverseJoinColumns = {@JoinColumn(name = "reference_id")})
  private Set<ArticleEntity> references = new LinkedHashSet<>();

  @ManyToMany(mappedBy = "articles")
  private Set <StateOfTheArtEntity> state_of_the_art;

  @ManyToMany(cascade = {
    CascadeType.PERSIST,
    CascadeType.MERGE})
  @JoinTable(name = "tag_article",
    joinColumns = {@JoinColumn(name = "article_id")},
    inverseJoinColumns = {@JoinColumn(name = "tag_id")})
  private Set<TagEntity> tags = new LinkedHashSet<>();



}
