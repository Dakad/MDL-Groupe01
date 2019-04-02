package be.unamur.info.mdl.dal.repository;

import be.unamur.info.mdl.dal.entity.StateOfTheArtEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StateOfTheArtRepository extends JpaRepository<StateOfTheArtEntity,Long> {

  List<StateOfTheArtEntity> findDistinctByNameLike(String title, Pageable pageable);
}
