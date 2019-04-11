package be.unamur.info.mdl.dal.entity;

import java.util.Set;
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


  @ManyToMany(mappedBy = "authors")
  private Set<ArticleEntity> articles;


}
