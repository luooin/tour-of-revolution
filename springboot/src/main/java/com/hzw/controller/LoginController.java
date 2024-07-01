package com.hzw.controller;

import com.hzw.comon.ResponseResult;
import com.hzw.entity.Admin;
import com.hzw.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class LoginController{

    @Autowired
    private LoginService loginSercice;
    @PostMapping("/admin/login")
    public ResponseResult login(@RequestBody Admin admin){
       return loginSercice.login(admin);
    }

    @GetMapping("/loginOut")
    public ResponseResult logout(){
        return loginSercice.logout();
    }

    

}
