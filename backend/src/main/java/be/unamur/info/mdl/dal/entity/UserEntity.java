package be.unamur.info.mdl.dal.entity;


import be.unamur.info.mdl.dto.ProfileBasicInfoDTO;
import be.unamur.info.mdl.dto.ProfileSocialInfoDTO;
import be.unamur.info.mdl.dto.UniversityInfoDTO;
import be.unamur.info.mdl.dto.UserDTO;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.LinkedHashSet;
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

  @Column(name = "domain")
  private String domain;


  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "profil_id", referencedColumnName = "id", unique = true)
  private UserProfilEntity userProfil;


  @OneToMany(
    mappedBy = "creator",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private Set<ArticleEntity> articles = new LinkedHashSet<>();


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
  private Set<UserEntity> followers;

  @ManyToMany(cascade = {
    CascadeType.PERSIST,
    CascadeType.MERGE})
  @JoinTable(name = "user_follower",
    joinColumns = {@JoinColumn(name = "user_id")},
    inverseJoinColumns = {@JoinColumn(name = "following_id")})
  private Set<UserEntity> follows;


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
      userData.getFirstname(), userData.getLastname(), null, null, null, null, null, null, null, null,
      null, null, null
    );
  }


  public UserDTO toDTO() {
    return new UserDTO(username, password, lastname, firstname, email);
  }

  //TODO : UnJava this mess
  public ProfileBasicInfoDTO toProfileBasicInfoDTO(){
    Iterator<UniversityCurrent> iterator = university.iterator();
    UniversityInfoDTO universityInfoDTO = new UniversityInfoDTO();
    while(iterator.hasNext()){
      UniversityCurrent universityCurrent = iterator.next();
      if(!universityCurrent.isCurrent()) universityInfoDTO = universityCurrent.getUniversity().toInfoDTO();
    }
    String ppurl;
    if(userProfil != null){
      ppurl = userProfil.getProfilePictureURL();
    } else ppurl = "https://i.imgur.com/0MC7ZG4.jpg";
    return new ProfileBasicInfoDTO(lastname, firstname,domain,universityInfoDTO,email,ppurl);
  }

  public ProfileSocialInfoDTO toProfileSocialInfoDTO(){
    if(userProfil == null) return new ProfileSocialInfoDTO("This user hasn't added a bio.",
      follows.size(),followers.size(),null, null, null);
    return new ProfileSocialInfoDTO(userProfil.getDescription(),follows.size(),followers.size(),userProfil.getFacebookURL(),
      userProfil.getTwitterURL(),userProfil.getLinkedInURL());
  }

  //TODO : C'EST DEGUEULASSE AAAAAAAAAAAA
  public UserDTO[] getFollowersDTO(int page){
    UserEntity[] followerArray = (UserEntity[]) followers.toArray();
    UserDTO[] userDTOS = {};
    for(int i =0; i < 20; i++){
      if(followerArray[(page * 20) + i] == null) break;
      userDTOS[i] = followerArray[(page * 20) + i].toDTO();
    }
    return userDTOS;
  }

}
