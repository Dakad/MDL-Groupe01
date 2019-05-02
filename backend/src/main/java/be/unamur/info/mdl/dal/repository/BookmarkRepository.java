package be.unamur.info.mdl.dal.repository;

import be.unamur.info.mdl.dal.entity.ArticleEntity;
import be.unamur.info.mdl.dal.entity.BookmarkEntity;
import be.unamur.info.mdl.dal.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<BookmarkEntity, Long> {

  Page<BookmarkEntity> findByCreator(UserEntity creator, Pageable p);

  Optional<BookmarkEntity> findByCreatorAndArticle(UserEntity creator, ArticleEntity article);

  boolean existsByCreatorAndArticle(UserEntity creator, ArticleEntity article);
}
