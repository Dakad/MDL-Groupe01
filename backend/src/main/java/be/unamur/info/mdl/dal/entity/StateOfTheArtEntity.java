package be.unamur.info.mdl.dal.entity;

import be.unamur.info.mdl.dto.StateOfTheArtDTO;
import be.unamur.info.mdl.dto.StateOfTheArtDTO.StateOfTheArtDTOBuilder;
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
import javax.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "state_of_the_art")
public class StateOfTheArtEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;

  @Column(unique = true, nullable = false)
  private String reference;

  @Column(nullable = false)
  private String subject;

  @Column(nullable = false)
  @PastOrPresent
  private LocalDate date;

  @Column(name = "created_at")
  private LocalDate createdAt = LocalDate.now();


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", unique = true)
  private UserEntity creator;


  @ManyToMany(cascade = {
    CascadeType.PERSIST,
    CascadeType.MERGE
  })
  @JoinTable(name = "state_of_the_art_articles",
    joinColumns = @JoinColumn(name = "sota_id"),
    inverseJoinColumns = @JoinColumn(name = "article_id")
  )
  private List<ArticleEntity> articles;


  @ManyToMany(cascade = {
    CascadeType.PERSIST,
    CascadeType.MERGE})
  @JoinTable(name = "state_of_the_art_tags",
    joinColumns = {@JoinColumn(name = "sota_id")},
    inverseJoinColumns = {@JoinColumn(name = "tag_id")})
  private List<TagEntity> tags;


  public StateOfTheArtDTO toDTO() {
    List<TagDTO> listOfTags = tags.stream().map(t -> t.toDTO()).collect(Collectors.toList());

    StateOfTheArtDTOBuilder dto = StateOfTheArtDTO.builder();
    dto.id(id).name(name).reference(reference).subject(subject).date(date);
    dto.createdAt(createdAt).creator(creator.toDTO());
    dto.tags(listOfTags);

    return dto.build();
  }
}
