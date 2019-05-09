package be.unamur.info.mdl.dal.repository;

import be.unamur.info.mdl.dal.entity.TagEntity;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import be.unamur.info.mdl.dto.TagsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TagRepository extends JpaRepository<TagEntity, String> {

  Optional<TagEntity> findByName(String name);

  Optional<TagEntity> findBySlug(String slug);

  @Query("select t from TagEntity t where t.slug in :list or t.name in :list" )
  List<TagEntity> findByNameOrSlugIn(@Param("list") List<String> categories);

  @Query(value = "select t.name, count(distinct a.id) from TAG t, ARTICLE a, ARTICLE_KEYWORDS ak where (a.category_id = t.id and a.title ilike %?1%) or (ak.tag_id = t.id and a.title ilike %?1% and ak.article_id = a.id) group by t.name",
  nativeQuery = true)//or t in a.keywords
  List<Object[]> findByTerm(String term);
}
