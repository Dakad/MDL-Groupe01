package be.unamur.info.mdl.dal.repository;

import be.unamur.info.mdl.dal.entity.StateOfTheArtEntity;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateOfTheArtRepository extends JpaRepository<StateOfTheArtEntity, Long> {

  Stream<StateOfTheArtEntity> findDistinctByTitleContainingIgnoreCase(String title,
    Pageable pageable);

  Optional<StateOfTheArtEntity> findByReference(String reference);

  boolean existsByReference(String reference);
}
