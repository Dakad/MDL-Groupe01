package be.unamur.info.mdl.dal.entity;

import be.unamur.info.mdl.dto.ArticleDTO;
import be.unamur.info.mdl.dto.AuthorDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "author")
public class AuthorEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "name", unique = true, nullable = false)
  private String name;

  @Column(name = "slug", unique = true, nullable = false)
  @EqualsAndHashCode.Include
  private String slug;

  @Column(name = "created_at")
  @Builder.Default
  private LocalDate createdAt = LocalDate.now();


  @ManyToMany(mappedBy = "authors")
  private Set<ArticleEntity> articles;


  public static AuthorEntity of(AuthorDTO dto){
    return AuthorEntity.builder().slug(dto.getSlug()).name(dto.getName()).build();
  }


  public AuthorDTO toDTO() {
//    List<ArticleDTO> articleList = this.articles.stream()
//      .map(a -> a.toDTO()).collect(Collectors.toList());
    return AuthorDTO.builder().name(this.name).slug(this.slug).build();
  }
}
