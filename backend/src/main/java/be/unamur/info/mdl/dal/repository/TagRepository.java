package be.unamur.info.mdl.dal.repository;

import be.unamur.info.mdl.dal.entity.TagEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TagRepository extends JpaRepository<TagEntity, String> {

  Optional<TagEntity> findByName(String name);

  Optional<TagEntity> findBySlug(String slug);

  @Query("select t from TagEntity t where t.slug in :list or t.name in :list" )
  List<TagEntity> findByNameOrSlugIn(@Param("list") List<String> categories);
}
