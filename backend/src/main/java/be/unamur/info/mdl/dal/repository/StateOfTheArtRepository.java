package be.unamur.info.mdl.dal.repository;

import be.unamur.info.mdl.dal.entity.StateOfTheArtEntity;
import java.util.stream.Stream;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateOfTheArtRepository extends JpaRepository<StateOfTheArtEntity, Long> {

  Stream<StateOfTheArtEntity> findDistinctByNameContainingIgnoreCase(String title,
    Pageable pageable);
}
