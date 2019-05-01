package be.unamur.info.mdl.service.impl;

import be.unamur.info.mdl.dal.entity.ArticleEntity;
import be.unamur.info.mdl.dal.entity.StateOfTheArtEntity;
import be.unamur.info.mdl.dal.entity.UserEntity;
import be.unamur.info.mdl.dal.repository.ArticleRepository;
import be.unamur.info.mdl.dal.repository.BookmarkRepository;
import be.unamur.info.mdl.dal.repository.StateOfTheArtRepository;
import be.unamur.info.mdl.dal.repository.UserRepository;
import be.unamur.info.mdl.dto.ArticleDTO;
import be.unamur.info.mdl.dto.ProfileBasicInfoDTO;
import be.unamur.info.mdl.dto.ProfileProInfoDTO;
import be.unamur.info.mdl.dto.ProfileSocialInfoDTO;
import be.unamur.info.mdl.dto.UniversityInfoDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.ProfileService;
import be.unamur.info.mdl.service.exceptions.UsernameNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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

  @Autowired
  public ProfileServiceImpl(UserRepository userRepository, ArticleRepository articleRepository,
    StateOfTheArtRepository stateOfTheArtRepository, BookmarkRepository bookmarkRepository) {
    this.userRepository = userRepository;
    this.articleRepository = articleRepository;
    this.stateOfTheArtRepository = stateOfTheArtRepository;
    this.bookmarkRepository = bookmarkRepository;

  }

  @Override
  public ProfileBasicInfoDTO getBasicInfo(String username) throws UsernameNotFoundException {
    if (!userRepository.existsByUsername(username)) {
      throw new UsernameNotFoundException();
    }
    return userRepository.findByUsername(username).toProfileBasicInfoDTO();
  }

  @Override
  @Transactional
  public ProfileProInfoDTO getProInfo(String username) throws UsernameNotFoundException {
    if (!userRepository.existsByUsername(username)) {
      throw new UsernameNotFoundException();
    }

    PageRequest pagination = PageRequest.of(0, 3, Sort.by(Sort.Order.desc("createdAt")));
    UserEntity user = userRepository.findByUsername(username);
    int bound = 20;

    if (user.getUniversities().size() < bound) {
      bound = user.getUniversities().size();
    }

    List<UniversityInfoDTO> universities = new ArrayList<>();
    user.getUniversities().subList(0, bound)
      .forEach(u -> universities.add(u.getUniversity().toInfoDTO()));

    Map<String, String> articles = articleRepository
      .findDistinctByCreator(user, pagination)
      .collect(Collectors.toMap(ArticleEntity::getReference, ArticleEntity::getTitle));

    Map<Long, String> sotas = stateOfTheArtRepository
      .findDistinctByCreator(user, pagination)
      .collect(Collectors.toMap(StateOfTheArtEntity::getId, StateOfTheArtEntity::getTitle));

    List<String> researchGroup = new ArrayList<>(2);
    if (user.getResearchGroup() != null) {
      researchGroup.add(user.getResearchGroup().getName());
      researchGroup.add(user.getResearchGroup().getLink());
    }

    return new ProfileProInfoDTO(
      researchGroup,
      universities,
      articles,
      sotas
    );
  }

  public ProfileSocialInfoDTO getSocialInfo(String username) throws UsernameNotFoundException {
    if (!userRepository.existsByUsername(username)) {
      throw new UsernameNotFoundException();
    }
    return userRepository.findByUsername(username).toProfileSocialInfoDTO();
  }


  @Override
  public List<UserDTO> getFollowers(String username, int page) throws UsernameNotFoundException {
    if (!userRepository.existsByUsername(username)) {
      throw new UsernameNotFoundException();
    }
    return userRepository.findByUsername(username).getFollowersDTO(page);
  }


  @Override
  public List<UserDTO> getFollows(String username, int page) throws UsernameNotFoundException {
    if (!userRepository.existsByUsername(username)) {
      throw new UsernameNotFoundException();
    }
    return userRepository.findByUsername(username).getFollowsDTO(page);
  }


  @Override
  public Map<String, String> getBookmarks(String username, int page)
    throws UsernameNotFoundException {
    if (!userRepository.existsByUsername(username)) {
      throw new UsernameNotFoundException();
    }

    Sort sort = Sort.by("createdAt").descending();
    UserEntity creator = userRepository.findByUsername(username);
    Page<ArticleEntity> articles = bookmarkRepository
      .findByCreator(creator, PageRequest.of(page, 50, sort));

    return articles.stream().map(a -> a.toBookmarkInfoDTO())
      .collect(Collectors.toMap(ArticleDTO::getReference, ArticleDTO::getTitle));
  }

}
