package be.unamur.info.mdl.dal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "user_profile")
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileEntity {

  @Id
  private Long id;

  @Column
  private String description;

  @Enumerated(EnumType.STRING)
  @Column
  private UserStatus status;

  @Column(name = "avatar_url")
  private String profilePictureURL;

  @OneToOne
  private UserEntity user;
}
