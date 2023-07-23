package com.Instabasic.basiconly.Repository;


import com.Instabasic.basiconly.Model.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepo extends JpaRepository<User, Long> {


    User findFirstByEmail(String email);
}