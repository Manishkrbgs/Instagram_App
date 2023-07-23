package com.Instabasic.basiconly.Controller;

import com.Instabasic.basiconly.Model.Model.Post;
import com.Instabasic.basiconly.Model.Model.User;
import com.Instabasic.basiconly.Service.PostService;
import com.Instabasic.basiconly.Service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("post")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    TokenService authService;

    @PostMapping()
    public ResponseEntity<String> addPost(@Valid @RequestParam String email , @RequestParam String token , @RequestBody Post post){
        HttpStatus status;
        String msg = "";
        if(authService.authenticate(email,token))
        {
            User user =  authService.findUserByToken(token);
            post.setUser(user);
            postService.addPost(post);
            msg = " Post posted successfully";
            status = HttpStatus.OK;
        }
        else
        {
            msg = "Invalid user";
            status = HttpStatus.FORBIDDEN;
        }

        return new ResponseEntity<String>(msg , status);
    }

    @GetMapping()
    public ResponseEntity<List<Post>> getAllPosts(@RequestParam String email , @RequestParam String token){
        HttpStatus status;
        List<Post> postList = null;
        if(authService.authenticate(email,token))
        {
            postList = postService.getAllPosts(token);
            status = HttpStatus.OK;
        }
        else
        {

            status = HttpStatus.FORBIDDEN;
        }

        return new ResponseEntity<List<Post>>(postList , status);
    }
}