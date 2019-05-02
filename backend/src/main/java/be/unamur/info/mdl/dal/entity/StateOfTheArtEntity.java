package be.unamur.info.mdl.dal.entity;

import be.unamur.info.mdl.dto.ArticleDTO;
import be.unamur.info.mdl.dto.StateOfTheArtDTO;
import be.unamur.info.mdl.dto.StateOfTheArtDTO.StateOfTheArtDTOBuilder;
import be.unamur.info.mdl.dto.TagDTO;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "state_of_the_art")
public class StateOfTheArtEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @EqualsAndHashCode.Include
  private Long id;

  @Column(name = "title", unique = true, nullable = false)
  @EqualsAndHashCode.Include
  private String title;

  @Column(name = "reference", unique = true, nullable = false)
  @EqualsAndHashCode.Include
  private String reference;


  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
  @JoinColumn(name = "category_id", nullable = false)
  private TagEntity category;

  @Column(name = "created_at")
  @Builder.Default
  private LocalDate createdAt = LocalDate.now();


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity creator;


  @ManyToMany(cascade = {
    CascadeType.PERSIST,
    CascadeType.MERGE
  })
  @JoinTable(name = "state_of_the_art_articles",
    joinColumns = @JoinColumn(name = "sota_id"),
    inverseJoinColumns = @JoinColumn(name = "article_id")
  )
  @Builder.Default
  private List<ArticleEntity> articles = new LinkedList<>();


  @ManyToMany(cascade = {
    CascadeType.PERSIST,
    CascadeType.MERGE})
  @JoinTable(name = "state_of_the_art_tags",
    joinColumns = {@JoinColumn(name = "sota_id")},
    inverseJoinColumns = {@JoinColumn(name = "tag_id")})
  @Builder.Default
  private List<TagEntity> keywords = new LinkedList<>();

  @OneToMany(mappedBy = "sota")
  @Builder.Default
  private Set<BookmarkEntity> bookmarks = new LinkedHashSet<>();

  public static StateOfTheArtEntity of(StateOfTheArtDTO data) {
    List<TagEntity> listOfTags = data.getKeywords().stream().map(t -> TagEntity.of(t)).collect(
      Collectors.toList());
    List<ArticleEntity> listOfArticles = data.getArticles().stream().map(a -> ArticleEntity.of(a))
      .collect(Collectors.toList());

    StateOfTheArtEntityBuilder entity = StateOfTheArtEntity.builder();
    entity.id(data.getId()).reference(data.getReference()).title(data.getTitle());
    entity.keywords(listOfTags).articles(listOfArticles);

    return entity.build();
  }


  public StateOfTheArtDTO toDTO() {
    List<TagDTO> listOfTags = keywords.stream().map(t -> t.toDTO()).collect(Collectors.toList());
    List<ArticleDTO> listOfArticles = articles.stream().map(a -> a.toDTO()).collect(Collectors.toList());

    StateOfTheArtDTOBuilder dto = StateOfTheArtDTO.builder();
    dto.id(id).title(title).reference(reference).category(category.getName());
    dto.createdAt(createdAt).creator(creator.toDTO());
    dto.keywords(listOfTags);
    dto.articles(listOfArticles);

    return dto.build();
  }

}
