package ru.kata.spring.boot_security.demo.service.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDAO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleDAO roleDAO;

    @Autowired
    public RoleService(RoleRepository roleRepository, RoleDAO roleDAO) {
        this.roleRepository = roleRepository;
        this.roleDAO = roleDAO;
    }

    @Transactional
    public void deleteRole(long id) {
        roleDAO.deleteRole(id);
    }

    @Transactional
    public void saveRole(Role role) {
        roleRepository.save(role);
    }
}
