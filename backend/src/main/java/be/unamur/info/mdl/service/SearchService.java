package be.unamur.info.mdl.service;

import be.unamur.info.mdl.dto.SearchQueryDTO;
import be.unamur.info.mdl.dto.SearchResultDTO;

public interface SearchService {

  public SearchResultDTO getSearchResults(SearchQueryDTO searchQuerry);
}
