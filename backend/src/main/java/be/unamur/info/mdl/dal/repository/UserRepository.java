package be.unamur.info.mdl.dal.repository;


import org.springframework.data.repository.CrudRepository;

import be.unamur.info.mdl.dal.entity.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Long> {

}
