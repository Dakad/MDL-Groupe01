package be.unamur.info.mdl.dal.entity;

import be.unamur.info.mdl.dto.ArticleDTO;
import be.unamur.info.mdl.dto.ArticleDTO.ArticleDTOBuilder;
import be.unamur.info.mdl.dto.TagDTO;
import java.time.LocalDate;
import java.util.List;
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
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "article")
public class ArticleEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(unique = true, nullable = false)
  private String reference;

  @Column(unique = true, nullable = false)
  private String title;

  @Column(name = "abstract", unique = true, nullable = false)
  private String content;

  @Column(name = "url", unique = true, nullable = false)
  private String url;

  @Column(name = "journal", nullable = false)
  private String journal;

  @Column(name = "journal_volume")
  private String journalVolume;

  @Column(name = "journal_number")
  private String journalNumber;

  @Column(name = "publisher")
  private String publisher;

  @Column(name = "publication_year", nullable = false)
  private int publicationYear;

  @Column(name = "publication_month")
  private String publicationMonth;

  @Column
  private float price;

  @Column(name = "pages")
  private String pages;

  @Column(name = "nb_citations")
  @PositiveOrZero
  private int nbCitations;

  @Column(name = "nb_views")
  @PositiveOrZero
  private int nbViews;

  @Column(name = "created_at")
  private LocalDate createdAt = LocalDate.now();


  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "category_id", unique = true, nullable = false)
  private TagEntity category;


  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", unique = true, nullable = false)
  private UserEntity user;


  @ManyToMany(cascade = {
    CascadeType.PERSIST,
    CascadeType.MERGE})
  @JoinTable(
    name = "article_authors",
    joinColumns = @JoinColumn(name = "author_id"),
    inverseJoinColumns = @JoinColumn(name = "article_id"))
  private Set<AuthorEntity> authors;


  @ManyToMany(mappedBy = "articles")
  private Set<BookmarkEntity> bookmarks;


  @ManyToMany(cascade = {
    CascadeType.PERSIST,
    CascadeType.MERGE})
  @JoinTable(name = "article_references",
    joinColumns = {@JoinColumn(name = "article_id")},
    inverseJoinColumns = {@JoinColumn(name = "reference_id")})
  private Set<ArticleEntity> references;


  @ManyToMany(mappedBy = "articles")
  private Set<StateOfTheArtEntity> sotas;


  @ManyToMany(cascade = {
    CascadeType.PERSIST,
    CascadeType.MERGE})
  @JoinTable(name = "article_keywords",
    joinColumns = {@JoinColumn(name = "article_id")},
    inverseJoinColumns = {@JoinColumn(name = "tag_id")})
  private Set<TagEntity> keywords;


  /**
   * Convert the current Entity to its DTO version.
   *
   * @return the corresponding ArticleDTO of this entity
   */
  public ArticleDTO toDTO() {
    ArticleDTOBuilder data = ArticleDTO.builder()
      .id(this.id).reference(this.reference)
      .title(this.title).content(this.content).url(url).price(price);

    data.year(this.publicationYear).month(this.publicationMonth);
    data.pages(this.pages).nbCitations(nbCitations).nbViews(nbViews);

    List<String> listOfAuthors = authors.stream().map(a -> a.getName())
      .collect(Collectors.toList());
    data.authors(listOfAuthors);

    data.category(this.category.getName());

    if (!this.keywords.isEmpty()) {
      Set<TagDTO> listOfKeywords = keywords.stream().map(t -> t.toDTO())
        .collect(Collectors.toSet());
      data.keywords(listOfKeywords);
    }

    if (this.user != null) {
      data.user(user.toDTO());
    }

    return data.build();
  }

}
