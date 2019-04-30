package be.unamur.info.mdl.service.impl;

import be.unamur.info.mdl.dal.entity.ArticleEntity;
import be.unamur.info.mdl.dal.entity.StateOfTheArtEntity;
import be.unamur.info.mdl.dal.entity.TagEntity;
import be.unamur.info.mdl.dal.entity.UserEntity;
import be.unamur.info.mdl.dal.repository.ArticleRepository;
import be.unamur.info.mdl.dal.repository.StateOfTheArtRepository;
import be.unamur.info.mdl.dal.repository.TagRepository;
import be.unamur.info.mdl.dal.repository.UserRepository;
import be.unamur.info.mdl.dto.StateOfTheArtDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.StateOfTheArtService;
import be.unamur.info.mdl.service.exceptions.ArticleNotFoundException;
import be.unamur.info.mdl.service.exceptions.SotaAlreadyExistException;
import be.unamur.info.mdl.service.exceptions.SotaNotFoundException;
import be.unamur.info.mdl.service.exceptions.UsernameNotFoundException;
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

  @Autowired
  public StateOfTheArtServiceImpl(StateOfTheArtRepository sotaRepo, UserRepository userRepo,
    ArticleRepository articleRepo, TagRepository tagRepo) {
    this.sotaRepository = sotaRepo;
    this.articleRepository = articleRepo;
    this.userRepository = userRepo;
    this.tagRepository = tagRepo;

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
    newSota.setCreatedAt(LocalDate.now());

    this.attachReference(newSota, sotaData.getArticleList());

    this.attachKeywords(newSota, sotaData.getKeywordList());

    this.sotaRepository.save(newSota);

    return newSota.toDTO();
  }

  @Override
  public boolean delete(String reference, String username) throws UsernameNotFoundException {

    Optional<StateOfTheArtEntity> dbSota = sotaRepository.findByReference(reference);
    StateOfTheArtEntity sota;
    if (!dbSota.isPresent()) {
      throw new SotaNotFoundException("The referenced article was not found");
    } else {
      sota = dbSota.get();
    }

    if (!sota.getCreator().getUsername().equals(username)) {
      throw new UsernameNotFoundException("The user is not the owner of the sota");
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
   *
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


}

