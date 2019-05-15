package be.unamur.info.mdl.dal.repository;

import be.unamur.info.mdl.dal.entity.StateOfTheArtEntity;
import be.unamur.info.mdl.dal.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateOfTheArtRepository extends JpaRepository<StateOfTheArtEntity, Long> {

  Page<StateOfTheArtEntity> findDistinctByTitleContainingIgnoreCase(String title,
    Pageable pageable);

  Page<StateOfTheArtEntity> findDistinctByTitleContainingIgnoreCaseAndKeywords_SlugIn(
    String title, List<String> keywords, Pageable pageable);

  Stream<StateOfTheArtEntity> findDistinctByCreator(UserEntity user, Pageable pageable);

  Optional<StateOfTheArtEntity> findByReference(String reference);

  boolean existsByReference(String reference);

}
