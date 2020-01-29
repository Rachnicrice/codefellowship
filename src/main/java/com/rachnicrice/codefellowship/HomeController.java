package com.rachnicrice.codefellowship;

import com.rachnicrice.codefellowship.models.ApplicationUser;
import com.rachnicrice.codefellowship.models.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;

@Controller
public class HomeController {

    @Autowired
    ApplicationUserRepository repo;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/")
    public String getHome(Principal p, Model m){
        m.addAttribute("username", p.getName());
        m.addAttribute("user", repo.findByUsername(p.getName()));
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
    public RedirectView newUserSignup (String username, String password, String firstName) {
        ApplicationUser newUser = new ApplicationUser(username, encoder.encode(password), firstName);
        repo.save(newUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/");
    }
}
