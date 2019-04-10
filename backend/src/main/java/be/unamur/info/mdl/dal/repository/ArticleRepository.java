package be.unamur.info.mdl.dal.repository;

import be.unamur.info.mdl.dal.entity.ArticleEntity;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

  List<ArticleEntity> findDistinctByTitleLike(String title, Pageable pageable);

  ArticleEntity findByTitle(String title);

  ArticleEntity findByReference(String reference);

  boolean existsByTitle(String title);

  boolean existsByReference(String reference);
}
