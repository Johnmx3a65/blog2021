package com.springapp.blog.dao;

import com.springapp.blog.entity.Role;
import com.springapp.blog.entity.User;
import com.springapp.blog.models.user.UserEditModel;
import com.springapp.blog.models.user.UserRegisterModel;
import com.springapp.blog.repository.ArticleRepository;
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
import java.util.List;

@Component
public class UserDao {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ArticleRepository articleRepository;


    @Autowired
    public UserDao(UserRepository userRepository, RoleRepository roleRepository, ArticleRepository articleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.articleRepository = articleRepository;
    }

    public List<User> list(){
        return userRepository.findAll();
    }

    public User getOne(Integer id){
        return userRepository.findById(id).orElse(null);
    }

    public void edit(Integer id, UserEditModel user){
        User dbUser = userRepository.getOne(id);

        dbUser.setEmail(user.getEmail());
        dbUser.setFullName(user.getFullName());
        if (!user.getPassword().isEmpty()){
            dbUser.setPassword(user.getPassword());
        }

        userRepository.saveAndFlush(dbUser);
    }

    public void delete(Integer id){
        User user = userRepository.getOne(id);

        articleRepository.deleteAllByAuthor(user);
        userRepository.delete(user);
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

    public boolean isHimEmail(String email, Integer id){
        return userRepository.getOne(id).getEmail().equals(email);
    }

    public boolean emailIsExist(String email){
        return userRepository.findByEmail(email) != null;
    }
}
