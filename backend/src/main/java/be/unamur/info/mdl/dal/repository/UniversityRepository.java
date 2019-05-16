package be.unamur.info.mdl.dal.repository;

import be.unamur.info.mdl.dal.entity.UniversityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityRepository extends JpaRepository<UniversityEntity, Long> {

  UniversityEntity findByAbbreviation(String name);
}
