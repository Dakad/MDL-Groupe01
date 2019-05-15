package be.unamur.info.mdl.service.impl;

import be.unamur.info.mdl.dal.entity.*;
import be.unamur.info.mdl.dal.repository.*;
import be.unamur.info.mdl.dto.*;
import be.unamur.info.mdl.exceptions.InvalidProfilePictureLinkException;
import be.unamur.info.mdl.service.ProfileService;
import be.unamur.info.mdl.exceptions.UserNotFoundException;

import com.google.common.base.Strings;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.imageio.ImageIO;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service("profileService")
public class ProfileServiceImpl implements ProfileService {

  private UserRepository userRepository;
  private ArticleRepository articleRepository;
  private StateOfTheArtRepository stateOfTheArtRepository;
  private BookmarkRepository bookmarkRepository;
  private UniversityRepository universityRepository;
  private UserProfileRepository userProfileRepository;
  private TagRepository tagRepository;
  private ResearchGroupRepository researchGroupRepository;

  @Autowired
  public ProfileServiceImpl(UserRepository userRepository, ArticleRepository articleRepository,
    StateOfTheArtRepository stateOfTheArtRepository, BookmarkRepository bookmarkRepository,
    UniversityRepository universityRepository, UserProfileRepository userProfileRepository,
    TagRepository tagRepository, ResearchGroupRepository researchGroupRepository) {
    this.userRepository = userRepository;
    this.articleRepository = articleRepository;
    this.stateOfTheArtRepository = stateOfTheArtRepository;
    this.bookmarkRepository = bookmarkRepository;
    this.universityRepository = universityRepository;
    this.userProfileRepository = userProfileRepository;
    this.tagRepository = tagRepository;
    this.researchGroupRepository = researchGroupRepository;

  }

  @Override
  public ProfileBasicInfoDTO getBasicInfo(String username) throws UserNotFoundException {
    if (!userRepository.existsByUsername(username)) {
      throw new UserNotFoundException();
    }
    return userRepository.findByUsername(username).toProfileBasicInfoDTO();
  }

  @Override
  @Transactional
  public ProfileProInfoDTO getProInfo(String username) throws UserNotFoundException {
    if (!userRepository.existsByUsername(username)) {
      throw new UserNotFoundException();
    }

    PageRequest pagination = PageRequest.of(0, 3, Sort.by(Sort.Order.desc("createdAt")));
    UserEntity user = userRepository.findByUsername(username);

    List<UniversityInfoDTO> universities = new ArrayList<>();
    user.getUniversities().forEach(u -> universities.add(u.toInfoDTO()));

    Map<String, String> articles = articleRepository
      .findDistinctByCreator(user, pagination)
      .collect(Collectors.toMap(ArticleEntity::getReference, ArticleEntity::getTitle));

    Map<Long, String> sotas = stateOfTheArtRepository
      .findDistinctByCreator(user, pagination)
      .collect(Collectors.toMap(StateOfTheArtEntity::getId, StateOfTheArtEntity::getTitle));

    List<String> researchGroup = user.getResearchGroup().stream().map(e -> e.getName())
      .collect(Collectors.toList());

    return new ProfileProInfoDTO(
      researchGroup,
      universities,
      articles,
      sotas
    );
  }

  public ProfileSocialInfoDTO getSocialInfo(String username) throws UserNotFoundException {
    if (!userRepository.existsByUsername(username)) {
      throw new UserNotFoundException();
    }
    return userRepository.findByUsername(username).toProfileSocialInfoDTO();
  }


  @Override
  public List<UserDTO> getFollowers(String username, int page) throws UserNotFoundException {
    if (!userRepository.existsByUsername(username)) {
      throw new UserNotFoundException();
    }
    return userRepository.findByUsername(username).getFollowersDTO(page);
  }


  @Override
  public List<UserDTO> getFollows(String username, int page) throws UserNotFoundException {
    if (!userRepository.existsByUsername(username)) {
      throw new UserNotFoundException();
    }
    return userRepository.findByUsername(username).getFollowsDTO(page);
  }


  @Override
  public List<BookmarkDTO> getBookmarks(String username, int page)
    throws UserNotFoundException {
    if (!userRepository.existsByUsername(username)) {
      throw new UserNotFoundException();
    }

    Sort sort = Sort.by("createdAt").descending();
    UserEntity creator = userRepository.findByUsername(username);
    Page<BookmarkEntity> bookmarks = bookmarkRepository
      .findByCreator(creator, PageRequest.of(page, 50, sort));

    return bookmarks.stream().map(a -> a.toDTO()).collect(Collectors.toList());
  }

  @Override
  public boolean update(ProfileUpdateDTO updateDTO, String username)
    throws InvalidProfilePictureLinkException {
    UserEntity user = userRepository.findByUsername(username);

    //UPDATING UNIVERSITIES
    if (!Strings.isNullOrEmpty(updateDTO.getCurrentUniversity())) {
      //find the corresponding university entity
      UniversityEntity university = universityRepository
        .findByName(updateDTO.getCurrentUniversity());
      //add the old current university to the list of universities
      user.getUniversities().add(user.getCurrentUniversity());

      user.setCurrentUniversity(university);
    }

    //UPDATING RESEARCH GROUPS
    if(updateDTO.getResearchGroups() != null && !updateDTO.getResearchGroups().isEmpty()){
      Set<ResearchGroupEntity> researchGroups = updateDTO.getResearchGroups().stream().
        map(e -> ServiceUtils.getOrCreateResearchGroup(e, researchGroupRepository)).collect(Collectors.toSet());
      user.setResearchGroup(researchGroups);
    }

    //UPDATING EMAIL ADDRESS
    if(!Strings.isNullOrEmpty(updateDTO.getEmail()){
      user.setEmail(updateDTO.getEmail());
    }

    //UPDATING DOMAIN
    if(!Strings.isNullOrEmpty(updateDTO.getDomain())){
      user.setDomain(ServiceUtils.getOrCreateTag(updateDTO.getDomain(), tagRepository));
    }


    //UPDATING INTERESTS
    //need to transform a list of strings into a set of tags
    if(updateDTO.getInterests() != null){
      Stream<String> interests = updateDTO.getInterests().stream();
      Set<TagEntity> tags = interests.map(e -> ServiceUtils.getOrCreateTag(e, tagRepository))
        .collect(Collectors.toSet());
      user.setTags(tags);
    }

    //UPDATING PROFILE PICTURE
    if (updateDTO.getProfilePictureURL() != null) {
      try {
        // Check if the link is accessible
        URL link = new URL(updateDTO.getProfilePictureURL());
        link.openConnection();

        // Check if the link is an image
        if (ImageIO.read(link) == null) {
          throw new InvalidProfilePictureLinkException("The provided link does not redirect to an image");
        }
        // user.getUserProfile().setProfilePictureURL(updateDTO.getProfilePictureURL());
      } catch (IOException e) {
        throw new InvalidProfilePictureLinkException(
          "The provided avatar URL is invalid (not found)");
      }
    }

    if (updateDTO.getDescription() != null) {
      user.getUserProfile().setDescription(updateDTO.getDescription());
    }

    userRepository.save(user);
    userProfileRepository.save(user.getUserProfile());

    return true;
  }
}
