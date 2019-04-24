package be.unamur.info.mdl.dal.entity;


import be.unamur.info.mdl.dto.ProfileBasicInfoDTO;
import be.unamur.info.mdl.dto.ProfileSocialInfoDTO;
import be.unamur.info.mdl.dto.UniversityInfoDTO;
import be.unamur.info.mdl.dto.UserDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import javafx.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique = true, nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(name = "email")
  private String email;

  @Column(name = "first_name")
  private String firstname;

  @Column(name = "last_name")
  private String lastname;

  @Column(name = "created_at")
  private LocalDate createdAt;

  @Column(name = "domain")
  private String domain;



  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL,optional = true)
  @JoinColumn(name = "current_univerty_id")
  private UniversityEntity currentUniversity;


  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "profil_id", referencedColumnName = "id", unique = true)
  private UserProfileEntity userProfil;



  @OneToMany(
    mappedBy = "creator",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  @Builder.Default
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
  private Set<UniversityCurrent> universities;


  @ManyToMany(cascade = {
    CascadeType.PERSIST,
    CascadeType.MERGE})
  @JoinTable(name = "user_follower",
    joinColumns = {@JoinColumn(name = "user_id")},
    inverseJoinColumns = {@JoinColumn(name = "following_id")})
  private List<UserEntity> followers;

  @ManyToMany(cascade = {
    CascadeType.PERSIST,
    CascadeType.MERGE})
  @JoinTable(name = "user_follower",
    joinColumns = {@JoinColumn(name = "user_id")},
    inverseJoinColumns = {@JoinColumn(name = "following_id")})
  private List<UserEntity> follows;


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



  public static UserEntity of(UserDTO dto) {
    UserEntityBuilder entity = UserEntity.builder();
    entity.username(dto.getUsername()).password(dto.getPassword());
    entity.firstname(dto.getFirstname()).lastname(dto.getLastname()).email(dto.getEmail());
    return entity.build();
  }


  public UserDTO toDTO() {
    return new UserDTO(username, password, lastname, firstname, email);
  }


  public ProfileBasicInfoDTO toProfileBasicInfoDTO() {
    UniversityInfoDTO universityInfoDTO = null;
    if(this.currentUniversity != null){
      universityInfoDTO = this.currentUniversity.toInfoDTO();
    }

    String avatar;
    if (userProfil != null) {
      avatar = userProfil.getProfilePictureURL();
    } else {
      avatar = "https://i.imgur.com/0MC7ZG4.jpg";
    }
    return new ProfileBasicInfoDTO(lastname, firstname, domain, universityInfoDTO, email, avatar);
  }

  public ProfileSocialInfoDTO toProfileSocialInfoDTO(){
    if(userProfil == null) return new ProfileSocialInfoDTO("This user hasn't added a bio.",
      follows.size(),followers.size(),null, null, null);
    return new ProfileSocialInfoDTO(userProfil.getDescription(),follows.size(),followers.size(),userProfil.getFacebookURL(),
      userProfil.getTwitterURL(),userProfil.getLinkedInURL());
  }

  //TODO : test unitaire sur des listes de followers de tailles variées
  public List<UserDTO> getFollowersDTO(int page){
    int leftBound = page * 20;
    int rightBound = page *20 + 20;
    if(followers.size() <= leftBound) return null;
    else if (followers.size() <= rightBound) rightBound = followers.size();
    List<UserEntity> subList = followers.subList(leftBound, rightBound);
    List<UserDTO> dtoList = new ArrayList();
    subList.forEach(e -> dtoList.add(e.toDTO()));
    return dtoList;
  }

  //TODO : test unitaire sur des listes de followers de tailles variées
  public List<UserDTO> getFollowsDTO(int page){
    int leftBound = page * 20;
    int rightBound = page *20 + 20;
    if(follows.size() <= leftBound) return null;
    else if (follows.size() <= rightBound) rightBound = follows.size();
    List<UserEntity> subList = follows.subList(leftBound, rightBound);
    List<UserDTO> dtoList = new ArrayList();
    subList.forEach(e -> dtoList.add(e.toDTO()));
    return dtoList;
  }

}
