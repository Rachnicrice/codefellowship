package com.rachnicrice.codefellowship;

import com.rachnicrice.codefellowship.models.ApplicationUserRepository;
import com.rachnicrice.codefellowship.models.Post;
import com.rachnicrice.codefellowship.models.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;

@Controller
public class PostController {

    @Autowired
    ApplicationUserRepository repo;

    @Autowired
    PostRepository postRepo;

//    @GetMapping("/posts")
//    public String userPosts () {
//
//        return "posts";
//    }

    @GetMapping("addpost")
    public String addPost (Principal p, Model m) {
        m.addAttribute("username", p.getName());
        m.addAttribute("user", repo.findByUsername(p.getName()));
        return "add";
    }

    @GetMapping("/feed")
    public String viewFeed (Principal p, Model m) {
        m.addAttribute("username", p.getName());
        m.addAttribute("user", repo.findByUsername(p.getName()));
        return "feed";
    }

    @PostMapping("/addpost")
    public RedirectView submitNewPost (String body, String time, Principal p) {
        Post newPost = new Post(body, time, repo.findByUsername(p.getName()));
        postRepo.save(newPost);
        return new RedirectView("/myprofile");
    }
}
