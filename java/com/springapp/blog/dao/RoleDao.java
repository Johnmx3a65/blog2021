package com.springapp.blog.dao;

import com.springapp.blog.entity.Role;
import com.springapp.blog.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleDao {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleDao(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAll(){
        return roleRepository.findAll();
    }
}
