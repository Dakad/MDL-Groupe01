package be.unamur.info.mdl.dal.entity;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tag")
public class TagEntity {

  @Id
  private String name;

  @ManyToMany(mappedBy = "tags")
  private Set<ArticleEntity> articles;

  @ManyToMany(mappedBy = "tags")
  private Set<StateOfTheArtEntity> statesofthearts;
  @ManyToMany(mappedBy = "tags")
  private Set<UserEntity> follower = new LinkedHashSet<>();;
}
