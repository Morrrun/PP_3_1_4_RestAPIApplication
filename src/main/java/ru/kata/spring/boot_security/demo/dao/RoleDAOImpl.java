package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class RoleDAOImpl implements RoleDAO {
    private EntityManager entityManager;

   @PersistenceContext
   private void setEntityManager(EntityManager entityManager) {
      this.entityManager = entityManager;
   }

    @Override
    public void deleteRole(long id) {
        Query query = entityManager.createQuery("DELETE FROM Role WHERE id = :id");
        query.setParameter("id", id).executeUpdate();
    }
}
