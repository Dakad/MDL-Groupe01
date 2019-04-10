package be.unamur.info.mdl.dal.repository;

import be.unamur.info.mdl.dal.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<TagEntity, String> {

}
