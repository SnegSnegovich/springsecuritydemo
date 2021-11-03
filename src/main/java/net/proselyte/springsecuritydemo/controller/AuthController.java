package net.proselyte.springsecuritydemo.controller;

import net.proselyte.springsecuritydemo.model.Developer;
import net.proselyte.springsecuritydemo.model.Role;
import net.proselyte.springsecuritydemo.model.Status;
import net.proselyte.springsecuritydemo.model.User;
import net.proselyte.springsecuritydemo.repository.UserRepository;
import net.proselyte.springsecuritydemo.security.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/users")
    public List<User> getAll() {
        return userRepo.findAll();
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/success")
    public String getSuccessPage() {
        return "success";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
         User userFromDb =  userRepo.findByEmail(user.getEmail());
        if (userFromDb != null) {
            model.put("message", "User is exist!");
            return "registration";

        }
        user.setStatus(Status.ACTIVE);
        user.setRole(Role.USER);
        userRepo.save(user);

        return "login";
    }
}
