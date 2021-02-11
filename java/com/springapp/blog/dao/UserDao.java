package com.springapp.blog.dao;

import com.springapp.blog.entity.User;
import com.springapp.blog.models.user.UserRegisterModel;
import com.springapp.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserDao {

    private final UserRepository userRepository;

    @Autowired
    public UserDao(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(UserRegisterModel userRegisterModel){
        User user = new User(userRegisterModel.getEmail(), userRegisterModel.getFullname(), new BCryptPasswordEncoder().encode(userRegisterModel.getPassword()));

        userRepository.saveAndFlush(user);
    }

    public boolean emailIsExist(String email){
        return userRepository.findByEmail(email) != null;
    }
}
