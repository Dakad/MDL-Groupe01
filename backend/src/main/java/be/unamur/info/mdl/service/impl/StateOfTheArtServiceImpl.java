package be.unamur.info.mdl.service.impl;

import be.unamur.info.mdl.dal.entity.*;
import be.unamur.info.mdl.dal.repository.*;
import be.unamur.info.mdl.dto.StateOfTheArtDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.StateOfTheArtService;
import be.unamur.info.mdl.exceptions.ArticleNotFoundException;
import be.unamur.info.mdl.exceptions.BookmarkNotFoundException;
import be.unamur.info.mdl.exceptions.SotaAlreadyExistException;
import be.unamur.info.mdl.exceptions.SotaNotFoundException;
import be.unamur.info.mdl.exceptions.UserNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

@Service("stateOfTheArtService")
@Transactional
public class StateOfTheArtServiceImpl implements StateOfTheArtService {

  private StateOfTheArtRepository sotaRepository;

  private ArticleRepository articleRepository;
  private final UserRepository userRepository;
  private final TagRepository tagRepository;
  private final BookmarkRepository bookmarkRepository;

  @Autowired
  public StateOfTheArtServiceImpl(StateOfTheArtRepository sotaRepo, UserRepository userRepo,
         ArticleRepository articleRepo, TagRepository tagRepo, BookmarkRepository bookmarkRepository) {
    this.sotaRepository = sotaRepo;
    this.articleRepository = articleRepo;
    this.userRepository = userRepo;
    this.tagRepository = tagRepo;
    this.bookmarkRepository = bookmarkRepository;
  }


  @Override
  public StateOfTheArtDTO getSotaByReference(String reference) throws SotaNotFoundException {
    Optional<StateOfTheArtEntity> dbSota = this.sotaRepository.findByReference(reference);
    if (!dbSota.isPresent()) {
      throw new SotaNotFoundException("The referenced article was not found");
    } else {
      return dbSota.get().toDTO();
    }
  }


  @Override
  public StateOfTheArtDTO create(@Valid StateOfTheArtDTO sotaData, UserDTO currentUser)
    throws SotaAlreadyExistException, ArticleNotFoundException {
    if (sotaRepository.existsByReference(sotaData.getReference())) {
      throw new SotaAlreadyExistException(
        "The SoTA reference is already saved : " + sotaData.getReference());
    }

    if (articleRepository.existsByTitle(sotaData.getTitle())) {
      throw new SotaAlreadyExistException(
        "The SoTA title is already taken : " + sotaData.getTitle());
    }

    // Create a newDTO with the data sent (title, reference, ...)
    StateOfTheArtEntity newSota = StateOfTheArtEntity.of(sotaData);

    if (sotaData.getReference() == null || sotaData.getReference().isEmpty()) {
      newSota.setReference(this.generateReference(sotaData.getTitle()));
    }

    UserEntity creator = userRepository.findByUsername(currentUser.getUsername());
    newSota.setCreator(creator);

    // Set the category or create a new one
    this.attachCategory(newSota, sotaData.getCategory());

    this.attachArticles(newSota, sotaData.getArticleList());

    this.attachKeywords(newSota, sotaData.getKeywordList());

    this.sotaRepository.save(newSota);

    return newSota.toDTO();
  }

  @Override
  public boolean delete(String reference, String username) throws UserNotFoundException {

    Optional<StateOfTheArtEntity> dbSota = sotaRepository.findByReference(reference);
    StateOfTheArtEntity sota;
    if (!dbSota.isPresent()) {
      throw new SotaNotFoundException("The referenced article was not found");
    } else {
      sota = dbSota.get();
    }

    if (!sota.getCreator().getUsername().equals(username)) {
      throw new UserNotFoundException("The user is not the owner of the sota");
    }

    sota.getCreator().getStateOfTheArts().remove(sota);

    this.sotaRepository.save(sota);

    return true;
  }

  /**
   * Generate a MD5 Hash based on the provided string
   *
   * @param msg The provided string
   * @return The hashed msg
   */
  private String generateReference(String msg) {
    byte[] titleBytes = msg.getBytes();
    return DigestUtils.md5DigestAsHex(titleBytes);
  }


