package be.unamur.info.mdl.dal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_profile")
public class UserProfileEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column
  private String description;

  @Enumerated(EnumType.STRING)
  @Column
  @Builder.Default
  private UserStatus status = UserStatus.RESEARCHER;

  @Column(name = "avatar_url")
  private String profilePictureURL;

  @OneToOne
  private UserEntity user;
}
