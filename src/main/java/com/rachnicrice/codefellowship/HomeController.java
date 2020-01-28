package com.rachnicrice.codefellowship;

import com.rachnicrice.codefellowship.models.ApplicationUser;
import com.rachnicrice.codefellowship.models.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    ApplicationUserRepository repo;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/")
    public String getHome(Principal p, Model m){
        return "index";
    }

    @GetMapping("/login")
    public String getLogin () {
        return "login";
    }

    @GetMapping("/signup")
    public String getSignup () {
        return "signup";
    }

    @PostMapping("/signup")
    public RedirectView newUserSignup (String username, String password, String name) {
        ApplicationUser newUser = new ApplicationUser(username, encoder.encode(password), name);
        repo.save(newUser);
        return new RedirectView("/");
    }
}
