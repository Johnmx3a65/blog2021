package com.springapp.blog.models.user;

import javax.validation.constraints.NotEmpty;

public class UserRegisterModel extends UserLoginModel{

    @NotEmpty(message = "Name should not be empty")
    private String fullname;

    @NotEmpty(message = "Confirm password should not be empty")
    private String confirmPassword;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
