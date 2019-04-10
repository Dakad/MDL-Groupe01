package be.unamur.info.mdl.dal.repository;


import be.unamur.info.mdl.dal.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends JpaRepository<UserEntity, Long> {

  boolean existsByUsername(String username);

  List<UserEntity> findDistinctByFirstnameLikeOrLastnameLike(String firstname, String lastname, Pageable pageable);

  UserEntity findByUsername(String username);

  UserEntity findByEmail(String email);

  UserEntity findById(String id);
}
