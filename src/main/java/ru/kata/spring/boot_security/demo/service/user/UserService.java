package ru.kata.spring.boot_security.demo.service.user;

//import web.dao.UserDao;


import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService{
   private final UserRepository userRepository;
   private final RoleRepository roleRepository;
   private final PasswordEncoder passwordEncoder;
   @Autowired
   public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
      this.userRepository = userRepository;
      this.roleRepository = roleRepository;
      this.passwordEncoder = passwordEncoder;
   }



   public User loadUserByEmail(String email) throws UsernameNotFoundException {
      User user = userRepository.findByEmail(email);

      return user;
   }

   public List<User> getAllUsers() {
      return userRepository.findAll();
   }

   public User getUser(long id) {
      Optional<User> optionalUser = userRepository.findById(id);

      User userWithRole = optionalUser.get();

      Hibernate.initialize(userWithRole.getRoles());


      return optionalUser.orElse(null);
   }

   @Transactional
   public void addUser(User user) {

      user.setPassword(passwordEncoder.encode(user.getPassword()));
      user.addRoleToUser(new Role("ROLE_USER"));

      userRepository.save(user);
   }

   @Transactional
   public void updateUser(User user) {
      userRepository.save(user);
   }
   @Transactional
   public void deleteUser(long id) {
      userRepository.deleteById(id);
   }

}
