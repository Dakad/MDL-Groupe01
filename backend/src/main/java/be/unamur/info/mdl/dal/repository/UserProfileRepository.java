package be.unamur.info.mdl.dal.repository;

import be.unamur.info.mdl.dal.entity.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfileEntity, Long> {
}
