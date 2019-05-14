package be.unamur.info.mdl.dal.repository;

import be.unamur.info.mdl.dal.entity.ArticleEntity;
import be.unamur.info.mdl.dal.entity.BookmarkEntity;
import be.unamur.info.mdl.dal.entity.TagEntity;
import be.unamur.info.mdl.dal.entity.UserEntity;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

  Page<ArticleEntity> findDistinctByTitleContainingIgnoreCase(String title, Pageable pageable);

  Page<ArticleEntity> findDistinctByTitleContainingIgnoreCaseAndKeywords_SlugIn(
    String title, List<String> keywords, Pageable pageable);

  ArticleEntity findByTitle(String title);

  Optional<ArticleEntity> findByReference(String reference);

  boolean existsByTitle(String title);

  boolean existsByReference(String reference);

  Stream<ArticleEntity> findDistinctByCreator(UserEntity creator, Pageable pageable);

  Stream<ArticleEntity> findDistinctFirst5ByReferenceIsIn(List<String> references, Sort sortBy);

  Stream<ArticleEntity> findDistinctFirst5ByCategory(TagEntity category, Sort sortBy);

  @Query(value = "select a.* from article a, user u, user_follower f where u.id = f.user_id and a.creator_user_id = f.following_id and u.username = ?1",
    nativeQuery = true)
  Stream<ArticleEntity> findDistinctByFollower(String username, Pageable p);

  Stream<ArticleEntity> findByReferenceNotIn(List<String> references, Pageable p);

  Stream<ArticleEntity> findByCategoryAndReferenceNotIn(TagEntity domain, List<String> references, Pageable pageable);

  Stream<ArticleEntity> findByCategoryLikeOrCategoryInAndReferenceNotIn(TagEntity domain, Set<TagEntity> interests, List<String> references, Pageable pageable);
}