  /**
   * Attach a new category or the one persisted in DB.
   *
   * @param newSota The new SoTA being created
   * @param categoryName - The category name
   */
  private void attachCategory(StateOfTheArtEntity newSota, String categoryName) {
    TagEntity category = ServiceUtils.getOrCreateTag(categoryName, this.tagRepository);
    category.getStatesOfTheArts().add(newSota);
    newSota.setCategory(category);
  }

  /**
   * Attach the corresponding Article (persisted) to the new SoTA
   *
   * @param newSota The new SoTA being created
   * @param references The article's reference list
   * @throws ArticleNotFoundException The article reference is not in persistence.
   */
  private void attachArticles(StateOfTheArtEntity newSota, List<String> references)
    throws ArticleNotFoundException {
    List<ArticleEntity> list = new LinkedList<>();
    Optional<ArticleEntity> entity;

    for (String reference : references) {
      entity = this.articleRepository.findByReference(reference);
      if (!entity.isPresent()) {
        throw new ArticleNotFoundException("The referenced article was not found : " + reference);
      } else {
        entity.get().getSotas().add(newSota);
        list.add(entity.get());
      }
    }
    newSota.setArticles(list);
  }

  /**
   * Attach the corresponding Author(created or persisted) to the new SoTA
   *
   * @param newSota The new SoTA being created
   * @param keywords - The keyword's name list.
   */
  private void attachKeywords(StateOfTheArtEntity newSota, List<String> keywords) {
    List<TagEntity> list = new LinkedList<>();
    TagEntity keyword;

    for (String keywordName : keywords) {
      keyword = ServiceUtils.getOrCreateTag(keywordName, this.tagRepository);
      keyword.getStatesOfTheArts().add(newSota);
      list.add(keyword);
    }

    newSota.setKeywords(list);
  }

  @Override
  public boolean addBookmark(String reference, String username, String note)
    throws SotaNotFoundException {

    Optional<StateOfTheArtEntity> sota = sotaRepository.findByReference(reference);
    if (!sota.isPresent()) {
      throw new SotaNotFoundException("The requested state of the art was not found");
    }
    //Check if the user has already bookmarked this article
    UserEntity user = userRepository.findByUsername(username);
    if (user.getBookmarks().stream().anyMatch(b -> b.getArticle().equals(sota.get()))) {
      return false;
    }

    BookmarkEntity bookmark = new BookmarkEntity();
    bookmark.setSota(sota.get());
    bookmark.setCreator(user);
    bookmark.setNote(note);
    user.getBookmarks().add(bookmark);
    sota.get().getBookmarks().add(bookmark);

    return true;
  }


  @Override
  public boolean isBookmarked(String reference, String username) throws SotaNotFoundException {
    Optional<StateOfTheArtEntity> sota = sotaRepository.findByReference(reference);
    if (!sota.isPresent()) {
      throw new SotaNotFoundException("The requested state of the art was not found");
    }
    UserEntity user = userRepository.findByUsername(username);
    return bookmarkRepository.existsByCreatorAndSota(user, sota.get());
  }


  @Override
  public boolean removeBookmark(String reference, String username)
    throws SotaNotFoundException, BookmarkNotFoundException {
    Optional<StateOfTheArtEntity> sota = sotaRepository.findByReference(reference);
    if (!sota.isPresent()) {
      throw new SotaNotFoundException("State of the art does not exist");
    }
    UserEntity user = userRepository.findByUsername(username);
    Optional<BookmarkEntity> bookmark = bookmarkRepository
      .findByCreatorAndSota(user, sota.get());
    if (!bookmark.isPresent()) {
      throw new BookmarkNotFoundException("The request state of the art was not present in the bookmarks");
    }
    user.getBookmarks().remove(bookmark.get());
    sota.get().getBookmarks().remove(bookmark.get());

    userRepository.save(user);
    sotaRepository.save(sota.get());
    bookmarkRepository.delete(bookmark.get());
    return true;
  }

  @Override
  public Map<String,String> getAll(){
    return sotaRepository.findAll().stream().collect(Collectors.toMap(StateOfTheArtEntity::getReference,StateOfTheArtEntity::getTitle));
  }
}

