package be.unamur.info.mdl.service.impl;

import be.unamur.info.mdl.dal.entity.*;
import be.unamur.info.mdl.dal.repository.*;
import be.unamur.info.mdl.dto.StateOfTheArtDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.StateOfTheArtService;
import be.unamur.info.mdl.service.exceptions.ArticleNotFoundException;
import be.unamur.info.mdl.service.exceptions.BookmarkNotFoundException;
import be.unamur.info.mdl.service.exceptions.SotaAlreadyExistException;
import be.unamur.info.mdl.service.exceptions.SotatNotFoundException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
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
  public StateOfTheArtDTO getSotaByReference(String reference) throws SotatNotFoundException {
    Optional<StateOfTheArtEntity> dbSota = this.sotaRepository.findByReference(reference);
    if (!dbSota.isPresent()) {
      throw new SotatNotFoundException("The referenced article was not found");
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

    if(sotaData.getReference() == null || sotaData.getReference().isEmpty()){
      newSota.setReference(this.generateReference(sotaData.getTitle()));
    }

    UserEntity creator = userRepository.findByUsername(currentUser.getUsername());
    newSota.setCreator(creator);
    newSota.setCreatedAt(LocalDate.now());

    this.attachReference(newSota, sotaData.getArticleList());

    this.attachKeywords(newSota, sotaData.getKeywordList());

    this.sotaRepository.save(newSota);

    return newSota.toDTO();
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
   * Attach the corresponding Article (persisted) to the new SoTA
   *
   * @param newSota The new SoTA being created
   * @param references The article's reference list
   * @throws ArticleNotFoundException The article reference is not in persistence.
   */
  private void attachReference(StateOfTheArtEntity newSota, List<String> references)
    throws ArticleNotFoundException {
    List<ArticleEntity> list = new LinkedList<ArticleEntity>();
    Optional<ArticleEntity> entity;

    for (String reference : references) {
      entity = this.articleRepository.findByReference(reference);
      if (!entity.isPresent()) {
        throw new ArticleNotFoundException("The referenced article was not found : " + reference);
      } else {
        list.add(entity.get());
      }
    }
    newSota.setArticles(list);
  }

  /**
   * Attach the corresponding Author(created or persisted) to the new SoTA
   * @param newSota The new SoTA being created
   * @param keywords - The keyword's name list.
   */
  private void attachKeywords(StateOfTheArtEntity newSota, List<String> keywords) {
    List<TagEntity> list = new LinkedList<TagEntity>();
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
    throws SotatNotFoundException {

    Optional<StateOfTheArtEntity> sota = sotaRepository.findByReference(reference);
    if (!sota.isPresent()) {
      throw new SotatNotFoundException("The requested state of the art was not found");
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
  public boolean isBookmarked(String reference, String username) throws SotatNotFoundException {
    Optional<StateOfTheArtEntity> sota = sotaRepository.findByReference(reference);
    if (!sota.isPresent()) {
      throw new SotatNotFoundException("The requested state of the art was not found");
    }
    UserEntity user = userRepository.findByUsername(username);
    return bookmarkRepository.existsByCreatorAndSota(user, sota.get());
  }


  @Override
  public boolean removeBookmark(String reference, String username)
    throws SotatNotFoundException, BookmarkNotFoundException {
    Optional<StateOfTheArtEntity> sota = sotaRepository.findByReference(reference);
    if (!sota.isPresent()) {
      throw new SotatNotFoundException("State of the art does not exist");
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


}

