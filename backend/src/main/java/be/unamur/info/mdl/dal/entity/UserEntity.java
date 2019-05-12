package be.unamur.info.mdl.dal.entity;


import be.unamur.info.mdl.dto.ProfileBasicInfoDTO;
import be.unamur.info.mdl.dto.ProfileSocialInfoDTO;
import be.unamur.info.mdl.dto.ProfileSocialInfoDTO.ProfileSocialInfoDTOBuilder;
import be.unamur.info.mdl.dto.UniversityInfoDTO;
import be.unamur.info.mdl.dto.UserDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @EqualsAndHashCode.Include
  private Long id;

  @Column(unique = true, nullable = false)
  @EqualsAndHashCode.Include
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(name = "email", unique = true)
  @EqualsAndHashCode.Include
  private String email;

  @Column(name = "first_name")
  private String firstname;

  @Column(name = "last_name")
  private String lastname;

  @Column(name = "created_at")
  private LocalDate createdAt;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "domain")
  private TagEntity domain;


  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "current_university_id")
  private UniversityEntity currentUniversity;


  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "profile_id", referencedColumnName = "id", unique = true)
  private UserProfileEntity userProfile;


  @OneToMany(
    mappedBy = "creator",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  @Builder.Default
  private Set<ArticleEntity> articles = new LinkedHashSet<>();


  @OneToMany(
    mappedBy = "creator",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private Set<StateOfTheArtEntity> stateOfTheArts;


  @OneToMany(
    mappedBy = "creator",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private Set<BookmarkEntity> bookmarks;


  @OneToMany(mappedBy = "user")
  private List<UniversityCurrent> universities;


  @ManyToMany(cascade = {
    CascadeType.PERSIST,
    CascadeType.MERGE})
  @JoinTable(name = "user_follower",
    joinColumns = {@JoinColumn(name = "user_id")},
    inverseJoinColumns = {@JoinColumn(name = "following_id")})
  @Builder.Default
  private List<UserEntity> followers = new LinkedList<>();


  @ManyToMany(cascade = {
    CascadeType.PERSIST,
    CascadeType.MERGE})
  @JoinTable(name = "user_follower",
    joinColumns = {@JoinColumn(name = "user_id")},
    inverseJoinColumns = {@JoinColumn(name = "following_id")})
  @Builder.Default
  private List<UserEntity> follows = new LinkedList<>();


  @ManyToMany(cascade = {
    CascadeType.PERSIST,
    CascadeType.MERGE})
  @JoinTable(name = "user_group",
    joinColumns = {@JoinColumn(name = "user_id")},
    inverseJoinColumns = {@JoinColumn(name = "group_id")})
  private Set<ResearchGroupEntity> researchGroup;


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
    UserDTO dto = new UserDTO();
    dto.setUsername(username);
    dto.setLastname(lastname);
    dto.setFirstname(firstname);
    dto.setEmail(email);

    if (getUserProfile() != null) {
      dto.setProfilePicUrl(getUserProfile().getProfilePictureURL());
    }

    if (getCurrentUniversity() != null) {
      dto.setOrganisation(getCurrentUniversity().getName());
    }

    if(getDomain() != null){
      dto.setDomain(getDomain().getName());
    }

    return dto;
  }


  public ProfileBasicInfoDTO toProfileBasicInfoDTO() {
    UniversityInfoDTO universityInfoDTO = null;
    if (this.currentUniversity != null) {
      universityInfoDTO = this.currentUniversity.toInfoDTO();
    }

    String avatar;
    if (userProfile != null) {
      avatar = userProfile.getProfilePictureURL();
    } else {
      avatar = "https://i.imgur.com/0MC7ZG4.jpg";
    }
    String dom = (domain != null) ? domain.getName() : null;
    return new ProfileBasicInfoDTO(lastname, firstname, dom, universityInfoDTO, email, avatar);
  }


  public ProfileSocialInfoDTO toProfileSocialInfoDTO() {
    ProfileSocialInfoDTOBuilder dto = ProfileSocialInfoDTO.builder();

    dto.numFollowers(this.followers.size()).numFollows(this.follows.size());

    if (this.userProfile != null) {
      dto.bio(userProfile.getDescription());
      dto.facebookURL(this.userProfile.getFacebookURL())
        .linkedinURL(this.userProfile.getLinkedInURL())
        .twitterURL(this.userProfile.getTwitterURL());
    }
    return dto.build();
  }

  //TODO : test unitaire sur des listes de followers de tailles variées
  public List<UserDTO> getFollowersDTO(int page) {
    return getFollowers(page, this.followers);
  }

  //TODO : test unitaire sur des listes de follows de tailles variées
  public List<UserDTO> getFollowsDTO(int page) {
    return getFollowers(page, this.follows);
  }

  private List<UserDTO> getFollowers(int page, List<UserEntity> followers) {
    int leftBound = page * 20;
    if (followers.size() <= leftBound) {
      return new ArrayList<>();
    }
    int rightBound = (page * 20) + 20;
    if (followers.size() <= rightBound) {
      rightBound = followers.size();
    }
    List<UserEntity> subList = followers.subList(leftBound, rightBound);
    List<UserDTO> dtoList = new ArrayList<>(subList.size());
    subList.forEach(e -> dtoList.add(e.toDTO()));
    return dtoList;
  }


}
