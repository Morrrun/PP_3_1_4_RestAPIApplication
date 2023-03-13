package ru.kata.spring.boot_security.demo.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.entity.User;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select a from User a left join fetch a.roles where a.email = :email")
    Optional<User> findByEmail(String email);
    @Query("select a from User a left join fetch a.roles")
    List<User> findAll();
    @Query("select a from User a left join fetch a.roles where a.id = :id")
    Optional<User> findById(long id);


}
