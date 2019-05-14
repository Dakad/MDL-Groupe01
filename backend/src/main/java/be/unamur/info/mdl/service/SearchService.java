package be.unamur.info.mdl.service;

import be.unamur.info.mdl.dto.SearchQueryDTO;
import be.unamur.info.mdl.dto.SearchResultDTO;

import java.util.List;

public interface SearchService {

  SearchResultDTO getSearchResults(SearchQueryDTO searchQuerry);

  List<Object[]> getTags(String keyword);
}
