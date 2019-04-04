package be.unamur.info.mdl.dal.repository;

import be.unamur.info.mdl.dal.entity.ArticleEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<ArticleEntity,Long> {

  List<ArticleEntity> findDistinctByTitleLike(String title, Pageable pageable);
  boolean exist( Long id);
  ArticleEntity findByName(String title);


}
