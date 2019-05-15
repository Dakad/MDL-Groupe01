package be.unamur.info.mdl.dal.entity;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.*;

import be.unamur.info.mdl.dto.UniversityInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "university")
public class UniversityEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;
  @Column(unique = true, nullable = false)
  private String abbreviation;


  @Column(name = "website_url", unique = true, nullable = false)
  private String websiteUrl;


  @ManyToMany(cascade = {
    CascadeType.PERSIST,
    CascadeType.MERGE})
  @JoinTable(name = "user_university",
    joinColumns = {@JoinColumn(name = "university_id")},
    inverseJoinColumns = {@JoinColumn(name = "user_id")})
  private Set<UserEntity> members;


  @OneToMany(mappedBy = "currentUniversity")
  private Set<UserEntity> currentMembers = new LinkedHashSet<>();


  public UniversityInfoDTO toInfoDTO(){ return new UniversityInfoDTO(name,abbreviation, websiteUrl); }

  public static UniversityEntity of(UniversityInfoDTO dto) {
    UniversityEntityBuilder entity = UniversityEntity.builder();
    entity.name(dto.getName()).websiteUrl(dto.getWebsite()).abbreviation(dto.getAbbreviation());
    return entity.build();

  }
}
