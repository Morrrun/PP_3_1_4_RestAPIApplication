package ru.kata.spring.boot_security.demo.service.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.service.role.RoleService;


@Service
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {
   private final RoleService roleService;
   private final UserRepository userRepository;
   @Autowired
   public UserDetailsServiceImpl(RoleService roleService, UserRepository userRepository) {
      this.roleService = roleService;
      this.userRepository = userRepository;
   }

   @Transactional
   @Override
   public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      User user = userRepository.findByEmail(email);

      if(user == null) {
         throw new UsernameNotFoundException("Email not found!");
      }

      for (Role role : user.getRoles()) {
         roleService.findByRole(role.getRole());
      }

      return user;
   }

}
