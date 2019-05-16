package be.unamur.info.mdl.dal.entity;

import be.unamur.info.mdl.dto.ResearchGroupDTO;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "research_group")
@AllArgsConstructor
@NoArgsConstructor
public class ResearchGroupEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;

  @Column(unique = true, nullable = false)
  private String slug;

  @Column(name = "nb_members")
  @Min(1)
  @Builder.Default
  private int nbMembers = 1;

  @Column(name = "url_link")
  private String link;

  @Column(name = "created_at")
  @Builder.Default
  private LocalDate createdAt = LocalDate.now();

  @ManyToMany(mappedBy = "researchGroup")
  private Set<UserEntity> users = new LinkedHashSet<>();

  public static ResearchGroupEntity of(ResearchGroupDTO dto) {
    ResearchGroupEntityBuilder entity = ResearchGroupEntity.builder();
    entity.name(dto.getName()).link(dto.getLink()).slug(dto.getSlug()).nbMembers(dto.getNbMembers());
    return entity.build();

  }

}
