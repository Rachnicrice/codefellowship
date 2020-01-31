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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    ApplicationUserRepository repo;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/")
    public String getHome(Principal p, Model m){
       if (p != null) {
           m.addAttribute("username", p.getName());
           m.addAttribute("user", repo.findByUsername(p.getName()));
        }
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

    @GetMapping("/myprofile")
    public String userProfile (Principal p, Model m) {
        m.addAttribute("username", p.getName());
        m.addAttribute("user", repo.findByUsername(p.getName()));
        return "profile";
    }

    @GetMapping("/users")
    public String seeAllUsers (Principal p, Model m) {
        List<ApplicationUser> users = repo.findAll();
        m.addAttribute("username", p.getName());
        m.addAttribute("user", repo.findByUsername(p.getName()));
        m.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/users/{username}")
    public String viewUserProfile (@PathVariable String username, Model m, Principal p) {
        ApplicationUser currentUser = repo.findByUsername(p.getName());
        ApplicationUser viewingProfile = repo.findByUsername(username);
        m.addAttribute("username", username);
        m.addAttribute("user", viewingProfile);
        if (currentUser != viewingProfile) {
            m.addAttribute("follow", !currentUser.getUsersIAmFollowing().contains(viewingProfile));
        }
        return "profile";
    }

    @PostMapping("/signup")
    public RedirectView newUserSignup (String username, String password, String firstName, String lastName, String dob) {
        if (repo.findByUsername(username) == null) {
            ApplicationUser newUser = new ApplicationUser(username, encoder.encode(password), firstName, lastName, dob);
            repo.save(newUser);

            Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new RedirectView("/myprofile");
        } else {
            return new RedirectView("/signup?taken=true");
        }
    }

    @PostMapping("/users/{id}/follow")
    public RedirectView followNewUser (@PathVariable Long id, Principal p) {
        ApplicationUser currentUser = repo.findByUsername(p.getName());
        ApplicationUser userToBeFollowed = repo.getOne(id);

        currentUser.getUsersIAmFollowing().add(userToBeFollowed);
        repo.save(currentUser);

        return new RedirectView("/feed");
    }
}
