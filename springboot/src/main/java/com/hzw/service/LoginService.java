package com.hzw.service;

import com.hzw.comon.ResponseResult;
import com.hzw.entity.Admin;

import javax.servlet.http.HttpSession;

public interface LoginService {

    ResponseResult login(Admin admin);

    ResponseResult logout();

}
