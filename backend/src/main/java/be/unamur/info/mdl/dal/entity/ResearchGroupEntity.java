package be.unamur.info.mdl.dal.entity;

import java.time.LocalDate;
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
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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

  @Column(name = "nombre")
  @Min(1)
  private int nbre;

  @Column(name = "link")
  private String link;

  @Column(name = "created_at")
  private LocalDate createdAt = LocalDate.now();

  @ManyToMany(mappedBy = "research_group")
  private Set<UserEntity> users;


}
