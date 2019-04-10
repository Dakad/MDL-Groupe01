package be.unamur.info.mdl.dal.repository;

import be.unamur.info.mdl.dal.entity.AuthorEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {

  Optional<AuthorEntity> findByName(String name);
}
