package com.Instabasic.basiconly.Service;

import com.Instabasic.basiconly.Model.Model.AuthenticationToken;
import com.Instabasic.basiconly.Model.Model.Dto.SignInInput;
import com.Instabasic.basiconly.Model.Model.Dto.SignInOutput;
import com.Instabasic.basiconly.Model.Model.Dto.SignUpOutput;
import com.Instabasic.basiconly.Model.Model.User;
import com.Instabasic.basiconly.Repository.ITokenRepo;
import com.Instabasic.basiconly.Repository.IUserRepo;
import jakarta.validation.constraints.Pattern;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;

@Service
public class UserService {
    @Autowired
    IUserRepo userRepo;

    @Autowired
    ITokenRepo tokenRepo;

    @Autowired
    TokenService tokenService;

    public SignUpOutput signUp(User signUpDto) {
        User user = userRepo.findFirstByEmail(signUpDto.getEmail());

        if(user != null)
        {
            throw new IllegalStateException("user already exists sign in instead");
        }


        String encryptedPassword = null;

        try {
            encryptedPassword = encryptPassword(signUpDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
           e.printStackTrace();
        }
        signUpDto.setPassword(encryptedPassword);
        userRepo.save(signUpDto);

        return new SignUpOutput("user registered","account creation success");

    }

    private String encryptPassword(String userPassword) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");

        md5.update(userPassword.getBytes());
        byte[] digested = md5.digest();

        String hash = DatatypeConverter.printHexBinary(digested);

        return hash;

    }

    public SignInOutput signIn(SignInInput signInDto) {

        User user = userRepo.findFirstByEmail(signInDto.getEmail());

        if(user == null)
        {
            throw new IllegalStateException("Please check userid/password");
        }

        String encryptedPassword = null;

        try {
            encryptedPassword = encryptPassword(signInDto.getPassword());
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        }


        boolean isPasswordValid = encryptedPassword.equals(user.getPassword());

        if(!isPasswordValid)
        {
            throw new IllegalStateException("Please check userid/password");
        }

        AuthenticationToken token = new AuthenticationToken(user);

        tokenService.saveToken(token);


        return new SignInOutput("Login Successfull", token.getToken());


    }


    public void updateUser(User user , String token) {
        User originalUser = tokenRepo.findFirstByToken(token).getUser();


        if(!(user.getFirstName().isEmpty())){
            originalUser.setFirstName(user.getFirstName());
        }
        if((user.getLastName()!=null)){
            originalUser.setLastName(user.getLastName());
        }
        if((user.getPassword()!=null)){
            String encryptedPassword = null;

            try {
                encryptedPassword = encryptPassword(user.getPassword());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            originalUser.setPassword(encryptedPassword);
        }

        if((user.getPhoneNumber()!=null)){
            originalUser.setPhoneNumber(user.getPhoneNumber());
                 }

        if((user.getEmail()!=null)){
                originalUser.setEmail(user.getEmail());
        }

        userRepo.save(originalUser);
    }


}
