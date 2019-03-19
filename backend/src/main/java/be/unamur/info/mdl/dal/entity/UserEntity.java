package be.unamur.info.mdl.dal.entity;


import be.unamur.info.mdl.dto.UserDTO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.h2.engine.User;

@Data
@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique = true, nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  @Email
  private String email;

  @Column(name = "first_name")
  private String firstname;

  @Column(name = "last_name")
  private String lastname;


  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "profil_id", referencedColumnName = "id")
  private UserProfilEntity userProfil;


  @OneToMany(
      mappedBy = "user",
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  private List<ArticleEntity> articles = new ArrayList<>();


  @OneToMany(
      mappedBy = "user",
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  private List<StateOfTheArtEntity> stateofthearts = new ArrayList<>();


  @OneToMany(
      mappedBy = "user",
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  private List<BookmarkEntity> bookmarks = new ArrayList<>();


  @OneToMany(mappedBy = "user")
  private Set<UniversityEntity> university = new HashSet<>();


  @ManyToMany(cascade = {CascadeType.ALL})
  @JoinTable(name = "user_follower",
      joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "following_id")})
  private Set<User> users = new HashSet<>();


  public UserDTO toDTO() {
    return new UserDTO(username, password, lastname, firstname, email);
  }

}
