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
    public String addPost () {
        return "add";
    }

    @PostMapping("/addpost")
    public RedirectView submitNewPost (String time, String body, Principal p) {
        Post newPost = new Post(time, body, repo.findByUsername(p.getName()));
        postRepo.save(newPost);
        return new RedirectView("/");
    }
}
