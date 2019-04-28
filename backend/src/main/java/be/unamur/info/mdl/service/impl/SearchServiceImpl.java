package be.unamur.info.mdl.service.impl;

import be.unamur.info.mdl.dal.entity.ArticleEntity;
import be.unamur.info.mdl.dal.entity.AuthorEntity;
import be.unamur.info.mdl.dal.entity.StateOfTheArtEntity;
import be.unamur.info.mdl.dal.entity.UserEntity;
import be.unamur.info.mdl.dal.repository.ArticleRepository;
import be.unamur.info.mdl.dal.repository.AuthorRepository;
import be.unamur.info.mdl.dal.repository.StateOfTheArtRepository;
import be.unamur.info.mdl.dal.repository.UserRepository;
import be.unamur.info.mdl.dto.ArticleDTO;
import be.unamur.info.mdl.dto.AuthorDTO;
import be.unamur.info.mdl.dto.SearchQueryDTO;
import be.unamur.info.mdl.dto.SearchResultDTO;
import be.unamur.info.mdl.dto.SearchResultDTO.SearchResultMetaDTO;
import be.unamur.info.mdl.dto.SearchResultDTO.MetaField;
import be.unamur.info.mdl.dto.SearchResultDTO.SearchResultDTOBuilder;
import be.unamur.info.mdl.dto.StateOfTheArtDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.SearchService;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

@Service("searchService")
@Transactional
public class SearchServiceImpl implements SearchService {

  private final UserRepository userRepository;
  private final ArticleRepository articleRepository;
  private final StateOfTheArtRepository stateOfTheArtRepository;
  private final AuthorRepository authorRepository;


  @Autowired
  public SearchServiceImpl(UserRepository userRepository, ArticleRepository articleRepository,
    StateOfTheArtRepository stateOfTheArtRepository,
    AuthorRepository authorRepository) {
    this.articleRepository = articleRepository;
    this.userRepository = userRepository;
    this.stateOfTheArtRepository = stateOfTheArtRepository;
    this.authorRepository = authorRepository;
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

    // PAGEABLE
    int page = searchQuery.getPage();
    String searchTerm = searchQuery.getSearchTerm();
    Sort pageSort = this.getSort(sort, searchQuery.getOrder());
    Pageable pageable = PageRequest.of(page, 20, pageSort);
    SearchResultMetaDTO resultMeta = new SearchResultMetaDTO();

    // USERS

    Page<UserEntity> users = userRepository
      .findDistinctByFirstnameContainingIgnoreCaseOrFirstnameContainingIgnoreCase(searchTerm,
        searchTerm, pageable);
    List<UserDTO> userList = users.stream().map(u -> u.toDTO())
      .collect(Collectors.toList());

    searchResult.users(userList);
    resultMeta.setUsersMeta(this.createMeta(users, pageSort));

    // AUTHORS

    Page<AuthorEntity> authors = authorRepository
      .findDistinctByNameContainingIgnoreCase(searchTerm, pageable);

    List<AuthorDTO> authorsList = authors.stream().map(a -> a.toDTO())
      .collect(Collectors.toList());

    searchResult.authors(authorsList);
    resultMeta.setAuthorsMeta(this.createMeta(authors, pageSort));


    // ARTICLES

    pageSort = this.getSort(sort, searchQuery.getOrder());
    pageable = PageRequest.of(page, 20, pageSort);

    Page<ArticleEntity> articles = articleRepository
      .findDistinctByTitleContainingIgnoreCase(searchTerm, pageable);


    List<ArticleDTO> articleList = articles.stream().map(a -> a.toDTO())
      .collect(Collectors.toList());

    searchResult.articles(articleList);
    resultMeta.setArticlesMeta(this.createMeta(articles, pageSort));


    // SOTAS

    pageSort = this.getSort(sort, searchQuery.getOrder());
    pageable = PageRequest.of(page, 20, pageSort);

    Page<StateOfTheArtEntity> sotas = stateOfTheArtRepository
      .findDistinctByTitleContainingIgnoreCase(searchTerm, pageable);

    List<StateOfTheArtDTO> sotaList = sotas.get().map(s -> s.toDTO())
      .collect(Collectors.toList());

    searchResult.statesOfTheArt(sotaList);
    resultMeta.setSotasMeta(this.createMeta(sotas, pageSort));


    searchResult.metas(resultMeta);

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
        if (searchSortOrder.equalsIgnoreCase("ASC")) {
          pageSort = Sort.by("lastname", "firstname").ascending();
        }
        if (searchSortOrder.equalsIgnoreCase("DESC")) {
          pageSort = Sort.by(Sort.Order.desc("lastname"), Sort.Order.desc("firstname"));
        }
        break;
      case "createdAt":
        if (searchSortOrder.equalsIgnoreCase("ASC")) {
          pageSort = Sort.by(searchSortedBy).ascending();
        }
        break;
      default:
        pageSort = Sort.unsorted();
    }

    return pageSort;
  }

  /**
   * Create the meta data about the search result based on the provided Page result and its Sort
   *
   * @param page The provided Page used for the findBy
   * @param pageSort The Sort used with the Pageable
   * @return the {@link EnumMap} to store the specific meta
   */
  private EnumMap<MetaField, Object> createMeta(Page<?> page, Sort pageSort){
    EnumMap<MetaField, Object> meta = new EnumMap<>(MetaField.class);

    meta.put(MetaField.CURRENT_PAGE, page.getNumber() + 1);
    meta.put(MetaField.TOTAL_PAGES, page.getTotalPages());

    int size = page.getSize();
    int totalSize = (int) page.getTotalElements();
    size = (totalSize < size) ? totalSize : size;

    meta.put(MetaField.SIZE, size);
    meta.put(MetaField.TOTAL_SIZE, totalSize);

    Order order = pageSort.get().findFirst().get();
    meta.put(MetaField.ORDER_BY, order.getProperty());
    meta.put(MetaField.SORT_BY, order.getDirection().name());

    return meta;
  }
}
