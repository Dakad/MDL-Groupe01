package be.unamur.info.mdl.service;

import be.unamur.info.mdl.dto.SearchQueryDTO;
import be.unamur.info.mdl.dto.SearchResultDTO;
import be.unamur.info.mdl.dto.TagsDTO;

import java.util.List;
import java.util.Map;

public interface SearchService {

  SearchResultDTO getSearchResults(SearchQueryDTO searchQuerry);

  List<Object[]> getTags(String keyword);

  Map<String,String> getAllTags();

  List<String> getAllAuthors();
}
