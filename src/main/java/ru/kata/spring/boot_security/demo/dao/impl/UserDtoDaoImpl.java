package ru.kata.spring.boot_security.demo.dao.impl;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.dao.dto.UserDtoDao;
import ru.kata.spring.boot_security.demo.dao.util.ResultUtil;
import ru.kata.spring.boot_security.demo.model.dto.UserDTO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;
import java.util.Set;

@Repository
public class UserDtoDaoImpl implements UserDtoDao {
    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Set<UserDTO> getAll() {
        return ResultUtil.getSetResultOrNull(entityManager.createQuery(
                """
                        SELECT new ru.kata.spring.boot_security.demo.model.dto.UserDTO(u.id, u.firstName, u.lastName, u.age, u.email, u.password,
                          (SELECT new ru.kata.spring.boot_security.demo.model.dto.RoleDTO(r.id, r.role) FROM Role r WHERE r.id IN (
                            SELECT ur.id FROM userR ur
                          ))
                        )
                        FROM User u
                        LEFT JOIN u.roles userR
                        """, UserDTO.class));
    }

    @Override
    public Optional<UserDTO> getById(Long userId) {
        return ResultUtil.getSingleResultOrNull(entityManager.createQuery(
                """
                        SELECT new ru.kata.spring.boot_security.demo.model.dto.UserDTO(
                            u.id,
                            u.firstName,
                            u.lastName,
                            u.age,
                            u.email,
                            u.password,
                            u.roles
                        )
                        FROM User u
                        LEFT JOIN u.roles
                        WHERE u.id = :userId
                         """, UserDTO.class
        ).setParameter("userId", userId));
    }
}
