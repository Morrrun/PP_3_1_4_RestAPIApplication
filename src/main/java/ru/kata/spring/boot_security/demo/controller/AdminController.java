package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.role.RoleService;
import ru.kata.spring.boot_security.demo.service.role.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.user.UserService;
import ru.kata.spring.boot_security.demo.service.user.UserServiceImpl;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(PasswordEncoder passwordEncoder, UserServiceImpl userService, RoleServiceImpl roleService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String userPage(@ModelAttribute("add_user")
                           @Valid
                           User addUser,
                           Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.loadUserByEmail(userDetails.getUsername());

        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", user);

        return "/admin";
    }

    @PostMapping("/")
    public String addUser(@ModelAttribute
                          @Valid User user,
                          @RequestParam(value = "role", required = false) String userRole) {

        //Добавляем нового пользователя
        if (user.getId() == 0) {
            if (userRole.equals("ADMIN")) {
                Role role = roleService.findByRole("ADMIN");
                role.addUserToRole(user);
            }
            userService.addUser(user);

            //Обновляем старого пользователя
        } else {
            User userWithRole = userService.getUser(user.getId());
            Role role = roleService.findByRole("ADMIN");

            //Производим проверку на наличие роли
            if (userRole.equals("ADMIN") && !userWithRole.getRoles().contains(role)) {
                role.addUserToRole(user);
            } else if (!userRole.equals("ADMIN")) {
                userWithRole.getRoles().remove(role);
            }
            user.setRoles(userWithRole.getRoles());

            //Производим проверку на наличие изменений в пароле
            if (!user.getPassword().isEmpty()) {
                if (!userWithRole.getPassword().equals(user.getPassword())) {
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                }
            } else {
                user.setPassword(userWithRole.getPassword());
            }

            userService.updateUser(user);
        }
        return "redirect:/admin/";
    }

    @DeleteMapping("/remove")
    public String removeUser(@RequestParam(value = "id", required = false) Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/";
    }
}
