package be.unamur.info.mdl.dal.repository;

import be.unamur.info.mdl.dal.entity.AuthorEntity;
import java.util.Optional;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {

  Optional<AuthorEntity> findByName(String name);

  Stream<AuthorEntity> findDistinctByNameContainingIgnoreCase(String name, Pageable pageable);
}
