package be.unamur.info.mdl.dal.entity;

import be.unamur.info.mdl.dto.ArticleDTO;
import be.unamur.info.mdl.dto.ArticleDTO.ArticleDTOBuilder;
import be.unamur.info.mdl.dto.TagDTO;
import java.time.LocalDate;
import java.util.LinkedHashSet;
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
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Table(name = "article")
public class ArticleEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @EqualsAndHashCode.Include
  private long id;

  @Column(unique = true, nullable = false)
  @EqualsAndHashCode.Include
  private String reference;

  @Column(unique = true, nullable = false)
  @EqualsAndHashCode.Include
  private String title;

  @Column(name = "abstract", nullable = false)
  @Lob
  private String content;

  @Column(name = "url", unique = true, nullable = false)
  @EqualsAndHashCode.Include
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

  @Column(name = "price")
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
  @Builder.Default
  private LocalDate createdAt = LocalDate.now();


  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
  @JoinColumn(name = "category_id", nullable = false)
  private TagEntity category;


  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "creator_user_id", nullable = false)
  private UserEntity creator;


  @ManyToMany(cascade = {
    CascadeType.PERSIST,
    CascadeType.MERGE})
  @JoinTable(
    name = "article_authors",
    joinColumns = @JoinColumn(name = "author_id"),
    inverseJoinColumns = @JoinColumn(name = "article_id"))
  @Builder.Default
  private Set<AuthorEntity> authors = new LinkedHashSet<>();


  @ManyToMany(mappedBy = "articles")
  @Builder.Default
  private Set<BookmarkEntity> bookmarks = new LinkedHashSet<>();


  @ManyToMany(cascade = {
    CascadeType.PERSIST,
    CascadeType.MERGE})
  @JoinTable(name = "article_references",
    joinColumns = {@JoinColumn(name = "article_id")},
    inverseJoinColumns = {@JoinColumn(name = "reference_id")})
  @Builder.Default
  private Set<ArticleEntity> references = new LinkedHashSet<>();


  @ManyToMany(mappedBy = "articles")
  @Builder.Default
  private Set<StateOfTheArtEntity> sotas = new LinkedHashSet<>();


  @ManyToMany(cascade = {
    CascadeType.PERSIST,
    CascadeType.MERGE})
  @JoinTable(name = "article_keywords",
    joinColumns = {@JoinColumn(name = "article_id")},
    inverseJoinColumns = {@JoinColumn(name = "tag_id")})
  @Builder.Default
  private Set<TagEntity> keywords = new LinkedHashSet<>();


  /**
   * Convert the current Entity to its DTO version.
   *
   * @return the corresponding ArticleDTO of this entity
   */
  public ArticleDTO toDTO() {
    ArticleDTOBuilder data = ArticleDTO.builder()
      .reference(this.reference)
      .title(this.title).content(this.content).url(url).price(price);

    data.journal(this.journal).number(this.journalNumber).publisher(this.publisher);
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

    if (this.creator != null) {
      data.creator(creator.toDTO());
    }

    return data.build();
  }


  public static ArticleEntity of(ArticleDTO dto) {
    ArticleEntityBuilder entity = ArticleEntity.builder();

    entity.reference(dto.getReference());
    entity.title(dto.getTitle()).content(dto.getContent()).url(dto.getUrl()).price(dto.getPrice());

    entity.journal(dto.getJournal()).journalVolume(dto.getVolume()).journalNumber(dto.getNumber());
    entity.publisher(dto.getPublisher()).publicationYear(dto.getYear())
      .publicationMonth(dto.getMonth());
    entity.pages(dto.getPages()).nbCitations(dto.getNbCitations()).nbViews(dto.getNbViews());

    if (dto.getCreator() != null) {
      entity.creator(UserEntity.of(dto.getCreator()));
    }

    return entity.build();
  }

}
