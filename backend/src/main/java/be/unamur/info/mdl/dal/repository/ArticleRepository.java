package be.unamur.info.mdl.dal.repository;

import be.unamur.info.mdl.dal.entity.ArticleEntity;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

  Stream<ArticleEntity> findDistinctByTitleContainingIgnoreCase(String title, Pageable pageable);

  ArticleEntity findByTitle(String title);

  Optional<ArticleEntity> findByReference(String reference);

  boolean existsByTitle(String title);

  boolean existsByReference(String reference);
}
