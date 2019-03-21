package be.unamur.info.mdl.dal.entity;


import be.unamur.info.mdl.dto.UserDTO;
import java.time.LocalDate;
import java.util.LinkedHashSet;
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

  @Column(name = "created_at")
  private LocalDate createdAt;


  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "profil_id", referencedColumnName = "id", unique = true)
  private UserProfilEntity userProfil;


  @OneToMany(
    mappedBy = "user",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private Set<ArticleEntity> articles;


  @OneToMany(
    mappedBy = "user",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private Set<StateOfTheArtEntity> stateOfTheArts;


  @OneToMany(
    mappedBy = "user",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private Set<BookmarkEntity> bookmarks;


  @OneToMany(mappedBy = "user")
  private Set<UniversityCurrent> university;


  @ManyToMany(cascade = {
    CascadeType.PERSIST,
    CascadeType.MERGE})
  @JoinTable(name = "user_follower",
    joinColumns = {@JoinColumn(name = "user_id")},
    inverseJoinColumns = {@JoinColumn(name = "following_id")})
  private Set<UserEntity> followers = new LinkedHashSet<>();


  @ManyToMany(cascade = {
    CascadeType.PERSIST,
    CascadeType.MERGE})
  @JoinTable(name = "user_group",
    joinColumns = {@JoinColumn(name = "user_id")},
    inverseJoinColumns = {@JoinColumn(name = "group_id")})
  private Set<ResearchGroupEntity> research_group;


  @ManyToMany(cascade = {
    CascadeType.PERSIST,
    CascadeType.MERGE})
  @JoinTable(name = "follower_tags",
    joinColumns = {@JoinColumn(name = "follower_id")},
    inverseJoinColumns = {@JoinColumn(name = "tag_id")})
  private Set<TagEntity> tags;


  public static UserEntity of(UserDTO userData) {
    return new UserEntity(null, userData.getUsername(), userData.getPassword(), userData.getEmail(),
      userData.getFirstname(), userData.getLastname(), null, null, null, null, null, null, null,
      null, null
    );
  }


  public UserDTO toDTO() {
    return new UserDTO(username, password, lastname, firstname, email);
  }

}
