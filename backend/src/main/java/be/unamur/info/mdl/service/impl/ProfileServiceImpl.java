package be.unamur.info.mdl.service.impl;

import be.unamur.info.mdl.dal.entity.*;
import be.unamur.info.mdl.dal.repository.ArticleRepository;
import be.unamur.info.mdl.dal.repository.StateOfTheArtRepository;
import be.unamur.info.mdl.dal.repository.UserRepository;
import be.unamur.info.mdl.dto.ProfileBasicInfoDTO;
import be.unamur.info.mdl.dto.ProfileProInfoDTO;
import be.unamur.info.mdl.dto.UniversityInfoDTO;
import be.unamur.info.mdl.dto.ProfileSocialInfoDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.ProfileService;
import be.unamur.info.mdl.service.exceptions.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.List;

@Service("profileService")
public class ProfileServiceImpl implements ProfileService {

  private UserRepository userRepository;
  private ArticleRepository articleRepository;
  private StateOfTheArtRepository stateOfTheArtRepository;

  @Autowired
  public ProfileServiceImpl(UserRepository userRepository, ArticleRepository articleRepository, StateOfTheArtRepository stateOfTheArtRepository){
    this.userRepository = userRepository;
    this.articleRepository = articleRepository;
    this.stateOfTheArtRepository = stateOfTheArtRepository;
  }

  @Override
  public ProfileBasicInfoDTO getBasicInfo(String username) throws UsernameNotFoundException {
    if (!userRepository.existsByUsername(username)) throw new UsernameNotFoundException();
    return userRepository.findByUsername(username).toProfileBasicInfoDTO();
  }

  @Override
  @Transactional
  public ProfileProInfoDTO getProInfo(String username) throws UsernameNotFoundException{
    if (!userRepository.existsByUsername(username)) throw new UsernameNotFoundException();
    UserEntity user = userRepository.findByUsername(username);
    int bound = 20;
    if(user.getUniversities().size()< bound) bound = user.getUniversities().size();
    List<UniversityInfoDTO> uni = new ArrayList();
    user.getUniversities().subList(0,bound).forEach(u -> uni.add(u.getUniversity().toInfoDTO()));
    UniversityInfoDTO[] arrayUni = new UniversityInfoDTO[20];
    uni.toArray(arrayUni);
  Stream<ArticleEntity> articleEntities = articleRepository.findDistinctByCreator(user, PageRequest.of(0, 3, Sort.by(Sort.Order.desc("createdAt"))));
    List<Pair<String, Long>> articleInfo = new ArrayList();
    articleEntities.forEach(e -> articleInfo.add(e.toArticleInfo()));
    Stream<StateOfTheArtEntity> sotaEntities = stateOfTheArtRepository.findDistinctByUser(user, PageRequest.of(0, 3, Sort.by(Sort.Order.desc("createdAt"))));
    List<Pair<String, Long>> sotaInfo = new ArrayList();
    sotaEntities.forEach(e -> sotaInfo.add(e.toSotaInfo()));
    String researchGroupName = (user.getResearch_group() != null) ? user.getResearch_group().getName() : null;
    String researchGroupLink = (user.getResearch_group() != null) ? user.getResearch_group().getLink() : null;
    return new ProfileProInfoDTO(
      new Pair<String, String>(researchGroupName,researchGroupLink),
      arrayUni,
      articleInfo,
      sotaInfo
    );
  }

  public ProfileSocialInfoDTO getSocialInfo(String username) throws UsernameNotFoundException{
    if (!userRepository.existsByUsername(username)) throw new UsernameNotFoundException();
    return userRepository.findByUsername(username).toProfileSocialInfoDTO();
  }

  @Override
  public List<UserDTO> getFollowers(String username, int page) throws UsernameNotFoundException{
    if (!userRepository.existsByUsername(username)) throw new UsernameNotFoundException();
    return userRepository.findByUsername(username).getFollowersDTO(page);
  }

  @Override
  public List<UserDTO> getFollows(String username, int page) throws UsernameNotFoundException{
    if (!userRepository.existsByUsername(username)) throw new UsernameNotFoundException();
    return userRepository.findByUsername(username).getFollowsDTO(page);
  }


}
