package be.unamur.info.mdl.dal.repository;

import be.unamur.info.mdl.dal.entity.StateOfTheArtEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateOfTheArtRepository extends JpaRepository<StateOfTheArtEntity,Long> {

  Optional <StateOfTheArtEntity> findDistinctByNameLike(String title);
}
