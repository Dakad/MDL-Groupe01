package be.unamur.info.mdl.dal.repository;

import be.unamur.info.mdl.dal.entity.TagEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<TagEntity, String> {

  Optional<TagEntity> findByName(String name);

  Optional<TagEntity> findBySlug(String slug);
}
