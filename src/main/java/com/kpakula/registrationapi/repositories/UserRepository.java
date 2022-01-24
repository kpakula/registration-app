package com.kpakula.registrationapi.repositories;

import com.kpakula.registrationapi.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    boolean existsUserByUsername(String username);
}
