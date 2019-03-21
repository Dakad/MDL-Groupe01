package be.unamur.info.mdl.service.impl;

import be.unamur.info.mdl.dal.entity.UserEntity;
import be.unamur.info.mdl.dal.repository.UserRepository;
import be.unamur.info.mdl.dto.SearchQueryDTO;
import be.unamur.info.mdl.dto.SearchResultDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class SearchServiceImpl implements SearchService {
  private UserRepository userRepository;

  @Autowired
  public SearchServiceImpl(UserRepository userRepository){
    this.userRepository = userRepository;
  }

  @Override
  public SearchResultDTO getSearchResults(SearchQueryDTO searchQuery){
    SearchResultDTO searchResultDTO = new SearchResultDTO();

    //USERS
    String keywords = String.join(" ",searchQuery.getKeyword());
    List<UserEntity> userEntityList = userRepository.findDistinctByFirstnameLikeOrLastnameLike(keywords,keywords);
    List<UserDTO> userDTOList = new ArrayList();
    userEntityList.forEach(u -> userDTOList.add(u.toDTO()));
    searchResultDTO.setUsers(userDTOList);

    //ARTICLES

    return searchResultDTO;
  }
}
