package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.role.RoleService;
import ru.kata.spring.boot_security.demo.service.user.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String userPage(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.loadUserByEmail(userDetails.getUsername());


        model.addAttribute("user", user);

        return "/user";
    }

    @GetMapping("/show_users")
    public String showUsers(Model model) {

        List<User> users = userService.getAllUsers();

        model.addAttribute("users", users);

        return "users/out_users";
    }

    @GetMapping("/update")
    public String newUser(@ModelAttribute("user") User user) {

        return "users/info_users";
    }

    @PostMapping("/")
    public String addUser(@ModelAttribute("user") User user,
                          @RequestParam(value = "boolRole", required = false) boolean boolRole) {

        if (user.getId() == 0) {
            if (boolRole) {
                user.addRoleToUser(new Role("ROLE_ADMIN"));
            }

            userService.addUser(user);
        } else {

            User userWithRole = userService.getUser(user.getId());
            if(boolRole) {
                for (Role role : userWithRole.getRoles()) {
                    if (!role.getRole().equals("ROLE_ADMIN")) {
                        user.addRoleToUser(new Role("ROLE_ADMIN"));
                    }
                }
            } else {
                for (Role role : userWithRole.getRoles()) {
                    if (role.getRole().equals("ROLE_ADMIN")) {
                        role.setUser(null);
                        roleService.saveRole(role);
                        roleService.deleteRole(role.getId());
                    }
                }
            }

            userService.updateUser(user);
        }
        return "redirect:/admin/show_users";
    }

    @PatchMapping("/update")
    public String updateUser(@RequestParam(value = "id", required = false) Long id,
                             Model model) {
        boolean boolRole = false;
        User user = userService.getUser(id);

        for (Role role : user.getRoles()) {
            if (role.getRole().equals("ROLE_ADMIN")) {
                boolRole = true;
            }
        }
        model.addAttribute("user", user);
        model.addAttribute("boolRole", boolRole);
        return "users/info_users";
    }

    @DeleteMapping("/remove")
    public String removeUser(@RequestParam(value = "id", required = false) Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/show_users";
    }
}
