package be.unamur.info.mdl.service.impl;

import be.unamur.info.mdl.dal.repository.ArticleRepository;
import be.unamur.info.mdl.dal.repository.StateOfTheArtRepository;
import be.unamur.info.mdl.dal.repository.UserRepository;
import be.unamur.info.mdl.dto.ArticleDTO;
import be.unamur.info.mdl.dto.SearchQueryDTO;
import be.unamur.info.mdl.dto.SearchResultDTO;
import be.unamur.info.mdl.dto.SearchResultDTO.SearchResultDTOBuilder;
import be.unamur.info.mdl.dto.StateOfTheArtDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.SearchService;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service("searchService")
@Transactional
public class SearchServiceImpl implements SearchService {

  private UserRepository userRepository;
  private ArticleRepository articleRepository;
  private StateOfTheArtRepository stateOfTheArtRepository;

  @Autowired
  public SearchServiceImpl(UserRepository userRepository, ArticleRepository articleRepository,
    StateOfTheArtRepository stateOfTheArtRepository) {
    this.articleRepository = articleRepository;
    this.userRepository = userRepository;
    this.stateOfTheArtRepository = stateOfTheArtRepository;
  }

  @Override
  public SearchResultDTO getSearchResults(SearchQueryDTO searchQuery) {
    SearchResultDTOBuilder searchResult = SearchResultDTO.builder();

    // Replace the provided sort by the real Entities field
    String sort = searchQuery.getSort();
    switch (sort) {
      case ("NAME"):
        sort = "title";
        break;
      case ("DATE"):
      default:
        sort = "createdAt";
        break;
    }

    //PAGEABLE
    int page = searchQuery.getPage();
    String searchTerm = searchQuery.getSearchTerm();
    Sort pageSort = this.getSort(sort, searchQuery.getOrder());
    Pageable pageable = PageRequest.of(page, 20, pageSort);

    //USERS

    List<UserDTO> userList = userRepository
      .findDistinctByFirstnameContainingIgnoreCaseOrFirstnameContainingIgnoreCase(searchTerm, searchTerm, pageable)
      .map(u -> u.toDTO())
      .collect(Collectors.toList());

    searchResult.users(userList);

    //ARTICLES
    pageSort = this.getSort(sort, searchQuery.getOrder());
    pageable = PageRequest.of(page, 20, pageSort);

    List<ArticleDTO> articleList = articleRepository
      .findDistinctByTitleContainingIgnoreCase(searchTerm, pageable)
      .map(a -> a.toDTO())
      .collect(Collectors.toList());

    searchResult.articles(articleList);

    //SOTA
    pageSort = this.getSort(sort, searchQuery.getOrder());
    pageable = PageRequest.of(page, 20, pageSort);

    List<StateOfTheArtDTO> sotaList = stateOfTheArtRepository
      .findDistinctByNameContainingIgnoreCase(searchTerm, pageable)
      .map(s -> s.toDTO())
      .collect(Collectors.toList());

    searchResult.statesOfTheArt(sotaList);

    return searchResult.build();
  }

  /**
   * Get the correct Sort based on the sortedBy term and sortedOrder
   * @param searchSortedBy What to sort on
   * @param searchSortOrder Which order for the sort (ASC or DESC)
   * @return The correct Sort
   */
  private Sort getSort(String searchSortedBy, String searchSortOrder) {
    Sort pageSort = Sort.unsorted();
    switch (searchSortedBy) {
      case "name":
      case "title":
        if (searchSortOrder == "ASC") {
          pageSort = Sort.by("lastname", "firstname").ascending();
        }
        if (searchSortOrder == "DESC") {
          pageSort = Sort.by(Sort.Order.desc("lastname"), Sort.Order.desc("firstname"));
        }
        break;
      case "createdAt":
        if (searchSortOrder == "ASC") {
          pageSort = Sort.by(searchSortedBy).ascending();
        }
        break;
      default:
        pageSort = Sort.unsorted();
    }

    return pageSort;
  }
}
