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
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "user_profil")
@AllArgsConstructor
@NoArgsConstructor
public class UserProfilEntity {

  @Id
  private Long id;

  @Column
  private String description;

  @Enumerated(EnumType.STRING)
  @Column
  private Status status;

  @Column(name = "avatar_url")
  private String profilePictureURL;

  @Column
  private String facebookURL;

  @Column
  private String twitterURL;

  @Column
  private String linkedInURL;

  @OneToOne
  private UserEntity user;
}
