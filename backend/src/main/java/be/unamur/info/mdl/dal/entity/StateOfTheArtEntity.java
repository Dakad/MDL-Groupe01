package be.unamur.info.mdl.dal.entity;

import be.unamur.info.mdl.dto.StateOfTheArtDTO;
import be.unamur.info.mdl.dto.StateOfTheArtDTO.StateOfTheArtDTOBuilder;
import be.unamur.info.mdl.dto.TagDTO;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "state_of_the_art")
public class StateOfTheArtEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "title", unique = true, nullable = false)
  private String title;

  @Column(name = "reference", unique = true, nullable = false)
  private String reference;

  @Column(name = "description")
  private String description;

  @Column(name = "created_at")
  private LocalDate createdAt = LocalDate.now();


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
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



  public static StateOfTheArtEntity of(StateOfTheArtDTO data) {
    List<TagEntity> listOfTags = data.getKeywords().stream().map(t -> TagEntity.of(t)).collect(
      Collectors.toList());
    List<ArticleEntity> listOfArticles = data.getArticles().stream().map(a -> ArticleEntity.of(a))
      .collect(Collectors.toList());

    StateOfTheArtEntityBuilder entity = StateOfTheArtEntity.builder();
    entity.id(data.getId()).reference(data.getReference());
    entity.title(data.getTitle()).description(data.getDescription());
    entity.createdAt(data.getCreatedAt());
    entity.keywords(listOfTags).articles(listOfArticles);

    return entity.build();
  }


  public StateOfTheArtDTO toDTO() {
    List<TagDTO> listOfTags = keywords.stream().map(t -> t.toDTO()).collect(Collectors.toList());

    StateOfTheArtDTOBuilder dto = StateOfTheArtDTO.builder();
    dto.id(id).title(title).reference(reference).description(description);
    dto.createdAt(createdAt).creator(creator.toDTO());
    dto.keywords(listOfTags);

    return dto.build();
  }

}
