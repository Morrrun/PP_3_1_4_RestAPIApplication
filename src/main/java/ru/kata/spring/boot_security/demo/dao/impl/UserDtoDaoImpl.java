package ru.kata.spring.boot_security.demo.dao.impl;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.dao.abstracts.dto.UserDtoDao;
import ru.kata.spring.boot_security.demo.dao.util.SingleResultUtil;
import ru.kata.spring.boot_security.demo.model.dto.UserDTO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class UserDtoDaoImpl implements UserDtoDao {
    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<UserDTO> getById(Long userId) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery(
                """
                        SELECT new ru.kata.spring.boot_security.demo.model.dto.UserDTO(
                        u.id,
                        u.firstName,
                        u.lastName,
                        u.email,
                        u.password,
                        rol
                        ),
                        (SELECT new ru.kata.spring.boot_security.demo.model.dto.RoleDTO(r.id, r.role) FROM r) as rol
                        FROM User u
                        LEFT JOIN u.roles r
                        WHERE u.id = :userId
                        """,
                UserDTO.class
        ).setParameter("userId", userId));

//        Optional<UserDTO> userDTO = SingleResultUtil.getSingleResultOrNull(entityManager.createQuery(
//                """
//                        SELECT NEW ru.kata.spring.boot_security.demo.model.dto.UserDTO(
//                            u.id,
//                            u.firstName,
//                            u.lastName,
//                            u.age,
//                            u.email,
//                            u.password
//                        )
//                        FROM User u
//                        WHERE u.id = :userId
//                        """, UserDTO.class
//        ).setParameter("userId", userId));
//
//        Set<RoleDTO> roleDTO = ResultListUtil.getSetResultOrNull(entityManager.createQuery(
//                """
//                        SELECT NEW ru.kata.spring.boot_security.demo.model.dto.RoleDTO(
//                            r.id,
//                            r.role
//                        )
//                        FROM User u
//                        LEFT JOIN u.roles r
//                        WHERE u.id = :userId
//                        """, RoleDTO.class
//        ).setParameter("userId", userId));
//
//        userDTO.get().setRoles(roleDTO);

//        return null;
    }
}
