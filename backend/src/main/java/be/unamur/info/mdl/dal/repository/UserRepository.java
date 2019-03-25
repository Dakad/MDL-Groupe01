package be.unamur.info.mdl.dal.repository;


import be.unamur.info.mdl.dal.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends JpaRepository<UserEntity, Long> {

  boolean existsByUsername(String username);

  UserEntity findByUsername(String username);

  UserEntity findByEmail(String email);
}
