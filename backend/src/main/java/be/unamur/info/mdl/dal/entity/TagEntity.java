package be.unamur.info.mdl.dal.entity;

import be.unamur.info.mdl.dto.TagDTO;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "tag")
@Entity
public class TagEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @EqualsAndHashCode.Include
  private long id;

  @Column(name= "name", unique = true, nullable = false)
  @EqualsAndHashCode.Include
  private String name;

  @Column( name = "slug", unique = true, nullable = false)
  @EqualsAndHashCode.Include
  private String slug;


  @OneToMany(mappedBy = "category",
    cascade = CascadeType.ALL,
    orphanRemoval = true)
  @Builder.Default
  private Set<ArticleEntity> articlesByCategory = new LinkedHashSet<>();


  @ManyToMany(mappedBy = "keywords")
  @Builder.Default
  private Set<ArticleEntity> articlesByKeyword = new LinkedHashSet<>();


  @ManyToMany(mappedBy = "keywords")
  @Builder.Default
  private Set<StateOfTheArtEntity> statesOfTheArts = new LinkedHashSet<>();


  @ManyToMany(mappedBy = "tags")
  @Builder.Default
  private Set<UserEntity> followers = new LinkedHashSet<>();



  public static TagEntity of(TagDTO dto){
    return TagEntity.builder().name(dto.getName()).slug(dto.getSlug()).build();
  }

  public TagDTO toDTO() { return new TagDTO(name, slug); }
}
