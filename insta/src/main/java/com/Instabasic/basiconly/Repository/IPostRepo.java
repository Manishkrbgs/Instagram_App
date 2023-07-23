package com.Instabasic.basiconly.Repository;


import com.Instabasic.basiconly.Model.Model.Post;
import com.Instabasic.basiconly.Model.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPostRepo extends JpaRepository<Post, Integer> {


    List<Post> findByUser(User user);
}