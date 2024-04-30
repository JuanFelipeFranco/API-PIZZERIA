package com.felipe.pizzeria.persistence.repository;

import com.felipe.pizzeria.persistence.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository <UserEntity, String> {

}
