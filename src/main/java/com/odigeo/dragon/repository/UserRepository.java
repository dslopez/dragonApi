package com.odigeo.dragon.repository;

import org.springframework.data.repository.CrudRepository;

import com.odigeo.dragon.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

}
