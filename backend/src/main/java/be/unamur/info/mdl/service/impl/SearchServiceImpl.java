package be.unamur.info.mdl.service.impl;

import be.unamur.info.mdl.dal.entity.ArticleEntity;
import be.unamur.info.mdl.dal.entity.StateOfTheArtEntity;
import be.unamur.info.mdl.dal.entity.UserEntity;
import be.unamur.info.mdl.dal.repository.ArticleRepository;
import be.unamur.info.mdl.dal.repository.StateOfTheArtRepository;
import be.unamur.info.mdl.dal.repository.UserRepository;
import be.unamur.info.mdl.dto.*;
import be.unamur.info.mdl.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("searchService")

public class SearchServiceImpl implements SearchService {
  private UserRepository userRepository;
  private ArticleRepository articleRepository;
  private StateOfTheArtRepository stateOfTheArtRepository;

  @Autowired
  public SearchServiceImpl(UserRepository userRepository, ArticleRepository articleRepository,StateOfTheArtRepository stateOfTheArtRepository){
    this.articleRepository = articleRepository;
    this.userRepository = userRepository;
    this.stateOfTheArtRepository = stateOfTheArtRepository;
  }

  @Override
  public SearchResultDTO getSearchResults(SearchQueryDTO searchQuery){
    SearchResultDTO searchResultDTO = new SearchResultDTO();

    //PAGEABLE
    int page = searchQuery.getPage();
    String sort;
    switch(searchQuery.getSort()){
      case("DATE") : sort = "createdAt";
      case("NAME") : sort = "title";
      default: sort = "createdAt";
    }

    String keywords = searchQuery.getKeyword();
    //USERS
    Pageable pageable;
    if(sort == "name") {
      if(searchQuery.getOrder() == "ASC") pageable = PageRequest.of(page, 20, Sort.by("lastname").ascending());
      else pageable = PageRequest.of(page, 20, Sort.by("lastname").descending());
    }
    else pageable = PageRequest.of(page, 20);
    List<UserEntity> userEntityList = userRepository.findDistinctByFirstnameLikeOrLastnameLike(keywords,keywords,pageable);
    List<UserDTO> userDTOList = new ArrayList();
    userEntityList.forEach(u -> userDTOList.add(u.toDTO()));
    searchResultDTO.setUsers(userDTOList);

    //ARTICLES
    if(searchQuery.getOrder() == "ASC") pageable = PageRequest.of(page,20,Sort.by(sort).ascending());
    else pageable = PageRequest.of(page,20,Sort.by(sort).descending());
    List<ArticleEntity> articleEntityList = articleRepository.findDistinctByTitleLike(keywords,pageable);
    List<ArticleDTO> articleDTOList = new ArrayList();
    articleEntityList.forEach(a -> articleDTOList.add(a.toDTO()));
    searchResultDTO.setArticles(articleDTOList);

    //SOTA
    List<StateOfTheArtEntity> sotaList = stateOfTheArtRepository.findDistinctByNameLike(keywords);
    List<StateOfTheArtDTO> sotaDTOList = new ArrayList();
    sotaList.forEach(s -> sotaDTOList.add(s.toDTO()));
    searchResultDTO.setStatesOfTheArt(sotaDTOList);

    return searchResultDTO;
  }
}
