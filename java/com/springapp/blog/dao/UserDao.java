package com.springapp.blog.dao;

import com.springapp.blog.entity.Role;
import com.springapp.blog.entity.User;
import com.springapp.blog.models.user.UserRegisterModel;
import com.springapp.blog.repository.RoleRepository;
import com.springapp.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserDao {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserDao(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public void register(UserRegisterModel userRegisterModel){
        User user = new User(userRegisterModel.getEmail(), userRegisterModel.getFullname(), new BCryptPasswordEncoder().encode(userRegisterModel.getPassword()));
        Role role = roleRepository.findByName("ROLE_USER");
        user.addRole(role);

        userRepository.saveAndFlush(user);
    }

    public void logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null){
            new SecurityContextLogoutHandler().logout(request,response,auth);
        }
    }

    public boolean emailIsExist(String email){
        return userRepository.findByEmail(email) != null;
    }
}
