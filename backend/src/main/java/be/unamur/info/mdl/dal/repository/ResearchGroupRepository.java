package be.unamur.info.mdl.dal.repository;

import be.unamur.info.mdl.dal.entity.ResearchGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResearchGroupRepository extends JpaRepository<ResearchGroupEntity, Long> {

  Optional<ResearchGroupEntity> findBySlug(String slug);
}
