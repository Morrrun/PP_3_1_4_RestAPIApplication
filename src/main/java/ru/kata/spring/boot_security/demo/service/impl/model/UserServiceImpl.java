package ru.kata.spring.boot_security.demo.service.impl.model;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.entity.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.service.abstracts.model.UserService;
import ru.kata.spring.boot_security.demo.util.exception.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User loadUserByEmail(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElse(null);
    }

    public Set<User> getAllUsers() {
        return new HashSet<>(userRepository.findAll());
    }

    public User getUser(long id) throws UsernameNotFoundException{
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> {throw new UserNotFoundException("Пользователь не найден");});
    }

    @Transactional
    public void addUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
    }

    @Transactional
    public void updateUser(User user) {

        //Получаю пользователя для дальнейшего сравнения
        User checkUser = getUser(user.getId());

        //Производим проверку на наличие изменений в пароле
        if (!user.getPassword().isEmpty()) {
            if (!checkUser.getPassword().equals(user.getPassword())) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        } else {
            user.setPassword(checkUser.getPassword());
        }

        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

}
