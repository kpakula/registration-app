package com.kpakula.registrationapi.repositories;

import com.kpakula.registrationapi.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByUsername(String username);
    boolean existsUserByUsername(String username);
}
