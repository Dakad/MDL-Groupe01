package be.unamur.info.mdl.service.impl;

import be.unamur.info.mdl.dal.entity.*;
import be.unamur.info.mdl.dal.repository.*;
import be.unamur.info.mdl.dto.*;
import be.unamur.info.mdl.exceptions.InvalidProfilePictureLinkException;
import be.unamur.info.mdl.service.ProfileService;
import be.unamur.info.mdl.exceptions.UserNotFoundException;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.imageio.ImageIO;
import javax.transaction.Transactional;

import com.github.slugify.Slugify;
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
    int bound = 20;

    if (user.getUniversities().size() < bound) {
      bound = user.getUniversities().size();
    }

    List<UniversityInfoDTO> universities = new ArrayList<>();
    user.getUniversities().subList(0, bound)
      .forEach(u -> universities.add(u.toInfoDTO()));

    Map<String, String> articles = articleRepository
      .findDistinctByCreator(user, pagination)
      .collect(Collectors.toMap(ArticleEntity::getReference, ArticleEntity::getTitle));

    Map<Long, String> sotas = stateOfTheArtRepository
      .findDistinctByCreator(user, pagination)
      .collect(Collectors.toMap(StateOfTheArtEntity::getId, StateOfTheArtEntity::getTitle));

    List<String> researchGroup = user.getResearchGroup().stream().map(e->e.getName()).collect(Collectors.toList());

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
  public boolean update(ProfileUpdateDTO updateDTO, String username) throws InvalidProfilePictureLinkException {
    UserEntity user = userRepository.findByUsername(username);

    //UPDATING UNIVERSITIES
    if (updateDTO.getCurrentUniversity() != null) {
      //find the corresponding university entity
      UniversityEntity university = universityRepository.findByName(updateDTO.getCurrentUniversity());
      //add the old current university to the list of universities
      user.getUniversities().add(user.getCurrentUniversity());
      //clean the duplicates in the list
      user.setUniversities(user.getUniversities().stream().distinct().collect(Collectors.toList()));
      //remove the new current university from the list
      user.getUniversities().remove(university);
      //set the current university to the new one
      user.setCurrentUniversity(university);
    }

    //UPDATING RESEARCH GROUPS
    Set<ResearchGroupEntity> researchGroups = updateDTO.getResearchGroups().stream().
      map(e -> ServiceUtils.getOrCreateRG(e, researchGroupRepository)).collect(Collectors.toSet());
    user.setResearchGroup(researchGroups);

    //UPDATING EMAIL ADDRESS
    user.setEmail(updateDTO.getEmail());

    //UPDATING DOMAIN
    user.setDomain(updateDTO.getDomain());

    //UPDATING INTERESTS
    //need to transform a list of strings into a set of tags
    Stream<String> interests = updateDTO.getInterests().stream();
    Set<TagEntity> tags = interests.map(e -> ServiceUtils.getOrCreateTag(e,tagRepository)).collect(Collectors.toSet());
    user.setTags(tags);

    //UPDATING PROFILE PICTURE
    //first need to check if the link is accessible
    try{
      URL link = new URL(updateDTO.getProfilePicURL());
      link.openConnection();
      //then we check if the link is an image
      Image img = ImageIO.read(link);
      if(img == null) throw new InvalidProfilePictureLinkException("[INVALID URL] : Link does not direct to an image");
    }catch(MalformedURLException e){
      throw new InvalidProfilePictureLinkException("[INVALID URL] : " + e.getMessage());
    }catch(IOException e){
      throw new InvalidProfilePictureLinkException("[CONNECTION FAILURE] : "+ e.getMessage());
    }
    //and if everything is fine, we set the user's profile picture url to the new one
    user.getUserProfile().setProfilePictureURL(updateDTO.getProfilePicURL());

    //UPDATING USER BIO
    user.getUserProfile().setDescription(updateDTO.getDescription());

    //SAVING ENTITIES
    userRepository.save(user);
    userProfileRepository.save(user.getUserProfile());

    return true;

  }
}
