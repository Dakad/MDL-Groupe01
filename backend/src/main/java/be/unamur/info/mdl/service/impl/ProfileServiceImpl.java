package be.unamur.info.mdl.service.impl;

import be.unamur.info.mdl.dal.entity.ArticleEntity;
import be.unamur.info.mdl.dal.repository.BookmarkRepository;
import be.unamur.info.mdl.dal.repository.UserRepository;
import be.unamur.info.mdl.dto.ProfileBasicInfoDTO;
import be.unamur.info.mdl.dto.ProfileSocialInfoDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.ProfileService;
import be.unamur.info.mdl.service.exceptions.UsernameNotFoundException;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QSort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@Service("profileService")
public class ProfileServiceImpl implements ProfileService {

  private UserRepository userRepository;
  private BookmarkRepository bookmarkRepository;

  @Autowired
  public ProfileServiceImpl(UserRepository userRepository, BookmarkRepository bookmarkRepository){
    this.userRepository = userRepository;
    this.bookmarkRepository = bookmarkRepository;
  }

  @Override
  public ProfileBasicInfoDTO getBasicInfo(String username) throws UsernameNotFoundException {
    if (!userRepository.existsByUsername(username)) throw new UsernameNotFoundException();
    return userRepository.findByUsername(username).toProfileBasicInfoDTO();
  }

  @Override
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

  @Override
  public List<Pair<Long,String>> getBookmarks(String username, int page) throws UsernameNotFoundException{
    if(!userRepository.existsByUsername(username)) throw new UsernameNotFoundException();
    Sort sort = Sort.by("createdAt").descending();
    Page<ArticleEntity> articles = bookmarkRepository.findByUser(userRepository.findByUsername(username), new PageRequest(page,50, sort));
    List<Pair<Long,String>> articlesInfo = new ArrayList();
    articles.forEach(a -> articlesInfo.add(a.toInfoDTO()));
    return articlesInfo;
  }

}
