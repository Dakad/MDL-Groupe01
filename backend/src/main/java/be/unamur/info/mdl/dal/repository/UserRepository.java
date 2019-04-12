package be.unamur.info.mdl.dal.repository;


import be.unamur.info.mdl.dal.entity.UserEntity;
import java.util.stream.Stream;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

  boolean existsByUsername(String username);

  Stream<UserEntity> findDistinctByFirstnameContainingIgnoreCaseOrFirstnameContainingIgnoreCase(
    String firstname,
    String lastname, Pageable pageable);

  UserEntity findByUsername(String username);

  UserEntity findByEmail(String email);
}
