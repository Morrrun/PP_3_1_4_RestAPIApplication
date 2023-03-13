package ru.kata.spring.boot_security.demo.service.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.entity.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.Optional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
   private final UserRepository userRepository;
   @Autowired
   public UserDetailsServiceImpl(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   @Override
   public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      Optional<User> user = userRepository.findByEmail(email);

      return user.orElseThrow(() -> {throw new UsernameNotFoundException("Пользователь с таким email не найден!");});
   }

}
