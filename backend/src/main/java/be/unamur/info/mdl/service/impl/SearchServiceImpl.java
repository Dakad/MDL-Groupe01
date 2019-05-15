package be.unamur.info.mdl.service.impl;

import be.unamur.info.mdl.dal.entity.ArticleEntity;
import be.unamur.info.mdl.dal.entity.AuthorEntity;
import be.unamur.info.mdl.dal.entity.StateOfTheArtEntity;
import be.unamur.info.mdl.dal.entity.TagEntity;
import be.unamur.info.mdl.dal.entity.UserEntity;
import be.unamur.info.mdl.dal.repository.ArticleRepository;
import be.unamur.info.mdl.dal.repository.AuthorRepository;
import be.unamur.info.mdl.dal.repository.StateOfTheArtRepository;
import be.unamur.info.mdl.dal.repository.TagRepository;
import be.unamur.info.mdl.dal.repository.UserRepository;
import be.unamur.info.mdl.dto.ArticleDTO;
import be.unamur.info.mdl.dto.AuthorDTO;
import be.unamur.info.mdl.dto.SearchQueryDTO;
import be.unamur.info.mdl.dto.SearchResultDTO;
import be.unamur.info.mdl.dto.SearchResultDTO.MetaField;
import be.unamur.info.mdl.dto.SearchResultDTO.SearchResultDTOBuilder;
import be.unamur.info.mdl.dto.SearchResultDTO.SearchResultMetaDTO;
import be.unamur.info.mdl.dto.StateOfTheArtDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.SearchService;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

  public static final int PAGE_SIZE_MAX = 20;
  public static final String SORT_BY_TITLE = "title";
  private final UserRepository userRepository;
  private final ArticleRepository articleRepository;
  private final StateOfTheArtRepository stateOfTheArtRepository;
  private final AuthorRepository authorRepository;
  private final TagRepository tagRepository;


  @Autowired
  public SearchServiceImpl(UserRepository userRepository, ArticleRepository articleRepository,
    StateOfTheArtRepository stateOfTheArtRepository,
    AuthorRepository authorRepository, TagRepository tagRepository) {
    this.articleRepository = articleRepository;
    this.userRepository = userRepository;
    this.stateOfTheArtRepository = stateOfTheArtRepository;
    this.authorRepository = authorRepository;
    this.tagRepository = tagRepository;
  }

  @Override
  public SearchResultDTO getSearchResults(SearchQueryDTO searchQuery) {
    SearchResultDTOBuilder searchResult = SearchResultDTO.builder();

    // PAGEABLE
    int page = 0;
    String searchTerm = searchQuery.getTerm();
    String sort = searchQuery.getSort().toLowerCase();
    String order = searchQuery.getOrder().toLowerCase();

    SearchResultMetaDTO resultMeta = new SearchResultMetaDTO();
    Sort pageSort;
    Pageable pageable;

    if (!searchQuery.getOnly().equalsIgnoreCase("ALL")) {
      page = searchQuery.getPage() - 1;
    }

    // USERS
    if (searchQuery.getOnly().equalsIgnoreCase("ALL") || searchQuery.getOnly()
      .equalsIgnoreCase("USERS") || searchQuery.getOnly()
      .equalsIgnoreCase("AUTHORS")) {
      pageSort = this.getSortForUser(searchQuery.getSort(), searchQuery.getOrder());
      pageable = PageRequest.of(page, PAGE_SIZE_MAX, pageSort);
      searchForUsers(searchResult, searchTerm, resultMeta, pageable);
    }

    // AUTHORS
    if (searchQuery.getOnly().equalsIgnoreCase("ALL") || searchQuery.getOnly()
      .equalsIgnoreCase("AUTHORS") || searchQuery.getOnly()
      .equalsIgnoreCase("USERS")) {
      pageSort = this.getSortForAuthor(sort, order);
      pageable = PageRequest.of(page, PAGE_SIZE_MAX, pageSort);
      searchForAuthors(searchResult, searchTerm, resultMeta, pageable);
    }

    // ARTICLES
    if (searchQuery.getOnly().equalsIgnoreCase("ALL") || searchQuery.getOnly()
      .equalsIgnoreCase("ARTICLES")) {
      pageSort = this.getSortForArticle(sort, order);
      pageable = PageRequest.of(page, PAGE_SIZE_MAX, pageSort);
      searchForArticles(searchResult, searchTerm, resultMeta, pageable, searchQuery.getTag());
    }

    // SOTAS
    if (searchQuery.getOnly().equalsIgnoreCase("ALL") || searchQuery.getOnly()
      .equalsIgnoreCase("SOTAS")) {
      pageSort = this.getSortForSota(sort, order);
      pageable = PageRequest.of(page, PAGE_SIZE_MAX, pageSort);
      searchForSotas(searchResult, searchTerm, resultMeta, pageable, searchQuery.getTag());
    }

    searchResult.metas(resultMeta);

    return searchResult.build();
  }


  private void searchForAuthors(SearchResultDTOBuilder searchResult, String searchTerm,
    SearchResultMetaDTO resultMeta, Pageable pageable) {
    Page<AuthorEntity> authors = authorRepository
      .findDistinctByNameContainingIgnoreCase(searchTerm, pageable);

    List<AuthorDTO> authorsList = authors.stream().map(a -> a.toDTO())
      .collect(Collectors.toList());

    searchResult.authors(authorsList);
    resultMeta.setAuthorsMeta(this.createMeta(authors, pageable.getSort()));
  }


  private void searchForUsers(SearchResultDTOBuilder searchResult, String searchTerm,
    SearchResultMetaDTO resultMeta, Pageable pageable) {
    Page<UserEntity> users = userRepository
      .findDistinctByFirstnameContainingIgnoreCaseOrFirstnameContainingIgnoreCase(searchTerm,
        searchTerm, pageable);
    List<UserDTO> userList = users.stream().map(u -> u.toDTO())
      .collect(Collectors.toList());

    searchResult.users(userList);
    resultMeta.setUsersMeta(this.createMeta(users, pageable.getSort()));
  }


  private void searchForArticles(SearchResultDTOBuilder searchResult, String searchTerm,
    SearchResultMetaDTO resultMeta, Pageable pageable, List<String> tags) {
    Page<ArticleEntity> articles;
    if (tags.isEmpty()) {
      articles = articleRepository
        .findDistinctByTitleContainingIgnoreCase(searchTerm, pageable);
    } else {
      articles = articleRepository
        .findDistinctByTitleContainingIgnoreCaseAndKeywords_NameIn(searchTerm,tags,pageable);
    }

    List<ArticleDTO> articleList = articles.stream().map(a -> a.toDTO())
      .collect(Collectors.toList());

    searchResult.articles(articleList);
    resultMeta.setArticlesMeta(this.createMeta(articles, pageable.getSort()));
  }


  private void searchForSotas(SearchResultDTOBuilder searchResult, String searchTerm,
    SearchResultMetaDTO resultMeta, Pageable pageable, List<String> tags) {
    Page<StateOfTheArtEntity> sotas;
    if (tags.isEmpty()) {
      sotas = stateOfTheArtRepository
        .findDistinctByTitleContainingIgnoreCase(searchTerm, pageable);
    } else {
      sotas = stateOfTheArtRepository
        .findDistinctByTitleContainingIgnoreCaseAndKeywords_NameIn(searchTerm, tags, pageable);
    }

    List<StateOfTheArtDTO> sotaList = sotas.get().map(s -> s.toDTO())
      .collect(Collectors.toList());

    searchResult.statesOfTheArt(sotaList);
    resultMeta.setSotasMeta(this.createMeta(sotas, pageable.getSort()));
  }


  private Sort getSortForUser(final String sort, final String order) {
    if (sort.equalsIgnoreCase("name") || sort.equalsIgnoreCase(SORT_BY_TITLE)) {
      if (order.equalsIgnoreCase("ASC")) {
        return Sort.by("lastname", "firstname").ascending();
      }
      if (order.equalsIgnoreCase("DESC")) {
        return Sort.by(Sort.Order.desc("lastname"), Sort.Order.desc("firstname"));
      }
    }
    if(sort.equalsIgnoreCase("date")){
      return Sort.by("lastname","firstname").ascending();
    }
    return this.getSort(sort, order);
  }

  private Sort getSortForAuthor(final String sort, final String order) {
    switch (sort) {
      case "name":
      case SORT_BY_TITLE:
        if (order.equalsIgnoreCase("DESC")) {
          return Sort.by("name").descending();
        } else {
          return Sort.by("name").ascending();
        }
      case "date" : return Sort.by("name").ascending();
      default:
        return this.getSort(sort, order);
    }

  }

  private Sort getSortForArticle(final String sort, final String order) {
    if (sort.equalsIgnoreCase("name")) {
      return this.getSort(SORT_BY_TITLE, order);
    }
    return this.getSort(sort, order);
  }


  private Sort getSortForSota(final String sort, final String order) {
    if (sort.equalsIgnoreCase("name")) {
      return this.getSort(SORT_BY_TITLE, order);}
    else if(sort.equalsIgnoreCase("date")){
      return Sort.by("createdAt");
    } else {
      return this.getSort(sort, order);
    }
  }

  /**
   * Get the correct Sort based on the sortedBy term and sortedOrder
   *
   * @param searchSortedBy What to sort on
   * @param searchSortOrder Which order for the sort (ASC or DESC)
   * @return The correct Sort
   */
  private Sort getSort(String searchSortedBy, String searchSortOrder) {
    Sort pageSort = null;
    switch (searchSortedBy.toLowerCase()) {
      case SORT_BY_TITLE:
        pageSort = Sort.by(SORT_BY_TITLE);
        break;
      case "date":
        pageSort = Sort.by("publicationYear");
        break;
      case "name":
        break;
      default:
        break;
    }

    if (pageSort == null) {
      return Sort.unsorted();
    }

    if (searchSortOrder.equalsIgnoreCase("ASC")) {
      return pageSort.ascending();
    }
    if (searchSortOrder.equalsIgnoreCase("DESC")) {
      return pageSort.descending();
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
  private EnumMap<MetaField, Object> createMeta(Page<?> page, Sort pageSort) {
    EnumMap<MetaField, Object> meta = new EnumMap<>(MetaField.class);

    meta.put(MetaField.CURRENT_PAGE, page.getNumber() + 1);
    meta.put(MetaField.TOTAL_PAGES, page.getTotalPages());

    int size = page.getSize();
    int totalSize = (int) page.getTotalElements();
    size = (totalSize < size) ? totalSize : size;

    meta.put(MetaField.PAGE_SIZE, size);
    meta.put(MetaField.TOTAL_PAGE_SIZE, totalSize);

    Optional<Order> order = pageSort.get().findFirst();
    if (order.isPresent()) {
      meta.put(MetaField.ORDER_BY, order.get().getProperty());
      meta.put(MetaField.SORT_BY, order.get().getDirection().name());
    }

    return meta;
  }

  @Override
  public List<Object[]> getTags(String keyword) {
    return tagRepository.findByTerm(keyword);
  }

  @Override
  public Map<String, String> getAllTags() {
    return tagRepository.findAll().stream()
      .collect(Collectors.toMap(TagEntity::getSlug, TagEntity::getName));
  }

  @Override
  public List<String> getAllAuthors() {
    return authorRepository.findAll().stream().map(a -> a.getName()).collect(Collectors.toList());
  }
}
