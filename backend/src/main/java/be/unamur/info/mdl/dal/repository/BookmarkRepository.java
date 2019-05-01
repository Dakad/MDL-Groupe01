package be.unamur.info.mdl.dal.repository;

import be.unamur.info.mdl.dal.entity.ArticleEntity;
import be.unamur.info.mdl.dal.entity.BookmarkEntity;
import be.unamur.info.mdl.dal.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<BookmarkEntity, Long> {

  Page<ArticleEntity> findByCreator(UserEntity creator, Pageable p);

  Optional<BookmarkEntity> findByCreatorAndArticle(UserEntity creator, ArticleEntity article);
}
