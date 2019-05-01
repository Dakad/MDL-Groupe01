package be.unamur.info.mdl.dal.repository;

import be.unamur.info.mdl.dal.entity.ArticleEntity;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import be.unamur.info.mdl.dal.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

  Page<ArticleEntity> findDistinctByTitleContainingIgnoreCase(String title, Pageable pageable);

  ArticleEntity findByTitle(String title);

  Optional<ArticleEntity> findByReference(String reference);

  boolean existsByTitle(String title);

  boolean existsByReference(String reference);

  Stream<ArticleEntity> findDistinctByCreator(UserEntity creator, Pageable pageable);

  Stream<ArticleEntity> findDistinctFirstByReferenceIsIn(List<String> references, Sort sortBy);
}
