package ru.kata.spring.boot_security.demo.service.user;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.DTO.RoleDTO;
import ru.kata.spring.boot_security.demo.DTO.UserDTO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.service.role.RoleService;
import ru.kata.spring.boot_security.demo.util.Exception.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, RoleService roleService,
                           UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.modelMapper = modelMapper;
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User loadUserByEmail(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    public List<UserDTO> getAllUsersDTO() {
        return userRepository.findAll().stream()
                .map(this::converterToUserDTO)
                .toList();
    }

    public UserDTO getUserDTO(long id) {
        Optional<User> user = userRepository.findById(id);
        return converterToUserDTO(user.orElseThrow(UserNotFoundException::new));
    }

    @Transactional
    public void addUserDTO(UserDTO userDTO) {

        User user = converterToUser(userDTO);

        Set<Role> roles = new HashSet<>();
        if(userDTO.getRoles() != null) {
            for (RoleDTO role : userDTO.getRoles()) {
                roles.add(roleService.findByRole(role.getRole()));
            }
        }
        user.setRoles(roles);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setCreatedAt(LocalDateTime.now());

        Role role = roleService.findByRole("USER");
        if (role == null) {
            role = new Role("USER");
            role.addUserToRole(user);
            roleService.saveRole(role);
        } else {
            role.addUserToRole(user);
        }
        userRepository.save(user);
    }

    @Transactional
    public void updateUserDTO(UserDTO userDTO, int id) {

        //Получаю пользователя для дальнейшего сравнения
        UserDTO checkUser = getUserDTO(id);

        //Производим проверку на наличие изменений в пароле
        if (!userDTO.getPassword().isEmpty()) {
            if (!checkUser.getPassword().equals(userDTO.getPassword())) {
                userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            }
        } else {
            userDTO.setPassword(checkUser.getPassword());
        }

        User user = converterToUser(userDTO);
        System.out.println(userDTO);
        user.setId(id);
        user.setUpdatedAt(LocalDateTime.now());

        Set<Role> roles = new HashSet<>();
        for(RoleDTO role : userDTO.getRoles()) {
            roles.add(roleService.findByRole(role.getRole()));
        }

        System.out.println(user);

        user.setRoles(roles);

        System.out.println(user.getRoles());

        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }


    //Метод для преобразования в JSONObjectDTO
    private UserDTO converterToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    //Метод для преобразования в User
    private User converterToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

}
