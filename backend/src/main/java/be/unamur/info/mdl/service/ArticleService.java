package be.unamur.info.mdl.service;

import be.unamur.info.mdl.dto.ArticleDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.exceptions.ArticleAlreadyExistException;
import be.unamur.info.mdl.service.exceptions.ArticleNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ArticleService {

  boolean create(ArticleDTO article, UserDTO creator) throws ArticleAlreadyExistException;

  /**
   * Retrieve a specific article
   * @param reference The reference of wanted article
   * @return The referenced article
   */
  ArticleDTO getArticleByReference(String reference) throws ArticleNotFoundException;

  /**
   * Retrieve a list of article based their references
   * @param references The provided references
   * @return The list of referenced articles
   */
  List<ArticleDTO> listArticleByReferences(List<String> references);

  /**
   * Retrieve a list of article based their categories
   * @param categories The provided categories
   * @return The list of referenced articles
   */
  Map<String, List<ArticleDTO>> listArticleByCategories(List<String> categories);
}
