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

import org.hibernate.annotations.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

  List<ArticleEntity> findDistinctByTitleContainingIgnoreCase(String title, Pageable pageable);

  @Query(value = "select distinct a.* from article a, tag t, article_keywords ak where a.title ilike %?1% " +
    "and ((a.category_id = t.id and t.name in (?2)) " +
    "or (select count(distinct name) from tag t where a.id = ak.article_id and ak.tag_id = t.id and t.name in (?2)) > 0) " +
    "group by a.id"
    , nativeQuery = true)
  List<ArticleEntity> findSearchTagsResults(
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

  @Query("select a from ArticleEntity a, TagEntity t, TagEntity c where a.title like ?1 and t in a.keywords and c = a.category and (t.name in ?2 or c.name in ?2)")
  Stream<ArticleEntity> findByCategoryAndReferenceNotIn(TagEntity domain, List<String> references, Pageable pageable);

  Stream<ArticleEntity> findByCategoryLikeOrCategoryInAndReferenceNotIn(TagEntity domain, Set<TagEntity> interests, List<String> references, Pageable pageable);

  Stream<ArticleEntity> findByCategory(TagEntity domain, Pageable pageable);
}
