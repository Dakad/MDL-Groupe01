package be.unamur.info.mdl.dal.repository;


import be.unamur.info.mdl.dal.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

  boolean existsByUsername(String username);

  Page<UserEntity> findDistinctByFirstnameContainingIgnoreCaseOrFirstnameContainingIgnoreCase(
    String firstname,
    String lastname, Pageable pageable);

  UserEntity findByUsername(String username);

  UserEntity findByEmail(String email);

}
